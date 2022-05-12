import {Button, Form, Modal, InputGroup} from "react-bootstrap";
import axios from "axios"; 
import React, {useState, useRef} from "react";
import { useParams } from "react-router-dom";


export function BoatOwnerForm({show, setShow, owner}) {
    const {id} = useParams();
    const form = useRef();
    const [showPassword, setShowPassword] = useState(false);
    const [validatedInfo, setValidatedInfo] = useState(false);
    const [boatOwner, setBoatOwner] = useState(owner);
    const handleShowPassword = () => setShowPassword(true);

    const setFirstName = (value) => {
        setBoatOwner( prevState => {
           return {...prevState, firstName:value}
        })
    }
    const setLastName = (value) => {
        setBoatOwner( prevState => {
           return {...prevState, lastName:value}
        })
    }
    const setPhoneNumber = (value) => {
        setBoatOwner( prevState => {
           return {...prevState, phoneNumber:value}
        })
    }

    const setStreet = (value) => {
        setBoatOwner( prevState => {
            var newaddress = boatOwner.address
            newaddress.street = value
            return {...prevState, address:newaddress}
        })
    }
    const setNumber = (value) => {
        setBoatOwner( prevState => {
            var newaddress = boatOwner.address
            newaddress.number = value
            return {...prevState, address:newaddress}
        })
    }
    const setCity = (value) => {
        setBoatOwner( prevState => {
            var newaddress = boatOwner.address
            newaddress.place = value
            return {...prevState, address:newaddress}
        })
    }
    const setCountry = (value) => {
        setBoatOwner( prevState => {
            var newaddress = boatOwner.address
            newaddress.country = value
            return {...prevState, address:newaddress}
        })
    }
    const handleSubmit = e => {
        e.preventDefault()
        if (form.current.checkValidity() === false) {
            e.stopPropagation();
            setValidatedInfo(true);
        }
        else {
            var data = new FormData(form.current);
            data.append("street", boatOwner.address.street)
            data.append("number", boatOwner.address.number)
            data.append("place", boatOwner.address.place)
            data.append("country", boatOwner.address.country)
            axios
            .post("http://localhost:4444/boatowner/updateOwner/" + id, data)
            .then(res => {
                window.location.reload();
            });
        }
    }
    return <Modal size="lg" show={show} onHide={() => setShow(false)}>
        <Form ref={form} noValidate validated={validatedInfo}>
            <Modal.Header closeButton>
                <Modal.Title>Ažuriranje profila</Modal.Title>
            </Modal.Header>

            <Modal.Body>

                <Form.Group className="mb-3 m-2" controlId="firstName">
                    <Form.Label>Ime</Form.Label>
                    <Form.Control required type="text" name="firstName" defaultValue={boatOwner.firstName} onChange={e => setFirstName(e.target.value)}/>
                    <Form.Control.Feedback type="invalid">Molimo Vas unesite ime.</Form.Control.Feedback>               
                </Form.Group>

                <Form.Group className="mb-3 m-2" controlId="lastName">
                    <Form.Label>Prezime</Form.Label>
                    <Form.Control required type="text" name="lastName" defaultValue={boatOwner.lastName} onChange={e => setLastName(e.target.value)}/>
                    <Form.Control.Feedback type="invalid">Molimo Vas unesite prezime.</Form.Control.Feedback>               
                </Form.Group>

                <Form.Group className="mb-3 m-2" controlId="phoneNumber">
                    <Form.Label>Broj telefona</Form.Label>
                    <Form.Control required type="text" name="phoneNumber" defaultValue={boatOwner.phoneNumber} onChange={e => setPhoneNumber(e.target.value)}/>
                    <Form.Control.Feedback type="invalid">Molimo Vas unesite broj telefona.</Form.Control.Feedback>               
                </Form.Group>

                <div className="d-flex">
                    <Form.Group className="mb-3 m-2">
                        <Form.Label>Ulica i broj</Form.Label>
                        <InputGroup>
                        <Form.Control required className='w-50' type="text" defaultValue={boatOwner.address.street} onChange={e => setStreet(e.target.value)}/>
                        <Form.Control required className='w-50' type="text" defaultValue={boatOwner.address.number} onChange={e => setNumber(e.target.value)}/>
                        <Form.Control.Feedback type="invalid">Molimo Vas unesite ulicu i broj.</Form.Control.Feedback>               
                        </InputGroup>
                    </Form.Group>

                    <Form.Group className="mb-3 m-2" controlId="place">
                        <Form.Label>Mesto</Form.Label>
                        <Form.Control required type="text" defaultValue={boatOwner.address.place} onChange={e => setCity(e.target.value)}/>
                        <Form.Control.Feedback type="invalid">Molimo Vas unesite mesto.</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group className="mb-3 m-2" controlId="country">
                        <Form.Label>Drzava</Form.Label>
                        <Form.Control required type="text" defaultValue={boatOwner.address.country} onChange={e => setCountry(e.target.value)}/>
                        <Form.Control.Feedback type="invalid">Molimo Vas unesite državu.</Form.Control.Feedback>
                    </Form.Group>
                </div>
                <Button variant="link" onClick={handleShowPassword}>Promenite lozinku</Button>
                <ChangePassword  show={showPassword} setShow={setShowPassword}/>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="outline-danger" className="me-auto" onClick={()=>setShow(false)}> Obrisi</Button>
                <Button variant="secondary" onClick={()=>setShow(false)}> Otkazi  </Button>
                <Button variant="primary" onClick={handleSubmit}> Izmeni </Button>
            </Modal.Footer>
        </Form>
    </Modal>
}

