import {useEffect, useState} from "react";
import axios from "axios";
import {backLink} from "../Consts";

export function GetAllAdventures() {
    const [adventures, setAdventures] = useState([]);

    const fetchAdventures = () => {
        axios.get(backLink + "/adventure").then(res => {
            console.log(res.data);
            setAdventures(res.data);
        });
    };

    useEffect(() => {
        fetchAdventures();
    }, []);

    return adventures;
}