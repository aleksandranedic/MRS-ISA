import React, {useState} from 'react';
import {Sidebar} from "./Sidebar/Sidebar";
import {Badge, Button, ButtonGroup, Card, Col, FloatingLabel, Form, Row, ToggleButton} from "react-bootstrap";
import {MdStars} from "react-icons/md";
import {TiDeleteOutline} from "react-icons/ti";
import "./LoyaltyCategories.css"

const loyaltyColorPalette = {
    'pink-blue': "linear-gradient( 64.5deg,  rgba(245,116,185,1) 14.7%, rgba(89,97,223,1) 88.7% )",
    'green-blue': "linear-gradient( 109.6deg,  rgba(61,245,167,1) 11.2%, rgba(9,111,224,1) 91.1% )",
    'pink-purple': "radial-gradient( circle farthest-corner at 10.2% 55.8%,  rgba(252,37,103,1) 0%, rgba(250,38,151,1) 46.2%, rgba(186,8,181,1) 90.1% )",
    'green': "radial-gradient( circle farthest-corner at 10% 20%,  rgba(14,174,87,1) 0%, rgba(12,116,117,1) 90% )",
    'blue-purple': "linear-gradient( 83.2deg,  rgba(150,93,233,1) 10.8%, rgba(99,88,238,1) 94.3% )",
    'purple-orange': "linear-gradient( 358.4deg,  rgba(249,151,119,1) -2.1%, rgba(98,58,162,1) 90% )",
    'blue': "linear-gradient( 109.6deg,  rgba(62,161,219,1) 11.2%, rgba(93,52,236,1) 100.2% )",

}

