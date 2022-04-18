import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';

function HouseForm(props) {
    return (
        <Form>
            <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridName">
                    <Form.Label>Naziv</Form.Label>
                    <Form.Control type="name" placeholder="Naziv vikendice" />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridPrice">
                    <Form.Label>Cena</Form.Label>
                    <Form.Control type="price" placeholder="Cena vikendice po noćenju" />
                </Form.Group>
            </Row>

            <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                <Form.Label>Opis</Form.Label>
                <Form.Control as="textarea" rows={3} />
            </Form.Group>

            <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridRooms">
                    <Form.Label>Broj soba</Form.Label>
                    <Form.Control type="name" placeholder="Broj soba" />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridCapacity">
                    <Form.Label>Kapacitet</Form.Label>
                    <Form.Control type="price" placeholder="Kapacitet vikendice" />
                </Form.Group>
            </Row>

            <Form.Group className="mb-3" controlId="formGridRules">
                <Form.Label>Pravila ponašanja</Form.Label>
                <Form.Control placeholder="Dozvoljeno i zabranjeno" />
            </Form.Group>

            <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridAddress">
                    <Form.Label>Adresa</Form.Label>
                    <Form.Control />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridCity">
                    <Form.Label>Grad</Form.Label>
                    <Form.Control />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridCountry">
                    <Form.Label>Država</Form.Label>
                    <Form.Control />
                </Form.Group>
            </Row>

            <Form.Group className="mb-3" controlId="formGridServices">
                <Form.Label>Dodatne usluge</Form.Label>
                <Form.Control placeholder="WiFi, parking, bazen, pet-friendly..." />
            </Form.Group>

            <Form.Group controlId="formFileMultiple" className="mb-3">
                <Form.Label>Fotografije vikendice</Form.Label>
                <Form.Control type="file" multiple />
            </Form.Group>
            </Form>
    );
}

export default HouseForm;