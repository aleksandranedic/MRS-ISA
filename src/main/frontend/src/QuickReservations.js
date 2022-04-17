import React from 'react'
import {} from 'bootstrap'
import {Button, Card} from 'react-bootstrap'
import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";
import v1 from "./images/vikendica1.jpeg"
import v2 from "./images/vikendica2.jpeg"
import v3 from "./images/vikendica3.jpeg"
import QuickReservation from "./QuickReservation";
import PlusCard from "./PlusCard";
const responsive = {
    superLargeDesktop: {
        // the naming can be any, depends on you.
        breakpoint: { max: 4000, min: 3000 },
        items: 5
    },
    desktop: {
        breakpoint: { max: 3000, min: 1400 },
        items: 4
    },
    desktop2: {
        breakpoint: { max: 1400, min: 1024 },
        items: 3
    },
    tablet: {
        breakpoint: { max: 1024, min: 700 },
        items: 2
    },
    mobile: {
        breakpoint: { max: 700, min: 0 },
        items: 1
    }
};

export default function QuickReservations() {
    return (<div className="m-5">
            <Carousel responsive={responsive} interval="25000">
                <QuickReservation startDate="25.05." endDate="28.05." numberOfPeople="5" price="100e" tags={["Cool", "Outside"]} image={v1}/>
                <QuickReservation startDate="21.05." endDate="27.05." numberOfPeople="3" price="140e" tags={["Warm", "Outside"]} image={v2}/>

                <QuickReservation startDate="25.05." endDate="28.05." numberOfPeople="5" price="100e" tags={["Cool", "Outside"]} image={v3}/>
                <PlusCard/>
            </Carousel>
        </div>


    )
}