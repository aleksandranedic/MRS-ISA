import React, {useState} from 'react'
import {Button, Form, Modal} from "react-bootstrap";
import background from "./images/registration.jpg"

export default function Registration() {

    const [form, setForm] = useState({})
    const [errors, setErrors] = useState({})

    const stringRegExp = new RegExp("[A-Z][A-Za-z]+")
    const streetRegExp = new RegExp("[A-Z][A-Za-z]+.?[A-Za-z]*")
    const passwordExp = new RegExp(".[^\\n\\t\\s]+")
    const numExp = new RegExp("[1-9][0-9]*[a-z]?")
    const phoneNumRegExp = new RegExp("[0-9]{7,10}")
    const emailRegExp =new RegExp(".+@gmail\\.com")

    const findFormErrors = () => {
        const {firstName, lastName, password, confPass, number, street, place, country, phoneNumber,email} = form
        const newErrors = {}
        if(!firstName) newErrors.firstName = 'Polje ne sme da bude prazno!'
        else if (!stringRegExp.test(firstName)) newErrors.firstName = 'Mora da pocne sa velikim slovom!'

        if(!lastName) newErrors.lastName = 'Polje ne sme da bude prazno!'
        else if (!stringRegExp.test(lastName)) newErrors.lastName = 'Mora da pocne sa velikim slovom!'

        if (!password || !passwordExp.test(password)) newErrors.password = 'Polje ne sme da bude prazno!'

        if (!confPass || !passwordExp.test(confPass)) newErrors.confPass = 'Polje ne sme da bude prazno!'
        else if (confPass !== password) newErrors.confPass = 'Sifre se ne poklapaju!'

        if(!number) newErrors.number = 'Polje ne sme da bude prazno!'
        else if (!numExp.test(number)) newErrors.number = 'Mora da sadrzi brojeve!'

        console.log(street)
        if (!street || !streetRegExp.test(street)) newErrors.street = 'Polje ne sme da bude prazno!'

        if (!streetRegExp.test(place)) newErrors.place = 'Polje ne sme da bude prazno!'

        if (!streetRegExp.test(country)) newErrors.country = 'Polje ne sme da bude prazno!'

        if(!phoneNumber)newErrors.phoneNumber = 'Polje ne sme da bude prazno!'
        if (!phoneNumRegExp.test(phoneNumber)) newErrors.phoneNumber = 'Mora da sadrzi od 7 do 10 cifara!'

        if (!email) newErrors.email = 'Polje ne sme da bude prazno!'
        else if(!emailRegExp.test(email)) newErrors.email = 'Mora da sadrzi @gmail.com!'

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
                email: form.email,
                address: {
                    number: form.number,
                    street: form.street,
                    place: form.place,
                    country: form.country
                }
            }
            console.log(userDTO)
            // updateClient(userDTO)
            // handleClose()
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
        <div className="m-0 p-0 min-vw-100 min-vh-100" style={{
            backgroundImage: `url(${background})`,  backgroundSize: "cover",
        }} >
            <div className="d-flex justify-content-center h-100 w-100">
                <Modal.Dialog size="lg">
                    <Modal.Header closeButton>
                        <Modal.Title>Registracija</Modal.Title>
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
                                <Form.Group className="mb-3 m" controlId="formBasicEmail">
                                    <Form.Label>Majl</Form.Label>
                                    <Form.Control type="email"
                                                  onChange={e => setField('email', e.target.value)}
                                                  isInvalid={!!errors.email}/>
                                    <Form.Control.Feedback type='invalid'>
                                        {errors.email}
                                    </Form.Control.Feedback>
                                </Form.Group>
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
                        <Button variant="btn btn-outline-primary">
                            Prijavi se
                        </Button>
                        <Button variant="success" onClick={handleSubmit}>
                            Registruj se
                        </Button>
                    </Modal.Footer>
                </Modal.Dialog>
            </div>
        </div>
    );
}
