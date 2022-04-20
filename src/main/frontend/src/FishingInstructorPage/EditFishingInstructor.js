import {Button, Form, Modal} from "react-bootstrap";
import React, {useState} from "react";

export function EditFishingInstructor({show, setShow, fishingInstructor}) {

    const [showPassword, setShowPassword] = useState(false);
    const handleShowPassword = () => setShowPassword(true);


    const handleClose = () => setShow(false);
    const editFishingInstructor = () => {
        setShow(false);
    }

    return <Modal
        size="lg"
        show={show}
        onHide={() => setShow(false)}>

        <Form>
            <Modal.Header closeButton>
                <Modal.Title>{fishingInstructor.firstName + " " + fishingInstructor.lastName}</Modal.Title>
            </Modal.Header>

            <Modal.Body>

                <Form.Group className="mb-3 m-2" controlId="firstName">
                    <Form.Label>Ime</Form.Label>
                    <Form.Control type="text" defaultValue={fishingInstructor.firstName}/>
                </Form.Group>

                <Form.Group className="mb-3 m-2" controlId="lastName">
                    <Form.Label>Prezime</Form.Label>
                    <Form.Control type="text" defaultValue={fishingInstructor.lastName}/>
                </Form.Group>

                <Form.Group className="mb-3 m-2" controlId="phoneNumber">
                    <Form.Label>Broj telefona</Form.Label>
                    <Form.Control type="text" defaultValue={fishingInstructor.phoneNumber}/>
                </Form.Group>

                <div className="d-flex">
                    <Form.Group className="mb-3 m-2" controlId="street">
                        <Form.Label>Ulica</Form.Label>
                        <Form.Control type="text" defaultValue={""}/>
                    </Form.Group>
                    <Form.Group className="mb-3 m-2 " controlId="number">
                        <Form.Label>Broj</Form.Label>
                        <Form.Control type="text" defaultValue={""}/>
                    </Form.Group>
                    <Form.Group className="mb-3 m-2" controlId="city">
                        <Form.Label>Grad</Form.Label>
                        <Form.Control type="text" defaultValue={""}/>
                    </Form.Group>
                    <Form.Group className="mb-3 m-2" controlId="country">
                        <Form.Label>Drzava</Form.Label>
                        <Form.Control type="text" defaultValue={""}/>
                    </Form.Group>
                </div>
                <Button variant="link" onClick={handleShowPassword}>Promenite lozinku</Button>
                <ChangePassword  show={showPassword} setShow={setShowPassword} fishingInstructor={fishingInstructor}/>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="outline-danger" className="me-auto" onClick={handleClose}>
                    Obrisi
                </Button>
                <Button variant="secondary" onClick={handleClose}>
                    Otkazi
                </Button>
                <Button variant="primary" onClick={editFishingInstructor}>
                    Izmeni
                </Button>
            </Modal.Footer>
        </Form>
    </Modal>
}

export function ChangePassword({show, setShow, fishingInstructor}) {

    const handleClose = () => setShow(false);
    const changePassword = () => {
        setShow(false);
    }

    return <Modal
        show={show}
        onHide={() => setShow(false)}>

        <Form>
            <Modal.Header closeButton>
                <Modal.Title>Promena lozinke</Modal.Title>
            </Modal.Header>

            <Modal.Body>

                <Form.Group className="mb-3 m-2" controlId="firstName">
                    <Form.Label>Stara lozinka</Form.Label>
                    <Form.Control type="password"/>
                </Form.Group>

                <Form.Group className="mb-3 m-2" controlId="lastName">
                    <Form.Label>Nova lozinka</Form.Label>
                    <Form.Control type="password"/>
                </Form.Group>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Otkazi
                </Button>
                <Button variant="primary" onClick={changePassword}>
                    Izmeni
                </Button>
            </Modal.Footer>
        </Form>
    </Modal>
}

