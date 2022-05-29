import React, {useState, useEffect} from 'react';
import axios from "axios";
import Banner from '../Banner';
import BeginButton from '../BeginButton';

import OwnerInfo from '../OwnerInfo';
import OwnerHouses from './OwnerHouses';
import AddVacationHouse from './AddVacationHouse';
import HouseOwnerForm from "./HouseOwnerForm";
import {useParams} from "react-router-dom";
import Navigation from "../Navigation/Navigation";
import {backLink, profilePicturePlaceholder} from '../Consts';
import {Calendar} from "../Calendar/Calendar";
import {ReservationCardGrid} from "../Calendar/ReservationCardGrid";
import {Collapse} from "react-bootstrap";
import {ReservationsTable} from "../Calendar/ReservationsTable";
import {ReservationsToReview} from "../Calendar/ReservationsToReview";
import {processReservationsForUsers} from "../ProcessToEvent";


const  UpdateOwner = ({show, setShow, owner}) => {
    if (typeof owner.firstName !== "undefined"){
        if (owner.profileImg !== null) {
            let profileImg = backLink + owner.profileImg.path;
        }
        else {
            let profileImg = profilePicturePlaceholder;
        }
        return <HouseOwnerForm show={show} setShow={setShow} owner={owner} profileImg={profileImg} />
    }
    else {
        return <></>
    }
}

function HouseOwnerPage() {
    const {id} = useParams();
    const [houseOwner, setHouseOwner] = useState({address: '', profileImg: {path: ""}});
    const [ownerHouses, setOwnerHouses] = useState([]);
    const [events, setEvents] = useState(null);
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const [reservations, setReservations] = useState([]);
    const [open, setOpen] = useState(false);

    const fetchReservations = () => {
        axios.get(backLink + "/house/reservation/vacationHouseOwner/" + id).then(res => {
            setReservations(res.data);
            setEvents(processReservationsForUsers(res.data));
        })
    }

    const fetchOwnerHouses = () => {
        axios
            .get("http://localhost:4444/house/getownerhouses/" + id)
            .then(res => {
                var houses = res.data;
                for (let house of houses) {
                    if (!house.thumbnailPath.includes(backLink)) {
                        house.thumbnailPath = backLink + house.thumbnailPath;
                    }
                }
                setOwnerHouses(res.data);
            });
    };
    const fetchHouseOwner = () => {
        axios
            .get(backLink + "/houseowner/" + id)
            .then(res => {
                setHouseOwner(res.data);
            });
    };
    useEffect(() => {
        fetchOwnerHouses();
        fetchReservations();
        fetchHouseOwner();
    }, []);

    let html = "";

    html = <><>
        <Banner caption={houseOwner.firstName + " " + houseOwner.lastName}/>
        <Navigation buttons={
            [
                {text: "Osnovne informacije", path: "#info"},
                {text: "Vikendice", path: "#houses"},
                {text: "Rezervacije", path: "#sales"},
                {text: "Izveštaji", path: "#reports"}
            ]}
                    editable={true} editFunction={handleShow} searchable={true} showProfile={true}/>
        <AddVacationHouse/>
        <UpdateOwner show={show} setShow={setShow} owner={houseOwner}/>
        <div className='p-5 pt-0'>
            {houseOwner.profileImg !== null ?

                <OwnerInfo
                    name={houseOwner.firstName + " " + houseOwner.lastName}
                    rate={4.5}
                    email={houseOwner.email}
                    phoneNum={houseOwner.phoneNumber}
                    address={houseOwner.address}
                    profileImg={backLink + houseOwner.profileImg.path}
                />
                :
                <OwnerInfo
                    name={houseOwner.firstName + " " + houseOwner.lastName}
                    rate={4.5}
                    email={houseOwner.email}
                    phoneNum={houseOwner.phoneNumber}
                    address={houseOwner.address}
                    profileImg={profilePicturePlaceholder}
                />
            }
            <hr/>
            <OwnerHouses houses={ownerHouses}/>

        </div>


        <hr className="me-5 ms-5"/>
        <Calendar events={events} reservable={false}/>

        <h2 className="me-5 ms-5 mt-5" id="reservations">Predstojaće rezervacije</h2>
        <hr className="me-5 ms-5"/>

        <ReservationCardGrid reservations={reservations}/>

        <ReservationsToReview type={"vacationHouse"}/>

        <h2 className="me-5 ms-5 mt-5" onClick={() => setOpen(!open)}
            aria-controls="reservationsTable"
            aria-expanded={open}
            style={{cursor: "pointer"}}
        >Istorija rezervacija</h2>

        <hr className="me-5 ms-5"/>
        <Collapse in={open}>
            <div id="reservationsTable">
                <ReservationsTable reservations={reservations} showResource={false}/>
            </div>
        </Collapse>

        <BeginButton/>
    </>
    </>;

    return html;
}

export default HouseOwnerPage;