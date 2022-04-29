import React, {useState} from "react";
import {Button, Form, Modal} from "react-bootstrap";
import axios from "axios";
import {backLink, missingDataErrors} from "../Consts";


export function AdventureForm({adventure, show, setShow}) {
    let initialState = {}
    if (adventure) {
        initialState = {
            title: adventure.title,
            description: adventure.description,
            street: adventure.address.street,
            number: adventure.address.number,
            place: adventure.address.place,
            country: adventure.address.country,
            numberOfClients: adventure.numberOfClients,
            price: adventure.pricelist.price,
            cancellationFee: adventure.cancellationFee,
            additionalServices: "",
            rulesAndRegulations: adventure.rulesAndRegulations,
            ownerId: adventure.owner.id,
        };
    }
    else {
        initialState = {
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
            rulesAndRegulations: "",
            ownerId: 1,
        };
    }


    const [formValues, setFormValues] = useState(initialState);
    const [formErrors, setFormErrors] = useState({});

    const setField = (fieldName, value) => {
        setFormValues({
            ...formValues,
            [fieldName]: value
        })
        if (!!formErrors[fieldName]) {
            setFormErrors({
                ...formErrors,
                [fieldName]: null
            })
        }
    }

    function addAdventure() {
        axios
            .post(backLink + "/adventure/add", formValues)
            .then(response => {
                console.log(response)
            })
            .catch(error => {
                console.log(error)
            })
    }

    function editAdventure() {
        console.log(adventure.id);
        axios
            .post(backLink + "/adventure/" + adventure.id + "/edit", formValues)
            .then(response => {
                console.log(response)
            })
            .catch(error => {
                console.log(error)
            })
    }

    const handleSubmit = e => {
        e.preventDefault()
        let errors = validateForm()
        if (Object.keys(errors).length > 0) {
            setFormErrors(errors);
        } else {
            if (adventure) {
                editAdventure();
            }
            else {
                addAdventure();
            }
        }
    }

    const validateForm = () => {
        let errors = {}
        if (formValues.title === "") {
            errors.title = missingDataErrors.title;
        }
        if (formValues.description === "") {
            errors.description = missingDataErrors.description;
        }
        if (formValues.street === "") {
            errors.street = missingDataErrors.address.street;
        }
        if (formValues.number === "") {
            errors.number = missingDataErrors.address.number;
        }
        if (formValues.place === "") {
            errors.place = missingDataErrors.address.place;
        }
        if (formValues.country === "") {
            errors.country = missingDataErrors.address.country;
        }
        if (formValues.numberOfClients === "") {
            errors.numberOfClients = missingDataErrors.numberOfClients;
        }
        if (formValues.price === "") {
            errors.price = missingDataErrors.price;
        }
        if (formValues.cancellationFee === "") {
            errors.cancellationFee = missingDataErrors.cancellationFee;
        }
        if (formValues.rulesAndRegulations === "") {
            errors.rulesAndRegulations = missingDataErrors.rulesAndRegulations;
        }
        return errors;
    }

    return (
        <>
            <Modal show={show} onHide={() => setShow(false)} size="lg">
                <Form>
                    <Modal.Header closeButton>
                        <Modal.Title>{adventure ? 'Izmena avanture' : 'Dodavanje avanture'}</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                        <Form.Group className="mb-3 m-2">
                            <Form.Label>Naslov</Form.Label>
                            <Form.Control type="text" name="title"
                                          value={formValues.title}
                                          onChange={(e) => setField("title", e.target.value)}
                                          isInvalid={!!formErrors.title}
                            />
                            <Form.Control.Feedback type="invalid">
                                {formErrors.title}
                            </Form.Control.Feedback>

                        </Form.Group>
                        <Form.Group className="mb-3 m-2">
                            <Form.Label>Opis</Form.Label>
                            <Form.Control as="textarea" rows={3} name="description"
                                          value={formValues.description}
                                          onChange={(e) => setField("description", e.target.value)}
                                          isInvalid={!!formErrors.description}/>
                            <Form.Control.Feedback type="invalid">
                                {formErrors.description}
                            </Form.Control.Feedback>
                        </Form.Group>


                        <div className="d-flex" id="address">
                            <Form.Group className="mb-3 m-2">
                                <Form.Label>Ulica</Form.Label>
                                <Form.Control type="text" name="street"
                                              value={formValues.street}
                                              onChange={(e) => setField("street", e.target.value)}
                                              isInvalid={!!formErrors.street}/>
                                <Form.Control.Feedback type="invalid">
                                    {formErrors.street}
                                </Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group className="mb-3 m-2 ">
                                <Form.Label>Broj</Form.Label>
                                <Form.Control type="text" name="number"
                                              value={formValues.number}
                                              onChange={(e) => setField("number", e.target.value)}
                                              isInvalid={!!formErrors.number}/>
                                <Form.Control.Feedback type="invalid">
                                    {formErrors.number}
                                </Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group className="mb-3 m-2">
                                <Form.Label>Mesto</Form.Label>
                                <Form.Control type="text" name="place"
                                              value={formValues.place}
                                              onChange={(e) => setField("place", e.target.value)}
                                              isInvalid={!!formErrors.place}/>
                                <Form.Control.Feedback type="invalid">
                                    {formErrors.place}
                                </Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group className="mb-3 m-2">
                                <Form.Label>Drzava</Form.Label>
                                <Form.Control type="text" name="country"
                                              value={formValues.country}
                                              onChange={(e) => setField("country", e.target.value)}
                                              isInvalid={!!formErrors.country}/>
                                <Form.Control.Feedback type="invalid">
                                    {formErrors.country}
                                </Form.Control.Feedback>
                            </Form.Group>
                        </div>

                        <div className="d-flex">
                            <div className="m-2 w-50">
                                <Form.Group className="mb-3">
                                    <Form.Label>Broj klijenata</Form.Label>
                                    <Form.Control type="text" name="numberOfClients"
                                                  value={formValues.numberOfClients}
                                                  onChange={(e) => setField("numberOfClients", e.target.value)}
                                                  isInvalid={!!formErrors.numberOfClients}/>
                                    <Form.Control.Feedback type="invalid">
                                        {formErrors.numberOfClients}
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <Form.Group controlId="images" className="mb-3">
                                    <Form.Label>Fotografije</Form.Label>
                                    <Form.Control type="file" multiple/>
                                </Form.Group>
                            </div>
                            <div className="m-2 w-50">
                                <Form.Group className="mb-3">
                                    <Form.Label>Cena</Form.Label>
                                    <Form.Control type="number" name="price"
                                                  value={formValues.price}
                                                  onChange={(e) => setField("price", e.target.value)}
                                                  isInvalid={!!formErrors.price}/>
                                    <Form.Control.Feedback type="invalid">
                                        {formErrors.price}
                                    </Form.Control.Feedback>
                                </Form.Group>

                                <Form.Group className="mb-3">
                                    <Form.Label>Naknada za otkazivanje</Form.Label>
                                    <Form.Control type="number" name="cancellationFee"
                                                  value={formValues.cancellationFee}
                                                  onChange={(e) => setField("cancellationFee", e.target.value)}
                                                  isInvalid={!!formErrors.cancellationFee}/>
                                    <Form.Control.Feedback type="invalid">
                                        {formErrors.cancellationFee}
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                        </div>

                        <Form.Group className="mb-3 m-2">
                            <Form.Label>Dodatne usluge</Form.Label>
                            <Form.Control type="text" name="additionalServices"/>
                        </Form.Group>
                        <Form.Group className="mb-3 m-2">
                            <Form.Label>Pravila ponasanja</Form.Label>
                            <Form.Control as="textarea" rows={3} name="rulesAndRegulations"
                                          value={formValues.rulesAndRegulations}
                                          onChange={(e) => setField("rulesAndRegulations", e.target.value)}
                                          isInvalid={!!formErrors.rulesAndRegulations}/>
                            <Form.Control.Feedback type="invalid">
                                {formErrors.rulesAndRegulations}
                            </Form.Control.Feedback>
                        </Form.Group>

                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={() => setShow(false)}>
                            Otkazi
                        </Button>
                        <Button variant="primary" onClick={handleSubmit}>
                            {adventure ? 'Izmeni' : 'Dodaj'}
                        </Button>
                    </Modal.Footer>
                </Form>
            </Modal>
        </>
    );
}