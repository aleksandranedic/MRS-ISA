import React, {useState} from 'react'
import {useParams} from "react-router-dom";
import axios from "axios";
// import {BsCheckCircle} from "@react-icons/all-files/bs/BsCheckCircle";
import {BsFillCheckCircleFill} from "react-icons/bs";
import background from "./images/fishing3.jpg";
import {IoCloseCircleSharp} from "react-icons/io5";
// import {BsFillCheckCircleFill} from "react-icons/all";

export default function EmailConfirmed() {
    const token = useParams()
    const [confirmed, setConfirmed] = useState(false)
    const [messageTitle, setMessageTitle] = useState("Vas nalog nije verifikovan")
    const [message, setMessage] = useState("Vas nalog nije verifikovan. Molim vas pokusajte ponovo.")


    function sendConfirmOnBack() {
        console.log(token.token)
        axios.get("http://localhost:4444/registration/confirm/" + token.token).then(res => {
            console.log(res.data)
            if(res.data==="Vasa verifikacija je uspesna"){
                setMessageTitle("Vasa verifikacija je uspesna")
                setConfirmed(true)
                setMessage("Vas nalog je verifikovan. Mozete se ulogavati na nas sajt")
            }else {
                setMessageTitle(res.data)
            }
        })
    }

    function redirectToLogin() {
        window.location.href = "http://localhost:3000/login"
    }

    function redirectToRegistration() {
        window.location.href = "http://localhost:3000/registration"
    }

    return (
        <div className="m-0 p-0 min-vw-90 min-vh-100" style={{
            backgroundImage: `url(${background})`, backgroundSize: "cover",
        }}>
            <div className="d-flex justify-content-center">
                <div
                    className="d-flex flex-column align-items-center border border-white m-5 p-3 gap-2 rounded border-3">
                    {confirmed ? <BsFillCheckCircleFill size="5rem" color="green" className="invisible"/> :
                        <IoCloseCircleSharp size="5rem" color="red" className="visible"/>}
                    <h1 className="text-center text-white">{messageTitle}</h1>
                    <label className="text-center" color="white">{message}</label>
                    <div className="d-flex flex-row gap-3">
                        <button className="btn btn-primary" onClick={redirectToLogin}>Prijavi se na sajt</button>
                        <button className="btn btn-primary" onClick={redirectToRegistration}>Ponovo se registrujte
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}
