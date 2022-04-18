import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

import {AdventurePage} from "./AdventurePage";
import VacationHousePage from "./VacationHousePage"


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/adventure" element={<AdventurePage/>}/>
                <Route path="/house" element={<VacationHousePage/>}/>
            </Routes>
        </Router>
    );
}

export default App;
