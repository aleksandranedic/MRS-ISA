import {Button, Form, Modal} from "react-bootstrap";
import {backLink, loadingToast, updateForFetchedDataError, updateForFetchedDataSuccess} from "../Consts";
import React, {useState} from "react";
import axios from "axios";
import {ChangeClientPassword} from "../ClientPage/ChangeClientPassword";

export function AdminForm({show, setShow, administrator}) {
    let initialValue = {
        firstName: "",
        lastName: "",
        number: "",
        street: "",
        place: "",
        country: "",
        phoneNumber: "",
        email: ""
    }

    if (administrator !== null) {
        initialValue = {
            firstName: administrator.firstName,
            lastName: administrator.lastName,
            number: administrator.address.number,
            street: administrator.address.street,
            place: administrator.address.place,
            country: administrator.address.country,
            phoneNumber: administrator.phoneNumber,
            email: administrator.email
        }
    }

    const [form, setForm] = useState(initialValue)

    const [showPassword, setShowPassword] = useState(false);
    const handleShowPassword = () => setShowPassword(true);

    const [errors, setErrors] = useState({})

    const stringRegExp = new RegExp("[A-Z][A-Za-z]+")
    const streetRegExp = new RegExp("[A-Z][A-Za-z]+.?[A-Za-z]*")
    const numExp = new RegExp("[1-9][0-9]*[a-z]?")
    const phoneNumRegExp = new RegExp("^[0-9]{7,10}$")
    const emailRegExp = new RegExp(".+(@.+\\.com)|(@maildrop\\.cc)")

    const findFormErrors = () => {
        const {firstName, lastName, number, street, place, country, phoneNumber, email, role} = form
        const newErrors = {}

        if (!firstName) newErrors.firstName = 'Polje ne sme da bude prazno!'
        else if (!stringRegExp.test(firstName)) newErrors.firstName = 'Mora da pocne sa velikim slovom!'

        if (!lastName) newErrors.lastName = 'Polje ne sme da bude prazno!'
        else if (!stringRegExp.test(lastName)) newErrors.lastName = 'Mora da pocne sa velikim slovom!'

        if (!number) newErrors.number = 'Polje ne sme da bude prazno!'
        else if (!numExp.test(number)) newErrors.number = 'Mora da sadrzi brojeve!'

        if (!street || !streetRegExp.test(street)) newErrors.street = 'Polje ne sme da bude prazno!'

        if (!place) newErrors.place = 'Polje ne sme da bude prazno!'
        else if (!streetRegExp.test(place)) newErrors.place = 'Mora da pocne sa velikim slovom!'

        if (!country) newErrors.country = 'Polje ne sme da bude prazno!'
        else if (!streetRegExp.test(country)) newErrors.country = 'Mora da pocne sa velikim slovom!'

        if (!phoneNumber) newErrors.phoneNumber = 'Polje ne sme da bude prazno!'
        if (!phoneNumRegExp.test(phoneNumber)) newErrors.phoneNumber = 'Mora da sadrzi od 7 do 10 cifara!'

        if (!email) newErrors.email = 'Polje ne sme da bude prazno!'
        else if (!emailRegExp.test(email)) newErrors.email = 'Mora da sadrzi @gmail.com!'

        console.log(Object.keys(newErrors).length);
        console.log(newErrors);

        return newErrors

    }

    const handleSubmit = e => {
        e.preventDefault()
        const newErrors = findFormErrors()
        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors)
        } else {

            if (administrator == null) {
                registerAdmin()
            }
            else {
                editAdmin()
            }

        }
    }

    function registerAdmin() {
        const userDTO = {
            firstName: form.firstName,
            lastName: form.lastName,
            password: "",
            phoneNumber: form.phoneNumber,
            email: form.email,
            userRole: "ADMINISTRATOR",
            biography: "",
            registrationRationale: "",
            number: form.number,
            street: form.street,
            place: form.place,
            country: form.country
        }
        console.log(userDTO)
        axios.post(backLink+"/registration", userDTO).then(res => {
            console.log(res.data);
            window.location.reload();
        })

    }

    function editAdmin() {
        const userDTO = {
            firstName: form.firstName,
            lastName: form.lastName,
            phoneNumber: form.phoneNumber,
            email: form.email,
            number: form.number,
            street: form.street,
            place: form.place,
            country: form.country,
            id: administrator.id
        }

        axios.post(backLink+"/admin/edit", userDTO).then(res =>{
            console.log(res.data);
            window.location.reload();
        })
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


    return <Modal size="lg" show={show} onHide={()=>setShow(false)}>
        <Modal.Header closeButton>
            <Modal.Title>{administrator == null ? "Registracija" : "Izmena podataka"}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Form className="d-flex scrollbar scrollbar-primary">
                <div className="m-2 w-50">
                    <Form.Group className="mb-3" controlId="formName">
                        <Form.Label>Ime</Form.Label>
                        <Form.Control type="text"
                                      value={form.firstName}
                                      onChange={e => setField('firstName', e.target.value)}
                                      isInvalid={!!errors.firstName}/>
                        <Form.Control.Feedback type='invalid'>
                            {errors.firstName}
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formPhoneNumber">
                        <Form.Label>Broj telefona</Form.Label>
                        <Form.Control type="text"
                                      value={form.phoneNumber}
                                      onChange={e => setField('phoneNumber', e.target.value)}
                                      isInvalid={!!errors.phoneNumber}/>
                        <Form.Control.Feedback type='invalid'>
                            {errors.phoneNumber}
                        </Form.Control.Feedback>
                    </Form.Group>
                </div>
                <div className="m-2 w-50">
                    <Form.Group className="mb-3" controlId="formSurname">
                        <Form.Label>Prezime</Form.Label>
                        <Form.Control type="text"
                                      value={form.lastName}
                                      onChange={e => setField('lastName', e.target.value)}
                                      isInvalid={!!errors.lastName}/>
                        <Form.Control.Feedback type='invalid'>
                            {errors.lastName}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group className="mb-3 m" controlId="formBasicEmail">
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="email"
                                      value={form.email}
                                      onChange={e => setField('email', e.target.value)}
                                      isInvalid={!!errors.email}/>
                        <Form.Control.Feedback type='invalid'>
                            {errors.email}
                        </Form.Control.Feedback>
                    </Form.Group>

                </div>
            </Form>
            <Form className="d-flex">
                <Form.Group className="mb-3 m-2" controlId="formStreet">
                    <Form.Label>Ulica</Form.Label>
                    <Form.Control type="text"
                                  value={form.street}
                                  onChange={e => setField('street', e.target.value)}
                                  isInvalid={!!errors.street}/>
                    <Form.Control.Feedback type='invalid'>
                        {errors.street}
                    </Form.Control.Feedback>
                </Form.Group>
                <Form.Group className="mb-3 m-2" controlId="formNumOfAddress">
                    <Form.Label>Broj</Form.Label>
                    <Form.Control type="text"
                                  value={form.number}
                                  onChange={e => setField('number', e.target.value)}
                                  isInvalid={!!errors.number}/>
                    <Form.Control.Feedback type='invalid'>
                        {errors.number}
                    </Form.Control.Feedback>
                </Form.Group>
                <Form.Group className="mb-3 m-2 " controlId="formCity">
                    <Form.Label>Mesto</Form.Label>
                    <Form.Control type="text"
                                  value={form.place}
                                  onChange={e => setField('place', e.target.value)}
                                  isInvalid={!!errors.place}/>
                    <Form.Control.Feedback type='invalid'>
                        {errors.place}
                    </Form.Control.Feedback>
                </Form.Group>

                <Form.Group className="mb-3 m-2" controlId="formCountry">
                    <Form.Label>Država</Form.Label>
                    <Form.Control type="text"
                                  value={form.country}
                                  onChange={e => setField('country', e.target.value)}
                                  isInvalid={!!errors.country}/>
                    <Form.Control.Feedback type='invalid'>
                        {errors.country}
                    </Form.Control.Feedback>
                </Form.Group>

                <Button variant="link" onClick={handleShowPassword}>Promenite lozinku</Button>
                <ChangeClientPassword show={showPassword} setShow={setShowPassword}/>
            </Form>
        </Modal.Body>
        <Modal.Footer>
            <Button variant="outline-secondary" onClick={()=>setShow(false)}>
                Odustani
            </Button>
            {administrator == null ?
                <Button variant="success" onClick={handleSubmit}>
                    Registruj
                </Button>
                :
                <Button variant="success" onClick={handleSubmit}>
                    Izmeni
                </Button>
            }

        </Modal.Footer>
    </Modal>

}