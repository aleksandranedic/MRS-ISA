import {Button, Form, Modal} from "react-bootstrap";
import React from "react";

export function EditAdventure({show, setShow, adventure}) {


    const handleClose = () => setShow(false);
    const editAdventure = () => {
        setShow(false);
    }

    return <Modal
        size="lg"
        show={show}
        onHide={() => setShow(false)}>

        <Form>
            <Modal.Header closeButton>
                <Modal.Title>{adventure.title}</Modal.Title>
            </Modal.Header>

            <Modal.Body>

                <Form.Group className="mb-3 m-2" controlId="title">
                    <Form.Label>Naslov</Form.Label>
                    <Form.Control type="text" defaultValue={adventure.title}/>
                </Form.Group>

                <Form.Group className="mb-3 m-2" controlId="description">
                    <Form.Label>Opis</Form.Label>
                    <Form.Control as="textarea" rows={3} defaultValue={adventure.description}/>
                </Form.Group>

                <div className="d-flex">

                    <Form.Group className="mb-3 m-2" controlId="street">
                        <Form.Label>Ulica</Form.Label>
                        <Form.Control type="text" defaultValue={adventure.address}/>
                    </Form.Group>

                    <Form.Group className="mb-3 m-2 " controlId="number">
                        <Form.Label>Broj</Form.Label>
                        <Form.Control type="text" defaultValue={adventure.address}/>
                    </Form.Group>

                    <Form.Group className="mb-3 m-2" controlId="city">
                        <Form.Label>Grad</Form.Label>
                        <Form.Control type="text" defaultValue={adventure.address}/>
                    </Form.Group>

                    <Form.Group className="mb-3 m-2" controlId="country">
                        <Form.Label>Drzava</Form.Label>
                        <Form.Control type="text" defaultValue={adventure.address}/>
                    </Form.Group>

                </div>

                <div className="d-flex">

                        <Form.Group className="mb-3 m-2 w-25" controlId="numberOfClients">
                            <Form.Label>Broj klijenata</Form.Label>
                            <Form.Control type="text" defaultValue={adventure.numberOfClients}/>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2 w-50" controlId="price">
                            <Form.Label>Cena</Form.Label>
                            <Form.Control type="text" defaultValue={adventure.price}/>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2 w-25" controlId="cancellationFee">
                            <Form.Label>Naknada za otkazivanje</Form.Label>
                            <Form.Control type="number" defaultValue={adventure.cancellationFee}/>
                        </Form.Group>
                </div>

                <Form.Group className="mb-3 m-2" controlId="additionalServices">
                    <Form.Label>Dodatne usluge</Form.Label>
                    <Form.Control type="text" defaultValue={adventure.additionalServices}/>
                </Form.Group>

                <Form.Group className="mb-3 m-2" controlId="rulesAndRegulations">
                    <Form.Label>Pravila ponasanja</Form.Label>
                    <Form.Control as="textarea" rows={3} defaultValue={adventure.rulesAndRegulations}/>
                </Form.Group>

            </Modal.Body>
            <Modal.Footer>

                <Button variant="outline-danger" className="me-auto" onClick={handleClose}>
                    Obrisi
                </Button>
                <Button variant="secondary" onClick={handleClose}>
                    Otkazi
                </Button>
                <Button variant="primary" onClick={editAdventure}>
                    Izmeni
                </Button>
            </Modal.Footer>
        </Form>
    </Modal>
}