import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

import {AdventurePage} from "./AdventurePage";
import VacationHousePage from "./VacationHousePage"
import {ClientProfilePage} from "./ClientProfilePage";
import Login from "./LogIn";


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/adventure" element={<AdventurePage/>}/>
                <Route path="/house" element={<VacationHousePage/>}/>
                <Route path="/client" element={<ClientProfilePage/>}/>
                <Route path="/login" element={<Login/>}/>
            </Routes>
        </Router>
    );
}

export default App;