export function LoyaltyCategories() {

    //TODO: Dobavljanje kategorija, cuvanje kategorija, validacija forme

    const [type, setType] = useState(0);
    const [colorId, setColorId] = useState(null);

    const [userCategories, setUserCategories] = useState([{
        color: loyaltyColorPalette["pink-blue"],
        title: "Pink & Blue",
        minimumPoints: 0,
        maximumPoints: 2,
        priceAlteration: 0
    }, {
        color: loyaltyColorPalette["green-blue"],
        title: "Green & Blue",
        minimumPoints: 3,
        maximumPoints: 5,
        priceAlteration: 5
    }, {
        color: loyaltyColorPalette["pink-purple"],
        title: "Pink & Purple",
        minimumPoints: 6,
        maximumPoints: 8,
        priceAlteration: 10
    }

    ]);

    const [vendorCategories, setVendorCategories] = useState([ {
        color: loyaltyColorPalette.green,
        title: "Green",
        minimumPoints: 9,
        maximumPoints: 12,
        priceAlteration: 15
    }, {color: loyaltyColorPalette.blue, title: "Blue", minimumPoints: 13, maximumPoints: 20, priceAlteration: 20}, {
        color: loyaltyColorPalette["blue-purple"],
        title: "Purple & Blue",
        minimumPoints: 21,
        maximumPoints: 35,
        priceAlteration: 25
    }, {
        color: loyaltyColorPalette["purple-orange"],
        title: "Purple & Orange",
        minimumPoints: 36,
        maximumPoints: 100,
        priceAlteration: 30
    }

    ])


    function setSelectedColor(elementId) {

        let buttons = document.getElementsByClassName("color-button")
        for (let i = 0; i < buttons.length; i++) {
            if (buttons[i].classList.contains("selected-color")) {
                buttons[i].classList.remove("selected-color");
            }
        }

        setColorId(elementId);

        let button = document.getElementById(elementId);
        button.addClass("selected-color");
    }

    return (<div className="d-flex" style={{height: "100vh"}}>
        <div className="w-25" style={{backgroundColor: "#f7f8f9"}}>
            <Sidebar/>
        </div>


        <div className="w-75" style={{overflow:" scroll"}}>

            <div className="m-4">
                <div className="display-6" style={{fontSize: "2rem"}}>Nova kategorija</div>

                <Form className="mt-3">
                    <Form.Group>
                        <FloatingLabel
                            controlId="floatingInput"
                            label="Naziv"
                            className="mb-2"
                        >
                            <Form.Control type="text" placeholder="Title"/>
                        </FloatingLabel>

                    </Form.Group>

                    <div className="d-flex">
                        <Form.Group className="w-25 me-2">
                            <FloatingLabel
                                controlId="floatingInput"
                                label="Minimalan broj poena"
                                className="mb-2"
                            >
                                <Form.Control type="number" placeholder="Title"/>
                            </FloatingLabel>
                        </Form.Group>
                        <Form.Group className="w-25">
                            <FloatingLabel
                                controlId="floatingInput"
                                label="Maksimalan broj poena"
                                className="mb-2"
                            >
                                <Form.Control type="number" placeholder="Title"/>
                            </FloatingLabel>
                        </Form.Group>

                        <Form.Group className="w-50 ms-2">
                            <FloatingLabel
                                controlId="floatingInput"
                                label="Popust (%)"
                                className="mb-2"
                            >
                                <Form.Control type="number" placeholder="Title"/>
                            </FloatingLabel>
                        </Form.Group>
                    </div>

                    <div className="d-flex">

                        <ButtonGroup className="m-2 ms-0">

                            <ToggleButton
                                type="radio"
                                variant="outline-secondary"
                                name="radio"
                                onClick={()=>setType(1)}
                                checked={type === 1}>
                                Klijent
                            </ToggleButton>

                            <ToggleButton
                                type="radio"
                                variant="outline-secondary"
                                name="radio"
                                onClick={()=>setType(2)}
                                checked={type === 2}>
                                Pružalac usluga
                            </ToggleButton>

                        </ButtonGroup>

                        <Button onClick={() => setSelectedColor("purple-orange")} id="purple-orange"
                                className="color-button" style={{background: loyaltyColorPalette["purple-orange"]}}/>
                        <Button id="blue-purple" className="color-button"
                                style={{background: loyaltyColorPalette["blue-purple"]}}/>
                        <Button id="green-blue" className="color-button"
                                style={{background: loyaltyColorPalette["green-blue"]}}/>
                        <Button id="green" className="color-button" style={{background: loyaltyColorPalette["green"]}}/>
                        <Button id="blue" className="color-button" style={{background: loyaltyColorPalette["blue"]}}/>
                        <Button id="pink-blue" className="color-button"
                                style={{background: loyaltyColorPalette["pink-blue"]}}/>
                        <Button id="pink-purple" className="color-button"
                                style={{background: loyaltyColorPalette["pink-purple"]}}/>



                        <Button className="ms-auto mt-2 mb-2" variant="outline-secondary">Dodaj</Button>

                    </div>

                </Form>
            </div>

            <div className="display-6 m-4" style={{fontSize: "1.5rem"}}>Kategorije klijenata</div>
            <hr className="m-3"/>

            <Row xs={1} md={4} className="g-4 ms-2">
                {userCategories.map((category) => (<Col>
                    <Card className="m-2" style={{background: category.color, width: "17vw", height: "21.5vw"}}>

                        <Card.Body className="w-100 m-2">
                            <Card.Title className="text-light">{category.title}</Card.Title>
                            <Card.Text className="d-flex flex-column">

                                <div className="d-flex">
                                    <MdStars style={{color: "white", height: "1.5rem", width: "1.5rem"}}/>
                                    <p className="text-light align-center p-0 m-0 ms-2">{category.minimumPoints} - {category.maximumPoints}</p>
                                </div>


                            </Card.Text>
                        </Card.Body>

                        <Card.Footer style={{backgroundColor: "rgba(0,0,0,0.1)"}} className="d-flex">
                            <Button size="sm" variant="outline-light"
                                    className="d-flex align-items-center justify-content-center">

                                <TiDeleteOutline style={{color: "white", height: "1.2rem", width: "1.2rem"}}/>
                            </Button>
                            {category.priceAlteration > 0 && <Badge bg="light"
                                                                    className="ms-auto d-flex align-items-center justify-content-end w-25">
                                <p className="text-secondary h-100 align-center p-0 m-0 ms-2"
                                   style={{
                                       fontSize: "1.25rem", fontWeight: "400"
                                   }}>{category.priceAlteration}%</p>
                            </Badge>}
                        </Card.Footer>
                    </Card>
                </Col>))}
            </Row>

            <div className="display-6 m-4" style={{fontSize: "1.5rem"}}>Kategorije pružaoca usluga</div>
            <hr className="m-3"/>
            <Row xs={1} md={4} className="g-4 ms-2">
                {vendorCategories.map((category) => (<Col>
                    <Card className="m-2" style={{background: category.color, width: "17vw", height: "21.5vw"}}>

                        <Card.Body className="w-100 m-2">
                            <Card.Title className="text-light">{category.title}</Card.Title>
                            <Card.Text className="d-flex flex-column">

                                <div className="d-flex">
                                    <MdStars style={{color: "white", height: "1.5rem", width: "1.5rem"}}/>
                                    <p className="text-light align-center p-0 m-0 ms-2">{category.minimumPoints} - {category.maximumPoints}</p>
                                </div>


                            </Card.Text>
                        </Card.Body>

                        <Card.Footer style={{backgroundColor: "rgba(0,0,0,0.1)"}} className="d-flex">
                            <Button size="sm" variant="outline-light"
                                    className="d-flex align-items-center justify-content-center">

                                <TiDeleteOutline style={{color: "white", height: "1.2rem", width: "1.2rem"}}/>
                            </Button>
                            {category.priceAlteration > 0 && <Badge bg="light"
                                                                    className="ms-auto d-flex align-items-center justify-content-end w-25">
                                <p className="text-secondary h-100 align-center p-0 m-0 ms-2"
                                   style={{
                                       fontSize: "1.25rem", fontWeight: "400"
                                   }}>{category.priceAlteration}%</p>
                            </Badge>}
                        </Card.Footer>
                    </Card>
                </Col>))}
            </Row>
        </div>


    </div>)
}
