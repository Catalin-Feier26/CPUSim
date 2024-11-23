// src/HowToUsePage.js
import React from "react";
import './HowToUsePage.css'; // Optional: If you want custom styling

const HowToUsePage = () => {
    return (
        <div className="how-to-use">
            <h1>MIPS Pipeline Simulator tutorial</h1>
            <ol>
                <li><strong>Home Page:</strong> Access the main page of the application.</li>
                <li><strong>See Instructions:</strong> View a list of the current instructions in the Instruction Memory in MIPS</li>
                <li><strong>Modify Instructions:</strong> Modify the list of instructions from the Instruction Memory in the MIPS simulator</li>
                <li><strong>Select Clock Type:</strong> Select the type of clock to use during the simulation. Automatic on 1 second or manual by pressing the right arrow key</li>
                <li><strong>Check Hazards:</strong> Check if the current instructions in the Instruction Memory have hazards and how they are resolved.</li>
                <li><strong>Start Simulation:</strong> Begin the simulation using the selected instructions and clock type.</li>
            </ol>
        </div>
    );
};

export default HowToUsePage;
