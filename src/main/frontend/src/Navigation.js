import React from 'react'
import {} from 'bootstrap'
import {Navbar, Nav, Container, Button} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.css'
import NavigationButton from "./NavigationButton";

export default function Navigation() {
    return(<Navbar  className="d-flex" style={{background: "rgba(204, 204, 204, 0.4)"}}>
            <Container >
                <Nav className="d-flex w-100 justify-content-center">
                    <NavigationButton text="Osnovne informacije" path="#info"/>
                    <NavigationButton text="Fotografije" path="#photos"/>
                    <NavigationButton text="Akcije" path="#sales"/>
                    <NavigationButton text="Kalendar zauzetosti" path="#calendar"/>
                </Nav>
            </Container>
        </Navbar>


    )
}