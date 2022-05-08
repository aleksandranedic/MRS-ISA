import React, {useState} from 'react'
import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";
import AdventureCard from "./AdventureCard";
import {AdventureForm} from "./AdventureForm";
import PlusCard from "../PlusCard";


export const responsive = {
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

export default function AdventureCarousel({adventures, add}) {
    const [show, setShow] = useState(false);
    let editable;
    editable = add === true;

    return (<div className="m-5">
            <Carousel responsive={responsive} interval="25000">
                {adventures.map((adventure) => {
                        return <AdventureCard key={adventure.id} adventure={adventure} editable={editable}/>
                    }
                )}
                {
                    add &&
                    <>
                        <PlusCard onClick={() => setShow(true)}/>
                        <AdventureForm show={show} setShow={setShow}/>
                    </>
                }


            </Carousel>
        </div>

    )
}


