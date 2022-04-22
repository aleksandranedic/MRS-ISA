import React, {useEffect, useState} from "react";
import axios from "axios";
import ImageGallery from "./ImageGallery";
import QuickReservations from "./QuickReservations";
import Banner from "./Banner";
import Navigation from "./Navigation";
import HouseInfo from "./HouseInfo";
import UpdateHouse from "./UpdateHouse"
import BeginButton from "./BeginButton";
import { useParams } from "react-router-dom";

const Houses = ({house}) => {
        return <div>
            <HouseInfo house={house} />
        </div>
};

const Gallery = ({house}) => {
    if (typeof house.imagePaths !== "undefined"){
        return <ImageGallery images={house.imagePaths}/>
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

    const fetchHouse = () => {
        axios
        .get("http://localhost:4444/house/houseprof/" + id)
        .then(res => {
            setHouse(res.data);
        });
    };
    useEffect(() => {
        fetchHouse();
    }, []);
  
    return (
    <>
        <Banner caption={house.title}/>
        <Navigation buttons={
            [
                {text: "Osnovne informacije", path: "#info"},
                {text: "Fotografije", path: "#gallery"},
                {text: "Akcije", path: "#"},
                {text: "Kalendar zauzetosti", path: "#"}
            ]}
                    editable={true} editFunction={handleShow}/>
        <Houses house={house}/>
        <UpdateHouse closeModal={handleClose} showModal={show} vacationHouse = {house}/>
        <div className='p-5 pt-0'>
            <hr/>
            <Gallery house={house}/>
            <hr/>
            <QuickReservations/>
        </div>
        <BeginButton/>
    </>

    )
}
export default VacationHousePage;