import React, {useState} from "react";
import {Sidebar} from "./Sidebar/Sidebar";
import {Input, Slider} from "@material-ui/core";
import {Button, FloatingLabel, Form} from "react-bootstrap";


export function Points() {

    const [ clientPoints, setClientPoints ] = useState();
    const [ vendorPoints, setVendorPoints ] = useState();


    //TODO: Ovo se sve nalazi u clasi Buissness

    return (<div className="d-flex" style={{height: "100vh"}}>
        <div className="w-25" style={{backgroundColor: "#f7f8f9"}}>
            <Sidebar/>
        </div>
        <div className="w-75 d-flex flex-column">
            <div className="display-6 m-5" style={{fontSize: "2rem"}}>Podešavanje dobijanja bodova</div>
            <div className="d-flex w-50 mt-5 ms-5 ">
                <div className="m-1">Broj bodova koje klijent osvaja pri rezervaciji</div>
                <Input className="ms-auto"
                    value={clientPoints}
                    size="small"
                       disabled={true}

                    inputProps={{
                        step: 1,
                        min: 0,
                        max: 10,
                        type: 'number'
                    }}
                />

            </div>
            <Slider
                className="w-50 m-1 ms-5 mt-0"
                value={clientPoints}
                onChange={(e, data)=>setClientPoints(data)}
                valueLabelDisplay="auto"
                step={1}
                min={0}
                max={10}
                style={{color: "#0d6efc"}}
            />


            <div className="d-flex w-50 mt-5 ms-5 ">
                <div className="m-1">Broj bodova koje pružalac usluga osvaja pri rezervaciji</div>
                <Input className="ms-auto"
                       value={vendorPoints}
                       size="small"
                       disabled={true}
                       inputProps={{
                           step: 1,
                           min: 0,
                           max: 10,
                           type: 'number'
                       }}
                />

            </div>

            <Slider
                className="w-50 m-1 ms-5 mt-0"
                value={vendorPoints}
                onChange={(e, data)=>setVendorPoints(data)}
                valueLabelDisplay="auto"
                step={1}
                min={0}
                max={10}
                style={{color: "#0d6efc"}}
            />



            <Form.Group className="w-50 mt-5 ms-5 ">
                <FloatingLabel
                    controlId="floatingInput"
                    label="Procenat koji sajt dobija od svake rezervacije"
                    className="mb-2"
                >
                    <Form.Control type="number" placeholder="Title"/>
                </FloatingLabel>

            </Form.Group>

            <div className=" m-5 d-flex w-50">
                <Button variant="outline-primary" className="w-50 me-1">Sačuvaj izmene</Button>
                <Button variant="outline-primary" className="w-50 ms-0"
                    onClick={()=> window.location.reload()}
                >Otkaži izmene</Button>

            </div>

        </div>


    </div>)
}