import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Import necessary components
import Layout from './Layout'; // Import Layout component
import MIPS from './MIPS.svg'; // Import your MIPS image
import './index.css'; // Import CSS for styling
import InstructionsPage from "./InstructionsPage";
function App() {
    return (
        <Router> {/* Wrap the app with Router to enable routing */}
            <Layout> {/* Use Layout to display the navbar and other common UI elements */}
                <Routes>
                    <Route
                        path="/"
                        element={
                            <div className="App">
                                <header className="App-header">
                                    <img src={MIPS} className="App-logo" alt="MIPS Diagram" />
                                    <div>Home Page</div>
                                </header>
                            </div>
                        }
                    />
                    <Route
                        path="/instructions"
                        element={<InstructionsPage />}
                    />
                    <Route
                        path="/modify"
                        element={<div>Modify Instructions Page</div>}
                    />
                    <Route
                        path="/clock"
                        element={<div>Clock Type Page</div>}
                    />
                    <Route
                        path="/hazards"
                        element={<div>Hazards Page</div>}
                    />
                    <Route
                        path="/simulation"
                        element={<div>Simulation Page</div>}
                    />
                </Routes>
            </Layout>
        </Router>
    );
}

export default App;
