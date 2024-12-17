import React, {useState, useEffect, useCallback, useRef} from "react";
import SvgHighlighter from "./SvgHighlighter";
import './SimulationPage.css'
import Mips from "./Mips";

const SimulationPage = () => {

    const svgRef = useRef(null);

    const [instructions, setInstructions] = useState([]);
    const [registerFile, setRegisterFile] = useState([]);
    const [aluData, setAluData] = useState("");
    const [memoryData, setMemoryData] = useState([]);
    const [isRunning, setIsRunning] = useState(false);
    // Active instructions from each stage
    const [activeStages, setActiveStages] = useState({
        activeIF: null,
        activeID: null,
        activeEX: null,
        activeMEM: null,
        activeWB: null,
    });


    //state for the highlighted instructions
    const [highlightIF, setHighlightIF] = useState([]);
    const [highlightID, setHighlightID] = useState([]);
    const [highlightEX, setHighlightEX] = useState([]);
    const [highlightMEM, setHighlightMEM] = useState([]);
    const [highlightWB, setHighlightWB] = useState([]);

    const [notHighlight, setNotHighlight] = useState([]);
    // Interval ID for polling
    const [pollingInterval, setPollingInterval] = useState(null);

    const fetchSimulationData = async () => {
        try {
            const [instructionsResponse, registerResponse, aluResponse, memoryResponse] = await Promise.all([
                fetch("/api/arrayInstructions").then(res => res.json()),
                fetch("/api/registerFile").then(res => res.json()),
                fetch("/api/aluData").then(res => res.text()),
                fetch("/api/memoryData").then(res => res.json()),
            ]);
            const activeStagesResponse = await fetch("/api/activeStages").then(res => res.json());
            setActiveStages(activeStagesResponse);

            const [
                highlightIFResponse,
                highlightIDResponse,
                highlightEXResponse,
                highlightMEMResponse,
                highlightWBResponse,
                notHighlightResponse,
            ] = await Promise.all([
                fetch("/api/highlightInstructionFetch").then(res => res.json()),
                fetch("/api/highlightInstructionDecode").then(res => res.json()),
                fetch("/api/highlightInstructionExecute").then(res => res.json()),
                fetch("/api/highlightMemoryStage").then(res => res.json()),
                fetch("/api/highlightWriteBackStage").then(res => res.json()),
                fetch("api/notHighlight").then(res => res.json()),
            ]);

            setHighlightIF(highlightIFResponse || []);
            setHighlightID(highlightIDResponse || []);
            setHighlightEX(highlightEXResponse || []);
            setHighlightMEM(highlightMEMResponse || []);
            setHighlightWB(highlightWBResponse || []);
            setNotHighlight(notHighlightResponse || []);


            setInstructions(Array.isArray(instructionsResponse) ? instructionsResponse : []);
            setRegisterFile(Object.entries(registerResponse).map(([key, value]) => `${key}: ${value} `));
            setAluData(aluResponse || "No ALU data available.");
            setMemoryData(Object.entries(memoryResponse));
        } catch (error) {
            console.error("Error fetching simulation data:", error);
        }
    };

    const startPolling = () => {
        if (!pollingInterval) {
            const intervalId = setInterval(fetchSimulationData, 1000); // Fetch every second
            setPollingInterval(intervalId);
        }
    };

    const stopPolling = useCallback(() => {
        if (pollingInterval) {
            clearInterval(pollingInterval);
            setPollingInterval(null);
        }
    }, [pollingInterval]);

    const startSimulation = () => {
        fetch("/api/start", { method: "POST" })
            .then(() => {
                setIsRunning(true);
                startPolling(); // Start updating data
            })
            .catch(error => console.error("Error starting the simulation", error));
    };

    const stopSimulation = async () => {
        try {

            if (svgRef.current) {
                const svgElements = svgRef.current.querySelectorAll('[id]');
                svgElements.forEach((element) => {
                    element.style.stroke = "black";
                });
            }

            const response = await fetch('/api/stop', { method: 'POST' });
            if (response.ok) {
                setIsRunning(false);
                stopPolling(); // Stop updating data
                console.log("Simulation stopped successfully.");
            } else {
                console.error("Failed to stop simulation.");
            }
        } catch (error) {
            console.error("Error stopping simulation:", error);
        }
    };

    const nextClockCycle = async () => {
        try {
            const response = await fetch('/api/nextCycle', { method: 'POST' });
            if (response.ok) {
                await fetchSimulationData();
                console.log("Next cycle executed.");
            } else {
                console.error("Failed to execute next cycle.");
            }
        } catch (error) {
            console.error("Error executing next cycle:", error);
        }
    };

    useEffect(() => {
        // Clean up polling on component unmount
        return () => stopPolling();
    }, [stopPolling]);

    return (
        <div className="simulation-page">
            <div className="instructions-section">
                <h2>Final Instructions</h2>
                <ul>
                    {instructions.map((instruction, index) => {
                        let highlightClass = "";
                        if (activeStages.activeIF === index) highlightClass = "highlightIF";
                        if (activeStages.activeID === index) highlightClass = "highlightID";
                        if (activeStages.activeEX === index) highlightClass = "highlightEX";
                        if (activeStages.activeMEM === index) highlightClass = "highlightMEM";
                        if (activeStages.activeWB === index) highlightClass = "highlightWB";
                        return (
                            <li key={index} className={highlightClass}>
                                {instruction}
                            </li>
                        );
                    })}
                </ul>
            </div>
            <div className="data-section">
                <div className="register-file">
                    <h2>Register File</h2>
                    <div className="register-grid">
                        {registerFile.map((register, index) => (
                            <span key={index} className="register-item">{register}</span>
                        ))}
                    </div>
                </div>
                <div className="alu-data">
                    <h2>ALU Data</h2>
                    {aluData ? (
                        <pre className="alu-data-details">{aluData}</pre>
                    ) : (
                        <p>No ALU data available.</p>
                    )}
                </div>
                <div className="memory-data">
                    <h2>Memory Data</h2>
                    <ul>
                        {memoryData.map(([address, value], index) => (
                            <li key={index}>{`Address: ${address}, Value: ${value}`}</li>
                        ))}
                    </ul>
                </div>
            </div>
            <div className="mips-section">
                <div id="mips-diagram-container">
                    <div id="mips-diagram" ref={svgRef}>
                        <Mips/> {/* Render the React SVG component */}
                    </div>
                    <SvgHighlighter
                        svgRef={svgRef} // Pass the ref to the highlighter
                        activeComponents={[
                            ...highlightIF.map(component => ({id: component, color: "purple"})),
                            ...highlightID.map(component => ({id: component, color: "orange"})),
                            ...highlightEX.map(component => ({id: component, color: "green"})),
                            ...highlightMEM.map(component => ({id: component, color: "blue"})),
                            ...highlightWB.map(component => ({id: component, color: "red"})),
                            ...notHighlight.map(component => ({id: component, color: "black"})),
                        ]}
                    />
                </div>
                <div className="buttons">
                    <button className="btn" onClick={startSimulation} disabled={isRunning}>Start</button>
                    <button className="btn" onClick={nextClockCycle} disabled={!isRunning}>Next</button>
                    <button className="btn" onClick={stopSimulation} disabled={!isRunning}>Stop</button>
                </div>
            </div>
        </div>
    );
};

export default SimulationPage;