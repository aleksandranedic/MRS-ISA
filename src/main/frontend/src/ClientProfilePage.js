import axios from "axios";
import React, { useEffect, useState } from "react";
import Banner from "./Banner";
import Navigation from "./Navigation";
import ClientInfo from "./ClientInfo";
import ClientLoyalty from "./ClientLoyalty";

const Client = () => {
    const [client, setClient] = useState([]);

    const fetchClients = () => {
        axios.get("http://localhost:4444/client").then(res => {
            console.log(res);
            setClient(res.data);
        });
    };

    useEffect(() => {
        fetchClients();
    }, []);

    return fetchClients.map((client, index) => {
        return <div key={index}>
        </div>
    })
};

export default function ClientProfilePage() {
    return (
        <>
            <Banner caption={"Petar  Milosevic"} />
            <Navigation />
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
        </>
    )
}
