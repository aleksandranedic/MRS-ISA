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

    function handleDeleteAccount(clientId){
        return null
        //znaci ovde treba axios zahtev da obrisem korisnika
    }
    function handleUpdateAccount(userDTO){
        return null
        //ovde mi treba azuriranje
    }

    return clients.map((client, index) => {
        return <div key={index}>
            <Banner caption={client.firstName + " " +client.lastName}/>
            <UpdateClientInfo client={client} handleDeleteAccount={handleDeleteAccount} handleClose={handleClose} showPopUp={show} />
            <Navigation handleEvent={handleShow}/>
            <ClientInfo
                firstName={client.firstName}
                lastName={client.lastName}
                address={"moras pogledati to"}
                email={client.email}
                phoneNumber={client.phoneNumber}
                city={"Novi sad"}
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



