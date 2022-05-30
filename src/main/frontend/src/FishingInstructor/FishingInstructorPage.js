import axios from "axios";
import React, {useEffect, useState} from "react";
import Banner from "../Banner";

import {FishingInstructorForm} from "./FishingInstructorForm";
import AdventureCarousel from "../Adventure/AdventureCarousel";
import Navigation from "../Navigation/Navigation";
import {useParams} from "react-router-dom";
import {Calendar} from "../Calendar/Calendar";
import {backLink, profilePicturePlaceholder} from "../Consts";
import {ReservationCardGrid} from "../Calendar/ReservationCardGrid";
import {Collapse} from "react-bootstrap";
import OwnerInfo from "../OwnerInfo"
import {ReservationsTable} from "../Calendar/ReservationsTable";
import {ReservationsToReview} from "../Calendar/ReservationsToReview";
import {processReservationsForResources, processReservationsForUsers} from "../ProcessToEvent";
import {AddReview} from "../Reviews/AddReview";
import {isLoggedIn, isMyPage} from "../Autentification";
import BeginButton from "../BeginButton.js"

const FishingInstructors = ({id}) => {
    const [fishingInstructor, setFishingInstructor] = useState({address: '', profileImg: {path: ""}});
    const [adventures, setAdventures] = useState([]);
    const [reservations, setReservations] = useState([]);
    const [open, setOpen] = useState(false);
    const [events, setEvents] = useState(null);
    const [myPage, setMyPage] = useState(null);


    const fetchReservations = () => {
        axios.get(backLink + "/adventure/reservation/fishingInstructor/" + id).then(res => {
            console.log(res.data);
            setReservations(res.data);
            setEvents(processReservationsForUsers(res.data));
        })
    }

    const fetchFishingInstructors = () => {
        axios.get(backLink + "/fishinginstructor/" + id).then(res => {
            setFishingInstructor(res.data);
            setMyPage(isMyPage("FISHING_INSTRUCTOR", id));
        });
    };

    const fetchAdventures = () => {
        axios.get(backLink + "/adventure/owner/" + id).then(res => {
            setAdventures(res.data);
        });
    };

    useEffect(() => {
        fetchFishingInstructors();
        fetchAdventures();
        fetchReservations();
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
                        editable={myPage} editFunction={handleShow} searchable={true} showProfile={true}
            />


            {fishingInstructor.profileImg !== null ?

                <div className="pe-5 pt-0">
                    <OwnerInfo bio={fishingInstructor.biography}
                               name={fishingInstructor.firstName + " " + fishingInstructor.lastName}
                               rate={4.5}
                               email={fishingInstructor.email}
                               phoneNum={fishingInstructor.phoneNumber}
                               address={fishingInstructor.address}
                               profileImg={fishingInstructor.profileImg !== null ? backLink + fishingInstructor.profileImg.path : profilePicturePlaceholder}
                    />
                </div>
                :
                <div className="pe-5 pt-0">
                    <OwnerInfo bio={fishingInstructor.biography}
                               name={fishingInstructor.firstName + " " + fishingInstructor.lastName}
                               rate={4.5}
                               email={fishingInstructor.email}
                               phoneNum={fishingInstructor.phoneNumber}
                               address={fishingInstructor.address}
                               profileImg={profilePicturePlaceholder}
                    />
                </div>
            }
            <hr className="me-5 ms-5"/>

            <AdventureCarousel adventures={adventures} add={true} ownerId={fishingInstructor.id}/>

            <FishingInstructorForm show={show} setShow={setShow} fishingInstructor={fishingInstructor}  profileImg= {fishingInstructor.profileImg !== null ? backLink + fishingInstructor.profileImg.path : profilePicturePlaceholder}/>

            <hr className="me-5 ms-5"/>

            {isLoggedIn() && !myPage &&
                <div className="d-flex justify-content-center">
                    <AddReview type={"fishingInstructor"}/>
                </div>
            }

            <Calendar events={events} reservable={false}/>

            <hr className="me-5 ms-5"/>

            {myPage && <>

                <ReservationsToReview type={"adventure"}/>

                <ReservationCardGrid reservations={reservations}/>

                <h4 className="me-5 ms-5 mt-5" onClick={() => setOpen(!open)}
                    aria-controls="reservationsTable"
                    aria-expanded={open}
                    style={{cursor: "pointer"}}
                    id="reservationsHistory"
                >Istorija rezervacija</h4>

                <hr className="me-5 ms-5"/>
                <Collapse in={open}>
                    <div id="reservationsTable">
                        <ReservationsTable reservations={reservations} showResource={false}/>
                    </div>
                </Collapse>

                <BeginButton/>
            </>}
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