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
import { Calendar } from "../Calendar/Calendar";

const HOST = "http://localhost:4444";
const Gallery = ({house, images}) => {
    if (typeof house.imagePaths !== "undefined"){
        let empty = images.length === 0;
        for (let i=0; i<house.imagePaths.length; i++){
            if (!house.imagePaths[i].includes(HOST)){
                house.imagePaths[i] = HOST + house.imagePaths[i];
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

const Reservations = ({reservations, name, address}) => {
    if (typeof reservations !== "undefined"){
        return <QuickReservations reservations={reservations} name={name} address={address} entity="house" priceText="po noćenju" durationText="dana"/>
    }
    else {
        return <></>
    }
}

const CalendarComp = ({reservations, reservable, house, perHour}) => {
    if (typeof house !== "undefined"){
        var priceList = {id:"1", price:house.price}
        return <Calendar reservations={reservations} reservable={reservable} pricelist={priceList} perHour={perHour}/>
    }
    else {
        return <></>
    }
}

const ReviewsComp = ({reviews}) => {
    if (typeof reviews !== "undefined"){
        return <Ratings reviews = {reviews}/>
    }
    else {
        return <></>
    }
}

export function VacationHousePage() {
    const [house, setHouse] = useState({});
    const [houseReviews, setHouseReviews] = useState([])
    const [reservations, setReservations] = useState([])
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const {id} = useParams();
    var [imgs, setImgs] = useState([{thumbnail: '', original: ''}]);
    
    const fetchHouse = () => {
        axios
        .get("http://localhost:4444/house/houseprof/" + id)
        .then(res => {
            setHouse(res.data);
            setImgs([]);
        });
    };
    const fetchReviews = () => {
        axios
        .get("http://localhost:4444/review/getReviews/" + id)
        .then(res => {
            setHouseReviews(res.data);
        });
    };

    const fetchReservations = () => {
        axios
         .get("http://localhost:4444/house/getReservations/" + id)
         .then(res => {        
            setReservations(res.data);
         })
    };

    useEffect(() => {
        fetchHouse();
        fetchReviews();
        fetchReservations();
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
        <HouseInfo house={house}/>
        <Update closeModal={handleClose} showModal={show} vacationHouse = {house}/>
        <div className='p-5 pt-0'>
            <Gallery house={house} images={imgs}/>
            <hr/>
            <Reservations reservations={house.quickReservations} name={house.name} address={house.address}/>
            <footer className="blockquote-footer">Svi izlasci iz vikendice obavljaju se do 10:00h.</footer>
            <hr/>
            <CalendarComp reservations={reservations} reservable={true} house={house} perHour={false}/>    
            <ReviewsComp reviews = {houseReviews}/>
        </div>
        <BeginButton/>
    </>

    )
}
export default VacationHousePage;