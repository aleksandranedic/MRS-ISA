import {Button, Form, Modal} from "react-bootstrap";
import React, {useState} from "react";
import DeleteClientPopUp from "./DeleteClientPopUp";

function UpdateClientInfo({client,handleDeleteAccount,handleClose, showPopUp}) {
    const [showDeleteClient, setShow] = useState(false);

    const handleCloseDeleteClient = () => setShow(false);
    const handleShowDeleteAccPopUp = () => setShow(true);

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
                                <Form.Control type="text" defaultValue={client.firstName} placeholder=""/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formSurname">
                                <Form.Label>Prezime</Form.Label>
                                <Form.Control type="text" defaultValue={client.lastName} placeholder=""/>
                            </Form.Group>
                        </div>
                        <div className="m-2 w-50">
                            <Form.Group className="mb-3 m-2" controlId="formBasicPassword">
                                <Form.Label>Sifra</Form.Label>
                                <Form.Control defaultValue={client.password} type="text" placeholder=""/>
                            </Form.Group>

                            <Form.Group className="mb-3 m-2" controlId="description">
                                <Form.Label>Ponovi sifru</Form.Label>
                                <Form.Control defaultValue={client.password} type="password" placeholder=""/>
                            </Form.Group>
                        </div>
                    </Form>
                    <Form className="d-flex gap-4">
                        <Form.Group className="mb-3 m-2" controlId="formNumOfAddress">
                            <Form.Label>Broj</Form.Label>
                            <Form.Control defaultValue={"10"} type="text" placeholder=""/>
                        </Form.Group>
                        <Form.Group className="mb-3 m-2" controlId="formAddress">
                            <Form.Label>Adresa</Form.Label>
                            <Form.Control defaultValue={"kako ovo"} type="text" placeholder=""/>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2 " controlId="formCity">
                            <Form.Label>Grad</Form.Label>
                            <Form.Control defaultValue={"kako ovo"} type="text" placeholder=""/>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2" controlId="formCountry">
                            <Form.Label>Drzava</Form.Label>
                            <Form.Control defaultValue={"kako ovo"} type="text" placeholder=""/>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button className="me-auto" variant="btn btn-outline-danger" onClick={handleShowDeleteAccPopUp}>
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
            <DeleteClientPopUp showDeleteClient={showDeleteClient} handleCloseDeleteClient={handleCloseDeleteClient} handleDeleteAccount={handleDeleteAccount}/>
        </>
    );
}
export default UpdateClientInfo