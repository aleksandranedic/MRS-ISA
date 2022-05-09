import React, {useState} from 'react'
import {Slider} from "@material-ui/core"
import {Form} from "react-bootstrap";

export function ResourceFilterForm({minimumValue, maximumValue}) {
    const [priceRangeValue, setPriceRangeValue] = useState([minimumValue, maximumValue]);
    const updatePriceRangeValue = (e, data) => {
        setPriceRangeValue(data)
    }

    return (<div className="m-3">
        <Form>
            <Form.Group>
                <Form.Label>Cena</Form.Label>
                <Slider
                    value={priceRangeValue}
                    onChange={updatePriceRangeValue}
                    valueLabelDisplay="auto"
                    min={minimumValue}
                    max={maximumValue}
                    style={{color: "#0d6efc"}}
                />
            </Form.Group>
            <Form.Check
                type="switch"
                id="adventure-switch"
                label="Bez naknade za otkazivanje"
            />

        </Form>

    </div>)
}