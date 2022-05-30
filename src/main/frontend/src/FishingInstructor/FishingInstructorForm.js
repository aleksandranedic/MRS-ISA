import {Button, Form, Modal} from "react-bootstrap";
import React, {useState, useRef} from "react";
import {backLink, missingDataErrors} from "../Consts";
import axios from "axios";
import { useParams } from "react-router-dom";


export function FishingInstructorForm({show, setShow, fishingInstructor, profileImg}) {
    
    const [showPassword, setShowPassword] = useState(false);
    const handleShowPassword = () => setShowPassword(true);

    const {id} = useParams();
    const formRef = useRef();

    const [formValues, setFormValues] = useState(fishingInstructor);
    const [formErrors, setFormErrors] = useState({});
    const [isSubmit, setIsSubmit] = useState(false);


    const opetFileExplorer = () => {
        document.getElementById("fileImage").click();
    }

    const setProfileImageView = () => {
        var file = document.getElementById("fileImage").files[0]
        document.getElementById("profPic").src = URL.createObjectURL(file);
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValues({ ...formValues, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault()
        let errors = validate(formValues)
        if (Object.keys(errors).length > 0) {
            setFormErrors(errors);
        } else {
            var file = document.getElementById("fileImage").files[0];
            if (typeof file !== "undefined"){
                var files = document.getElementById("fileImage").files;
                var data = new FormData();
                var images = []
                for (let i=0; i < files.length; i++){
                    images.push(files[i])
                }
                data.append("fileImage",file);
                axios
                .post("http://localhost:4444/fishinginstructor/changeProfilePicture/" + id, data)
                .then(res => {
                        console.log(res.data)
                });
            }
            axios
                .post(backLink + "/fishinginstructor/" + fishingInstructor.id + "/edit", formValues)
                .then(response => {
                    console.log(response)
                })
                .catch(error => {
                    console.log(error)
                })

            setShow(false);
            window.location.reload();
        }
    };

    const validate = (formValues) => {
        const errors = {};
        if (!formValues.firstName) {
            errors.firstName = missingDataErrors.firstName;
        }
        if (!formValues.lastName) {
            errors.lastName = missingDataErrors.lastName;
        }
        if (!formValues.phoneNumber) {
            errors.phoneNumber = missingDataErrors.phoneNumber;
        }
        if (!formValues.address.street) {
            errors.address.street = missingDataErrors.address.street;
        }if (!formValues.address.place) {
            errors.address.place = missingDataErrors.address.place;
        }if (!formValues.address.number) {
            errors.address.number = missingDataErrors.address.number;
        }if (!formValues.address.country) {
            errors.address.country = missingDataErrors.address.country;
        }

        return errors;
    };


    return <Modal
        size="lg"
        show={show}
        onHide={() => setShow(false)}>

        <Form>
            <Modal.Header closeButton>
                <Modal.Title>{formValues.firstName + " " + formValues.lastName}</Modal.Title>
            </Modal.Header>

            <Modal.Body>

                <div className="d-flex">
                    <div className="d-flex justify-content-center" style={{width:"28%"}}>
                        <img id="profPic" className="rounded-circle" style={{objectFit: "cover", maxWidth: "25vh", minWidth: "25vh", maxHeight: "25vh", minHeight: "25vh"}} src={profileImg}/>
                        <Form.Control id="fileImage" onChange={e => setProfileImageView()} className="d-none" type="file" name="fileImage" style={{position:"absolute", width:"25vh", top:"12vh"}}/>
                        <p id="setNewProfileImage" className="d-flex justify-content-center align-items-center" onClick={e => opetFileExplorer()}><u>Postavite profilnu</u></p>
                    </div>
                    <div style={{width:"72%"}}>
                        <Form.Group className="mb-3 m-2" controlId="firstName" ref={formRef}>
                            <Form.Label>Ime</Form.Label>
                            <Form.Control type="text" defaultValue={formValues.firstName}/>
                            <Form.Control.Feedback> {formErrors.firstName} </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2" controlId="lastName">
                            <Form.Label>Prezime</Form.Label>
                            <Form.Control type="text" defaultValue={formValues.lastName}/>
                        </Form.Group>
                    </div>
                </div>

                <Form.Group className="mb-3 m-2" controlId="phoneNumber">
                    <Form.Label>Broj telefona</Form.Label>
                    <Form.Control type="text" defaultValue={formValues.phoneNumber}/>
                </Form.Group>

                <div className="d-flex">
                    <Form.Group className="mb-3 m-2" controlId="street">
                        <Form.Label>Ulica</Form.Label>
                        <Form.Control type="text" defaultValue={formValues.address.street}/>
                    </Form.Group>
                    <Form.Group className="mb-3 m-2 " controlId="number">
                        <Form.Label>Broj</Form.Label>
                        <Form.Control type="text" defaultValue={formValues.address.number}/>
                    </Form.Group>
                    <Form.Group className="mb-3 m-2" controlId="place">
                        <Form.Label>Mesto</Form.Label>
                        <Form.Control type="text" defaultValue={formValues.address.place}/>
                    </Form.Group>
                    <Form.Group className="mb-3 m-2" controlId="country">
                        <Form.Label>Drzava</Form.Label>
                        <Form.Control type="text" defaultValue={formValues.address.country}/>
                    </Form.Group>
                </div>
                <Button variant="link" onClick={handleShowPassword}>Promenite lozinku</Button>
                <ChangePassword  show={showPassword} setShow={setShowPassword} user={formValues}/>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="outline-danger" className="me-auto" onClick={()=>setShow(false)}>
                    Obrisi
                </Button>
                <Button variant="secondary" onClick={()=>setShow(false)}>
                    Otkazi
                </Button>
                <Button variant="primary" onClick={handleSubmit}>
                    Izmeni
                </Button>
            </Modal.Footer>
        </Form>
    </Modal>
}

export function ChangePassword({show, setShow, user}) {

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

