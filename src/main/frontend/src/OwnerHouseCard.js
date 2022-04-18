import React from 'react';
import {Card} from "react-bootstrap";
import {BsGeoAltFill, BsPencilSquare} from 'react-icons/bs'
function OwnerHouseCard({pic, title, text, address}) {
    return (
        <Card>
            <a href="house" className="text-decoration-none h-100">
            <Card.Img variant="top" src={pic} />
            </a>
            <Card.Body className="w-100">
                <Card.Title className="text-dark">
                    <div className="d-flex justify-content-between">
                        {title}
                        <BsPencilSquare/>
                    </div>
                    </Card.Title>
                <Card.Text className="text-dark">
                    {text}
                </Card.Text>
            </Card.Body>
            <Card.Footer className="w-100">
            <small className="text-muted">
                <BsGeoAltFill/>
                <span className="ms-2">{address}</span>
            </small>
            </Card.Footer>
        </Card>
    );
}

export default OwnerHouseCard;