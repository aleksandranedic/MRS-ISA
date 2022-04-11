import React from 'react'
import {} from 'bootstrap'
import {} from 'react-bootstrap'
import Carousel from 'react-bootstrap/Carousel'

import './Banner.css';

export default function Banner({caption}) {
    return (<div>
            <Carousel fade>

                <Carousel.Item>
                    <img className="carousel-image d-block w-100" height="500vh" src={require("./images/boatsnotext.png")}
                        alt="First"/>
                    <Carousel.Caption >
                        <h1>{caption}</h1>
                    </Carousel.Caption>
                </Carousel.Item>

                <Carousel.Item>
                    <img className="d-block w-100" height="500vh" src={require("./images/housenotext.png")}
                         alt="Second"
                    />
                    <Carousel.Caption>
                        <h1>{caption}</h1>
                    </Carousel.Caption>
                </Carousel.Item>



            </Carousel>

        </div>


    )
}