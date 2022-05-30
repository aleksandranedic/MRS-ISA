import React, {useState} from 'react'
import 'bootstrap/dist/css/bootstrap.css';
import {Form} from "react-bootstrap";
import axios from "axios";
import Button from "react-bootstrap/Button";
import {backLink, frontLink, notifyError, notifySuccess} from "./Consts";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'

export default function Login() {
    function handleValidateLogIn({email, password}) {

        let loginDto = {
            username: email,
            password: password
        }
        axios.post(backLink + "/login", loginDto).then(res => {
            notifySuccess("Uspešno ste se ulogovali")
            localStorage.setItem("token", res.data)
            setTimeout(function (){
                window.location.href = frontLink
            },5000)
        }).catch(error => {
            notifyError(error.response.data)
        })
    }

    const [form, setForm] = useState({})
    const [errors, setErrors] = useState({})

    const emailRegExp = new RegExp("[A-Za-z0-9]+@[a-z]+.(com)")
    const passwordExp = new RegExp(".[^ ]+")

    const findFormErrors = () => {
        const {email, password} = form
        const newErrors = {}

        if (!password || !passwordExp.test(password)) newErrors.password = 'cannot be blank!'

        if (!email || !emailRegExp.test(email)) newErrors.email = 'cannot be blank! must have @ and .com'

        return newErrors
    }

    const handleSubmit = e => {
        e.preventDefault()
        const newErrors = findFormErrors()
        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors)
        } else {
            handleValidateLogIn(form)
        }
    }
    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })
        if (!!errors[field]) setErrors({
            ...errors,
            [field]: null
        })
    }
    return (
        <>
            <div className="d-flex justify-content-center w-100"
            style={{height: "100vh" ,background: "linear-gradient(175deg, rgba(248,248,248,0.9) 50%, rgba(201, 201, 201, 0.6) 100%)"}}
            >
                <img
                     src={require("./images/pexels-te-lensfix-1371360.jpg")}
                     alt="login"
                     style={{width: "60%"}}
                />
                <div className="p-5 d-flex flex-column"
                     style={{width: "40%", backgroundColor: "rgba(255,255,255, 0.8)"}}>
                    <h1 className="mb-5">Prijava</h1>
                    <Form.Group className="mb-3" controlId="formEmail">
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="text"
                                      onChange={e => setField('email', e.target.value)}
                                      isInvalid={!!errors.email}/>
                        <Form.Control.Feedback type='invalid'>
                            {errors.email}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formPassword">
                        <Form.Label>Lozinka</Form.Label>
                        <Form.Control type="password"
                                      onChange={e => setField('password', e.target.value)}
                                      isInvalid={!!errors.password}/>
                        <Form.Control.Feedback type='invalid'>
                            {errors.password}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <button onClick={handleSubmit} type="submit" className="btn btn-primary btn-block mt-2">
                        Prijavi se
                    </button>
                    <div className="d-flex flex-column mt-5 pt-lg-5">
                        <label>Želite da se registrujete?</label>
                        <Button className="btn btn-primary btn-block mt-2"
                                href={frontLink + "registration"}>Registracija</Button>
                        <label>Da li želite da posetite sajt?</label>
                        <Button className="btn btn-primary btn-block mt-2" href={frontLink}>Nazad na početnu
                            stranu</Button>
                    </div>
                </div>
            </div>
            <ToastContainer
                position="top-center"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme={"colored"}
            />
            <ToastContainer
                position="top-center"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme={"colored"}
            />
        </>
    )
}
