import React, {useEffect, useState} from "react";
import axios from "axios";
import ImagesGallery from "../ImageGallery";
import Banner from "../Banner";
import Navigation from "../Navigation/Navigation";
import AdventureInfo from "./AdventureInfo";
import {Calendar} from "../Calendar/Calendar";
import {AdventureForm} from "./AdventureForm";
import {useParams} from "react-router-dom";

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
    const [images, setImages] = useState([]);

    let html;
    const fetchAdventures = () => {
        axios.get("http://localhost:4444/adventure/"+ id).then(res => {
            setAdventure(res.data);
            setImages([]);
        });
    };

    useEffect(() => {
        fetchAdventures();
    }, []);


    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    if (adventure.length !== 0) {
        html = (<div>
            <Banner caption={adventure.title}/>
            <Navigation buttons={
                [
                    {text: "Osnovne informacije", path: "#info"},
                    // {text: "Fotografije", path: "#"},
                    // {text: "Akcije", path: "#"},
                    {text: "Kalendar zauzetosti", path: "#calendar"}
                ]}
                        editable={true} editFunction={handleShow} searchable={true}
            />
            <AdventureForm show={show} setShow={setShow} adventure={adventure}/>
            <AdventureInfo adventure={adventure}/>
            <Calendar adventure={adventure} reservable={true}/>
            )
        </div>)
    }

    return (html)
}


export default AdventurePage;