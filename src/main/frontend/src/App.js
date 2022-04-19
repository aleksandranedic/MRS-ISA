import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

import {AdventurePage} from "./AdventurePage";
import VacationHousePage from "./VacationHousePage"
import {ClientProfilePage} from "./ClientProfilePage";

import FishingInstructorPage from "./FishingInstructorPage"
import HouseOwnerPage from "./HouseOwnerPage"

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/adventure" element={<AdventurePage/>}/>
                <Route path="/house" element={<VacationHousePage/>}/>
                <Route path="/client" element={<ClientProfilePage/>}/>
                <Route path="houseOwner" element={<HouseOwnerPage/>}/>
                <Route path="instructor" element={<FishingInstructorPage/>}/>
            </Routes>
        </Router>
    );
}

export default App;
