import React, { useEffect, useState } from "react";
import './InstructionsPage.css';

const InstructionsPage = () => {
    const [instructions, setInstructions] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        fetch("/api/instructions")
            .then(response => response.text())
            .then(data => setInstructions(data))
            .catch(error => {
                console.error("Error fetching instructions:", error);
                setError("Failed to load instructions");
            });
    }, []);

    return (
        <div className="instructions-page">
            <h1 className="h1">Current instructions in Instruction Memory</h1>
            {error ? <p>{error}</p> : <pre className="pre1">{instructions}</pre>}
        </div>
    );
};

export default InstructionsPage;