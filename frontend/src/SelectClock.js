import React, { useState, useEffect } from 'react';
import './SelectClock.css';

const SelectClock = () => {
    const [clockType, setClockType] = useState('AUTOMATIC');

    useEffect(() => {
        if (!clockType) {
            setClockType('AUTOMATIC');
        }
    }, [clockType]);

    const handleClockChange = (event) => {
        const selectedClockType = event.target.value;
        setClockType(selectedClockType);
    };

    const handleSubmit = () => {
        // Send the selected clock type to the backend
        fetch('/api/setClockType', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ clockType: clockType }),
        })
            .then(response => response.json())
            .then(data => console.log('Success:', data))
            .catch(error => console.error('Error:', error));
    };

    return (
        <div className="select-clock-container">
            <h2 className="hClock">Select the type of clock</h2>
            <div className="checkbox-container">
                <label>
                    <input
                        className="checkbox1"
                        type="checkbox"
                        value="AUTOMATIC"
                        checked={clockType === 'AUTOMATIC'}
                        onChange={handleClockChange}
                    />
                    Automatic (1 second)
                </label>
                <label>
                    <input
                        className="checkbox1"
                        type="checkbox"
                        value="MANUAL"
                        checked={clockType === 'MANUAL'}
                        onChange={handleClockChange}
                    />
                    Manual (press "Next" button)
                </label>
            </div>
            <button className="Submitbtn" onClick={handleSubmit}>Submit</button>
        </div>
    );
};

export default SelectClock;