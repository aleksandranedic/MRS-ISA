import React, {useEffect, useState} from 'react'
import {useParams} from "react-router-dom";
import axios from "axios";
import {BsFillCheckCircleFill} from "react-icons/bs";
import {IoCloseCircleSharp} from "react-icons/io5";
import Button from "react-bootstrap/Button";
import {frontLink} from "./Consts";


export default function EmailConfirmed() {
    const token = useParams()
    const [confirmed, setConfirmed] = useState(false)
    const [messageTitle, setMessageTitle] = useState("Vas nalog nije verifikovan")
    const [message, setMessage] = useState("Vas nalog nije verifikovan. Molim vas pokusajte ponovo.")

    useEffect(() => {
        sendConfirmOnBack()
    }, [])

    function sendConfirmOnBack() {
        axios.get("http://localhost:4444/registration/confirm/" + token.token).then(res => {
            console.log(res.data)
            if (res.data === "Vasa verifikacija je uspesna") {
                setMessageTitle("Vasa verifikacija je uspesna")
                setConfirmed(true)
                setMessage("Vas nalog je verifikovan. Mozete se ulogavati na nas sajt")
            } else {
                setMessageTitle(res.data)
                setConfirmed(false)
            }
        })
    }


    return (
        <div className="m-0 p-0 min-vw-90 min-vh-100"
            // style={{backgroundImage: `url(${background})`, backgroundSize: "cover",}}
        >
            <div className="d-flex justify-content-center">
                <div
                    className="d-flex flex-column align-items-center border border-white m-5 p-3 gap-2 rounded border-3">
                    {confirmed ? <BsFillCheckCircleFill size="5rem" color="green"/> :
                        <IoCloseCircleSharp size="5rem" color="red"/>}
                    <h1 className="text-center">{messageTitle}</h1>
                    <label className="text-center" color="white">{message}</label>
                    <div className="d-flex flex-row gap-3">
                        <Button className="btn btn-primary" href={frontLink + "login"}>Prijavi se na sajt</Button>
                        <Button className="btn btn-primary" href={frontLink + "registration"}>Ponovo se
                            registrujte</Button>
                    </div>
                </div>
            </div>
        </div>
    )
}
