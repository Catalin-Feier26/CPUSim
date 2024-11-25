import React, { useState, useEffect } from "react";
import MIPS from "./MIPS.svg";
import './SimulationPage.css'

const SimulationPage = () => {
    const [instructions, setInstructions] = useState([]);
    const [registerFile, setRegisterFile] = useState([]);
    const [aluData, setAluData] = useState("");
    const [memoryData, setMemoryData] = useState([]);

    useEffect(() => {
        fetch("/api/arrayInstructions")
            .then(response => response.json())
            .then(data => setInstructions(Array.isArray(data) ? data : []));

        fetch("/api/registerFile")
            .then(response => response.json())
            .then(data => {
                const formattedData = Object.entries(data).map(([key, value]) => `${key}: ${value} `);
                setRegisterFile(formattedData.length ? formattedData : [""]);
            });

        fetch("/api/aluData")
            .then((response) => response.text()) // Use .text() instead of .json() since the backend sends a plain string
            .then((data) => setAluData(data || "No ALU data available."));

        fetch("/api/memoryData")
            .then(response => response.json())
            .then(data => setMemoryData(Object.entries(data)))
            .catch(error => console.error("Error fetching memory data:", error));

    }, []);

    return (
        <div className="simulation-page">
            <div className="instructions-section">
                <h2>Final Instructions</h2>
                <ul>
                    {instructions.map((instruction, index) => (
                        <li key={index}>{instruction}</li>
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
                        {memoryData.map((data, index) => (
                            <li key={index}>{data}</li>
                        ))}
                    </ul>
                </div>
            </div>
            <div className="mips-section">
                <img src={MIPS} className="App-logo" alt="MIPS Diagram"/>
                <div className="buttons">
                    <button  className="btn"  onClick={() => console.log("Start clicked")}>Start</button>
                    <button  className="btn">Next</button>
                    <button  className="btn">Stop</button>
                </div>
            </div>
        </div>
    );
};

export default SimulationPage;