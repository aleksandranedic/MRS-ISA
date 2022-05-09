import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

import VacationHousePage from "./VacationHousePage"
import {ClientProfilePage} from "./ClientPage/ClientProfilePage";
import Login from "./LogIn";


import HouseOwnerPage from "./HouseOwnerPage"
import {FishingInstructorPage} from "./FishingInstructorPage/FishingInstructorPage";
import Registration from './Registration';
import AdventurePage from "./AdventurePage/AdventurePage";
import EmailConfirmed from "./EmailConfirmed";

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
                <Route path='/registration' element={<Registration/>}/>
                <Route path='/confirmedEmail/:token' element={<EmailConfirmed/>}/>
            </Routes>
        </Router>
    );
}

export default App;
