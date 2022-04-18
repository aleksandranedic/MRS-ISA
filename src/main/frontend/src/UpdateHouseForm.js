import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import UpdateHouseImages from './UpdateHouseImages';

function UpdateHouseForm({house}) {
    return (
        <Form>
            <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridTitle">
                    <Form.Label>Naziv</Form.Label>
                    <Form.Control type="title" defaultValue={house.title} />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridPrice">
                    <Form.Label>Cena</Form.Label>
                    <Form.Control type="price" defaultValue={house.price} />
                </Form.Group>
            </Row>

            <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                <Form.Label>Opis</Form.Label>
                <Form.Control as="textarea" rows={3} defaultValue={house.description}/>
            </Form.Group>

            <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridRooms">
                    <Form.Label>Broj soba</Form.Label>
                    <Form.Control type="name" defaultValue={house.number_of_rooms}/>
                </Form.Group>

                <Form.Group as={Col} controlId="formGridCapacity">
                    <Form.Label>Kapacitet</Form.Label>
                    <Form.Control type="price" defaultValue={house.number_of_beds_per_room} />
                </Form.Group>
            </Row>

            <Form.Group className="mb-3" controlId="formGridRules">
                <Form.Label>Pravila ponašanja</Form.Label>
                <Form.Control defaultValue={house.rules_and_regulations} />
            </Form.Group>

            <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridAddress">
                    <Form.Label>Adresa</Form.Label>
                    <Form.Control defaultValue={house.address}/>
                </Form.Group>

                <Form.Group as={Col} controlId="formGridCity">
                    <Form.Label>Grad</Form.Label>
                    <Form.Control defaultValue={house.city} />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridCountry">
                    <Form.Label>Država</Form.Label>
                    <Form.Control defaultValue={house.country}/>
                </Form.Group>
            </Row>

            <Form.Group className="mb-3" controlId="formGridServices">
                <Form.Label>Dodatne usluge</Form.Label>
                <Form.Control defaultValue={house.services} />
            </Form.Group>

            <Form.Group controlId="formFileMultiple" className="mb-3">
                <Form.Label>Fotografije vikendice</Form.Label>
                <UpdateHouseImages/>
                <Form.Label>Dodaj</Form.Label>
                <Form.Control type="file" multiple/>
            </Form.Group>
            </Form>
    );
}

export default UpdateHouseForm;