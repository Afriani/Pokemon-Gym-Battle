import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Home from './pages/Home.jsx';
import ChoosePokemon from './pages/ChoosePokemon.jsx';
import Battle from './pages/BattleScreen.jsx';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/choose" element={<ChoosePokemon />} />
                <Route path="/battle" element={<Battle />} />
            </Routes>
        </Router>
    );
}

export default App


