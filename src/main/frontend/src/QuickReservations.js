import {React, useState} from 'react'
import Carousel from "react-multi-carousel";
import {Container, Button } from 'react-bootstrap'
import {BsFillPlusCircleFill} from 'react-icons/bs'
import "react-multi-carousel/lib/styles.css";
import AddQuickReservation from './AddQuickReservation'
import QuickReservation from "./QuickReservation";
import {responsive} from "./Consts";


function QuickReservations({reservations, name, address, entity, priceText, durationText, type, addable, myPage}) {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    return (
    <div className="m-5" id="actions">
        <div className='w-100 d-flex flex-column align-items-center mb-3'>
            <h1 className="m-0 text-lead" style={{color: "#313041", fontSize: "46px", lineHeight: "60px", letterSpacing: "-.02em"}}> Specijalne ponude i popusti</h1>
            <hr className='w-100'/>
        </div>
        <Container className='d-flex p-0'>          
            <Carousel className="w-100 h-100 quick-reservation-carousel" responsive={responsive}  interval="250000" autoPlay={false} autoPlaySpeed={9000000}>
            {reservations.map( (reservation) => (
                <QuickReservation key={reservation.reservationID} type={type} reservation={reservation} name={name} address={address} image={"./images/loginBackground.jpg"} entity={entity} priceText={priceText} durationText={durationText} myPage={myPage}/>
            ))}
            </Carousel>


            { addable &&
                <>
                    <Button className="btn btn-light add border-radius-lg align-self-center" onClick={handleShow}>
                        <BsFillPlusCircleFill viewBox='0 0 16 16' size={25} fill="#7d7d7d"/>
                    </Button>
                    <AddQuickReservation closeModal={handleClose} showModal={show} entity={entity} priceText={priceText}
                                         durationText={durationText}/>
                </>

            }

        </Container>
    </div>


    )
}
export default QuickReservations;