import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import {Button} from "react-bootstrap";
import React, {useState} from "react";
import interactionPlugin from "@fullcalendar/interaction";
import {ReservationModal} from "./ReservationModal";
import {BusyPeriodModal} from "./BusyPeriodModal";
import {BsPlusLg} from "react-icons/bs";

export function Calendar({adventureReservations}){
    const [showReservationDialog, setShowReservationDialog] = useState(false);
    const [showBusyPeriodDialog, setShowBusyPeriodDialog] = useState(false);

    const [date, setDate] = useState("");
    const events = [
        { title: '', date: '2022-04-01' },
        { title: '', date: '2022-04-08' }
    ];

    if (adventureReservations) {
        adventureReservations.forEach(reservation => {
            events.add({
                title: reservation.adventure.title,
                date: reservation.startDate
            })
        })
    }

    const handleDateClick = (arg) => {
        setDate(arg.dateStr);
        setShowReservationDialog(true);
    }

    const reservationClick = () => {
        setDate("");
        setShowReservationDialog(true);
    }

    return (<div className="d-flex justify-content-center">
        <div className="ms-5 me-3 mt-4" style={{width: "65%"}}>
            <FullCalendar
                plugins={[dayGridPlugin, interactionPlugin]}
                initialView="dayGridMonth"
                events={events}
                dateClick={handleDateClick}
            />
        </div>
        <div className="d-flex flex-column align-items-center justify-content-center">
            <Button className="m-2" style={{width: "12rem"}} onClick={reservationClick}>Rezervisi</Button>
            <Button className="m-2 d-flex align-items-center" style={{width: "12rem"}} onClick={()=>setShowBusyPeriodDialog(true)}>
                <BsPlusLg className="ms-1 me-1" style={{height: "0.8rem", paddingTop: "0.1rem"}}/>
                Period zauzetosti
            </Button>
        </div>

        <ReservationModal show={showReservationDialog} setShow={setShowReservationDialog} date={date}/>
        <BusyPeriodModal show={showBusyPeriodDialog} setShow={setShowBusyPeriodDialog}/>
    </div>)
}