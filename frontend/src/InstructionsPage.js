import React, {useEffect, useState} from "react";
import styles from './InstructionsPage.css'

const InstructionsPage = ({Children}) =>{
    const[instructions, setInstructions] = useState('');
    const [error, setError]=useState('');

    useEffect(() => {
        // Fetch the instructions from the Spring Boot backend
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
            <h1>Instructions</h1>
            <pre>{instructions}</pre>
        </div>
    );
};

export default InstructionsPage;