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
        fetch('/api/setClockType', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ clockType: clockType }),
        })
            .then(response => response.text()) // Get the raw response as text
            .then(text => {
                console.log('Response Text:', text); // Log the response to see what is returned
                try {
                    const data = JSON.parse(text); // Try to parse it as JSON
                    console.log('Success:', data);
                } catch (error) {
                    console.error('Error parsing JSON:', error); // Handle any JSON parsing errors
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
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