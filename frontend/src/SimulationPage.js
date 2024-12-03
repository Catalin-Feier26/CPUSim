import React, { useState, useEffect, useCallback } from "react";
import MIPS from "./MIPS.svg";
import './SimulationPage.css'

const SimulationPage = () => {
    const [instructions, setInstructions] = useState([]);
    const [registerFile, setRegisterFile] = useState([]);
    const [aluData, setAluData] = useState("");
    const [memoryData, setMemoryData] = useState([]);
    const [isRunning, setIsRunning] = useState(false);
    const [activeInstructionIndexes, setActiveInstructionIndexes] = useState([]); // Updated state for active instruction indexes

    // Interval ID for polling
    const [pollingInterval, setPollingInterval] = useState(null);

    const fetchSimulationData = async () => {
        try {
            const [instructionsResponse, registerResponse, aluResponse, memoryResponse, activeInstructionIndexesResponse] = await Promise.all([
                fetch("/api/arrayInstructions").then(res => res.json()),
                fetch("/api/registerFile").then(res => res.json()),
                fetch("/api/aluData").then(res => res.text()),
                fetch("/api/memoryData").then(res => res.json()),
                fetch("/api/currentInstructionIndex").then(res => res.json()), // Fetch the active instructions list
            ]);

            setInstructions(Array.isArray(instructionsResponse) ? instructionsResponse : []);
            setRegisterFile(Object.entries(registerResponse).map(([key, value]) => `${key}: ${value} `));
            setAluData(aluResponse || "No ALU data available.");
            setMemoryData(Object.entries(memoryResponse));
            setActiveInstructionIndexes(activeInstructionIndexesResponse); // Set the active instruction indexes
        } catch (error) {
            console.error("Error fetching simulation data:", error);
        }
    };

    const startPolling = () => {
        if (!pollingInterval) {
            const intervalId = setInterval(fetchSimulationData, 500); // Fetch every second
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
                    {instructions.map((instruction, index) => (
                        <li key={index} className={activeInstructionIndexes.includes(index) ? "highlighted" : ""}>
                            {instruction}
                        </li>
                    ))}
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
                <img src={MIPS} className="App-logo" alt="MIPS Diagram"/>
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