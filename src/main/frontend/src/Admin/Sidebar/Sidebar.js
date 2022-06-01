import {Button, Nav, Navbar} from "react-bootstrap";
import React from 'react';
import 'bootstrap/dist/css/bootstrap.css'
import {BsArrowRight} from "react-icons/bs";
import {logOut, logOutAdmin} from "../../Consts";

export function Sidebar({user}) {

    return (<div className="flex-column"><Navbar bg="light" expand="lg">
        <Nav className="d-flex flex-column m-3 align-items-stretch">
            <h2 className="fw-light m-3">
                {user.firstName + " " + user.lastName}
            </h2>
            <h4 className="fw-light m-3">Zahtevi</h4>

            <Nav.Link className="ms-4" href={"http://localhost:3000/admin/registrationRequests"}>
                <BsArrowRight className="me-2"/>
                Zahtevi za registraciju
            </Nav.Link>

            <Nav.Link className="ms-4" href="http://localhost:3000/admin/deletionRequests">
                <BsArrowRight className="me-2"/>
                Zahtevi za brisanje naloga
            </Nav.Link>

            <Nav.Link className="ms-4">
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
    </Navbar>
        <Button className="m-3" variant="outline-secondary" onClick={() => logOutAdmin()}>Odjavi se</Button>
    </div>)
}