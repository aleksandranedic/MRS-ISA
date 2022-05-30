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

    const findFormErrors = () => {
        const {email, password} = form
        const newErrors = {}

        if (!password) newErrors.password = 'cannot be blank!'

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
            <div className="mt-4 d-flex justify-content-center ">
                <img className="border rounded-start border-end-0 border-2 border-dark"
                     src={require("./images/login.jpg")}
                     alt="login"/>
                <div className="py-5 px-4 border rounded-end border-2 border-start-0 border-dark d-flex flex-column"
                     style={{width: "335px"}}>
                    <h1 className="mb-5">Login</h1>
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
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="password"
                                      onChange={e => setField('password', e.target.value)}
                                      isInvalid={!!errors.password}/>
                        <Form.Control.Feedback type='invalid'>
                            {errors.password}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <button onClick={handleSubmit} type="submit" className="btn btn-primary btn-block mt-2">Uloguj se
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
        </>
    )
}
