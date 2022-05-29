import React, {useEffect, useState} from "react";
import axios from "axios";
import ImagesGallery from "../ImageGallery";
import QuickReservations from "../QuickReservations";
import Banner from "../Banner";
import BoatInfo from "./BoatInfo";
import UpdateBoat from "./UpdateBoat"
import BeginButton from "../BeginButton";
import { useParams } from "react-router-dom";
import "react-image-gallery/styles/css/image-gallery.css";
import Navigation from "../Navigation/Navigation";
import Ratings from "../Reviews/Ratings";
import {backLink} from "../Consts";
import {Calendar} from "../Calendar/Calendar";
import {ReservationCardGrid} from "../Calendar/ReservationCardGrid";
import {Collapse} from "react-bootstrap";
import {ReservationsTable} from "../Calendar/ReservationsTable";


const Gallery = ({boat, images}) => {
    if (typeof boat.imagePaths !== "undefined"){
        let empty = images.length === 0;
        for (let i=0; i<boat.imagePaths.length;i++){
            if (!boat.imagePaths[i].includes(backLink)){
                boat.imagePaths[i] = backLink + boat.imagePaths[i];
                images.push({original:boat.imagePaths[i], thumbnail:boat.imagePaths[i]})
            } else if (empty){
                images.push({original:boat.imagePaths[i], thumbnail:boat.imagePaths[i]})   
            }
        }
        return <ImagesGallery images={images}/>
    }
    else {
        return <></>
    }
}
const Update = ({boat, showModal, closeModal}) => {
    if (typeof boat.name !== "undefined"){
        if (boat.navigationEquipment.length === 0){
            boat.navigationEquipment = [{id:0, text:''}]
        }
        if (boat.additionalServices.length === 0){
            boat.additionalServices = [{id:0, text:''}]
        }
        if (boat.fishingEquipment.length === 0){
            boat.fishingEquipment = [{id:0, text:''}]
        }
        return <UpdateBoat closeModal={closeModal} showModal={showModal} boat = {boat}/>
    }
    else {
        return <></>
    }
}

const Reservations = ({reservations, name, address}) => {
    if (typeof reservations !== "undefined"){
        return <QuickReservations reservations={reservations} name={name} address={address} entity="boat" priceText="po vožnji" durationText="h"/>
    }
    else {
        return <></>
    }
}

const ReviewsComp = ({reviews}) => {
    if (typeof reviews !== "undefined"){
        return <Ratings reviews = {reviews} type={"boat"}/>
    }
    else {
        return <></>
    }
}


export function BoatProfilePage() {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const {id} = useParams();
    const [boat, setBoat] = useState({});
    const [boatReviews, setBoatReviews] = useState([])
    var [imgs, setImgs] = useState([{thumbnail: '', original: ''}]);


    const [reservations, setReservations] = useState([]);
    const [open, setOpen] = useState(false);

    const fetchReservations = () => {
        axios.get(backLink+ "/boat/reservation/boat/" + id).then(res => {
            setReservations(res.data);
        })
    }

    const [busyPeriod, setBusyPeriod] = useState([]);
    const fetchBusyPeriod = () => {
        axios.get(backLink+ "/boat/reservation/busyPeriod/boat/" + id).then(res => {
            setBusyPeriod(res.data);
        })
    }


    const fetchBoat = () => {
        axios
        .get("http://localhost:4444/boat/boatprof/" + id)
        .then(res => {
            setBoat(res.data);
            setImgs([]);
        });
    };

    const fetchReviews = () => {
        axios
        .get("http://localhost:4444/review/getReviews/" + id)
        .then(res => {
            setBoatReviews(res.data);
        });
    };

    useEffect(() => {
        fetchBoat();
        fetchReviews();
        fetchReservations();
        fetchBusyPeriod();
    }, []);
    
    return (
    <>
        <Banner caption={boat.name}/>
        <Navigation buttons={
            [
                {text: "Osnovne informacije", path: "#info"},
                {text: "Fotografije", path: "#gallery"},
                {text: "Akcije", path: "#actions"},
                {text: "Kalendar zauzetosti", path: "#"}
            ]}
                    editable={true} editFunction={handleShow} searchable={true} showProfile={true}/>
        <BoatInfo boat={boat}/>
        <Update closeModal={handleClose} showModal={show} boat = {boat}/>
        <div className='p-5 pt-0'>
            <Gallery boat={boat} images={imgs}/>
            
            <Reservations reservations={boat.quickReservations} name={boat.name} address={boat.address}/>
            <hr className="me-5 ms-5"/>
            <Calendar reservations={reservations} reservable={true} pricelist={{price: boat.price}} type="boat" resourceId={id} busyPeriods={busyPeriod}/>

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
            
            <ReviewsComp reviews = {boatReviews}/>
            
        </div>
        <BeginButton/>
    </>

    )
}
export default BoatProfilePage;