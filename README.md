![Battle Screen](./frontend/src/assets/pokemon.jpg)

# 🛡️ Pokémon Gym Battle Simulator

A full-stack Pokémon battle engine featuring a **Java Spring Boot** backend and a **React + Vite** frontend. This project simulates a classic Pokémon gym experience with real-time combat logic, dynamic animations, and authentic sound effects.

![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

---

## 🎮 Features

### ⚔️ Dynamic Battle System
- **Turn-Based Combat:** Fully functional battle logic handled by a robust Java backend.
- **Move Sets:** 18 different Pokémon types with unique moves and damage calculations.
- **AI Opponent:** A "Gym Owner" logic that reacts to player moves.

### 🎨 Immersive Frontend
- **Interactive UI:** Built with React, featuring health bars, experience tracking, and a real-time battle log.
- **Animations:** Custom CSS and JavaScript animations for attacks (Fireballs, Lightning, Water pulses) and Pokémon "fainting" effects.
- **Responsive Design:** A dashboard-style layout that brings the Pokémon world to life.

### 🔊 Audio Experience
- **Move SFX:** Unique sound effects for every attack type (Thunderbolt, Flamethrower, etc.).
- **Authentic Cries:** Dynamic loading of Pokémon cries from the PokeAPI GitHub repository.
- **Staggered Audio:** Realistic sound timing where move sounds play on click and Pokémon cries play upon taking damage.

---

## 🛠️ Tech Stack

### Backend (Java / Spring Boot)
- **REST API:** Exposes endpoints for battle initialization and attack processing.
- **OOP Principles:** Utilizes Inheritance and Polymorphism for different Pokémon types (Fire, Water, Grass, etc.).
- **Maven:** Dependency management for a multi-module project structure.

### Frontend (React / Vite)
- **State Management:** Uses React Hooks (`useState`, `useEffect`) to manage complex battle states.
- **Axios:** Handles asynchronous communication with the Spring Boot API.
- **PokeAPI Integration:** Dynamically fetches sprites and data for over 1000+ Pokémon.

---

## 🚀 Getting Started

### Prerequisites
- **Node.js** (v18+)
- **Java JDK** (17+)
- **IntelliJ IDEA** (Recommended)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/pokemon-gym-battle.git
   cd pokemon-gym-battle

![Battle Screen](./frontend/src/assets/pokemonandfriends.gif)
