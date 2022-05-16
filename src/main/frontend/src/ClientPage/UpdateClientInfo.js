import {Button, Form, Modal} from "react-bootstrap";
import React, {useState} from "react";
import DeleteClientPopUp from "./DeleteClientPopUp";
import axios from "axios";
import {ChangeClientPassword} from "./ChangeClientPassword";
import {backLink} from "../Consts";

function UpdateClientInfo({client, handleClose, showPopUp,setClient}) {
    const [showDeleteClient, setShow] = useState(false);

    const [showPassword, setShowPassword] = useState(false);
    const handleShowPassword = () => setShowPassword(true);


    function handleUpdateAccount(userDTO) {
        axios.put(backLink+"client/update/" + client.id, userDTO).then(res => {
            console.log(res);
            setClient(res.data)
        });
    }

    const handleCloseDeleteClient = () => setShow(false);
    const handleShowDeleteAccPopUp = () => setShow(true);

    const [form, setForm] = useState({
        firstName: client.firstName,
        lastName: client.lastName,
        number: client.address.number,
        street: client.address.street,
        place: client.address.place,
        country: client.address.country,
        phoneNumber: client.address.phoneNumber
    })
    const [errors, setErrors] = useState({})

    const stringRegExp = new RegExp("[A-Z][A-Za-z]+")
    const streetRegExp = new RegExp("[A-Z][A-Za-z]+.?[A-Za-z]*")
    const numExp = new RegExp("[1-9][0-9]*[a-z]?")
    const phoneNumRegExp = new RegExp("[0-9]{7,12}")

    const findFormErrors = () => {
        const newErrors = {}
        if (!form.firstName) newErrors.firstName = 'Polje ne sme da bude prazno!'
        else if (!stringRegExp.test(form.firstName)) newErrors.firstName = 'Mora da počne sa velikim slovom!'

        if (!form.lastName) newErrors.lastName = 'Polje ne sme da bude prazno!'
        else if (!stringRegExp.test(form.lastName)) newErrors.lastName = 'Mora da počne sa velikim slovom!'

        if (!form.number) newErrors.number = 'Polje ne sme da bude prazno!'
        else if (!numExp.test(form.number)) newErrors.number = 'Mora da sadrži brojeve!'

        if (!form.street || !streetRegExp.test(form.street)) newErrors.street = 'Polje ne sme da bude prazno!'

        if (!form.place) newErrors.place = 'Polje ne sme da bude prazno!'
        else if (!streetRegExp.test(form.place)) newErrors.place = 'Mora da počne sa velikim slovom!'

        if (!form.country) newErrors.country = 'Polje ne sme da bude prazno!'
        else if (!streetRegExp.test(form.country)) newErrors.country = 'Mora da počne sa velikim slovom!'

        if (!form.phoneNumber) newErrors.phoneNumber = 'Polje ne sme da bude prazno!'
        if (!phoneNumRegExp.test(form.phoneNumber)) newErrors.phoneNumber = 'Mora da sadrži od 7 do 10 cifara!'

        return newErrors
    }

    const handleSubmit = e => {
        e.preventDefault()
        // get our new errors
        const newErrors = findFormErrors()
        // Conditional logic:
        if (Object.keys(newErrors).length > 0) {
            // We got errors!
            setErrors(newErrors)
        } else {
            const userDTO = {
                firstName: form.firstName,
                lastName: form.lastName,
                phoneNumber: form.phoneNumber,
                address: {
                    number: form.number,
                    street: form.street,
                    place: form.place,
                    country: form.country
                }
            }
            handleUpdateAccount(userDTO)
            handleClose()
        }
    }

    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })
        if (!!errors[field]) setErrors({
            ...errors,
            [field]: null
        })
    }
    return (
        <>
            <Modal show={showPopUp} onHide={handleClose} size="lg">
                <Modal.Header closeButton>
                    <Modal.Title>Ažuriranje podataka</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form className="d-flex">
                        <div className="m-2 w-100">
                            <Form.Group className="mb-3" controlId="formName">
                                <Form.Label>Ime</Form.Label>
                                <Form.Control type="text" defaultValue={client.firstName}
                                              onChange={e => setField('firstName', e.target.value)}
                                              isInvalid={!!errors.firstName}/>
                                <Form.Control.Feedback type='invalid'>
                                    {errors.firstName}
                                </Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formSurname">
                                <Form.Label>Prezime</Form.Label>
                                <Form.Control type="text" defaultValue={client.lastName}
                                              onChange={e => setField('lastName', e.target.value)}
                                              isInvalid={!!errors.lastName}/>
                                <Form.Control.Feedback type='invalid'>
                                    {errors.lastName}
                                </Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formPhoneNumber">
                                <Form.Label>Broj telefona</Form.Label>
                                <Form.Control type="text" defaultValue={client.phoneNumber}
                                              onChange={e => setField('phoneNumber', e.target.value)}
                                              isInvalid={!!errors.phoneNumber}/>
                                <Form.Control.Feedback type='invalid'>
                                    {errors.phoneNumber}
                                </Form.Control.Feedback>
                            </Form.Group>
                        </div>
                    </Form>
                    <Form className="d-flex gap-4">
                        <Form.Group className="mb-3 m-2" controlId="formNumOfAddress">
                            <Form.Label>Broj</Form.Label>
                            <Form.Control type="text" defaultValue={client.address.number}
                                          onChange={e => setField('number', e.target.value)}
                                          isInvalid={!!errors.number}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.number}
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb-3 m-2" controlId="formStreet">
                            <Form.Label>Adresa</Form.Label>
                            <Form.Control type="text" defaultValue={client.address.street}
                                          onChange={e => setField('street', e.target.value)}
                                          isInvalid={!!errors.street}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.street}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2 " controlId="formCity">
                            <Form.Label>Grad</Form.Label>
                            <Form.Control type="text" defaultValue={client.address.place}
                                          onChange={e => setField('place', e.target.value)}
                                          isInvalid={!!errors.place}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.place}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2" controlId="formCountry">
                            <Form.Label>Drzava</Form.Label>
                            <Form.Control type="text" defaultValue={client.address.country}
                                          onChange={e => setField('country', e.target.value)}
                                          isInvalid={!!errors.country}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.country}
                            </Form.Control.Feedback>
                        </Form.Group>
                    </Form>
                    <Button variant="link" onClick={handleShowPassword}>Promenite lozinku</Button>
                    <ChangeClientPassword show={showPassword} setShow={setShowPassword}/>
                </Modal.Body>
                <Modal.Footer>
                    <Button className="me-auto" variant="btn btn-outline-danger" onClick={handleShowDeleteAccPopUp}>
                        Obrisi nalog
                    </Button>
                    <Button variant="secondary" onClick={handleClose}>
                        Otkazi
                    </Button>
                    <Button variant="primary" onClick={handleSubmit}>
                        Azuriraj
                    </Button>
                </Modal.Footer>
            </Modal>
            <DeleteClientPopUp client={client} showDeleteClient={showDeleteClient}
                               handleCloseDeleteClient={handleCloseDeleteClient}/>
        </>
    );
}

export default UpdateClientInfo