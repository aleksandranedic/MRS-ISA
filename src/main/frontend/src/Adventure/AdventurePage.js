import React, {useEffect, useState} from "react";
import axios from "axios";
import ImagesGallery from "../ImageGallery";
import Banner from "../Banner";
import Navigation from "../Navigation/Navigation";
import AdventureInfo from "./AdventureInfo";
import {Calendar} from "../Calendar/Calendar";
import {AdventureForm} from "./AdventureForm";

export function AdventurePage() {
    return (
        <>
            <Adventures/>


        </>
    )
}

const Adventures = ()  =>{

    const [adventure, setAdventure] = useState([]);
    let html;
    const fetchAdventures = () => {
        axios.get("http://localhost:4444/adventure/1").then(res => {
            setAdventure(res.data);
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
                    {text: "Osnovne informacije", path: "#"},
                    {text: "Fotografije", path: "#"},
                    {text: "Akcije", path: "#"},
                    {text: "Kalendar zauzetosti", path: "#"}
                ]}
                        editable={true} editFunction={handleShow} searchable={true}
            />
            <AdventureForm show={show} setShow={setShow} adventure={adventure}/>
            <AdventureInfo adventure={adventure}/>
            {/*<ImagesGallery images={[
            {original:
            thumbnail:

            }]} />*/}
            )
        </div>)
    }

    return (html)
}


export default AdventurePage;