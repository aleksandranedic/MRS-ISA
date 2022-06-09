import {React, useState} from 'react'
import Carousel from "react-multi-carousel";
import {Button, Container} from 'react-bootstrap'
import {BsFillPlusCircleFill} from 'react-icons/bs'
import "react-multi-carousel/lib/styles.css";
import AddQuickReservation from './AddQuickReservation'
import QuickReservation from "./QuickReservation";
import {backLink, notifySuccess, responsive} from "./Consts";
import {isClient, isLoggedIn} from "./Autentification";
import axios from "axios";
import {useParams} from "react-router-dom";
import {ToastContainer} from "react-toastify";


function QuickReservations({reservations, name, address, entity, priceText, durationText, type, addable, myPage}) {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const [isLoggedUserSubscribed, setIsUserSubscribed] = useState(false)
    const {id} = useParams();

    const checkSubscription=()=>{
        const dto = {
            userId: localStorage.getItem("userId"),
            entityId: id,
        }
        console.log(dto)
        axios.post(backLink + "/"+entity+"/isSubscribed", dto).then(
            response => {
                console.log(response.data)
                setIsUserSubscribed(response.data)
            }
        )
    }

    useState(() => {
        checkSubscription()
    },[])
    const subscribeUser = () => {
        const dto = {
            userId: localStorage.getItem("userId"),
            entityId: id,
        }
        axios.post(backLink + "/"+entity+"/subscribe", dto).then(
            response => {
                console.log(response.data)
                notifySuccess(response.data)
            }
        )
    }
    const unsubscribeUser = () => {
        const dto = {
            userId: localStorage.getItem("userId"),
            entityId: id,
        }
        axios.post(backLink + "/"+entity+"/unsubscribe", dto).then(
            response => {
                console.log(response.data)
                notifySuccess(response.data)
            }
        )
    }


    return (<>
            <div className="m-5" id="actions">
                <div className='w-100 d-flex justify-content-center mb-3 align-items-end'>
                    <h1 className="ms-auto m-0 text-lead me-auto" style={{
                        color: "#313041",
                        fontSize: "46px",
                        lineHeight: "60px",
                        letterSpacing: "-.02em"
                    }}> Specijalne ponude i popusti</h1>

                    {isLoggedIn() && isClient() && !isLoggedUserSubscribed &&
                <Button style={{border: "none", height: "2.8rem", backgroundColor: "rgb(236,115,2)"}}
                    onClick={()=>{
                        subscribeUser()
                    }}
                >Prijavi se</Button>}
                {isLoggedIn() && isClient() && isLoggedUserSubscribed &&
                <Button style={{border: "none", height: "2.8rem", backgroundColor: "rgb(236,115,2)"}}
                        onClick={()=>{
                            unsubscribeUser()
                        }}
                >Odjavi se</Button>}


            </div>
            <hr className='w-100'/>
            <Container className='d-flex p-0'>
                <Carousel className="w-100 h-100 quick-reservation-carousel" responsive={responsive} interval="250000"
                          autoPlay={false} autoPlaySpeed={9000000}>
                    {reservations.map((reservation) => (
                        <QuickReservation key={reservation.reservationID} type={type} reservation={reservation}
                                          name={name} address={address} image={"./images/loginBackground.jpg"}
                                          entity={entity} priceText={priceText} durationText={durationText}
                                          myPage={myPage}/>
                    ))}
                </Carousel>


                {addable &&
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
            <ToastContainer
                position="top-center"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme={"colored"}
            />
        </>

    )
}

export default QuickReservations;