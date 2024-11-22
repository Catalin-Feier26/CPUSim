import React from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate hook
import styles from './styles.css'
const Layout = ({children}) => {
    const navigate = useNavigate(); // Create navigate function

    const handleHomeClick = () => navigate('/');
    const handleSeeInstructionsClick = () => navigate('/instructions');
    const handleModifyInstructionsClick = () => navigate('/modify');
    const handleSelectClockTypeClick = () => navigate('/clock');
    const handleCheckHazrdsClick = () => navigate('/hazards');
    const handleStartSimulationClick = () => navigate('/simulation');

    return (
        <div>
            {/* Navbar */}
            <nav style={styles.navbar}>
                <button className="btn" onClick={handleHomeClick}>Home</button>
                <button className="btn" onClick={handleSeeInstructionsClick}>See Instructions</button>
                <button className="btn" onClick={handleModifyInstructionsClick}>Modify Instructions</button>
                <button className="btn" onClick={handleSelectClockTypeClick}>Select Clock Type</button>
                <button className="btn" onClick={handleCheckHazrdsClick}>Check Hazards</button>
                <button className="btn" onClick={handleStartSimulationClick}>Start Simulation</button>
            </nav>
            <main className="content">{children}</main>
        </div>
    );
}

export default Layout;
