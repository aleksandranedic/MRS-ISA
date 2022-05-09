import CustomCard from "../CustomCard";
import {frontLink} from "../Consts";
import React from "react";

export default function VacationHouseCard({vacationHouse, editable}) {

    return (<CustomCard
        imagePath={"../images/vikendica3.jpeg"}
        editable={editable}
        itemToEdit={vacationHouse}
        title={vacationHouse.title}
        subtitle={vacationHouse.description}
        link={frontLink + "house/" + vacationHouse.id}
        type={"houseOwner"}
        address={vacationHouse.address.street + " " + vacationHouse.address.number +", " + vacationHouse.address.place}

    />)


}