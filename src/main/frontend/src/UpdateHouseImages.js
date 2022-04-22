import React from 'react';
import {Container, Button, Image} from'react-bootstrap'
import OwlCarousel from 'react-owl-carousel';  

import {BsTrashFill} from 'react-icons/bs'

import v1 from "./images/vikendica1.jpeg"
import v2 from "./images/vikendica2.jpeg"
import v3 from "./images/vikendica3.jpeg"
import v4 from "./images/vikendica4.jpeg"
import boatnotext from "./images/boatsnotext.png";

import "./UpdateHouseImages.css"

function UpdateHouseImages({images}) {

    const options = {
        center: true,
        responsiveClass: true,
        responsive: {
          0: {
            items: 1
          },
          600: {
            items: 3
          },
          1000: {
            items: 3
          },
          1400: {
            items: 3
          }
        },
        autoplay: false,
        dots: false,
        nav: true
      };
    return (
        <Container className='d-flex p-0'>            
            <OwlCarousel className="owl-theme owl-theme houses-images-carousel d-flex" loop {...options} navElement>    
                {images.map( (image) => (
                    <div className='house-images-container' key={image}>
                    <BsTrashFill  size={18} className="delete-photo"/>
                    <Image src={v1}/>
                  </div> 
                ))}     
            </OwlCarousel> 
        </Container>
    );
}

export default UpdateHouseImages;