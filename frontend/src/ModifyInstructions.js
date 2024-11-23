import React, { useEffect, useState } from "react";
import './ModifyInstructions.css';

const ModifyInstructions = () => {
    const [currentInstructions, setCurrentInstructions] = useState('');
    const [syntaxList, setSyntaxList] = useState('');
    const [newInstructions, setNewInstructions] = useState('');

    useEffect(() => {
        fetch("/api/instructions")
            .then(response => response.text())
            .then(data => setCurrentInstructions(data))
            .catch(error => console.error("Error fetching instructions:", error));

        fetch("/api/syntax")
            .then(response => response.text())
            .then(data => setSyntaxList(data))
            .catch(error => console.error("Error fetching syntax list:", error));
    }, []);

    const handleInstructionsChange = (event) => {
        setNewInstructions(event.target.value);
    };

    const handleSave = () => {
        // Logic to save new instructions
        console.log("New Instructions:", newInstructions);
    };

    return (
        <div className="modify-instructions">
            <div className="instructions-section">
                <h2>Current Instructions</h2>
                <textarea
                    value={newInstructions || currentInstructions}
                    onChange={handleInstructionsChange}
                    rows="20"
                    cols="50"
                />
                <button onClick={handleSave}>Save Instructions</button>
            </div>
            <div className="syntax-section">
                <h2>Instruction Syntax</h2>
                <text className="syntaxText">Below is a list of all available
                    instructions. The Registers R1 R2 R3 are only examples, they can take
                    values from 0 to 31.
                    For instructions where an immediate value is expected it has to be within the
                    range of an integer, the same goes for the shift amount. The jump address should be
                    the instruction number.</text>
                <pre className="pre2">{syntaxList}</pre>
            </div>
        </div>
    );
};

export default ModifyInstructions;