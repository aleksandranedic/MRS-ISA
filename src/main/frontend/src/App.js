import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

import VacationHousePage from "./VacationHousePage/VacationHousePage"
import {ClientProfilePage} from "./ClientProfilePage";
import Login from "./LogIn";
import AdventurePage from "./Adventure/AdventurePage";

import {SearchResultsPage} from "./Search/SearchResultsPage";
import HouseOwnerPage from "./VacationHouseOwnerPage/HouseOwnerPage";
import BoatOwnerPage from './BoatOwnerPage/BoatOwnerPage';
import BoatProfilePage from './BoatPage/BoatProfilePage'
import {FishingInstructorPage} from "./FishingInstructor/FishingInstructorPage";


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/adventure" element={<AdventurePage/>}/>
                <Route path="/house/:id" element={<VacationHousePage/>}/>
                <Route path="/client" element={<ClientProfilePage/>}/>
                <Route path="/houseOwner/:id" element={<HouseOwnerPage/>}/>
                <Route path="/boatOwner/:id" element={<BoatOwnerPage/>}/>
                <Route path="/boat/:id" element={<BoatProfilePage/>}/>
                <Route path="/fishingInstructor" element={<FishingInstructorPage/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/search" element={<SearchResultsPage/>}/>
            </Routes>
        </Router>
    );
}

export default App;
