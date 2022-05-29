import React, {useState} from 'react'
import {Button, Collapse, Container, Form, Nav, Navbar} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.css'
import 'react-bootstrap-range-slider/dist/react-bootstrap-range-slider.css';
import NavigationButton from "./NavigationButton";
import {BsFilter, BsPencilSquare, BsPerson, BsSearch, BsSortAlphaDown, BsSortAlphaUp} from "react-icons/bs";
import {FilterModal} from "../Filter/FilterModal";

import {frontLink, isLoggedIn, logOut} from "../Consts";
import axios from "axios";

axios.interceptors.request.use(config => {
        config.headers.authorization = "Bearer " + localStorage.getItem('token')
        return config
    },
    error => {
        return Promise.reject(error)
    }
)
export default function Navigation(props) {

    const [searchTerm, setSearchTerm] = useState("");

    function isLoggedIn() {
        return localStorage.getItem('token') !== "" && localStorage.getItem('token') !== null;
    }

    const [open, setOpen] = useState(false);
    const [showFilters, setShowFilters] = useState(false);
    const handleShow = () => setShowFilters(true);

    return (<Navbar className="d-flex" style={{background: "rgba(204, 204, 204, 0.75)", zIndex: 3}}>
            <Container style={{minHeight: "2.4rem"}}>
                <Nav className="d-flex justify-content-evenly w-100">
                    <div className="d-flex justify-content-start">
                        {props.searchable &&
                        <>
                            <Button variant="outline-light"
                                    className="border-0 p-0 d-flex justify-content-right align-items-center"
                                    style={{marginLeft: "1rem", marginRight: "1rem"}} width="0.8rem"
                                    height="1rem"
                                    onClick={() => setOpen(!open)}>
                                <BsSearch aria-controls="collapse-search-bar" aria-expanded={open}
                                          style={{width: '0.8rem', height: '1rem', color: "rgb(106,106,106)"}}/>
                            </Button>


                            <Collapse in={open} dimension="width">
                                <Form id="collapse-search-bar">
                                    <div className="d-flex" style={{width: '30rem'}}>
                                        <Form.Control type="text" value={searchTerm}
                                                      onChange={e => setSearchTerm(e.target.value)}/>
                                        <Button variant="outline-light"
                                                className="border-0 p-0 d-flex justify-content-right align-items-center"
                                                style={{marginLeft: "0.3rem"}} width="0.8rem"
                                                height="1rem">
                                            <BsFilter aria-controls="collapse-search-bar" aria-expanded={open}
                                                      style={{
                                                          width: '2rem',
                                                          height: '1.2rem',
                                                          color: "rgb(106,106,106)"
                                                      }}
                                                      onClick={handleShow}/>
                                        </Button>
                                        <Button variant="outline-secondary" value="Pretraga"
                                                style={{marginLeft: '0.3rem'}}
                                                href={frontLink + "search/" + searchTerm}
                                        >Pretraga</Button>
                                    </div>
                                </Form>
                            </Collapse> </>}
                    </div>

                    <div className="d-flex justify-content-center" style={{width: "90%"}}>
                        <NavigationButton text={"Početna strana"} path={frontLink} key={999999}/>
                        {props.buttons.map(
                            (button, index) => {
                                return <NavigationButton text={button.text} path={button.path} key={index}/>
                            }
                        )}
                    </div>

                    <div className="d-flex justify-content-end">


                        {(isLoggedIn()) && <Button variant="outline-light"
                                                    className="border-0 p-0 ms-2 d-flex justify-content-right align-items-center"
                                                    width="2rem" height="1rem"
                                                    href={"http://localhost:3000/client"}>
                            {/*TODO ovde mora da se dobavi id korisnika*/}

                            <BsPerson style={{width: '2rem', height: '1rem', color: "rgb(106,106,106)"}}/>
                        </Button>}
                        {props.editable && <Button variant="outline-light"
                                                   className="border-0 p-0 d-flex justify-content-right align-items-center"
                                                   width="2em" height="1rem" onClick={props.editFunction}>
                            <BsPencilSquare style={{width: '0.8rem', height: '1rem', color: "rgb(106,106,106)"}}/>
                        </Button>}
                    </div>


                    {(isLoggedIn()) ?

                        <Button className="ms-3 font-monospace"
                        style={{background: "#eaeaea", borderColor: "#eaeaea", color: "#6a6a6a", minWidth: "8rem"}}
                        onClick={()=> logOut()}>
                        Odjavi se
                        </Button>
                        :
                        <Button className="ms-3 font-monospace text-center"
                                style={{
                                    background: "#eaeaea",
                                    borderColor: "#eaeaea",
                                    color: "#6a6a6a",
                                    minWidth: "8rem"
                                }}
                                href={frontLink + "login"}>
                            Prijavi se
                        </Button>
                    }

                    <FilterModal updateResults={props.updateResults} setShowFilters={setShowFilters}
                                 showFilters={showFilters}/>
                </Nav>
            </Container>

        </Navbar>


    )
}