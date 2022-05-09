import React, {useEffect, useState} from "react";
import Banner from "../Banner";
import Navigation from "../Navigation/Navigation";
import {SearchResultCard} from "./SearchResultCard";
import axios from "axios";
import {useLocation, useParams} from "react-router-dom";
import {backLink, frontLink} from "../Consts";


const Adventures = ({term}) => {
    const [adventures, setAdventures] = useState([]);

    const fetchAdventures = () => {
        axios.get(backLink + "adventure",).then(res => {


            let adventures = []

            for (let i = 0; i < res.data.length; i++) {
                let a = res.data[i];
                if (a.title.toLowerCase().includes(term.toLowerCase())) {
                    adventures.push(a);
                }
                if (a.description.toLowerCase().includes(term.toLowerCase())) {
                    adventures.push(a);
                }
            }

            setAdventures(adventures);
        });
    };

    useEffect(() => {
        fetchAdventures();
    }, []);

    let html;
    if (adventures.length !== 0) {

        html = (adventures.map((adventure) => {
                return <SearchResultCard key={adventure.id} title={adventure.title} description={adventure.description}
                                         link={frontLink + "adventure/" + adventure.id}/>
            }
        ))
    }
    return html;
}

const FishingInstructors = ({term}) => {
    const [fishingInstructors, setFishingInstructors] = useState([]);

    const fetchFishingInstructors = () => {
        axios.get(backLink + "fishinginstructor").then(res => {

            let instructors = []

            for (let i = 0; i < res.data.length; i++) {
                let a = res.data[i];
                if (a.firstName.toLowerCase().includes(term.toLowerCase())) {
                    instructors.push(a);
                }
                if (a.lastName.toLowerCase().includes(term.toLowerCase())) {
                    instructors.push(a);
                }
            }

            setFishingInstructors(instructors);
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
                                         link={frontLink + "fishingInstructor/" + fishingInstructor.id}/>
            }
        ))
    }
    return html;
}

const VacationHouses = ({term}) => {
    const [vacationHouses, setVacationHouses] = useState([]);

    const fetchVacationHouses = () => {
        axios.get(backLink + "house",).then(res => {

            let houses = []

            for (let i = 0; i < res.data.length; i++) {
                let a = res.data[i];
                if (a.title.toLowerCase().includes(term.toLowerCase())) {
                    houses.push(a);
                }
                if (a.description.toLowerCase().includes(term.toLowerCase())) {
                    houses.push(a);
                }
            }

            setVacationHouses(houses);
        });
    };

    useEffect(() => {
        fetchVacationHouses();
    }, []);

    let html;
    if (vacationHouses.length !== 0) {

        html = (vacationHouses.map((vacationHouse) => {
                return <SearchResultCard key={vacationHouse.id} title={vacationHouse.title} description={vacationHouse.description}
                                                link={frontLink + "house/" + vacationHouse.id}/>
            }
        ))
    }
    return html;
}

export function SearchResultsPage() {

    const {searchTerm} = useParams();

    return (
        <>
            <Banner caption={"Rezultati pretrage"}/>
            <Navigation buttons={[]} editable={false} searchable={true}/>
            <div style={{marginLeft: "5%", marginRight: "10%"}}>
                <h4 className="fw-light m-3">Avanture</h4>
                <hr/>
                <Adventures term={searchTerm}/>
                <h4 className="fw-light m-3">Instruktori pecanja</h4>
                <hr/>
                <FishingInstructors term={searchTerm}/>
                <h4 className="fw-light m-3">Vikendice</h4>
                <hr/>
                <VacationHouses term={searchTerm}/>
            </div>

        </>


    )
}