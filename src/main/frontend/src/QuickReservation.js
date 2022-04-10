import React from 'react'
import {} from 'bootstrap'
import {Button, Card} from 'react-bootstrap'
import {TagList} from "./Info";





export default function QuickReservation({startDate, endDate, price, numberOfPeople, tags, image}) {
    return (<div>
            <Card style={{width: '18rem', height: '25rem'}}>
                <Card.Header className="d-flex">
                    <div style={{width: "90%"}}>{startDate}-{endDate}</div>
                    <div style={{fontWeight: "bold"}}>{price}</div></Card.Header>
                <Card.Img variant="top" src={image} height="200px"/>
                <Card.Body>

                    <Card.Text>
                        <ul>
                            <li>
                                Broj mesta: {numberOfPeople}
                            </li>
                        </ul>
                        <TagList tags={tags}/>
                    </Card.Text>
                    <div className="d-flex justify-content-center">
                        <Button variant="primary">Rezervisi</Button>
                    </div>

                </Card.Body>
            </Card>


        </div>


    )
}