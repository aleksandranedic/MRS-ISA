import {Button, Form, Modal} from "react-bootstrap";
import React, {useState} from "react";
import DeleteClientPopUp from "./DeleteClientPopUp";

function UpdateClientInfo({handleDeleteAccount, handleClose, showPopUp, updateClient}) {
    const [showDeleteClient, setShow] = useState(false);

    const handleCloseDeleteClient = () => setShow(false);
    const handleShowDeleteAccPopUp = () => setShow(true);

    const [form, setForm] = useState({})
    const [errors, setErrors] = useState({})

    const stringRegExp = new RegExp("[A-Z][A-Za-z]+")
    const streetRegExp = new RegExp("[A-Z][A-Za-z]+.?[A-Za-z]*")
    const passwordExp = new RegExp(".[^ ]+")
    const numExp = new RegExp("[1-9][0-9]*[a-z]?")
    const phoneNumRegExp = new RegExp("^[0-9]{7,10}$")

    const findFormErrors = () => {
        const {firstName, lastName, password, confPass, number, street, place, country, phoneNumber} = form
        const newErrors = {}
        if (!firstName) newErrors.firstName = 'Polje ne sme da bude prazno!'
        else if (!stringRegExp.test(firstName)) newErrors.firstName = 'Mora da pocne sa velikim slovom!'

        if (!lastName) newErrors.lastName = 'Polje ne sme da bude prazno!'
        else if (!stringRegExp.test(lastName)) newErrors.lastName = 'Mora da pocne sa velikim slovom!'

        if (!password || !passwordExp.test(password)) newErrors.password = 'Polje ne sme da bude prazno!'

        if (!confPass || !passwordExp.test(confPass)) newErrors.confPass = 'Polje ne sme da bude prazno!'
        else if (confPass !== password) newErrors.confPass = 'Sifre se ne poklapaju!'

        if (!number) newErrors.number = 'Polje ne sme da bude prazno!'
        else if (!numExp.test(number)) newErrors.number = 'Mora da sadrzi brojeve!'

        if (!street || !streetRegExp.test(street)) newErrors.street = 'Polje ne sme da bude prazno!'

        if (!place) newErrors.place = 'Polje ne sme da bude prazno!'
        else if (!streetRegExp.test(place)) newErrors.place = 'Mora da pocne sa velikim slovom!'

        if (!country) newErrors.country = 'Polje ne sme da bude prazno!'
        else if (!streetRegExp.test(country)) newErrors.country = 'Mora da pocne sa velikim slovom!'

        if (!phoneNumber) newErrors.phoneNumber = 'Polje ne sme da bude prazno!'
        if (!phoneNumRegExp.test(phoneNumber)) newErrors.phoneNumber = 'Mora da sadrzi od 7 do 10 cifara!'


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
                password: form.password,
                phoneNumber: form.phoneNumber,
                address: {
                    number: form.number,
                    street: form.street,
                    place: form.place,
                    country: form.country
                }
            }
            updateClient(userDTO)
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
                    <Modal.Title>Azuriranje podataka</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form className="d-flex">
                        <div className="m-2 w-50">
                            <Form.Group className="mb-3" controlId="formName">
                                <Form.Label>Ime</Form.Label>
                                <Form.Control type="text"
                                              onChange={e => setField('firstName', e.target.value)}
                                              isInvalid={!!errors.firstName}/>
                                <Form.Control.Feedback type='invalid'>
                                    {errors.firstName}
                                </Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formSurname">
                                <Form.Label>Prezime</Form.Label>
                                <Form.Control type="text"
                                              onChange={e => setField('lastName', e.target.value)}
                                              isInvalid={!!errors.lastName}/>
                                <Form.Control.Feedback type='invalid'>
                                    {errors.lastName}
                                </Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formPhoneNumber">
                                <Form.Label>Broj telefona</Form.Label>
                                <Form.Control type="text"
                                              onChange={e => setField('phoneNumber', e.target.value)}
                                              isInvalid={!!errors.phoneNumber}/>
                                <Form.Control.Feedback type='invalid'>
                                    {errors.phoneNumber}
                                </Form.Control.Feedback>
                            </Form.Group>
                        </div>
                        <div className="m-2 w-50">
                            <Form.Group className="mb-3 m" controlId="formBasicPassword">
                                <Form.Label>Sifra</Form.Label>
                                <Form.Control type="password"
                                              onChange={e => setField('password', e.target.value)}
                                              isInvalid={!!errors.password}/>
                                <Form.Control.Feedback type='invalid'>
                                    {errors.password}
                                </Form.Control.Feedback>
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formConfirmPassword">
                                <Form.Label>Ponovi sifru</Form.Label>
                                <Form.Control type="password"
                                              onChange={e => setField('confPass', e.target.value)}
                                              isInvalid={!!errors.confPass}/>
                                <Form.Control.Feedback type='invalid'>
                                    {errors.confPass}
                                </Form.Control.Feedback>
                            </Form.Group>
                        </div>
                    </Form>
                    <Form className="d-flex gap-4">
                        <Form.Group className="mb-3 m-2" controlId="formNumOfAddress">
                            <Form.Label>Broj</Form.Label>
                            <Form.Control type="text"
                                          onChange={e => setField('number', e.target.value)}
                                          isInvalid={!!errors.number}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.number}
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb-3 m-2" controlId="formStreet">
                            <Form.Label>Adresa</Form.Label>
                            <Form.Control type="text"
                                          onChange={e => setField('street', e.target.value)}
                                          isInvalid={!!errors.street}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.street}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2 " controlId="formCity">
                            <Form.Label>Grad</Form.Label>
                            <Form.Control type="text"
                                          onChange={e => setField('place', e.target.value)}
                                          isInvalid={!!errors.place}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.place}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2" controlId="formCountry">
                            <Form.Label>Drzava</Form.Label>
                            <Form.Control type="text"
                                          onChange={e => setField('country', e.target.value)}
                                          isInvalid={!!errors.country}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.country}
                            </Form.Control.Feedback>
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
                    <Button variant="primary" onClick={handleSubmit}>
                        Azuriraj
                    </Button>
                </Modal.Footer>
            </Modal>
            <DeleteClientPopUp showDeleteClient={showDeleteClient} handleCloseDeleteClient={handleCloseDeleteClient}
                               handleDeleteAccount={handleDeleteAccount} handleClose={handleClose}/>
        </>
    );
}

export default UpdateClientInfo