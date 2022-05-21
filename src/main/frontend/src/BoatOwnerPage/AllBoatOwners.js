import {useEffect, useState} from "react";
import axios from "axios";
import {backLink} from "../Consts";

export function GetAllBoatOwners() {
    const [boatOwners, setBoatOwners] = useState([]);

    const fetch = () => {
        axios.get(backLink + "/boatowner").then(res => {
            console.log(res.data);
            setBoatOwners(res.data);
        });
    };

    useEffect(() => {
        fetch();
    }, []);

    return boatOwners;
}