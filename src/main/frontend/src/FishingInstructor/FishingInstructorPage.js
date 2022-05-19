import axios from "axios";
import React, {useEffect, useState} from "react";
import Banner from "../Banner";
import {FishingInstructorInfo} from "./FishingInstructorInfo";

import ImageGallery from "../ImageGallery";
import {FishingInstructorForm} from "./FishingInstructorForm";
import AdventureCarousel from "../Adventure/AdventureCarousel";
import Navigation from "../Navigation/Navigation";
import {useParams} from "react-router-dom";
import {Calendar} from "../Calendar/Calendar";
import {backLink} from "../Consts";
import {ReservationCardGrid} from "../Calendar/ReservationCardGrid";
import {Collapse} from "react-bootstrap";
import {ReservationsTable} from "../Calendar/ReservationsTable";


const FishingInstructors = ({id}) => {

    const [fishingInstructor, setFishingInstructor] = useState([]);
    const [adventures, setAdventures] = useState([]);
    const [reservations, setReservations] = useState([]);
    const [open, setOpen] = useState(false);


    const fetchReservations = () => {
        axios.get(backLink+ "/adventure/reservation/fishingInstructor/" + id).then(res => {
            setReservations(res.data);
        })
    }

    const fetchFishingInstructors = () => {
        axios.get(backLink+"/fishinginstructor/"+ id).then(res => {
            setFishingInstructor(res.data);
        });
    };

    const fetchAdventures = () => {
        axios.get(backLink+ "/adventure/owner/" + id).then(res => {
            setAdventures(res.data);
        });
    };

    useEffect(() => {
        fetchFishingInstructors();
        fetchAdventures();
        fetchReservations();
    }, []);

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);

    let html;
  
    if (fishingInstructor.length !== 0) {
        html = (<div key={fishingInstructor.id}>

            <Banner caption={fishingInstructor.firstName + " " + fishingInstructor.lastName}/>

            <Navigation buttons={
                [
                    {text: "Osnovne informacije", path: "#info"},
                    {text: "Avanture", path: "#adventures"},
                    {text: "Kalendar zauzetosti", path: "#calendar"}
                ]}
                        editable={true} editFunction={handleShow} searchable={true} showProfile={true}
            />

            <FishingInstructorInfo fishingInstructor={fishingInstructor}/>
            <hr className="me-5 ms-5"/>

            <AdventureCarousel adventures={adventures} add={true}/>
            <hr className="me-5 ms-5"/>
            <FishingInstructorForm show={show} setShow={setShow} fishingInstructor={fishingInstructor}/>
            <Calendar reservations={reservations} reservable={false}/>


            <h2 className="me-5 ms-5 mt-5" id="reservations">Predstojaće rezervacije</h2>
            <hr className="me-5 ms-5"/>

            <ReservationCardGrid reservations={reservations}/>

            <h2 className="me-5 ms-5 mt-5" onClick={() => setOpen(!open)}
                aria-controls="reservationsTable"
                aria-expanded={open}
                style = {{cursor: "pointer"}}
                id="reservationsHistory"
            >Istorija rezervacija</h2>

            <hr className="me-5 ms-5"/>
            <Collapse in={open}>
                <div id="reservationsTable">
                    <ReservationsTable  reservations={reservations} showResource={false}/>
                </div>
            </Collapse>
        </div>)
    }

    return html;

};

export function FishingInstructorPage() {
    const {id} = useParams();
    return (
        <>

            <FishingInstructors id={id}/>

        </>


    )
}