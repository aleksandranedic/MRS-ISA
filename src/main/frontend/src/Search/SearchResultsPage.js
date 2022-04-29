import React, {useEffect, useState} from "react";
import Banner from "../Banner";
import Navigation from "../Navigation/Navigation";
import {SearchResultCard} from "./SearchResultCard";
import axios from "axios";
import {useLocation} from "react-router-dom";


const Adventures = () => {
    const [adventures, setAdventures] = useState([]);

    const fetchAdventures = () => {
        axios.get("http://localhost:4444/adventure",).then(res => {
            console.log(res.data);
            setAdventures(res.data);
        });
    };

    useEffect(() => {
        fetchAdventures();
    }, []);

    let html;
    if (adventures.length !== 0) {

        html = (adventures.map((adventure) => {
                return <SearchResultCard key={adventure.id} title={adventure.title} description={adventure.description}
                                         link={"http://localhost:3000/adventure"}/>
            }
        ))
    }
    return html;
}

const FishingInstructors = () => {
    const [fishingInstructors, setFishingInstructors] = useState([]);

    const fetchFishingInstructors = () => {
        axios.get("http://localhost:4444/fishinginstructor",).then(res => {
            console.log(res.data);
            setFishingInstructors(res.data);
        });
    };

    useEffect(() => {
        fetchFishingInstructors();
    }, []);

    let html;
    if (fishingInstructors.length !== 0) {

        html = (fishingInstructors.map((fishingInstructor) => {
                return <SearchResultCard key={fishingInstructor.id} title={fishingInstructor.firstName + " " + fishingInstructor.lastName}
                                         description={fishingInstructor.biography}
                                         link={"http://localhost:3000/fishingInstructor"}/>
            }
        ))
    }
    return html;
}

const VacationHouses = () => {
    const [vacationHouses, setVacationHouses] = useState([]);

    const fetchVacationHouses = () => {
        axios.get("http://localhost:4444/house",).then(res => {
            console.log(res.data);
            setVacationHouses(res.data);
        });
    };

    useEffect(() => {
        fetchVacationHouses();
    }, []);

    let html;
    if (vacationHouses.length !== 0) {

        html = (vacationHouses.map((vacationHouse) => {
                return <SearchResultCard title={vacationHouse.title} description={vacationHouse.description}
                                                link={"http://localhost:3000/house"}/>
            }
        ))
    }
    return html;
}

export function SearchResultsPage() {
    const {state} = useLocation();
    console.log(state);
    return (
        <>
            <Banner caption={"Rezultati pretrage"}/>
            <Navigation buttons={[]} editable={false} searchable={true}/>
            <div style={{marginLeft: "5%", marginRight: "10%"}}>
                <h4 className="fw-light m-3">Avanture</h4>
                <hr/>
                <Adventures/>
                <h4 className="fw-light m-3">Instruktori pecanja</h4>
                <hr/>
                <FishingInstructors/>
                <h4 className="fw-light m-3">Vikendice</h4>
                <hr/>
                <VacationHouses/>
            </div>

        </>


    )
}