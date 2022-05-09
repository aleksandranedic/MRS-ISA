import {Sidebar} from "./Sidebar/Sidebar";
import React, {useState} from "react";
import {RegistrationRequestCard} from "./RegistrationRequestCard";
import {DeletionRequestCard} from "./DeletionRequestCard";

export function DeletionRequests() {

    const [requests, setRequests] = useState([
        {
            name: "Anđelka Bosiljčić",
            type: "Klijent",
            email: "ranjeniorao@email.com",
            phoneNumber: "0619634647",
            address: {
                street: "Pere Dobrinovica",
                number: "25",
                country: "Srbija",
                place: "Novi Sad"
            },
            text: "Previše vremena provodim gledajući jahte koje ne mogu na iznajmim. Ovaj sajt je za mene koban."
        },
        {
            name: "Neda Mehmedović",
            type: "Vlasnik vikendice",
            email: "nenedadada@email.com",
            phoneNumber: "0645758147",
            address: {
                street: "Pere Dobrinovica",
                number: "25",
                country: "Srbija",
                place: "Novi Sad"
            },
            text: "Kako vikendica nije više u mom posednstvu (morala sam da je prodam da otplatim dugove bivšeg muža koji se kockao), ne treba mi ovaj nalog."
        }


    ])

    const remove = (request) => {
        setRequests(requests.filter(r => r !== request))
    }

    return (<div className="d-flex"  style={{height: "100vh"}}>
        <div className="w-25" style={{backgroundColor: "#f7f8f9"}}>
            <Sidebar/>
        </div>
        <div className="w-75">
            {requests.map(request => {
                return (
                    <DeletionRequestCard props={request} remove={remove}/>
                )
            })}
        </div>
    </div>)
}