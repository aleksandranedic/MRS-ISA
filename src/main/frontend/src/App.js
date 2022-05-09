import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

import VacationHousePage from "./VacationHousePage/VacationHousePage"
import {ClientProfilePage} from "./ClientProfilePage";
import VacationHousePage from "./VacationHousePage"
import {ClientProfilePage} from "./ClientPage/ClientProfilePage";
import Login from "./LogIn";
import AdventurePage from "./Adventure/AdventurePage";

import {SearchResultsPage} from "./Search/SearchResultsPage";
import HouseOwnerPage from "./VacationHouseOwnerPage/HouseOwnerPage";
import BoatOwnerPage from './BoatOwnerPage/BoatOwnerPage';
import BoatProfilePage from './BoatPage/BoatProfilePage'
import {FishingInstructorPage} from "./FishingInstructor/FishingInstructorPage";

import HouseOwnerPage from "./HouseOwnerPage"
import {FishingInstructorPage} from "./FishingInstructorPage/FishingInstructorPage";
import Registration from './Registration';
import AdventurePage from "./AdventurePage/AdventurePage";
import EmailConfirmed from "./EmailConfirmed";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/adventure/:id" element={<AdventurePage/>}/>
                <Route path="/house/:id" element={<VacationHousePage/>}/>
                <Route path="/client" element={<ClientProfilePage/>}/>
                <Route path="/houseOwner/:id" element={<HouseOwnerPage/>}/>
                <Route path="/boatOwner/:id" element={<BoatOwnerPage/>}/>
                <Route path="/fishingInstructor/:id" element={<FishingInstructorPage/>}/>
                <Route path="/boat/:id" element={<BoatProfilePage/>}/>
                <Route path="/fishingInstructor" element={<FishingInstructorPage/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/search/:searchTerm" element={<SearchResultsPage/>}/>

                <Route path='/registration' element={<Registration/>}/>
                <Route path='/confirmedEmail/:token' element={<EmailConfirmed/>}/>
            </Routes>
        </Router>
    );
}

export default App;
