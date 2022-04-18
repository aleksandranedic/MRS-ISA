import axios from "axios";
import React, {useEffect, useState} from "react";
import Banner from "./Banner";
import Navigation from "./Navigation";
import ClientInfo from "./ClientInfo";
import ClientLoyalty from "./ClientLoyalty";
import UpdateClientInfo from "./UpdateClientInfo"

localStorage.setItem('user', '2');
const Client = () => {
    const [client, setClient] = useState([]);

    let html;
    const fetchClient = () => {
        axios.get("http://localhost:4444/client/"+localStorage.getItem('user')).then(res => {
            console.log(res);
            setClient(res.data);
        });
    };

    useEffect(() => {
        fetchClient();
    }, []);

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    function handleDeleteAccount() {
        axios.delete("http://localhost:4444/client/"+localStorage.getItem('user')).then(
            res=>{
                console.log(res)
                window.location.reload(false);
            }
        )
    }

    function handleUpdateAccount(userDTO) {
        setClient(userDTO)
        axios.put("http://localhost:4444/client/"+localStorage.getItem('user'),userDTO).then(res => {
            console.log(res);
            setClient(res.data);
        });
    }
    if (client.length !== 0) {
        html = (<div>
            <Banner caption={client.firstName + " " + client.lastName}/>
            <UpdateClientInfo client={client} handleDeleteAccount={handleDeleteAccount} handleClose={handleClose}
                              showPopUp={show} updateClient={handleUpdateAccount}/>
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



