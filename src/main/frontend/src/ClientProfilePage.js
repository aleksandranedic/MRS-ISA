import axios from "axios";
import React, {useEffect, useState} from "react";
import Banner from "./Banner";
import Navigation from "./Navigation";
import ClientInfo from "./ClientInfo";
import ClientLoyalty from "./ClientLoyalty";
import {Button, Form, Modal} from "react-bootstrap";

const Client = () => {
    const [client, setClient] = useState([]);

    const fetchClients = () => {
        axios.get("http://localhost:4444/client").then(res => {
            console.log(res);
            setClient(res.data);
        });
    };

    useEffect(() => {
        fetchClients();
    }, []);

    return fetchClients.map((client, index) => {
        return <div key={index}>
        </div>
    })
};

export default function ClientProfilePage() {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    return (
        <>
            <Banner caption={"Petar  Milosevic"}/>
            <UpdateClientInfo handleClose={handleClose} showPopUp={show}/>
            <Navigation handleEvent={handleShow}/>
            <ClientInfo
                firstName={"Petar"}
                lastName={"Milosevic"}
                address={"Jovana Cvijica 25"}
                email={"petar.milosevic@eamil.com"}
                phoneNumber={"0656452628"}
                city={"Novi Sad"}
                country={"Srbija"}
            />
            <ClientLoyalty/>
        </>
    )
}


function UpdateClientInfo({handleClose, showPopUp}) {
    return (
        <>
            <Modal show={showPopUp} onHide={handleClose} size="lg">
                <Modal.Header closeButton>
                    <Modal.Title>Azuriranje podataka</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <Form className="d-flex">
                        <div className="m-2 w-50">
                            <Form.Group className="mb-3" controlId="formName">
                                <Form.Label>Ime</Form.Label>
                                <Form.Control type="text" placeholder=""/>
                            </Form.Group>
                        </div>
                        <div className="m-2 w-50">
                            <Form.Group className="mb-3" controlId="formSurname">
                                <Form.Label>Prezime</Form.Label>
                                <Form.Control type="text" placeholder=""/>
                            </Form.Group>
                        </div>
                    </Form>
                    <Form className="d-flex gap-4">
                        <Form.Group className="mb-3 m-2" controlId="formAddress">
                            <Form.Label>Adresa</Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>
                        <Form.Group className="mb-3 m-2 " controlId="formCity">
                            <Form.Label>Grad</Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2" controlId="formCountry">
                            <Form.Label>Drzava</Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>

                    </Form>
                    <div className="w-50 m-auto justify-content-center align-items-center">
                        <Form.Group className="mb-3 m-2" controlId="formBasicPassword">
                            <Form.Label>Sifra</Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2" controlId="description">
                            <Form.Label>Ponovi sifru</Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button className="me-auto" variant="btn btn-outline-danger" >
                        Obrisi nalog
                    </Button>
                    <Button variant="secondary" onClick={handleClose}>
                        Otkazi
                    </Button>
                    <Button variant="primary" onClick={handleClose}>
                        Dodaj
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

