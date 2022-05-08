import React, {useState} from "react";
import {Button, Card} from "react-bootstrap";
import {BsLink45Deg, BsPencilSquare} from "react-icons/bs";
import {AdventureForm} from "./Adventure/AdventureForm";
import {FishingInstructorForm} from "./FishingInstructor/FishingInstructorForm";


export default function CustomCard({imagePath, editable, itemToEdit, title, subtitle, link, type}) {
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);


    return (
        <Card className="bg-light text-black" style={{width: '18vw', padding: 0}}>

            <img style={{height: '30vh'}}  src={imagePath} alt="Card image"/>

            <div className="d-flex">
                <Card.Title className="text-dark h-25 w-75 p-3" id="title">
                    {title}
                </Card.Title>

                {editable && <BsPencilSquare className="m-3" style={{width: '1rem', height: '1.25rem', color: "black", cursor: "pointer"}}
                                             onClick={handleShow}/>}
            </div>

            <Card.Body className="w-100 d-flex">
                <Card.Subtitle className="text-secondary w-100">
                    {subtitle}
                </Card.Subtitle>

            </Card.Body>

            {type === "adventure" && <AdventureForm show={show} setShow={setShow} adventure={itemToEdit}/>}
            {type === "fishingInstructor" && <FishingInstructorForm show={show} setShow={setShow} fishingInstructor={itemToEdit}/>}

            <Button variant="dark" href={link} className="d-flex justify-content-center"  style={{ height: '2rem'}}>
                <BsLink45Deg style={{width: '1rem', height: '1rem', color: "white", cursor: "pointer"}}/>
            </Button>
        </Card>
    )
}