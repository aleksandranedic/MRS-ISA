import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from '@fullcalendar/timegrid';
import {Button, Card, Dropdown, DropdownButton} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import interactionPlugin from "@fullcalendar/interaction";
import {ReservationModal} from "./ReservationModal";
import {BusyPeriodModal} from "./BusyPeriodModal";
import {BsPlusLg} from "react-icons/bs";
import {convertToDate} from "./ReservationDateConverter";
import axios from "axios";
import {backLink} from "../Consts";

export function Calendar({reservations, reservable, pricelist, type, resourceId}){

    let perHour = false;
    if (type === "adventure") {
        perHour = true;
    }
    const [showReservationDialog, setShowReservationDialog] = useState(false);
    const [showBusyPeriodDialog, setShowBusyPeriodDialog] = useState(false);

    const [events, setEvents] = useState([]);
    const calendarRef = React.createRef();

    useEffect(() => {
        let reservationEvents = [];
        for (let i_r in reservations) {
            let r = reservations[i_r];

            let clientName = r.client.firstName + " " + r.client.lastName;
            let start = convertToDate(r.appointments.at(0).startTime);
            start.setHours(start.getHours()+2);
            let end = convertToDate(r.appointments.at(r.appointments.length-1).endTime);
            end.setHours(end.getHours()+2);
            reservationEvents.push({
                title: clientName,
                start: start,
                end: end,
                backgroundColor: "rgb(34,215,195)"
            })
        }

        if (reservationEvents.length !== events.length) {
            setEvents(reservationEvents);
        }


    })



    const reservationClick = () => {
        setShowReservationDialog(true);
    }

    const changeView = (view) => {
        calendarRef.current
            .getApi()
            .changeView(view);
    }

    return (<>

        <div id="calendar" className="d-flex justify-content-center">

        <div className="ms-5 me-3 mt-4" style={{width: "65%"}}>
            <Dropdown>
                <Dropdown.Toggle className="mb-3" variant="outline-primary" id="dropdown-basic">
                    Tip pregleda kalendara
                </Dropdown.Toggle>

                <Dropdown.Menu>
                    <Dropdown.Item onClick={()=> changeView('dayGrid')}>Dnevni pregled</Dropdown.Item>
                    <Dropdown.Item onClick={()=> changeView('dayGridWeek')}>Nedelji pregled</Dropdown.Item>
                    <Dropdown.Item onClick={()=> changeView('dayGridMonth')}>Mesečni pregled</Dropdown.Item>
                    <Dropdown.Item onClick={()=> changeView('timeGridWeek')}>Nedeljni vremenski pregled</Dropdown.Item>
                    <Dropdown.Item onClick={()=> changeView('timeGridDay')}>Dnevni vremenski pregled</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
            <FullCalendar
                plugins={[dayGridPlugin, interactionPlugin, timeGridPlugin]}
                initialView="dayGridMonth"
                timeZone="UTC"
                events={events}
                ref={calendarRef}
            />
        </div>

        {
            reservable &&
            <div className="d-flex flex-column align-items-center justify-content-center">
                <div className="d-flex align-items-center justify-content-center"
                    style={{
                        width: "12rem", height: "10rem",
                        background: "linear-gradient(50deg, rgba(13,110,252,1) 38%, rgba(13,252,229,1) 96%)",
                        borderRadius: "0.5rem"
                    }}

                >
                    <Card style={{
                        width: "11.5rem",
                        height: "9.5rem",
                        backgroundColor: "white",
                        border: "none"}}>
                        <Card.Body >
                            <div className="d-flex align-items-center justify-content-center w-100 h-75">
                                <h3 style={{color: "rgba(13,110,252,1)"}}>{pricelist.price}€</h3>
                                <h1 style={{fontSize: "5.5rem", fontWeight: "lighter"}}>/</h1>
                                <h6 style={{color: "rgba(13,252,229,1)"}}>{perHour? "Sat": "Dan"}</h6>
                            </div>


                        </Card.Body>
                    </Card>
                </div>
                <Button className="m-2" style={{width: "12rem"}} onClick={reservationClick}>Rezervisi</Button>
                <Button className="m-2 d-flex align-items-center" style={{width: "12rem"}} onClick={()=>setShowBusyPeriodDialog(true)}>
                    <BsPlusLg className="ms-1 me-1" style={{height: "0.8rem", paddingTop: "0.1rem"}}/>
                    Period zauzetosti
                </Button>
            </div>
        }


        <ReservationModal show={showReservationDialog} setShow={setShowReservationDialog} type={type} resourceId={resourceId}/>
        <BusyPeriodModal show={showBusyPeriodDialog} setShow={setShowBusyPeriodDialog} events={events} setEvents={setEvents}/>
    </div></>)
}