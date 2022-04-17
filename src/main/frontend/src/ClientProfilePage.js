import axios from "axios";
import React, {useEffect, useState} from "react";
import Banner from "./Banner";
import Navigation from "./Navigation";
import ClientInfo from "./ClientInfo";
import ClientLoyalty from "./ClientLoyalty";
import UpdateClientInfo from "./UpdateClientInfo"

const Client = () => {
    const [clients, setClient] = useState([]);

    const fetchClients = () => {
        axios.get("http://localhost:4444/client").then(res => {
            console.log(res);
            setClient(res.data);
        });
    };

    useEffect(() => {
        fetchClients();
    }, []);

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return clients.map((client, index) => {
        return <div key={index}>
            <Banner caption={"Petar  Milosevic"}/>
            <UpdateClientInfo handleClose={handleClose} showPopUp={show} />
            <Navigation handleEvent={handleShow}/>
            <ClientInfo
                firstName={"Petar"}
                lastName={"Milosevic"}
                address={"Jovana Cvijica 25"}
                email={"petar.milosevic@eamil.com"}
                phoneNumber={"0656452628"}
                city={"Novi Sad"}
                country={"Srbija"}
            />
            <ClientLoyalty/>
        </div>
    })
};

export function ClientProfilePage() {
    return (
        <>
            <Client/>
        </>
    )
}



