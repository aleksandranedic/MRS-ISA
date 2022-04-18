import React, { useState } from 'react';
import OwlCarousel from 'react-owl-carousel';  
import 'owl.carousel/dist/assets/owl.carousel.css';  
import 'owl.carousel/dist/assets/owl.theme.default.css'; 
import './OwnerHouses.css'
import {Container, Button} from "react-bootstrap";
import {BsFillPlusCircleFill} from 'react-icons/bs'

import v1 from "./images/vikendica1.jpeg"
import v2 from "./images/vikendica2.jpeg"
import v3 from "./images/vikendica3.jpeg"
import v4 from "./images/vikendica4.jpeg"
import boatnotext from "./images/boatsnotext.png";
import OwnerHouseCard from './OwnerHouseCard';
import AddVacationHouse from './AddVacationHouse';

function OwnerHouses(props) {
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

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
            <OwlCarousel className="owl-theme owl-theme houses-carousel d-flex" loop {...options} navElement>    
                <div className='house-container'>
                    <OwnerHouseCard
                        pic={v1}
                        title={"Naziv vikendice"}
                        text={"Opise vikendice"}
                        address={"Bulevar Oslobodjenja 31, Novi Sad, Srbija"}
                        />
                </div>  
                <div className='house-container'>
                    <OwnerHouseCard
                        pic={v2}
                        title={"Naziv vikendice"}
                        text={"Opise vikendice"}
                        address={"Bulevar Oslobodjenja 31, Novi Sad, Srbija"}
                        />
                </div>  
                <div className='house-container'>
                    <OwnerHouseCard
                        pic={v3}
                        title={"Naziv vikendice"}
                        text={"Opise vikendice"}
                        address={"Bulevar Oslobodjenja 31, Novi Sad, Srbija"}
                        />
                </div>  
                <div className='house-container'>
                    <OwnerHouseCard
                        pic={v4}
                        title={"Naziv vikendice"}
                        text={"Opise vikendice Some quick example text to build on the card title and make up the bulk of the card's content."}
                        address={"Bulevar Oslobodjenja 31, Novi Sad, Srbija"}
                        />
                </div>  
                <div className='house-container'>
                    <OwnerHouseCard
                        pic={boatnotext}
                        title={"Naziv vikendice"}
                        text={"Opise vikendicevBulevar Oake of  the card's content.Some quick example text to build on the card title and make up the bulk o the card's content."}
                        address={"Bulevar Oslobodjenja 31, Novi Sad, Srbija"}
                        />
                </div>        
            </OwlCarousel> 
            <Button className="btn btn-light add border-radius-lg" onClick={handleShow}>
                <BsFillPlusCircleFill viewBox='0 0 16 16' size={25} fill="#7d7d7d"/>          
            </Button> 
            <AddVacationHouse closeModal={handleClose} showModal={show}/>
    </Container>  
    )
}

export default OwnerHouses;