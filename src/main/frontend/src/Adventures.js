import React, {useState} from 'react'
import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";
import PlusCard from "./PlusCard";
import Adventure from "./Adventure";
import {Button, Form, Modal} from "react-bootstrap";
import {TagInput} from "./TagInput";


const responsive = {
    superLargeDesktop: {
        // the naming can be any, depends on you.
        breakpoint: {max: 4000, min: 3000},
        items: 5
    },
    desktop: {
        breakpoint: {max: 3000, min: 1400},
        items: 4
    },
    desktop2: {
        breakpoint: {max: 1400, min: 1024},
        items: 3
    },
    tablet: {
        breakpoint: {max: 1024, min: 700},
        items: 2
    },
    mobile: {
        breakpoint: {max: 700, min: 0},
        items: 1
    }
};

export default function Adventures({adventures}) {
    return (<div className="m-5">
            <Carousel responsive={responsive} interval="25000">
                {adventures.map((adventure, key) => {
                        return <Adventure image={adventure.image} description={adventure.description}
                                          numberOfClients={adventure.numberOfClients} address={adventure.address}
                                          fishingEquipment={adventure.fishingEquipment}/>
                    }
                )}
                <Adventure image="" description="Velika avantura vau" numberOfClients={5} address="Dunavska 152"
                           fishingEquipment={[]}/>
                <AddAdventure/>

            </Carousel>
        </div>

    )
}

function AddAdventure() {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <PlusCard onClick={handleShow}/>

            <Modal show={show} onHide={handleClose} size="lg">
                <Modal.Header closeButton>
                    <Modal.Title>Dodavanje avanture</Modal.Title>
                </Modal.Header>

                <Modal.Body>

                    <Form.Group className="mb-3 m-2" controlId="title">
                        <Form.Label>Naslov</Form.Label>
                        <Form.Control type="text" placeholder=""/>
                    </Form.Group>

                    <Form.Group className="mb-3 m-2" controlId="description">
                        <Form.Label>Opis</Form.Label>
                        <Form.Control as="textarea" rows={3}/>
                    </Form.Group>


                    <Form className="d-flex">

                        <Form.Group className="mb-3 m-2" controlId="formBasicPassword">
                            <Form.Label>Ulica</Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>
                        <Form.Group className="mb-3 m-2 " controlId="formBasicPassword">
                            <Form.Label>Broj</Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2" controlId="formBasicPassword">
                            <Form.Label>Grad</Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>

                        <Form.Group className="mb-3 m-2" controlId="formBasicPassword">
                            <Form.Label>Drzava</Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>

                    </Form>

                    <Form className="d-flex">
                        <div className="m-2 w-50">
                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Broj klijenata</Form.Label>
                                <Form.Control type="text" placeholder=""/>
                            </Form.Group>
                            <Form.Group controlId="formFileMultiple" className="mb-3">
                                <Form.Label>Fotografije</Form.Label>
                                <Form.Control type="file" multiple/>
                            </Form.Group>
                        </div>
                        <div className="m-2 w-50">
                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Cena</Form.Label>
                                <Form.Control type="text" placeholder=""/>
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Naknada za otkazivanje</Form.Label>
                                <Form.Control type="text" placeholder=""/>
                            </Form.Group>
                        </div>
                    </Form>

                    <Form.Group className="mb-3 m-2" controlId="formBasicPassword">
                        <Form.Label>Dodatne usluge</Form.Label>
                        <Form.Control type="text" placeholder=""/>
                    </Form.Group>

                    <Form.Group className="mb-3 m-2" controlId="description">
                        <Form.Label>Pravila ponasanja</Form.Label>
                        <Form.Control as="textarea" rows={3}/>
                    </Form.Group>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Otkazi
                    </Button>
                    <Button variant="primary" onClick={handleClose}>
                        Dodaj
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}


