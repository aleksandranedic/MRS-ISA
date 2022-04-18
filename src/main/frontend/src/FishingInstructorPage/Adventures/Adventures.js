import React from 'react'
import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";
import AdventureCard from "./AdventureCard";
import {AddAdventure} from "./AddAdventure";


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

export default function Adventures({adventures}) {
    return (<div className="m-5">
            <Carousel responsive={responsive} interval="25000">
                {adventures.map((adventure, key) => {
                        return <AdventureCard adventure={adventure}/>
                    }
                )}
                <AdventureCard adventure={{
                    title: "Naslov avanture",
                    description: "Opis avanture"
                }}/>
                <AddAdventure/>

            </Carousel>
        </div>

    )
}


