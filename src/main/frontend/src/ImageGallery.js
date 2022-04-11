import React from 'react';

import {Carousel, Image} from "react-bootstrap";




export default function ImageGallery({images}) {
    return (<>

            <div className="d-flex">
                <div style={{width: "25%"}}/>
                <Carousel slide="false">

                    {images.map((image, key) => (
                        <Carousel.Item>
                            <Image className="carousel-image"
                                 height="500em"
                                 width="700em !important"
                                 src={require("./images/fishing1.jpg")}
                                 alt={key}/>
                        </Carousel.Item>
                    ))}
                </Carousel>
                <div style={{width: "25%"}}/>
            </div>
        </>
    )
}

