import React from 'react'
import {} from 'bootstrap'
import {Button, Card} from 'react-bootstrap'
import {TagList} from "./Info";

import {BsPencilSquare} from "react-icons/bs";


export default function QuickReservation({startDate, endDate, price, numberOfPeople, tags, image}) {
    return (<div>
            <Card style={{width: '18rem', height: '25rem'}}>
                <Card.Header className="d-flex">
                    <div style={{width: "90%"}}>{startDate}-{endDate}</div>
                    <div style={{fontWeight: "bold", paddingRight: "0.2rem"}}>{price}</div>
                    <Button variant="light" width="0.8rem" height="1rem" className="p-0 m-0 d-flex justify-content-right align-items-center">
                        <BsPencilSquare style={{width: '0.8rem', height: '1rem', color:"black"}}/>
                    </Button>
                </Card.Header>

                <Card.Img variant="top" src={require("./images/fishing2.jpg")} height="200px"/>
                <Card.Body>

                    <Card.Text>
                        <ul>
                            <li>
                                Broj mesta: {numberOfPeople}
                            </li>
                        </ul>

                    </Card.Text>
                    <div className="d-flex justify-content-center">
                        <Button variant="primary">Rezervisi</Button>
                    </div>

                </Card.Body>
            </Card>


        </div>


    )
}