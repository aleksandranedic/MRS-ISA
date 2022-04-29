import {Accordion, Button, Form, Modal} from "react-bootstrap";

import React, {useState} from "react";
import {ResourceFilterForm} from "./ResourceFilterForm";
import {VacationHouseFilterForm} from "./VacationHouseFilterForm";
import {BoatFilterForm} from "./BoatFilterForm";
import {Typeahead} from "react-bootstrap-typeahead";


export function FilterModal({showFilters, setShowFilters}) {
    const handleClose = () => setShowFilters(false);
    const [adventuresChecked, setAdventuresChecked] = useState(true);
    const [vacationHousesChecked, setVacationHousesChecked] = useState(true);
    const [boatsChecked, setBoatsChecked] = useState(true);

    const [formValues, setFormValues] = useState({});

    const setField = (fieldName, value) => {
        setFormValues({
            ...formValues,
            [fieldName]: value
        })
        console.log(formValues);
    }

    return (
        <Modal show={showFilters} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Filteri</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>

                    <ResourceFilterForm minimumValue={50} maximumValue={3000}/>

                    <Accordion>
                        <Accordion.Item eventKey="0">
                            <Accordion.Header>
                                <Form.Check
                                    type="switch"
                                    id="adventure-switch"
                                    label="Avanture"
                                    defaultChecked={adventuresChecked}
                                    onChange={()=> setAdventuresChecked(!adventuresChecked)}
                                />
                            </Accordion.Header>
                            <Accordion.Body>
                                <Form>
                                    <Form.Group>
                                        <Form.Label>Broj klijenata</Form.Label>
                                        <Form.Control
                                            type="number"
                                            value={formValues.numberOfClients}
                                            onChange={(e) => setField("numberOfClients", e.target.value)}

                                        />
                                    </Form.Group>

                                    <Form.Group>
                                        <Form.Label>Instruktor</Form.Label>
                                        <Typeahead
                                            id="basic-typeahead-single"
                                            labelKey="name"
                                            options={['Petar Jovanovic', 'Mile']}
                                            selected={formValues.fishingInstructor}
                                            onChange={(e) => setField("fishingInstruktor", e.target.selected)}
                                        />
                                    </Form.Group>
                                </Form>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="1">
                            <Accordion.Header>
                                <Form.Check
                                    type="switch"
                                    id="vacationHouse-switch"
                                    label="Vikendice"
                                    defaultChecked={vacationHousesChecked}
                                    onChange={()=>setVacationHousesChecked(!vacationHousesChecked)}
                                />
                            </Accordion.Header>
                            <Accordion.Body>
                                <VacationHouseFilterForm/>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="2">
                            <Accordion.Header>
                                <Form.Check
                                    type="switch"
                                    id="boatSwitch"
                                    label="Brodovi"
                                    defaultChecked={boatsChecked}
                                    onChange={() => setBoatsChecked(!boatsChecked)}
                                />
                            </Accordion.Header>
                            <Accordion.Body>
                                <BoatFilterForm/>
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>




                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Otkaži
                </Button>
                <Button variant="primary" onClick={handleClose}>
                    Pretraži sa filterima
                </Button>
            </Modal.Footer>
        </Modal>
    )
}