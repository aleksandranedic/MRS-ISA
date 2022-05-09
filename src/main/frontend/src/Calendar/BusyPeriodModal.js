import {Button, Form, Modal} from "react-bootstrap";


export function BusyPeriodModal({show, setShow}) {

    return (<Modal show={show} onHide={() => setShow(false)}>
        <Modal.Header closeButton>
            <Modal.Title>Period zauzetosti</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Form>
                <Form.Group>
                    <Form.Label>Početni datum</Form.Label>
                    <Form.Control type="date"/>
                </Form.Group>

                <Form.Group>
                    <Form.Label>Krajnji datum</Form.Label>
                    <Form.Control type="date"/>
                </Form.Group>

            </Form>

        </Modal.Body>
        <Modal.Footer>
            <Button variant="secondary" onClick={() => setShow(false)}>
                Otkaži
            </Button>
            <Button variant="primary" onClick={() => setShow(false)}>
                Dodaj
            </Button>
        </Modal.Footer>
    </Modal>)
}