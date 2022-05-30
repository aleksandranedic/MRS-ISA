import {Button, Card} from "react-bootstrap";
import fishing3 from "../images/fishing3.jpg"


export function SearchResultCard({title, description, link, image}) {
    return (<Card className="m-3" style={{width: '100%', height: '10%'}}>
        <div className="d-flex" >
            <Card.Img src={image} style={{width: '20vw', height:"20vh", objectFit: 'cover'}}/>
            <Card.Body className="d-flex align-items-end">
                <div style={{width: '90%', height: '100%'}}>
                    <Card.Title>{title}</Card.Title>
                    <Card.Text>
                        {description}
                    </Card.Text>
                </div>
                <Button variant="dark" style={{width: '10%', minWidth:'6rem'}} href={link}>Pregledaj</Button>
            </Card.Body>
        </div>

    </Card>);
}