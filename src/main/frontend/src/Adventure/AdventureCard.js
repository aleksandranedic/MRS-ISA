import React, {useState} from 'react'
import {Button, Card} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.css'
import {BsLink45Deg, BsPencilSquare} from "react-icons/bs";
import {AdventureForm} from "./AdventureForm";


export default function AdventureCard({adventure}) {
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);


    return (
        <Card className="bg-light text-black" style={{width: '18rem', height: '25rem', padding: 0}}>
            <Card.Img variant="top" src={require("../images/fishing4.jpg")} alt="Card image"
                      style={{width: '18rem', height: '18rem'}}/>

            <div className="d-flex">
                <Card.Title className="text-dark h-25 w-75 p-3" id="title">
                    {adventure.title}
                </Card.Title>

                <BsPencilSquare className="m-3" style={{width: '1rem', height: '1.25rem', color: "black", cursor: "pointer"}}
                                onClick={handleShow}/>
            </div>



            <Card.Body className="w-100 d-flex">
                <Card.Subtitle className="text-secondary w-100">
                    {adventure.description}
                </Card.Subtitle>

            </Card.Body>

            <AdventureForm show={show} setShow={setShow} adventure={adventure}/>
            <Button variant="dark" href={"http://localhost:3000/adventure"} className="d-flex justify-content-center"  style={{ height: '2rem'}}>
                <BsLink45Deg style={{width: '1rem', height: '1rem', color: "white", cursor: "pointer"}}/>
            </Button>
        </Card>
    )
}