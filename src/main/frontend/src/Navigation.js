import React from 'react'
import {Button, Carousel, Container, Image, Nav, Navbar} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.css'
import NavigationButton from "./NavigationButton";
import {BsPencilSquare} from "react-icons/bs";

export default function Navigation(props) {
    return(<Navbar  className="d-flex" style={{background: "rgba(204, 204, 204, 0.4)"}}>
            <Container >
                <Nav className="d-flex w-100 justify-content-center">

                    {props.buttons.map(
                        (button, index) => {
                            return <NavigationButton text={button.text} path={button.path} key={index}/>
                        }
                    )}

                    {props.editable && <Button variant="outline-light" className="border-0 m-0 p-0 d-flex justify-content-right align-items-center" width="0.8rem" height="1rem" onClick={props.editFunction}>
                        <BsPencilSquare style={{width: '0.8rem', height: '1rem', color:"rgb(106,106,106)"}} onClick={props.editFunction}/>
                    </Button>}
                </Nav>
            </Container>
        </Navbar>


    )
}