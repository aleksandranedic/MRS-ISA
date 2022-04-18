import axios from "axios";
import React, {useEffect, useState} from "react";
import Banner from "../Banner";
import {FishingInstructorInfo} from "./FishingInstructorInfo";
import Adventures from "./Adventures/Adventures";
import Navigation from "../Navigation";
import ImageGallery from "../ImageGallery";
import {EditFishingInstructor} from "./EditFishingInstructor";


const FishingInstructors = () => {

    const [fishingInstructor, setFishingInstructor] = useState([]);

    const fetchFishingInstructors = () => {
        axios.get("http://localhost:4444/fishinginstructor/1").then(res => {
            setFishingInstructor(res.data);
        });
    };

    useEffect(() => {
        fetchFishingInstructors();
    }, []);

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);

    let html;

    if (fishingInstructor.length !== 0) {
        html = (<div key={fishingInstructor.id}>

            <Banner caption={fishingInstructor.firstName + " " + fishingInstructor.lastName}/>

            <Navigation buttons={
                [
                    {text: "Osnovne informacije", path: "#"},
                    {text: "Avanture", path: "#"},
                    {text: "Kalendar zauzetosti", path: "#"}
                ]}
                        editable={true} editFunction={handleShow}
            />

            <FishingInstructorInfo fishingInstructor={fishingInstructor}/>
            <hr className="me-5 ms-5"/>
            <ImageGallery/>
            <hr className="me-5 ms-5"/>
            <Adventures adventures={[]}/>
            <hr className="me-5 ms-5"/>

            <EditFishingInstructor show={show} setShow={setShow} fishingInstructor={fishingInstructor}/>

        </div>)
    }



    return html;

};

export function FishingInstructorPage() {
    return (
        <>

            <FishingInstructors/>

        </>


    )
}