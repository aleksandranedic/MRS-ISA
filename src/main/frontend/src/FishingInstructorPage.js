import axios from "axios";
import React, {useEffect, useState} from "react";
import Banner from "./Banner";
import {FishingInstructorInfo} from "./FishingInstructorInfo";
import Adventures from "./Adventures";
import Navigation from "./Navigation";


const FishingInstructors = () => {

    const [fishingInstructors, setFishingInstructors] = useState([]);

    const fetchFishingInstructors = () => {
        axios.get("http://localhost:4444/fishingInstructor").then(res => {
            console.log(res);
            setFishingInstructors(res.data);
        });
    };

    useEffect(() => {
        fetchFishingInstructors();
    }, []);

    return fishingInstructors.map((fishingInstructor, index) => {
        return <div key={index}>



        </div>
    })
};

export function FishingInstructorPage() {
    return (
        <>
            <Banner caption={"Petar" + " " + "Milosevic"}/>
            <Navigation/>
            <FishingInstructorInfo
                firstName={"Petar"}
                lastName={"Milosevic"}
                address={"Jovana Cvijica 25"}
                biography={"Jos sam bio sasvim mlad, neke barske ptice sam lovio tad..."}
                email={"petar.milosevic@eamil.com"}
                phoneNumber={"0656452628"}/>

            <Adventures adventures={[]}/>
        </>


    )
}
export default FishingInstructorPage;