import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import AdventurePage from "./Adventure/AdventurePage";
import VacationHousePage from "./VacationHousePage/VacationHousePage";
import {ClientProfilePage} from "./ClientPage/ClientProfilePage";
import HouseOwnerPage from "./VacationHouseOwnerPage/HouseOwnerPage";
import BoatOwnerPage from "./BoatOwnerPage/BoatOwnerPage";
import {FishingInstructorPage} from "./FishingInstructor/FishingInstructorPage";
import BoatProfilePage from "./BoatPage/BoatProfilePage";
import {SearchResultsPage} from "./Search/SearchResultsPage";
import Registration from "./Registration";
import EmailConfirmed from "./EmailConfirmed";
import Login from "./LogIn";
import {HomePage} from "./Home/HomePage";


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
                <Route path="/login" element={<Login/>}/>
                <Route path="/search/:searchTerm" element={<SearchResultsPage/>}/>
                <Route path='/registration' element={<Registration/>}/>
                <Route path='/confirmedEmail/:token' element={<EmailConfirmed/>}/>
                <Route path='' element={<HomePage/>}/>
            </Routes>
        </Router>
    );
}

export default App;
