import React from 'react';
import { Form, Row, Col, InputGroup, Button } from 'react-bootstrap';
import UpdateHouseImages from './UpdateHouseImages';
import { TagInfo } from './Info';

function UpdateHouseForm({house}) {
    return (
        <Form>
            <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridTitle">
                    <Form.Label>Naziv</Form.Label>
                    <Form.Control type="title" defaultValue={house.title} />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridPrice">
                    <Form.Label>Cena po noćenju</Form.Label>
                    <InputGroup>
                    <Form.Control type="price" defaultValue={house.priceList} />
                    <InputGroup.Text>€</InputGroup.Text>
                    </InputGroup>
                </Form.Group>
            </Row>

            <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                <Form.Label>Opis</Form.Label>
                <Form.Control as="textarea" rows={3} defaultValue={house.description}/>
            </Form.Group>

            <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridRooms">
                    <Form.Label>Broj soba</Form.Label>
                    <Form.Control type="name" defaultValue={house.numberOfRooms}/>
                </Form.Group>

                <Form.Group as={Col} controlId="formGridCapacity">
                    <Form.Label>Kapacitet</Form.Label>
                    <Form.Control type="price" defaultValue={house.capacity} />
                </Form.Group>
            </Row>

            <Form.Group className="mb-3" controlId="formGridRules">
                <Form.Label>Pravila ponašanja</Form.Label>
                <Form.Control defaultValue={house.rulesAndRegulations} />
            </Form.Group>

            <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridAddress">
                    <Form.Label>Ulica i broj</Form.Label>
                    <InputGroup>
                    <Form.Control className="w-50" defaultValue={house.street}/>
                    <Form.Control className="w-50" defaultValue={house.number}/>
                    </InputGroup>
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

            <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridServices">
                    <Form.Label>Dodatne usluge</Form.Label>
                    <div className='d-flex justify-content-start'>
                        <TagInfo tagList={house.additionalServices} edit={true}/>
                        <Button className='align-self-center ms-3 rounded-circle' style={{padding: "0.1rem 0.4rem", fontSize: "0.85rem"}}>+</Button>
                    </div>
                </Form.Group>

                <Form.Group as={Col} controlId="formGridFee">
                    <Form.Label>Naknada za otkazivanje</Form.Label>
                    <InputGroup>
                    <Form.Control defaultValue={house.cancellationFee} />
                    <InputGroup.Text>%</InputGroup.Text>
                    </InputGroup>
                </Form.Group>
            </Row>

            <Form.Group controlId="formFileMultiple" className="mb-3">
                <Form.Label>Fotografije vikendice</Form.Label>
                <UpdateHouseImages images={house.imagePaths}/>
                <Form.Label>Dodaj</Form.Label>
                <Form.Control type="file" multiple/>
            </Form.Group>
            </Form>
    );
}

export default UpdateHouseForm;