import {Button, Form, Modal} from "react-bootstrap";
import {useState} from "react";
import axios from "axios";
import {backLink} from "../Consts";


export function BusyPeriodModal({show, setShow, events, setEvents}) {


    const [formValues, setFormValues] = useState({startDate: null, endDate: null});

    const setField = (fieldName, value) => {
        setFormValues({
            ...formValues,
            [fieldName]: value
        })
    }

    function addReservation() {

        setEvents([...events, {
            title: '', date: formValues['startDate']
        }])

        axios
            .post(backLink + "/reservation/adventure/add", formValues)
            .then(response => {
                console.log(response)
            })
            .catch(error => {
                console.log(error)
            })
        setShow(false);
    }

    return (<Modal show={show} onHide={() => setShow(false)}>
        <Modal.Header closeButton>
            <Modal.Title>Period zauzetosti</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Form>
                <Form.Group>
                    <Form.Label>Datum</Form.Label>
                    <Form.Control type="date" name="startDate"
                                  onChange={(e) => setField("startDate", e.target.value)}

                    />
                </Form.Group>

            </Form>

        </Modal.Body>
        <Modal.Footer>
            <Button variant="secondary" onClick={() => setShow(false)}>
                Otkaži
            </Button>
            <Button variant="primary" onClick={addReservation}>
                Dodaj
            </Button>
        </Modal.Footer>
    </Modal>)
}