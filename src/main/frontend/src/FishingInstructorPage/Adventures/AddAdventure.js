import React, {useState} from "react";
import PlusCard from "../../PlusCard";
import {Button, Form, Modal} from "react-bootstrap";
import axios from "axios";


export function AddAdventure() {
    const [show, setShow] = useState(false);

    const handleClose = () => {
        resetData();
        setShow(false);
    }
    const handleShow = () => setShow(true);

    const url = "http://localhost:4444/adventure/add"

    const [data, setData] = useState({
        title: "",
        description: "",
        street: "",
        number: "",
        place: "",
        country: "",
        numberOfClients: "",
        price: "",
        cancellationFee: "",
        additionalServices: "",
        rulesAndRegulations: ""
    })

    function handle(e) {
        const newData = {...data}
        newData[e.target.id] = e.target.value
        setData(newData)


    }

    function resetData() {
        data.title = ""
        data.description = ""
        data.street = ""
        data.number = ""
        data.place = ""
        data.country = ""
        data.numberOfClients = ""
        data.price = ""
        data.cancellationFee = ""
        data.additionalServices = ""
        data.rulesAndRegulations = ""
    }

    const addAdventure = (e) => {
        e.preventDefault();

        axios.post(url, {
            title: data.title,
            description: data.description,
            street: data.street,
            number: data.number,
            place: data.place,
            country: data.country,
            numberOfClients: data.numberOfClients,
            price: data.price,
            cancellationFee: data.cancellationFee,
            additionalServices: data.additionalServices,
            rulesAndRegulations: data.rulesAndRegulations
        }).then(response => {
            console.log(response)
        })

        resetData();
        setShow(false);

    }

    return (
        <>
            <PlusCard onClick={handleShow}/>

            <Modal show={show} onHide={handleClose} size="lg">
                <Form>
                    <Modal.Header closeButton>
                        <Modal.Title>Dodavanje avanture</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>

                        <Form.Group className="mb-3 m-2">
                            <Form.Label>Naslov</Form.Label>
                            <Form.Control
                                type="text"
                                onBlur={(e) => handle(e)}
                                id="title"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3 m-2">
                            <Form.Label>Opis</Form.Label>
                            <Form.Control as="textarea" rows={3}
                                          onBlur={(e) => handle(e)}
                                          id="description"/>
                        </Form.Group>


                        <div className="d-flex">

                            <Form.Group className="mb-3 m-2">
                                <Form.Label>Ulica</Form.Label>
                                <Form.Control type="text"
                                              onBlur={(e) => handle(e)}
                                              id="street"/>
                            </Form.Group>
                            <Form.Group className="mb-3 m-2 ">
                                <Form.Label>Broj</Form.Label>
                                <Form.Control type="text"
                                              onBlur={(e) => handle(e)}
                                              id="number"/>
                            </Form.Group>

                            <Form.Group className="mb-3 m-2">
                                <Form.Label>Mesto</Form.Label>
                                <Form.Control type="text"
                                              onBlur={(e) => handle(e)}
                                              id="place"/>
                            </Form.Group>

                            <Form.Group className="mb-3 m-2">
                                <Form.Label>Drzava</Form.Label>
                                <Form.Control type="text"
                                              onBlur={(e) => handle(e)}
                                              id="country"/>
                            </Form.Group>

                        </div>

                        <div className="d-flex">
                            <div className="m-2 w-50">
                                <Form.Group className="mb-3">
                                    <Form.Label>Broj klijenata</Form.Label>
                                    <Form.Control type="text"
                                                  onBlur={(e) => handle(e)}
                                                  id="numberOfClients"/>
                                </Form.Group>
                                <Form.Group controlId="images" className="mb-3">
                                    <Form.Label>Fotografije</Form.Label>
                                    <Form.Control type="file" multiple/>
                                </Form.Group>
                            </div>
                            <div className="m-2 w-50">
                                <Form.Group className="mb-3">
                                    <Form.Label>Cena</Form.Label>
                                    <Form.Control type="number"
                                                  onBlur={(e) => handle(e)}
                                                  id="price"/>
                                </Form.Group>

                                <Form.Group className="mb-3">
                                    <Form.Label>Naknada za otkazivanje</Form.Label>
                                    <Form.Control type="number"
                                                  onBlur={(e) => handle(e)}
                                                  id="cancellationFee"/>
                                </Form.Group>
                            </div>
                        </div>

                        <Form.Group className="mb-3 m-2">
                            <Form.Label>Dodatne usluge</Form.Label>
                            <Form.Control type="text"
                                          onBlur={(e) => handle(e)}
                                          id="additionalServices"/>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2">
                            <Form.Label>Pravila ponasanja</Form.Label>
                            <Form.Control as="textarea" rows={3}
                                          onBlur={(e) => handle(e)}
                                          id="rulesAndRegulations"/>
                        </Form.Group>

                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleClose}>
                            Otkazi
                        </Button>
                        <Button variant="primary" onClick={addAdventure}>
                            Dodaj
                        </Button>
                    </Modal.Footer>
                </Form>
            </Modal>
        </>
    );
}