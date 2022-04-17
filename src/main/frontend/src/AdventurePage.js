import React, {useEffect, useState} from "react";


import axios from "axios";
import ImageGallery from "./ImageGallery";
import QuickReservations from "./QuickReservations";
import Banner from "./Banner";
import Navigation from "./Navigation";
import AdventureInfo from "./AdventureInfo";

const Adventures = () => {

    const [adventures, setAdventures] = useState([]);

    const fetchAdventures = () => {
        axios.get("http://localhost:4444/adventure").then(res => {
            console.log(res);
            setAdventures(res.data);
        });
    };

    useEffect(() => {
        fetchAdventures();
    }, []);

    return adventures.map((adventure, index) => {
        return <div key={index}>

            <Banner caption={adventure.title}/>
            <Navigation/>
            <AdventureInfo address={adventure.address.street}
                           description={adventure.description}
                           rulesAndRegulations={adventure.rulesAndRegulations}
                           numberOfClients={adventure.numberOfClients}
                           fishingEquipment={adventure.fishingEquipment}
                           biography={adventure.owner.biography}/>
        </div>
    })
};


export function AdventurePage() {
    return (
        <>
            <Adventures/>
            <ImageGallery/>
            <QuickReservations/>
        </>


    )
export default AdventurePage;