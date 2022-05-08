import React from 'react'
import 'bootstrap/dist/css/bootstrap.css'
import "../CustomCard.css"
import CustomCard from "../CustomCard";
import {frontLink} from "../Consts";


export default function AdventureCard({adventure, editable}) {

    return (<CustomCard
        imagePath={"../images/fishing1.jpg"}
        editable={editable}
        itemToEdit={adventure}
        title={adventure.title}
        subtitle={adventure.description}
        link={frontLink + "adventure/" + adventure.id}
        type={"adventure"}

    />)


}