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

const HOST = "http://localhost:4444";

const Gallery = ({boat, images}) => {
    if (typeof boat.imagePaths !== "undefined"){
        let empty = images.length === 0;
        for (let i=0; i<boat.imagePaths.length;i++){
            if (!boat.imagePaths[i].includes(HOST)){
                boat.imagePaths[i] = HOST + boat.imagePaths[i];
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
        if (boat.navigationEquipment.length == 0){
            boat.navigationEquipment = [{id:0, text:''}]
        }
        if (boat.additionalServices.length == 0){
            boat.additionalServices = [{id:0, text:''}]
        }
        if (boat.fishingEquipment.length == 0){
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
        return <Ratings reviews = {reviews}/>
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
            <hr/>
            <Gallery boat={boat} images={imgs}/>
            <hr/>
            <Reservations reservations={boat.quickReservations} name={boat.name} address={boat.address}/>
            <ReviewsComp reviews = {boatReviews}/>
        </div>
        <BeginButton/>
    </>

    )
}
export default BoatProfilePage;