import {useState} from "react";
import {Form} from "react-bootstrap";
import {Typeahead} from "react-bootstrap-typeahead";

export function BoatFilterForm() {
    const [selectedOwner, setSelectedOwner] = useState([]);
    const [selectedType, setSelectedTypes] = useState([]);
    return (<Form>

        <Form.Group>
            <Form.Label>Tip</Form.Label>
            <Typeahead
                id="basic-typeahead-single"
                labelKey="name"
                onChange={setSelectedTypes}
                options={['Jahta', 'Čamac']}
                selected={selectedType}
            />
        </Form.Group>

        <div className="d-flex">
            <Form.Group className="me-2">
                <Form.Label>Jačina motora</Form.Label>
                <Form.Control type="number"/>
            </Form.Group>

            <Form.Group className="ms-2">
                <Form.Label>Broj motora</Form.Label>
                <Form.Control type="number"/>
            </Form.Group>
        </div>

        <div className="d-flex">
            <Form.Group className="me-2">
                <Form.Label>Maksimalna brzina</Form.Label>
                <Form.Control type="number"/>
            </Form.Group>

            <Form.Group className="ms-2">
                <Form.Label>Kapacitet</Form.Label>
                <Form.Control type="number"/>
            </Form.Group>
        </div>

        <Form.Group>
            <Form.Label>Vlasnik</Form.Label>
            <Typeahead
                id="basic-typeahead-single"
                labelKey="name"
                onChange={setSelectedOwner}
                options={['Mirko Jovanovic']}
                selected={selectedOwner}
            />
        </Form.Group>

    </Form>)
}