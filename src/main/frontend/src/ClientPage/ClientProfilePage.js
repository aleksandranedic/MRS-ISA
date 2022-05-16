import axios from "axios";
import React, {useEffect, useState} from "react";
import Banner from "../Banner";
import ClientInfo from "./ClientInfo";
import ClientLoyalty from "./ClientLoyalty";
import UpdateClientInfo from "./UpdateClientInfo"
import Navigation from "../Navigation/Navigation";

axios.interceptors.request.use(config => {
        config.headers.authorization = "Bearer " + localStorage.getItem('token')
        return config
    }
)
const Client = () => {
    const [client, setClient] = useState([]);

    let html;
    const fetchClient = () => {
        axios.get("http://localhost:4444/getLoggedUser").then(res => {
            console.log(res);
            setClient(res.data)
        });
    };

    useEffect(() => {
        fetchClient();
    }, []);

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

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