export function ChangePassword({show, setShow}) {
    const formPassword = useRef();
    const {id} = useParams();
    const [validatedPassword, setvalidatedPassword] = useState(false);
    const [passwords, setPasswords] = useState({oldPassword:'', newPassword:''});
    const handleClose = () => setShow(false);
    const changePassword = e => {
        document.getElementById("invalidOldPassword").style.display = "none"
        e.preventDefault()
        if (formPassword.current.checkValidity() === false) {
            e.stopPropagation();
            setvalidatedPassword(true);
        }
        else {
            var oldPassword = passwords.oldPassword
            axios
            .post("http://localhost:4444/boatowner/checkPassword/" + id, oldPassword, {headers: {"Content-Type": "text/plain"}})
            .then(res => {
                if (res.data){
                    updatePassword();
                }
                else {
                    document.getElementById("invalidOldPassword").style.display = "block"
                }
            });
        }
    }
    const updatePassword = () => {
        var newPassword = passwords.newPassword
        axios
            .post("http://localhost:4444/boatowner/updatePassword/" + id, newPassword, {headers: {"Content-Type": "text/plain"}})
            .then(res => {
                window.location.reload();
            });
    }
    const setOldPassword = (value) => {
        setPasswords( prevState => {
           return {...prevState, oldPassword:value}
        })
    }
    const setNewPassword = (value) => {
        setPasswords( prevState => {
           return {...prevState, newPassword:value}
        })
    }
    return <Modal show={show} onHide={() => setShow(false)}>
        <Form noValidate validated={validatedPassword} ref={formPassword}>
            <Modal.Header closeButton>
                <Modal.Title>Promena lozinke</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form.Group className="mb-3 m-2" controlId="oldPassword">
                    <Form.Label>Stara lozinka</Form.Label>
                    <Form.Control name="oldPassword" required type="password" defaultValue={passwords.oldPassword} onChange={e => setOldPassword(e.target.value)}/>
                    <Form.Control.Feedback type="invalid">Molimo Vas unesite staru lozinku.</Form.Control.Feedback>
                    <p id="invalidOldPassword" style={{color:"#dc3545", fontSize: "0.875em", marginLeft:"0%", display:"none"}}>Trenutna lozinka je pogrešno uneta.</p>
                </Form.Group>

                <Form.Group className="mb-3 m-2" controlId="newPassword">
                    <Form.Label>Nova lozinka</Form.Label>
                    <Form.Control name="newPassword" required type="password" defaultValue={passwords.newPassword} onChange={e => setNewPassword(e.target.value)}/>
                    <Form.Control.Feedback type="invalid">Molimo Vas unesite novu lozinku.</Form.Control.Feedback>
                </Form.Group>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}> Otkazi </Button>
                <Button variant="primary" onClick={changePassword}> Izmeni </Button>
            </Modal.Footer>
        </Form>
    </Modal>
}
export default BoatOwnerForm;
