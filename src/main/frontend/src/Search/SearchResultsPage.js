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
        let array;
        axios.get(backLink + "/boat",).then(res => {
            array = res.data;
            if (searchTerm !== "" && searchTerm !== "searchTerm" && searchTerm !== undefined) {
                array = array.filter(boat => boat.title.toString().toLowerCase().includes(searchTerm.toLowerCase()))
            }
            setBoats(array);
        });
    };
    const [vacationHouses, setVacationHouses] = useState([]);
    const fetchVacationHouses = () => {
        let array;
        axios.get(backLink + "/house",).then(res => {
            array = res.data;
            if (searchTerm !== "" && searchTerm !== "searchTerm" && searchTerm !== undefined) {
                array = array.filter(boat => boat.title.toString().toLowerCase().includes(searchTerm.toLowerCase()))
            }
            setVacationHouses(array);
        });
    }
    const [adventures, setAdventures] = useState([]);
    const fetchAdventures = () => {
        let array;
        axios.get(backLink + "/adventure",).then(res => {
            array = res.data;
            if (searchTerm !== "" && searchTerm !== "searchTerm" && searchTerm !== undefined) {
                array = array.filter(boat => boat.title.toString().toLowerCase().includes(searchTerm.toLowerCase()))
            }
            setAdventures(array);
        });
    };


    useEffect(() => {
        //TODO resi nesto sto ne znas ti ce refresha kada imas popunjen filter
        fetchVacationHouses()
        fetchBoats()
        fetchAdventures()
    }, []);

    const {searchTerm} = useParams();

    const updateResults = (formValues) => {
        const adventureFilter = {
            adventuresChecked: formValues.adventuresChecked,
            numberOfClients: formValues.numberOfClients,
            fishingInstructorName: formValues.fishingInstructorName,
            reviewRating: formValues.reviewRating,
            priceRange: formValues.sort,
            startDate: formValues.startDate.toString(),
            endDate: formValues.endDate.toString(),
            startTime: formValues.startTime.toString(),
            endTime: formValues.endTime.toString(),
            location: formValues.location,
            cancellationFee: formValues.cancellationFee
        }
        console.log(adventureFilter)
        axios.post(backLink+"/adventure/filterAdventures", adventureFilter).then(
            response => {
                console.log(response.data)
                setAdventures(response.data)
            }
        )
        const houseFilter = {
            vacationHousesChecked: formValues.vacationHousesChecked,
            numOfVacationHouseRooms: formValues.numOfVacationHouseRooms,
            numOfVacationHouseBeds: formValues.numOfVacationHouseBeds,
            vacationHouseOwnerName: formValues.vacationHouseOwnerName,
            reviewRating: formValues.reviewRating,
            priceRange: formValues.sort,
            startDate: formValues.startDate,
            endDate: formValues.endDate,
            startTime: formValues.startTime,
            endTime: formValues.endTime,
            location: formValues.location,
            cancellationFee: formValues.cancellationFee
        }
        console.log(houseFilter)
        axios.post("/house/filterHouse", houseFilter).then(
            response => {
                setVacationHouses(response.data)
            }
        )
        const boatFilter = {
            boatType: formValues.boatType,
            boatEnginePower: formValues.boatEnginePower,
            boatEngineNum: formValues.boatEngineNum,
            boatMaxSpeed: formValues.boatMaxSpeed,
            boatCapacity: formValues.boatCapacity,
            boatOwnerName: formValues.boatOwnerName,
            reviewRating: formValues.reviewRating,
            priceRange: formValues.sort,
            startDate: formValues.startDate,
            endDate: formValues.endDate,
            startTime: formValues.startTime,
            endTime: formValues.endTime,
            location: formValues.location,
            cancellationFee: formValues.cancellationFee
        }
        // axios.post("/boat/filter", boatFilter).then(
        //     response => {
        //         setBoats(response.data)
        //     }
        // )
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