import {Card, Col, Row} from "react-bootstrap";
import {convertToDate, getEndTime, getStartTime} from "./ReservationDateConverter";
import Tag from "../Tag";
import React from "react";

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
            {reservations.map((reservation) => (
                <Col key={reservation.id}>
                    <Card style={{
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
                            {getStartTime(reservation)} - {getEndTime(reservation)}
                        </Card.Footer>
                    </Card>
                </Col>
            ))}
        </Row>


    </div>)
}