import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

import VacationHousePage from "./VacationHousePage/VacationHousePage"
import {ClientProfilePage} from "./ClientProfilePage";
import Login from "./LogIn";


import HouseOwnerPage from "./VacationHouseOwnerPage/HouseOwnerPage"
import {FishingInstructorPage} from "./FishingInstructorPage/FishingInstructorPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/adventure" element={<AdventurePage/>}/>
                <Route path="/house/:id" element={<VacationHousePage/>}/>
                <Route path="/client" element={<ClientProfilePage/>}/>
                <Route path="/houseOwner/:id" element={<HouseOwnerPage/>}/>
                <Route path="/fishingInstructor" element={<FishingInstructorPage/>}/>
                <Route path="/login" element={<Login/>}/>
            </Routes>
        </Router>
    );
}

export default App;
