import React, {useEffect, useState} from "react";
import axios from "axios";
import ImagesGallery from "../ImageGallery";
import Banner from "../Banner";
import Navigation from "../Navigation/Navigation";
import AdventureInfo from "./AdventureInfo";
import {Calendar} from "../Calendar/Calendar";
import {AdventureForm} from "./AdventureForm";
import {useParams} from "react-router-dom";
import {backLink} from "../Consts";
import {ReservationsTable} from "../Calendar/ReservationsTable";
import {Button, Collapse} from "react-bootstrap";
import {ReservationCardGrid} from "../Calendar/ReservationCardGrid";

export function AdventurePage() {
    const {id} = useParams();
    return (
        <>
            <Adventures id={id}/>
        </>
    )
}


const Adventures = ({id})  =>{

    const [adventure, setAdventure] = useState([]);
    const [reservations, setReservations] = useState([]);

    const [images, setImages] = useState([]);

    let html;
    const fetchAdventures = () => {
        axios.get(backLink+"adventure/"+ id).then(res => {
            setAdventure(res.data);
            setImages([]);
        });
    };

    const fetchReservations = () => {
        axios.get(backLink+ "adventure/reservation/adventure/" + id).then(res => {
            setReservations(res.data);
        })
    }

    useEffect(() => {
        fetchAdventures();
        fetchReservations();
    }, []);


    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);

    const [open, setOpen] = useState(false);

    if (adventure.length !== 0) {

        html = (<div>
            <Banner caption={adventure.title}/>
            <Navigation buttons={
                [
                    {text: "Osnovne informacije", path: "#info"},
                    // {text: "Fotografije", path: "#"},
                    // {text: "Akcije", path: "#"},
                    {text: "Kalendar zauzetosti", path: "#calendar"},
                    {text: "Predstojaće rezervacije", path: "#reservations"},
                    {text: "Istorija rezervacija", path: "#reservationsHistory"}
                ]}
                        editable={true} editFunction={handleShow} searchable={true}
            />
            <AdventureForm show={show} setShow={setShow} adventure={adventure}/>
            <AdventureInfo adventure={adventure}/>
            <hr className="me-5 ms-5"/>
            <Calendar reservations={reservations} reservable={true} pricelist={adventure.pricelist} perHour={true}/>

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

    return (html)
}


export default AdventurePage;