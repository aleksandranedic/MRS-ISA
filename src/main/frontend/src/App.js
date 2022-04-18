import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'

import VacationHousePage from "./VacationHousePage"
import AdventurePage from "./AdventurePage/AdventurePage";
import {FishingInstructorPage} from "./FishingInstructorPage/FishingInstructorPage";


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/adventure" element={<AdventurePage/>}/>
                <Route path="/house" element={<VacationHousePage/>}/>
                <Route path="/fishingInstructor" element={<FishingInstructorPage/>}/>
            </Routes>
        </Router>
    );
}

export default App;
