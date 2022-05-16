import React, {useState} from 'react'
import {Button, Form, Modal} from "react-bootstrap";
import {Info} from "../Info";
import axios from "axios";
import Collapse from "react-bootstrap/Collapse";

export default function DeleteClientPopUp({client, showDeleteClient, handleCloseDeleteClient}) {
    const [deleteReason, setReason] = useState("")

    function handleDeleteAccount() {
        axios.post("http://localhost:4444/client/delete/" + client.id, deleteReason).then(
            res => {
                console.log(res)
            }
        )
    }

    return (
        <Modal show={showDeleteClient} onHide={handleCloseDeleteClient} size="medium" className="mt-lg-5">
            <Modal.Header closeButton>
                <Modal.Title>Brisanje naloga korisnika</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Info text={"Da li ste sigurni da zelite da obrisete nalog?"}/>
                <Form.Group className="mb-3" controlId="formPhoneNumber">
                    <Form.Group className="mb-3 m-2 hidden" controlId="form">
                        <Form.Label>Obrazloženje za brisanje naloga</Form.Label>
                        <Form.Control as="textarea" rows={2}
                                      onChange={e => setReason(e.target.value)}/>
                    </Form.Group>
                </Form.Group>
            </Modal.Body>
            <Modal.Footer>
                <Button className="me-auto" variant="btn btn-outline-danger" onClick={function () {
                    handleDeleteAccount()
                    handleCloseDeleteClient()
                }}>
                    DA
                </Button>
                <Button variant="btn btn-outline-success" onClick={handleCloseDeleteClient}>
                    NE
                </Button>
            </Modal.Footer>
        </Modal>
    )
}
