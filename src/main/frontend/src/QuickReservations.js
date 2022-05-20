import {React, useState} from 'react'
import Carousel from "react-multi-carousel";
import {Container, Button } from 'react-bootstrap'
import {BsFillPlusCircleFill} from 'react-icons/bs'
import "react-multi-carousel/lib/styles.css";
import AddQuickReservation from './AddQuickReservation'
import QuickReservation from "./QuickReservation";

const responsive = {
    superLargeDesktop: {
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

function QuickReservations({reservations, name, address, entity, priceText, durationText}) {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    return (
    <div className="m-5" id="actions">
        <div className='w-100 d-flex flex-column align-items-center mb-3'>
            <h1 className="m-0 text-lead" style={{color: "#313041", fontSize: "46px", lineHeight: "60px", letterSpacing: "-.02em"}}> Specijalne ponude i popusti</h1>
        </div>
        <Container className='d-flex p-0'>          
            <Carousel className="w-100 h-100 quick-reservation-carousel" responsive={responsive}  interval="250000" autoPlay={false} autoPlaySpeed={9000000}>
            {reservations.map( (reservation) => (
                <QuickReservation key={reservation}  reservation={reservation} name={name} address={address} image={"http://localhost:3000/images/vikendica1.jpeg"} entity={entity} priceText={priceText} durationText={durationText}/>  
            ))}
            </Carousel>

            <Button className="btn btn-light add border-radius-lg align-self-center" onClick={handleShow}>
                <BsFillPlusCircleFill viewBox='0 0 16 16' size={25} fill="#7d7d7d"/>          
            </Button>
            <AddQuickReservation closeModal={handleClose} showModal={show} entity={entity} priceText={priceText}/>
        </Container>
    </div>


    )
}
export default QuickReservations;