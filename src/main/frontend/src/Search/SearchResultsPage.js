import React, {useEffect, useState} from "react";
import Banner from "../Banner";
import Navigation from "../Navigation/Navigation";
import axios from "axios";
import {useParams} from "react-router-dom";
import {backLink} from "../Consts";
import SearchResultsResources from "./SearchResultsItems";


export function SearchResultsPage() {

    const [boats, setBoats] = useState([]);
    const fetchBoats = () => {
        axios.get(backLink + "/boat",).then(res => {
            // let boats = []
            //
            // for (let i = 0; i < res.data.length; i++) {
            //     let a = res.data[i];
            //     if (a.title.includes(term)) {
            //         boats.push(a);
            //     }
            // }
            // setBoats(boats)
            setBoats(res.data)

        });
    };
    const [vacationHouses, setVacationHouses] = useState([]);
    const fetchVacationHouses = () => {
        axios.get(backLink + "/house",).then(res => {
            // let houses = []
            // for (let i = 0; i < res.data.length; i++) {
            //     let a = res.data[i];
            //     if (a.title.includes(term)) {
            //         houses.push(a);
            //     }
            // }
            setVacationHouses(res.data);
        });
    }
    const [adventures, setAdventures] = useState([]);
    const fetchAdventures = () => {
        axios.get(backLink + "/adventure",).then(res => {


            // let adventures = []
            //
            // for (let i = 0; i < res.data.length; i++) {
            //     let a = res.data[i];
            //     if (a.title.includes(term)) {
            //         adventures.push(a);
            //     }
            // }

            // setAdventures(adventures);
            setAdventures(res.data);
        });
    };


    useEffect(() => {
        fetchVacationHouses()
        fetchBoats()
        fetchAdventures()
    }, []);

    const {searchTerm} = useParams();

    const updateResults = (formValues) => {
        //TODO kako ovo uz paginaciju

        //SOLUTION mozda da napravis poseban za svaku sekciju i svima stavis index koliko moze da stane i tako pozivas
    }

    return (
        <>
            <Banner caption={"Rezultati pretrage"}/>
            <Navigation updateResults={updateResults} buttons={[]} editable={false} searchable={true}/>
            <div style={{marginLeft: "5%", marginRight: "10%"}}>
                <h4 className="fw-light m-3">Vikendice</h4>
                <hr/>
                <SearchResultsResources list={vacationHouses} name={"house"}/>
                <h4 className="fw-light m-3">Brodovi</h4>
                <hr/>
                <SearchResultsResources list={boats} name={"boat"}/>
                <h4 className="fw-light m-3">Avanture</h4>
                <hr/>
                <SearchResultsResources list={adventures} name={"adventure"}/>
            </div>
        </>


    )
}