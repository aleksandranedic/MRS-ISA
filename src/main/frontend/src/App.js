import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

import VacationHousePage from "./VacationHousePage"
import {ClientProfilePage} from "./ClientProfilePage";
import AdventurePage from "./AdventurePage/AdventurePage";

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
                <Route path="/fishingInstructor" element={<FishingInstructorPage/>}/>
            </Routes>
        </Router>
    );
}

export default App;
