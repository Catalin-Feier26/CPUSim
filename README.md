# MIPS Pipeline CPU Simulator

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Architecture](#architecture)
- [Contributing](#contributing)

---

## Overview
The MIPS Pipeline CPU Simulator is an interactive tool designed to help users understand and visualize the functioning of a pipelined MIPS CPU. The simulator uses a React front-end for user interaction and a Java Spring back-end for logic processing. It supports step-by-step execution and automated clock cycles, with features like hazard detection, instruction decoding, and visual representation of pipeline stages.

---

## Features
- **Pipeline Visualization**: Displays the instruction flow through the stages (IF, ID, EX, MEM, WB).
- **Interactive Controls**: Start, Stop, and Next buttons to control the simulation manually or automatically.
- **Hazard Detection**: Detects and resolves data and control hazards, displaying them in the UI.
- **Instruction Input**: Load custom instruction sets to observe pipeline behavior.
- **Threaded Execution**: The MIPS runs on a separate thread from the main thread in order to improve its overall control.
- **Highlighting**: Highlights active pipeline components during execution.

---

## Technologies Used
- **Frontend**: React
- **Backend**: Java Spring Boot
- **Programming Languages**: Java, JavaScript, CSS
- **Visualization**: Draw.io for pipeline diagrams
- **Testing**: JUnit for backend testing


---

## Setup and Installation
1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-repo/mips-pipeline-simulator.git
   cd mips-pipeline-simulator
