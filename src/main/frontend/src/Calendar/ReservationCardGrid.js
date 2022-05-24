import {Card, Col, Row} from "react-bootstrap";
import {convertToDate} from "./ReservationDateConverter";
import Tag from "../Tag";
import React from "react";

import {AiOutlineCalendar, AiOutlineClockCircle} from "react-icons/ai";

export function ReservationCardGrid({reservations}) {

    let indexesToPop = []
    for (let index in reservations) {
        let reservation = reservations.at(index);
        if (convertToDate(reservation.appointments.at(0).startTime)< Date.now()){
            indexesToPop.push(index);
        }
    }

    for (let index in indexesToPop) {
        reservations.pop(index);
    }

    return( <div className="ms-5 me-5"
                 style={{
                     maxHeight: "60vh",
                     overflowY: "scroll"}}>
        <Row xs={1} md={2} lg={3} className="g-4">
            {reservations.map((reservation, index) => (
                <Col key={index + "col"}>
                    <Card key={index} style={{
                        borderRadius: "0"
                    }}>
                        <Card.Header className="d-flex text-light lead" style={{
                            background: "linear-gradient(50deg, rgba(9,56,126,1) 38%, rgba(10,138,126) 96%)",
                            borderRadius: "0"
                        }}>
                            <div>{reservation.resourceTitle}</div>
                            <div className="ms-auto fw-bold">
                                {reservation.client.firstName + " " + reservation.client.lastName}
                            </div>
                        </Card.Header>
                        <Card.Body>

                            <div className="d-flex">
                                <div>
                                    <div>{reservation.additionalServices !== [] && reservation.additionalServices.map((tagData) => {
                                        return <Tag key={tagData.id} tag={tagData.text} id={tagData.id}/>
                                    })}</div>

                                </div>
                                <div className="ms-auto fs-4" style={{color: "#019191"}}>
                                    {reservation.price}€
                                </div>
                            </div>
                        </Card.Body>
                        <Card.Footer className="fw-light">


                            <TimeFooter reservation={reservation}/>
                        </Card.Footer>
                    </Card>
                </Col>
            ))}
        </Row>


    </div>)
}

function TimeFooter ({reservation}) {

    let startDateArray = reservation.appointments.at(0).startTime;
    let endDateArray = reservation.appointments.at(reservation.appointments.length - 1).endTime;

    let startDate = startDateArray.at(2).toString().padStart(2, '0') +
        "."
        + startDateArray.at(1).toString().padStart(2, '0') +
        "."
        + startDateArray.at(0).toString() +
        ".";
    let startTime = startDateArray.at(3).toString().padStart(2, '0') +
        ":"
        + startDateArray.at(4).toString().padStart(2, '0');

    let endDate = endDateArray.at(2).toString().padStart(2, '0') +
        "."
        + endDateArray.at(1).toString().padStart(2, '0') +
        "."
        + endDateArray.at(0).toString() +
        ".";
    let endTime = endDateArray.at(3).toString().padStart(2, '0') +
        ":"
        + endDateArray.at(4).toString().padStart(2, '0');



    let html =
        <div className="d-flex align-items-center">
            <p className="p-0 m-0">
                <AiOutlineCalendar className="ms-1 me-1"/>
                {startDate}
                <AiOutlineClockCircle className="ms-1 me-1"/>
                {startTime}
                -
                <AiOutlineCalendar className="ms-1 me-1"/>
                {endDate}
                <AiOutlineClockCircle className="ms-1 me-1"/>
                {endTime}
            </p>
        </div>;

    if (startDate === endDate) {
        html =
            <div className="d-flex align-items-center">
                <AiOutlineCalendar className="ms-1 me-1"/>
                <p className="p-0 m-0">
                    {startDate}
                </p>
                <AiOutlineClockCircle className="ms-1 me-1"/>
                {startTime}-{endTime}
            </div>
    }

    if (startTime === endTime) {
        html = <div className="d-flex align-items-center">
            <AiOutlineCalendar className="ms-1 me-1"/>
            <p className="p-0 m-0">
                {startDate}-{endDate}
            </p>
            <AiOutlineClockCircle className="ms-1 me-1"/>
            {startTime}
        </div>
    }

    return html;
}