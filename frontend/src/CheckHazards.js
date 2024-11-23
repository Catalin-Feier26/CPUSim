import React, { useEffect, useState } from "react";
import './CheckHazards.css';

const CheckHazards = () => {
    const [currentInstructions, setCurrentInstructions] = useState('');
    const [hazardLogs, setHazardLogs] = useState('');
    const [updatedInstructions, setUpdatedInstructions] = useState('');

    useEffect(() => {
        fetch("/api/instructions")
            .then(response => response.text())
            .then(data => setCurrentInstructions(data))
            .catch(error => console.error("Error fetching instructions:", error));
    }, []);

    const handleSolveHazards = () => {
        fetch("/api/hazardLogs")
            .then(response => response.text())
            .then(data => setHazardLogs(data))
            .catch(error => console.error("Error fetching hazard logs:", error));

        fetch("/api/updatedInstructions")
            .then(response => response.text())
            .then(data => setUpdatedInstructions(data))
            .catch(error => console.error("Error fetching updated instructions:", error));
    };

    const handleSave = () => {
        fetch("/api/updateInstructions", {
            method: 'POST',
            headers: {
                'Content-Type': 'text/plain',
            },
            body: updatedInstructions,
        })
            .then(response => response.text())
            .then(data => {
                console.log("Success:", data);
            })
            .catch(error => console.error("Error saving updated instructions:", error));
    };

    return (
        <div className="check-hazards">
            <div className="instructions-section2">
                <h2>Current Instructions</h2>
                <pre>{currentInstructions}</pre>
            </div>
            <div className="hazard-logs-section">
                <h2>Hazard Logs</h2>
                <pre>{hazardLogs}</pre>
                <button className="btn" onClick={handleSolveHazards}>Solve Hazards</button>
            </div>
            <div className="updated-instructions-section">
                <h2>Updated Instructions</h2>
                <pre>{updatedInstructions}</pre>
                <button className="btn" onClick={handleSave}>Save Instructions</button>
            </div>
        </div>
    );
};

export default CheckHazards;