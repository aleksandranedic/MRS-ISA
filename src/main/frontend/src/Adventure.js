import React from 'react'
import {} from 'bootstrap'
import {Button, Card} from 'react-bootstrap'
import {TagList} from "./Info";
import {BsPencilSquare} from "react-icons/bs";


export default function Adventure({address, description, numberOfClients, fishingEquipment, image}) {
    return (<div>
            <Card style={{width: '18rem', height: '25rem'}}>
                <Card.Img variant="top" src={require("./images/fishing4.jpg")} height="200px"/>
                <Card.Body>
                    <Card.Text>
                        <div className="d-flex">
                            <p>{description}</p>
                            <div className="w-50"/>
                            <Button variant="outline-light" className="border-0 m-0 p-0 d-flex justify-content-right align-items-top" width="0.8rem" height="1rem" >
                                <BsPencilSquare style={{width: '0.8rem', height: '1rem', color:"rgb(106,106,106)"}}/>
                            </Button>
                        </div>

                        <ul>
                            <li>Adresa: {address}</li>
                            <li>Broj mesta: {numberOfClients}</li>
                        </ul>
                        <TagList tags={fishingEquipment}/>
                    </Card.Text>
                    <div className="d-flex justify-content-right">


                        <Button variant="primary">Pregledaj</Button>

                    </div>


                </Card.Body>
            </Card>


        </div>


    )
}