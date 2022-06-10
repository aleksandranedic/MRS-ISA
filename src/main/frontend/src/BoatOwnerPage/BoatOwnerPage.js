import React, {useEffect, useState} from 'react';
import axios from "axios";
import Banner from '../Banner';
import BeginButton from '../BeginButton';

import BoatOwnerForm from './BoatOwnerForm'
import OwnerInfo from '../OwnerInfo';
import OwnerBoats from './OwnerBoats';
import AddBoat from './AddBoat';
import {useParams} from "react-router-dom";
import Navigation from "../Navigation/Navigation";
import {backLink, profilePicturePlaceholder} from '../Consts';
import {Calendar} from "../Calendar/Calendar";
import {ReservationCardGrid} from "../Calendar/ReservationCardGrid";
import {ReservationsToReview} from "../Calendar/ReservationsToReview";
import {processReservationsForUsers} from "../ProcessToEvent";
import Ratings from '../Reviews/Ratings';
import {Complaints} from "../Complaints";
import {isMyPage} from "../Autentification";

const UpdateOwner = ({show, setShow, owner}) => {
    if (typeof owner.firstName !== "undefined") {
        if (owner.profileImg !== null) {
            var profileImg = backLink + owner.profileImg.path;
        } else {
            var profileImg = profilePicturePlaceholder;
        }
        return <BoatOwnerForm show={show} setShow={setShow} owner={owner} profileImg={profileImg}/>
    } else {
        return <></>
    }
}


const ReviewsComp = ({reviews}) => {
    if (typeof reviews !== "undefined") {
        return <Ratings reviews={reviews} type={"boatOwner"}/>
    } else {
        return <></>
    }
}

function BoatOwnerPage() {
    const {id} = useParams();

    const [boatOwner, setBoatOwner] = useState({address: '', profileImg: {path: ""}});
    let [ownerBoats, setOwnerBoats] = useState([]);
    const [ownerReviews, setOwnerReviews] = useState([])

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);

    const [reservations, setReservations] = useState([]);
    const [events, setEvents] = useState(null);
    const [myPage, setMyPage] = useState(null);


    const fetchReservations = () => {
        axios.get(backLink + "/boat/reservation/boatOwner/" + id).then(res => {
            setReservations(res.data);
            setEvents(processReservationsForUsers(res.data));
        })
    }


    const fetchOwnerBoats = () => {
        axios
            .get(backLink + "/boat/getownerboats/" + id)
            .then(res => {
                var boats = res.data;
                for (let boat of boats) {
                    if (!boat.thumbnailPath.includes(backLink)) {
                        boat.thumbnailPath = backLink + boat.thumbnailPath;
                    }
                }
                setOwnerBoats(res.data);
            });
    };
    const fetchBoatOwner = () => {
        axios
            .get(backLink + "/boatowner/" + id)
            .then(res => {
                setBoatOwner(res.data);
                setMyPage(isMyPage("BOAT_OWNER", id));

            });
    };

    const fetchReviews = () => {
        axios
            .get(backLink + "/review/getVendorReviews/" + id)
            .then(res => {
                setOwnerReviews(res.data);
            });
    };

    useEffect(() => {
        fetchBoatOwner();
        fetchOwnerBoats();
        fetchReservations();
        fetchReviews();
    }, []);
    let buttons = [
        {text: "Osnovne informacije", path: "#info"},
        {text: "Brodovi", path: "#boats"},
        {text: "Kalendar", path: "#calendar"},
    ];
    if (myPage) {
        buttons.push({text: "Rezervacije", path: "#reservations"});
    }

    buttons.push({text: "Recenzije", path: "#reviews"});
    return (
        <>
            <Banner caption={boatOwner.firstName + " " + boatOwner.lastName}/>
            <Navigation buttons={buttons}
                        editable={myPage} editFunction={handleShow} searchable={true}
                        showReports={myPage} type="boat"/>
            <AddBoat/>
            <UpdateOwner show={show} setShow={setShow} owner={boatOwner}/>


            <div className='p-5 pt-0'>

                <OwnerInfo
                    name={boatOwner.firstName + " " + boatOwner.lastName}
                    rate={4.5}
                    email={boatOwner.email}
                    phoneNum={boatOwner.phoneNumber}
                    address={boatOwner.address}
                    profileImg={boatOwner.profileImg !== null ? backLink + boatOwner.profileImg.path : profilePicturePlaceholder}
                />
                <hr/>
                <OwnerBoats boats={ownerBoats} myPage={myPage}/>

            </div>
            <h2 className="mt-5 ms-5">Kalendar</h2>

            <hr className="me-5 ms-5"/>

            <Calendar events={events} reservable={false}/>


            {myPage && <div id="reservations">
                <ReservationsToReview type={"boat"}/>
                <h2 className="me-5 ms-5 mt-5" id="reservations">Predstojaće rezervacije</h2>
                <hr className="me-5 ms-5"/>

                <ReservationCardGrid reservations={reservations}/>
            </div>}

            <div className="ms-5">
                <h2 className="mt-5" id="reviews">Recenzije</h2>
                <hr/>
                <ReviewsComp reviews={ownerReviews}/>
            </div>

            <Complaints type={"boatOwner"} toWhom={boatOwner.firstName + " " + boatOwner.lastName}/>
            <BeginButton/>
        </>
    );
}

export default BoatOwnerPage;