import {Button, Nav, Navbar} from "react-bootstrap";
import React from 'react';
import 'bootstrap/dist/css/bootstrap.css'
import {BsArrowRight} from "react-icons/bs";

export function Sidebar() {


    return (<Navbar bg="light" expand="lg">
        <Nav className="d-flex flex-column m-3 align-items-stretch">
            <h2 className="fw-light m-3">Mirko Jovanovic</h2>
            <h4 className="fw-light m-3">Zahtevi</h4>

            <Nav.Link className="ms-4" href={"http://localhost:3000/admin/registrationRequests"}>
                <BsArrowRight className="me-2"/>
                Zahtevi za registraciju
            </Nav.Link>

            <Nav.Link className="ms-4" href="http://localhost:3000/admin/deletionRequests">
                <BsArrowRight className="me-2"/>
                Zahtevi za brisanje naloga
            </Nav.Link>

            <Nav.Link className="ms-4" href="http://localhost:3000/admin/penaltyRequests">
                <BsArrowRight className="me-2"/>
                Zahtevi za davanje penala
            </Nav.Link>

            <Nav.Link className="ms-4">
                <BsArrowRight className="me-2"/>
                Žalbe
            </Nav.Link>

            <h4 className="fw-light ms-3">Loyalti program</h4>

            <Nav.Link className="ms-4">
                <BsArrowRight className="me-2"/>
                Kategorije
            </Nav.Link>

            <Nav.Link className="ms-4">
                <BsArrowRight className="me-2"/>
                Bodovi
            </Nav.Link>

        </Nav>
    </Navbar>)
}