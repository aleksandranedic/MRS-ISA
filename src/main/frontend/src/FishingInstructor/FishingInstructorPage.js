import axios from "axios";
import React, {useEffect, useState} from "react";
import Banner from "../Banner";
import {FishingInstructorInfo} from "./FishingInstructorInfo";

import ImageGallery from "../ImageGallery";
import {FishingInstructorForm} from "./FishingInstructorForm";
import AdventureCarousel from "../Adventure/AdventureCarousel";
import Navigation from "../Navigation/Navigation";
import {useParams} from "react-router-dom";
import {Calendar} from "../Calendar/Calendar";


const FishingInstructors = ({id}) => {

    const [fishingInstructor, setFishingInstructor] = useState([]);
    const [adventures, setAdventures] = useState([]);


    const fetchFishingInstructors = () => {
        axios.get("http://localhost:4444/fishinginstructor/"+ id).then(res => {
            setFishingInstructor(res.data);
        });
    };

    const fetchAdventures = () => {
        axios.get("http://localhost:4444/adventure/owner/" + id).then(res => {
            console.log(res.data);
            setAdventures(res.data);
        });
    };

    useEffect(() => {
        fetchFishingInstructors();
    }, []);

    useEffect(() => {
        fetchAdventures();
    }, []);

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);

    let html;
  
    if (fishingInstructor.length !== 0) {
        html = (<div key={fishingInstructor.id}>

            <Banner caption={fishingInstructor.firstName + " " + fishingInstructor.lastName}/>

            <Navigation buttons={
                [
                    {text: "Osnovne informacije", path: "#info"},
                    {text: "Avanture", path: "#adventures"},
                    {text: "Kalendar zauzetosti", path: "#calendar"}
                ]}
                        editable={true} editFunction={handleShow} searchable={true} showProfile={true}
            />

            <FishingInstructorInfo fishingInstructor={fishingInstructor}/>
            <hr className="me-5 ms-5"/>

            <AdventureCarousel adventures={adventures} add={true}/>
            <hr className="me-5 ms-5"/>
            <FishingInstructorForm show={show} setShow={setShow} fishingInstructor={fishingInstructor}/>
            <Calendar adventure={fishingInstructor} reservable={false}/>
        </div>)
    }

    return html;

};

export function FishingInstructorPage() {
    const {id} = useParams();
    return (
        <>

            <FishingInstructors id={id}/>

        </>


    )
}