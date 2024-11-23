import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Import necessary components
import Layout from './Layout'; // Import Layout component
import './index.css'; // Import CSS for styling
import InstructionsPage from "./InstructionsPage";
import SimulationPage from "./SimulationPage";
import ModifyInstructions from "./ModifyInstructions";
import HowToUse from "./HowToUse";
import SelectClock from "./SelectClock";
function App() {
    return (
        <Router> {/* Wrap the app with Router to enable routing */}
            <Layout> {/* Use Layout to display the navbar and other common UI elements */}
                <Routes>
                    <Route
                        path="/"
                        element={<HowToUse/>}
                    />
                    <Route
                        path="/instructions"
                        element={<InstructionsPage />}
                    />
                    <Route
                        path="/modify"
                        element={<ModifyInstructions/>}
                    />
                    <Route
                        path="/clock"
                        element ={<SelectClock/>}
                    />
                    <Route
                        path="/hazards"
                        element={<div>Hazards Page</div>}
                    />
                    <Route
                        path="/simulation"
                        element={<SimulationPage/>}
                    />
                </Routes>
            </Layout>
        </Router>
    );
}

export default App;
