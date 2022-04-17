import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

import AdventurePage from "./AdventurePage";
import VacationHousePage from "./VacationHousePage"


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/adventure" element={<AdventurePage />}></Route>
                <Route path="/house" element={<VacationHousePage />}></Route>
            </Routes>
        </Router>
    );
}

export default App;
