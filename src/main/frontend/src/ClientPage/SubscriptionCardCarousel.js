import Carousel from "react-multi-carousel";
import {responsive} from "../Consts";
import {Container} from "react-bootstrap";
import React from "react";
import SubscriptionCard from "./SubscriptionCard";

export default function SubscriptionCardCarousel({subs}) {
    return <>
        <Container className='d-flex p-0'>
            <Carousel className="w-100 h-100 " responsive={responsive} interval="25000">
                {subs.map((entity) => {
                        return <SubscriptionCard entity={entity}/>
                    }
                )}
            </Carousel>

        </Container>
    </>
}