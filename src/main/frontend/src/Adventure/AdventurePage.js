import React, {useEffect, useState} from "react";
import axios from "axios";
import Banner from "../Banner";
import Navigation from "../Navigation/Navigation";
import AdventureInfo from "./AdventureInfo";
import {Calendar} from "../Calendar/Calendar";
import {AdventureModal} from "./AdventureModal";
import {useParams} from "react-router-dom";
import {backLink} from "../Consts";
import {ReservationsTable} from "../Calendar/ReservationsTable";
import {Collapse} from "react-bootstrap";
import {ReservationCardGrid} from "../Calendar/ReservationCardGrid";
import {AdventureGallery} from "./AdventureGallery";
import QuickReservations from "../QuickReservations";
import BeginButton from "../BeginButton";
import Ratings from "../Reviews/Ratings";
import {processReservationsForResources} from "../ProcessToEvent";

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
    const [quickReservations, setQuickReservations] = useState([]);
    const [events, setEvents] = useState(null)

    const [images, setImages] = useState([]);

    let html;

    const QuickReservationsComp = ({reservations, name, address}) => {
        if (typeof reservations !== "undefined"){
            return <QuickReservations type={"adventure"} reservations={reservations} name={name} address={address.street +" "+ address.number +  ", " + address.place + ", " +  address.country} entity="adventure" priceText="po vožnji" durationText="h"/>
        }
        else {
            return <></>
        }
    }
    
    const ReviewsComp = ({reviews}) => {
        if (typeof reviews !== "undefined"){
            return <Ratings reviews = {reviews} type={"adventure"}/>
        }
        else {
            return <></>
        }
    }

    const AdventureInfoComponent = ({adventure}) => {
        if (typeof adventure !== "undefined"){
            return <AdventureInfo adventure={adventure}/>
        }
        else {
            return <></>
        }
    }
    const fetchAdventure = () => {
        axios.get(backLink+"/adventure/"+ id).then(res => {
            setAdventure(res.data);
            setImages([]);
        });
    };

    const fetchReservations = () => {

        axios.get(backLink+ "/adventure/reservation/adventure/" + id).then(res => {
            setReservations(res.data);
            console.log(res.data);
            setEvents(processReservationsForResources(res.data))

        })

    }

    const fetchQuickReservations = () => {
        axios.get(backLink+ "/adventure/quickReservations/" + id).then(res => {
           
            setQuickReservations(res.data);
        })
    }

    const fetchReviews = () => {
        axios
        .get("http://localhost:4444/review/getReviews/" + id)
        .then(res => {
            setAdventureReviews(res.data);
        });
    };

    useEffect(() => {
        fetchReservations();
        fetchReviews();
        fetchQuickReservations();
        fetchAdventure();
    }, []);


    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const [adventureReviews, setAdventureReviews] = useState([])

    const [open, setOpen] = useState(false);

    if (adventure.length !== 0) {

        html = (<div>
            <Banner caption={adventure.title}/>
            <Navigation buttons={
                [
                    {text: "Osnovne informacije", path: "#info"},
                    {text: "Fotografije", path: "#photos"},
                    {text: "Kalendar", path: "#calendar"},
                    {text: "Rezervacije", path: "#reservations"},

                ]}
                        editable={true} editFunction={handleShow} searchable={true}
            />
            <AdventureModal show={show} setShow={setShow} adventure={adventure}/>
            <AdventureInfoComponent adventure={adventure}/>


            <div id="photos">
                <AdventureGallery id={id} images={images}/>
            </div>

            <QuickReservationsComp reservations={quickReservations} name={adventure.title} address={adventure.address}/>

            <hr className="me-5 ms-5"/>
            <Calendar reservable={true} pricelist={adventure.pricelist} resourceId={adventure.id} type={"adventure"} events={events}/>

            <h2 className="me-5 ms-5 mt-5" id="reservations">Predstojaće rezervacije</h2>
            <hr className="me-5 ms-5"/>

            <ReservationCardGrid reservations={reservations}/>

            <h2 className="me-5 ms-5 mt-5" onClick={() => setOpen(!open)}
                    aria-controls="reservationsTable"
                    aria-expanded={open}
                    style = {{cursor: "pointer"}}
            >Istorija rezervacija</h2>

            <hr className="me-5 ms-5"/>
            <Collapse in={open}>
                <div id="reservationsTable">
                    <ReservationsTable  reservations={reservations} showResource={false}/>
                </div>
            </Collapse>
            <div className="m-5 mb-0 me-0">
                <ReviewsComp reviews = {adventureReviews}/>
            </div>
            

            <BeginButton/>
        </div>)
    }

    return (html)
}


export default AdventurePage;