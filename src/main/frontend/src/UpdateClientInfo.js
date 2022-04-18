import {Button, Form, Modal} from "react-bootstrap";
import React, {useState} from "react";
import DeleteClientPopUp from "./DeleteClientPopUp";

function UpdateClientInfo({handleDeleteAccount, handleClose, showPopUp, updateClient}) {
    const [showDeleteClient, setShow] = useState(false);

    const handleCloseDeleteClient = () => setShow(false);
    const handleShowDeleteAccPopUp = () => setShow(true);

    const [form, setForm] = useState({})
    const [errors, setErrors] = useState({})

    const stringRegExp=new RegExp("[A-Z][A-Za-z]+")
    const addressRegExp=new RegExp("[A-Z][A-Za-z]+.?[A-Za-z]*")
    const passwordExp=new RegExp(".[^ ]+")
    const numExp=new RegExp("[1-9][0-9]*[a-z]?")
    const phoneNumRegExp=new RegExp("[0-9]{7,10}")

    const findFormErrors = () => {
        const {firstName, lastName, password, confPass, streetNum, address, city, country,phoneNumber} = form
        const newErrors = {}
        if (!stringRegExp.test(firstName)) newErrors.firstName = 'cannot be blank!'

        if (!stringRegExp.test(lastName)) newErrors.lastName = 'cannot be blank! must begin with capital'

        if (!password || !passwordExp.test(password)) newErrors.password = 'cannot be blank!'

        if (!confPass || !passwordExp.test(confPass)) newErrors.confPass = 'cannot be blank!'
        else if (confPass !== password) newErrors.confPass = 'passwords are not matching'

        if (!numExp.test(streetNum)) newErrors.streetNum = 'cannot be blank!'

        if (!address || !addressRegExp.test(address)) newErrors.address = 'cannot be blank!'

        if (!city || !addressRegExp.test(city)) newErrors.city = 'cannot be blank!'

        if (!country || !addressRegExp.test(country)) newErrors.country = 'cannot be blank!'

        if (!phoneNumber || !phoneNumRegExp.test(phoneNumber)) newErrors.phoneNumber = 'cannot be blank!'

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
            console.log('Thank you for your feedback!')
            console.log(form)
            updateClient(form) //ovo nam azurira korisnika
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
                                          onChange={e => setField('streetNum', e.target.value)}
                                          isInvalid={!!errors.streetNum}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.streetNum}
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb-3 m-2" controlId="formAddress">
                            <Form.Label>Adresa</Form.Label>
                            <Form.Control type="text"
                                          onChange={e => setField('address', e.target.value)}
                                          isInvalid={!!errors.address}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.address}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2 " controlId="formCity">
                            <Form.Label>Grad</Form.Label>
                            <Form.Control type="text"
                                          onChange={e => setField('city', e.target.value)}
                                          isInvalid={!!errors.city}/>
                            <Form.Control.Feedback type='invalid'>
                                {errors.city}
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