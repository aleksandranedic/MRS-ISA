import ImagesGallery from "../ImageGallery";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {backLink} from "../Consts";

export function AdventureGallery({id, images})  {
    const [adventure, setAdventure] = useState([]);

    const fetchAdventure = () => {
        axios.get(backLink+"/adventure/dto/"+ id).then(res => {
            setAdventure(res.data);
        });
    };

    useEffect(() => {
        fetchAdventure();
    }, []);

    const HOST = "http://localhost:4444";
    if (typeof adventure.imagePaths !== "undefined"){

        let empty = images.length === 0;

        for (let i=0; i<adventure.imagePaths.length; i++){
            if (!adventure.imagePaths[i].includes(HOST)){
                adventure.imagePaths[i] = HOST + adventure.imagePaths[i];
                images.push({original:adventure.imagePaths[i], thumbnail:adventure.imagePaths[i]})
            } else if (empty){
                images.push({original:adventure.imagePaths[i], thumbnail:adventure.imagePaths[i]})
            }
        }
        return (<ImagesGallery images={images}/>)
    }
    else {
        return (<></>)
    }
}