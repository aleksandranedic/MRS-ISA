import {Accordion, Button, Form, Modal} from "react-bootstrap";

import React, {useState} from "react";
import {ResourceFilterForm} from "./ResourceFilterForm";
import {VacationHouseFilterForm} from "./VacationHouseFilterForm";
import {BoatFilterForm} from "./BoatFilterForm";
import {FishingInstructorFilterForm} from "./FishingInstructorFilterForm";
import DropdownMultiselect from "react-multiselect-dropdown-bootstrap";
import {backLink} from "../Consts";
import axios from "axios";

export function FilterModal({updateResults, showFilters, setShowFilters}) {
    const handleClose = () => setShowFilters(false);
    const [adventuresChecked, setAdventuresChecked] = useState(true);
    const [vacationHousesChecked, setVacationHousesChecked] = useState(true);
    const [boatsChecked, setBoatsChecked] = useState(true);

    const sortingOptions = [
        'po imenu vikendice opadajuće',
        'po imenu vikendice rastuće',
        'po imenu avanture opadajuće',
        'po imenu avanture rastuće',
        'po imenu broda opadajuće',
        'po imenu broda rastuće'
    ]

    const [formValuesInput, setFormValuesInput] = useState({
        numberOfClients: "0",
        fishingInstructorName: "",
        numOfVacationHouseRooms: "",
        numOfVacationHouseBeds: "",
        vacationHouseOwnerName: "",
        boatType: "",
        boatEnginePower: "",
        boatEngineNum: "",
        boatMaxSpeed: "",
        boatCapacity: "",
        boatOwnerName: "",
        sort: []
    });

    function filterData() {
        formValuesInput.adventuresChecked = adventuresChecked
        formValuesInput.vacationHousesChecked = vacationHousesChecked
        formValuesInput.boatsChecked = boatsChecked
        updateResults(formValuesInput)
    }

    const setField = (fieldName, value) => {
        setFormValuesInput({
            ...formValuesInput,
            [fieldName]: value
        })
        console.log(formValuesInput);
    }

    return (
        <Modal show={showFilters} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Filteri</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <ResourceFilterForm minimumValue={50} maximumValue={3000} setField={setField}/>
                    <Accordion>
                        <Accordion.Item eventKey="0">
                            <Accordion.Header>
                                <Form.Check
                                    type="switch"
                                    id="adventure-switch"
                                    label="Avanture"
                                    defaultChecked={adventuresChecked}
                                    onChange={() => setAdventuresChecked(!adventuresChecked)}
                                />
                            </Accordion.Header>
                            <Accordion.Body>
                                <FishingInstructorFilterForm setField={setField}/>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="1">
                            <Accordion.Header>
                                <Form.Check
                                    type="switch"
                                    id="vacationHouse-switch"
                                    label="Vikendice"
                                    defaultChecked={vacationHousesChecked}
                                    onChange={() => setVacationHousesChecked(!vacationHousesChecked)}
                                />
                            </Accordion.Header>
                            <Accordion.Body>
                                <VacationHouseFilterForm setField={setField}/>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="2">
                            <Accordion.Header>
                                <Form.Check
                                    type="switch"
                                    id="boatSwitch"
                                    label="Brodovi"
                                    defaultChecked={boatsChecked}
                                    onChange={() => setBoatsChecked(!boatsChecked)}/>
                            </Accordion.Header>
                            <Accordion.Body>
                                <BoatFilterForm setField={setField}/>
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>
                    <Form.Label>Način sortiranja</Form.Label>
                    <DropdownMultiselect options={sortingOptions} name={"sortingOptions"}
                                         placeholder={"Ništa nije izabrano"}
                                         handleOnChange={(selected) => {
                                             setField('sortingOptions', selected)
                                         }}
                        // onChange={} //filter
                    />
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