import React, {useEffect, useState} from "react";
import axios from "axios";
import ImagesGallery from "../ImageGallery";
import QuickReservations from "../QuickReservations";
import Banner from "../Banner";
import HouseInfo from "./HouseInfo";
import UpdateHouse from "./UpdateHouse"
import BeginButton from "../BeginButton";
import { useParams } from "react-router-dom";
import Ratings from "../Reviews/Ratings";
import "react-image-gallery/styles/css/image-gallery.css";
import Navigation from "../Navigation/Navigation";
import {backLink, frontLink} from "../Consts";
import {Collapse} from "react-bootstrap";
import {ReservationsTable} from "../Calendar/ReservationsTable";
import {ReservationCardGrid} from "../Calendar/ReservationCardGrid";
import {Calendar} from "../Calendar/Calendar";
import {processReservationsForResources} from "../ProcessToEvent";
import {AddReview} from "../Reviews/AddReview";
import {getProfileLink} from "../Autentification";


const Gallery = ({house, images}) => {
    if (typeof house.imagePaths !== "undefined"){
        let empty = images.length === 0;
        for (let i=0; i<house.imagePaths.length; i++){
            if (!house.imagePaths[i].includes(backLink)){
                house.imagePaths[i] = backLink + house.imagePaths[i];
                images.push({original:house.imagePaths[i], thumbnail:house.imagePaths[i]})
            } else if (empty){
                images.push({original:house.imagePaths[i], thumbnail:house.imagePaths[i]})
            }
        }
        return <ImagesGallery images={images}/>
    }
    else {
        return <></>
    }
}
const Update = ({vacationHouse, showModal, closeModal}) => {
    if (typeof vacationHouse.name !== "undefined"){
        if (vacationHouse.additionalServices.length == 0){
            vacationHouse.additionalServices = [{id:0, text:''}]
        }
        return <UpdateHouse closeModal={closeModal} showModal={showModal} vacationHouse = {vacationHouse}/>
    }
    else {
        return <></>
    }
}

const Reservations = ({reservations, name, address, additionalServices}) => {
    if (typeof reservations !== "undefined"){
        return <QuickReservations type={"vacationHouse"} reservations={reservations} name={name} address={address} additionalServices={additionalServices} entity="house" priceText="po noćenju" durationText="dana"
        addable={true}/>
    }
    else {
        return <></>
    }
}

const ReviewsComp = ({reviews}) => {
    if (typeof reviews !== "undefined"){
        return <Ratings reviews = {reviews} type={"vacationHouse"}/>
    }
    else {
        return <></>
    }
}

export function VacationHousePage() {
    const [house, setHouse] = useState({});
    const [houseReviews, setHouseReviews] = useState([])
    const [show, setShow] = useState(false);
    const [open, setOpen] = useState(false);
    const [reservations, setReservations] = useState([]);
    const [events, setEvents] = useState(null);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const {id} = useParams();
    var [imgs, setImgs] = useState([{thumbnail: '', original: ''}]);

    const fetchReservations = () => {
        axios.get(backLink+ "/house/reservation/vacationHouse/" + id).then(res => {
            setReservations(res.data);
            setEvents(processReservationsForResources(res.data));
        })
    }

    const fetchHouse = () => {
        axios
        .get("http://localhost:4444/house/houseprof/" + id)
        .then(res => {
            if (res.data === ''){              
                window.location.href = frontLink + "pageNotFound"
            }            
            setHouse(res.data);
            setImgs([]);
            fetchReviews();
            fetchReservations();
        });
    };
    const fetchReviews = () => {
        axios
        .get("http://localhost:4444/review/getReviews/" + id)
        .then(res => {
            setHouseReviews(res.data);
        });
    };
    useEffect(() => {
        fetchHouse();
    }, []);
    
    return (
    <>
        <Banner caption={house.name}/>
        <Navigation buttons={
            [
                {text: "Osnovne informacije", path: "#info"},
                {text: "Fotografije", path: "#gallery"},
                {text: "Slobodni termini", path: "#actions"},
                {text: "Recenzije", path:"#reviews"}
            ]}
                    editable={true} editFunction={handleShow} searchable={true} showProfile={true}/>
        { typeof house.name !== "undefined" && <HouseInfo house={house}/>}
        <Update closeModal={handleClose} showModal={show} vacationHouse = {house}/>
        <div className='p-5 pt-0'>
            <Gallery house={house} images={imgs}/>

            <Reservations reservations={house.quickReservations} name={house.name} address={house.address} additionalServices={house.additionalServices}/>
            <footer className="blockquote-footer">Svi izlasci iz vikendice obavljaju se do 10:00h.</footer>
            
        </div>

       

            <hr className="me-5 ms-5"/>
                  <Calendar reservable={true} pricelist={{price: house.price}} resourceId={house.id} type={"vacationHouse"} events={events} myPage={true}/>
        
            <h2 className="me-5 ms-5 mt-5" id="reservations">Predstojaće rezervacije</h2>
            <hr className="me-5 ms-5"/>
            <ReservationCardGrid reservations={reservations}/>

            <h2 className="me-5 ms-5 mt-5" onClick={() => setOpen(!open)} aria-controls="reservationsTable" aria-expanded={open} style = {{cursor: "pointer"}}>
                Istorija rezervacija
            </h2>
            <hr className="me-5 ms-5"/>
            <Collapse in={open}>
                <div id="reservationsTable">
                    <ReservationsTable  reservations={reservations} showResource={false}/>
                </div>
            </Collapse>

            <div className="ms-5">
                <ReviewsComp reviews = {houseReviews}/>
            </div>

        <BeginButton/>
    </>

    )
}
export default VacationHousePage;