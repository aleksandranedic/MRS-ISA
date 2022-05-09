import {Sidebar} from "./Sidebar/Sidebar";
import React, {useState} from "react"
import {RegistrationRequestCard} from "./RegistrationRequestCard";
import {Button, Modal} from "react-bootstrap";
export function RegistrationRequests() {



    return (<div className="d-flex"  style={{height: "100vh"}}>
        <div className="w-25" style={{backgroundColor: "#f7f8f9"}}>
            <Sidebar/>
        </div>
        <div className="w-75 overflow-auto">
            <RegistrationRequestCard props={
                {
                    name: "Marko Jovanovic",
                    type: "Instruktor pecanja",
                    email: "marecare@email.com",
                    phoneNumber: "0659632147",
                    address: {
                        street: "Pere Dobrinovica",
                        number: "25",
                        country: "Srbija",
                        place: "Novi Sad"
                    }
                }
            }/>
            <RegistrationRequestCard props={
                {
                    name: "Sinisa Misic",
                    type: "Vlasnik vikendice",
                    email: "siiznisa@email.com",
                    phoneNumber: "0645832147",
                    address: {
                        street: "Pere Dobrinovica",
                        number: "25",
                        country: "Srbija",
                        place: "Novi Sad"
                    }
                }
            }/>


        </div>
    </div>)
}