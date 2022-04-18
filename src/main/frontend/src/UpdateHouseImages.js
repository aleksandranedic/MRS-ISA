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

function UpdateHouseImages(props) {
 
  function showBin(e) {
    console.log(e.target.nextSibling)
  }
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
                <div className='house-images-container'>
                    <Image src={v1}/>
                    <BsTrashFill  size={18} className="delete-photo"/>
                </div>  
                <div className='houses-images-container'>
                    <Image src={v2}/>
                    <BsTrashFill size={18} className="delete-photo"/>
                </div>  
                <div className='houses-images-container'>
                    <Image src={v3} onMouseOver={showBin} />
                    <BsTrashFill  size={18} className="delete-photo"/>
                </div>  
                <div className='houses-images-container'>
                    <Image src={v4} onMouseOver={showBin}/>
                    <BsTrashFill size={18} className="delete-photo"/>
                </div>  
                <div className='houses-images-container'>
                    <Image src={boatnotext} onMouseOver={showBin}/>
                    <BsTrashFill size={18} className="delete-photo"/>
                </div>        
            </OwlCarousel> 
        </Container>
    );
}

export default UpdateHouseImages;