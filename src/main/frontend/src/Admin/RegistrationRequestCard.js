import {Badge, Button, Card, Modal} from "react-bootstrap";
import React, {useState} from "react";
import image from "../images/cover.png"

export function RegistrationRequestCard({props}) {
    const [show, setShow] = useState(false);
    return (
        <div>
            <Card className="m-3">
                <Card.Header className="d-flex align-items-center">
                    {props.name}
                    <Badge className="ms-auto" bg="primary">{props.type}</Badge>
                </Card.Header>
                <Card.Body className="d-flex align-items-center">
                    <ul>
                        <li>Email: {props.email}</li>
                        <li>Broj telefona: {props.phoneNumber}</li>
                        <li>Adresa: {props.address.street + " " + props.address.number + ", " + props.address.place + ", " + props.address.country}</li>
                    </ul>

                    <Button onClick={() => setShow(true)} variant="outline-primary" className="ms-auto m-1">Pregledaj</Button>
                    <Button variant="outline-success" className="m-1">Odobri</Button>
                    <Button variant="outline-danger" className="m-1">Poništi</Button>
                </Card.Body>
            </Card>

            <Modal show={show} onHide={() => setShow(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>{props.name}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <img style={{width:"100%", objectFit: "contain"}} src={image} alt={"Nema dokumenata."}/>


                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => setShow(false)} variant="outline-success" className="m-1">Odobri</Button>
                    <Button onClick={() => setShow(false)} variant="outline-danger" className="m-1">Poništi</Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}