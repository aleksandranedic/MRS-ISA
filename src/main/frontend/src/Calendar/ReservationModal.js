import {Button, Form, Modal} from "react-bootstrap";

export function ReservationModal({show, setShow, date}) {

    return (<Modal show={show} onHide={() => setShow(false)}>
        <Modal.Header closeButton>
            <Modal.Title>Rezervacija</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Form>
                <Form.Group>
                    <Form.Label>Datum</Form.Label>
                    <Form.Control type="date" value={date}/>
                </Form.Group>

                <div className="d-flex w-100">
                    <Form.Group className="me-2 w-50 mt-2">
                        <Form.Label>Vreme početka</Form.Label>
                        <Form.Control type="time" min="05:00" max="20:00"/>
                    </Form.Group>

                    <Form.Group className="ms-2 w-50 mt-2">
                        <Form.Label>Vreme zavrsetka</Form.Label>
                        <Form.Control type="time" min="05:00" max="20:00"/>
                    </Form.Group>
                </div>
            </Form>

        </Modal.Body>
        <Modal.Footer>
            <Button variant="secondary" onClick={() => setShow(false)}>
                Otkaži
            </Button>
            <Button variant="primary" onClick={() => setShow(false)}>
                Rezerviši
            </Button>
        </Modal.Footer>
    </Modal>)
}