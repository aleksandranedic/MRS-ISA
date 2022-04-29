import {useState} from "react";
import {Form} from "react-bootstrap";
import {Typeahead} from "react-bootstrap-typeahead";

export function VacationHouseFilterForm() {
    const [selectedOwners, setSelectedOwners] = useState([]);
    return (<Form>

        <Form.Group>
            <Form.Label>Broj soba</Form.Label>
            <Form.Control type="number"/>
        </Form.Group>

        <Form.Group>
            <Form.Label>Broj mesta za spavanje</Form.Label>
            <Form.Control type="number"/>
        </Form.Group>

        <Form.Group>
            <Form.Label>Vlasnik</Form.Label>
            <Typeahead
                id="basic-typeahead-single"
                labelKey="name"
                onChange={setSelectedOwners}
                options={['Milenija Prokic']}
                selected={selectedOwners}
            />
        </Form.Group>

    </Form>)
}