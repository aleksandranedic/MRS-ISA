import React, {useState} from 'react'
import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";
import AdventureCard from "./AdventureCard";
import {AdventureModal} from "./AdventureModal";
import PlusCard from "../PlusCard";
import {responsive} from "../Consts";



export default function AdventureCarousel({adventures, add, ownerId}) {
    const [show, setShow] = useState(false);
    let editable;
    editable = add === true;

    return (<div className="m-5" id="adventures">
            <Carousel responsive={responsive} interval="25000">
                {adventures.map((adventure) => {
                        return <AdventureCard key={adventure.id} adventure={adventure} editable={editable}/>
                    }
                )}
                {
                    add &&
                    <>
                        <PlusCard onClick={() => setShow(true)}/>
                        <AdventureModal show={show} setShow={setShow} ownerId={ownerId}/>
                    </>
                }


            </Carousel>
        </div>

    )
}


