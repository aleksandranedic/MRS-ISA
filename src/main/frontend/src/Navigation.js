import React from 'react'
import {Button, Container, Nav, Navbar} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.css'
import NavigationButton from "./NavigationButton";
import {BsPencilSquare} from "react-icons/bs";

export default function Navigation({showUpdateModal}) {
    return(<Navbar  className="d-flex" style={{background: "rgba(204, 204, 204, 0.4)"}}>
            <Container >
                <Nav className="d-flex w-100 justify-content-center">
                    <NavigationButton text="Osnovne informacije" path="#info"/>
                    <NavigationButton text="Fotografije" path="#photos"/>
                    <NavigationButton text="Akcije" path="#sales"/>
                    <NavigationButton text="Kalendar zauzetosti" path="#calendar"/>
                    <Button onClick={showUpdateModal} variant="outline-light" className="border-0 m-0 p-0 d-flex justify-content-right align-items-center" width="0.8rem" height="1rem" >
                        <BsPencilSquare style={{width: '2rem', height: '1rem', color:"rgb(106,106,106)"}}/>
                    </Button>
                </Nav>
            </Container>
        </Navbar>
    )
}