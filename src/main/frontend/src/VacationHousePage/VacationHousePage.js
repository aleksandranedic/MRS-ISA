import React, {useEffect, useState} from "react";
import axios from "axios";
import ImagesGallery from "../ImageGallery";
import QuickReservations from "../QuickReservations";
import Banner from "../Banner";
import Navigation from "../Navigation";
import HouseInfo from "./HouseInfo";
import UpdateHouse from "./UpdateHouse"
import BeginButton from "../BeginButton";
import { useParams } from "react-router-dom";
import "react-image-gallery/styles/css/image-gallery.css";

const HOST = "http://localhost:4444";
const Gallery = ({house, images}) => {
    if (typeof house.imagePaths !== "undefined"){
        let empty = images.length === 0;
        for (let i=0; i<house.imagePaths.length;i++){
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

export function VacationHousePage() {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const {id} = useParams();
    const [house, setHouse] = useState({});
    var [imgs, setImgs] = useState([{thumbnail: '', original: ''}]);
    const fetchHouse = () => {
        axios
        .get("http://localhost:4444/house/houseprof/" + id)
        .then(res => {
            setHouse(res.data);

            setImgs([]);
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
                {text: "Akcije", path: "#"},
                {text: "Kalendar zauzetosti", path: "#"}
            ]}
                    editable={true} editFunction={handleShow}/>
        <HouseInfo house={house}/>
        <Update closeModal={handleClose} showModal={show} vacationHouse = {house}/>
        <div className='p-5 pt-0'>
            <hr/>
            <Gallery house={house} images={imgs}/>
            <hr/>
            <QuickReservations/>
        </div>
        <BeginButton/>
    </>

    )
}
export default VacationHousePage;