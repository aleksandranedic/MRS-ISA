import React from 'react'
import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";

import QuickReservation from "./QuickReservation";
import PlusCard from "./PlusCard";

const responsive = {
    superLargeDesktop: {
        // the naming can be any, depends on you.
        breakpoint: {max: 4000, min: 3000},
        items: 5
    },
    desktop: {
        breakpoint: {max: 3000, min: 1400},
        items: 4
    },
    desktop2: {
        breakpoint: {max: 1400, min: 1024},
        items: 3
    },
    tablet: {
        breakpoint: {max: 1024, min: 700},
        items: 2
    },
    mobile: {
        breakpoint: {max: 700, min: 0},
        items: 1
    }
};

export default function QuickReservations({reservations}) {
    return (<div className="m-5">
            <Carousel responsive={responsive} interval="25000">


                <QuickReservation startDate={"25.05."} endDate={"26.05."} numberOfPeople={7} price={100}
                                  tags={[]} image={""}/>
                <PlusCard/>
            </Carousel>
        </div>


    )


    /*{reservations.map(reservation=> {
                        return <QuickReservation startDate={reservation.startDate} endDate={reservation.endDate} numberOfPeople={reservation.numberOfClients} price={reservation.price}
                                                 tags={[]} image={reservation.resource.images.item(0)}/>

                    })}*/

}
