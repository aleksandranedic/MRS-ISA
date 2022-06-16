import background from "../images/boatsnotext.png";
import {Button, Form, Modal} from "react-bootstrap";

import React, {useState} from "react";
import {frontLink} from "../Consts";


export function Conformation() {
    const [form, setForm] = useState({})
    const [errors, setErrors] = useState({})
    const passwordExp = new RegExp(".[^\\n\\t\\s]+")
    const findFormErrors = () => {
        const {password, confPass} = form
        const newErrors = {}



        if (!password || !passwordExp.test(password)) newErrors.password = 'Polje ne sme da bude prazno!'

        if (!confPass || !passwordExp.test(confPass)) newErrors.confPass = 'Polje ne sme da bude prazno!'
        else if (confPass !== password) newErrors.confPass = 'Sifre se ne poklapaju!'



        return newErrors

    }

    const handleSubmit = e => {
        e.preventDefault()
        const newErrors = findFormErrors()
        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors)
        } else {
           window.location.href = frontLink + "admin"

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
        <div className="d-flex align-items-center justify-content-center m-0 p-0 min-vw-90 min-vh-100"
             style={{backgroundImage: `url(${background})`, backgroundSize: "cover",}}
        >
            <div className="d-flex justify-content-center h-50 w-100">
                <Modal.Dialog size="lg" style={{width: "50vw"}}>
                    <Modal.Header className="d-flex justify-content-center ">
                        <Modal.Title>Promena lozinke</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form className="m-5 mt-3 mb-3">

                                <Form.Group className="mb-3 m" controlId="formBasicPassword">
                                    <Form.Label>Nova lozinka</Form.Label>
                                    <Form.Control type="password"
                                                  onChange={e => setField('password', e.target.value)}
                                                  isInvalid={!!errors.password}/>
                                    <Form.Control.Feedback type='invalid'>
                                        {errors.password}
                                    </Form.Control.Feedback>
                                </Form.Group>

                                <Form.Group className="mb-3" controlId="formConfirmPassword">
                                    <Form.Label>Ponovi lozinku</Form.Label>
                                    <Form.Control type="password"
                                                  onChange={e => setField('confPass', e.target.value)}
                                                  isInvalid={!!errors.confPass}/>
                                    <Form.Control.Feedback type='invalid'>
                                        {errors.confPass}
                                    </Form.Control.Feedback>
                                </Form.Group>

                        </Form>

                    </Modal.Body>
                    <Modal.Footer>

                        <Button variant="success" onClick={handleSubmit}>
                            Promeni lozinku
                        </Button>
                    </Modal.Footer>
                </Modal.Dialog>
            </div>

        </div>
    );
}