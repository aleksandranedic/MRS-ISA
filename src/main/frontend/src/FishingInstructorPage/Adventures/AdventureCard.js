import React, {useState} from 'react'
import {Button, Card} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.css'
import {BsPencilSquare} from "react-icons/bs";
import {EditAdventure} from "../../AdventurePage/EditAdventure";

import './AdventureCard.css'
import {useHistory} from "react-router-dom";

export default function AdventureCard({adventure}) {
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);


    return (
        <Card className="bg-light text-black" style={{width: '18rem', height: '25rem', padding: 0}}>
            <Card.Img variant="top" src={require("../../images/fishing4.jpg")} alt="Card image"
                      style={{width: '18rem', height: '18rem'}}/>
            <Card.ImgOverlay className="d-flex p-3 m-0">
                <h4 className="text-light h-25" id="title">
                    {adventure.title}
                </h4>
                <div className="w-50"/>
                <BsPencilSquare style={{width: '1rem', height: '1.25rem', color: "black", cursor: "pointer"}} onClick={handleShow}/>
            </Card.ImgOverlay>

            <Card.Body className="w-100">
                <Card.Subtitle className="text-secondary w-100" >
                    {adventure.description}
                </Card.Subtitle>

            </Card.Body>
            <EditAdventure show={show} setShow={setShow} adventure={adventure}/>
        </Card>
    )
}