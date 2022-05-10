import React from 'react'
import 'bootstrap/dist/css/bootstrap.css'
import "../CustomCard.css"
import CustomCard from "../CustomCard";
import {frontLink} from "../Consts";


export default function BoatCard({boat, editable}) {

    return (<CustomCard
        imagePath={"../images/fishing1.jpg"}
        editable={editable}
        itemToEdit={boat}
        title={boat.title}
        subtitle={boat.description}
        link={frontLink + "boat/" + boat.id}
        type={"boat"}
        address={boat.address.street + " " + boat.address.number + ", " + boat.address.place}
    />)


}