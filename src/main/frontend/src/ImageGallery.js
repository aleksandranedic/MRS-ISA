import React, { Component } from 'react';
import ReactDOM from 'react-dom';


import v1 from "./images/vikendica1.jpeg"
import v2 from "./images/vikendica2.jpeg"
import v3 from "./images/vikendica3.jpeg"
import {Carousel} from "react-bootstrap";
import boatnotext from "./images/boatsnotext.png";



export default function ImageGallery() {
    return (<>

            <div className="d-flex">
                <div style={{width: "25%"}}>  </div>
                <Carousel slide="false">

                    <Carousel.Item>
                        <img className="carousel-image" height="500em" width="700em !important" src={v1} sstyle={{objectFit: "contain !important"}}
                             alt="First"/>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img className="carousel-image" height="500em" width="700em" src={v2} style={{objectFit: "contain !important"}}
                             alt="First"/>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img className="carousel-image" height="500em" width="700em" src={v3}  style={{objectFit: "contain !important"}}
                             alt="First"/>
                    </Carousel.Item>



                </Carousel>
                <div style={{width: "25%"}}>  </div>
            </div>
    </>


    )


}

