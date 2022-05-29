import axios from "axios";
import React, {useEffect, useState} from "react";
import Banner from "../Banner";
import ClientInfo from "./ClientInfo";
import ClientLoyalty from "./ClientLoyalty";
import UpdateClientInfo from "./UpdateClientInfo"
import Navigation from "../Navigation/Navigation";
import {useParams} from "react-router-dom";
import {backLink} from "../Consts";
import {Calendar} from "../Calendar/Calendar";
import {ReservationCardGrid} from "../Calendar/ReservationCardGrid";
import {Collapse} from "react-bootstrap";
import {ReservationsTable} from "../Calendar/ReservationsTable";


const Client = () => {
    const [client, setClient] = useState([]);
    const [reservations, setReservations] = useState([]);

    const {id}=useParams()
    let html;
    const fetchClient = () => {
        axios.get(backLink+"/client/"+id).then(res => {
            console.log(res);
            setClient(res.data)
        });
    };

    const fetchReservations = () => {

        let newReservations = [];
        axios.get(backLink+ "/adventure/reservation/client/" + id).then(res => {

            for (let index in res.data) {
                newReservations.push(res.data.at(index))
            }
        })

        axios.get(backLink+ "/boat/reservation/client/" + id).then(res => {


            for (let index in res.data) {
                newReservations.push(res.data.at(index))
            }

        })

        axios.get(backLink+ "/house/reservation/client/" + id).then(res => {
            for (let index in res.data) {
                newReservations.push(res.data.at(index))
            }

        })
        setReservations(newReservations);
    }

    useEffect(() => {
        fetchClient();
        if(localStorage.getItem("token")!==null && localStorage.getItem("token")!=="") //dodao sam jer neko kad gleda njegov profil ne moze tako da ostavi
            fetchReservations();
    }, []);

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [open, setOpen] = useState(false);

    if (client.length !== 0) {
        html = (<div>
            <Banner caption={client.firstName + " " + client.lastName}/>
            <UpdateClientInfo client={client} handleClose={handleClose}
                              showPopUp={show} setClient={setClient}/>
            <Navigation buttons={
                [
                    {text: "Osnovne informacije", path: "#"}
                ]}
                        editable={true} editFunction={handleShow}
            />
            <ClientInfo
                firstName={client.firstName}
                lastName={client.lastName}
                address={client.address}
                email={client.email}
                phoneNumber={client.phoneNumber}
            />
            <ClientLoyalty/>


            <hr className="me-5 ms-5"/>
            <Calendar reservations={reservations} reservable={false}/>

            <h2 className="me-5 ms-5 mt-5" id="reservations">Predstojaće rezervacije</h2>
            <hr className="me-5 ms-5"/>

            <ReservationCardGrid reservations={reservations}/>

            <h2 className="me-5 ms-5 mt-5" onClick={() => setOpen(!open)}
                aria-controls="reservationsTable"
                aria-expanded={open}
                style = {{cursor: "pointer"}}
            >Istorija rezervacija</h2>

            <hr className="me-5 ms-5"/>
            <Collapse in={open}>
                <div id="reservationsTable">
                    <ReservationsTable  reservations={reservations} showResource={false}/>
                </div>
            </Collapse>

        </div>)
    }
    return (html)
};

export function ClientProfilePage() {
    return (
        <>
            <Client/>
        </>
    )
}



