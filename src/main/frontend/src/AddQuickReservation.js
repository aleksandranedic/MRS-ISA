import {React, useState, useRef} from 'react';
import axios from "axios";  
import { useParams } from "react-router-dom";
import {Modal, Button, Form, Row, Col, InputGroup} from 'react-bootstrap'
import { TagInfo } from './Info';
import {DateTimePickerComponent} from '@syncfusion/ej2-react-calendars';
import './material.css'
import {backLink} from "./Consts";
import {MessagePopupModal} from "./MessagePopupModal";


function AddQuickReservation({showModal, closeModal, entity, priceText, durationText}) {
    const statePlaceHolder = {startDate:'', price:'', discount:'', numberOfPeople:'', duration:'', additionalServices:[{id:0, text:''}]};
    const [startDateInt, setStartDateInt] = useState("");
    let [state, setState] = useState(statePlaceHolder)
    const [tagText, setTagText] = useState('');
    const [validated, setValidated] = useState(false);
    const form = useRef();
    const {id} = useParams();
    const [showAlert, setShowAlert] = useState(false);

    const submit = e => {
        e.preventDefault() 
        if (form.current.checkValidity() === false || startDateInt === '') {
            if (startDateInt === '') {
                document.getElementById("noDate").style.display = "block";
            }
            else {
                setStartDateInt("")
                document.getElementById("noDate").style.display = "none";
            }
          e.stopPropagation();
          setValidated(true);
        }
        else {  
            var data = new FormData(form.current);   
            state.tagsText = []
            for (let i=0; i < state.additionalServices.length; i++){
                if (state.additionalServices[i].text !== ''){
                    state.tagsText.push(state.additionalServices[i].text)
                }
            } 
            data.append("tagsText", state.tagsText);
            data.append("startDate", startDateInt);
            axios
            .post(backLink+"/" + entity + "/addQuickReservation/" + id, data)
            .then(res => {
                window.location.reload();
            }).catch(error => {
                console.log(error);
                setShowAlert(true);

            })
            close();
        }
      
      }
      function close(){
        Reset();
        closeModal();
      }
      const Reset = () => {
        setValidated(false);
        setState(statePlaceHolder);
      }

    const setStartDate = (val) => {
        const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
        var minutes = (val.getMinutes()<10?'0':'') + val.getMinutes();
        var days = (val.getDate()<10?'0':'') + val.getDate();
        var hours = (val.getHours()<10?'0':'') + val.getHours();
        var newStartDate =  days + " " + monthNames[val.getMonth()] + " " + val.getFullYear() + " " + hours + ":" + minutes + "h"
        
        var month = val.getMonth() + 1;
        var monthInt = (month<10?'0':'') + month;
        var newStartDateInt =  days + " " + monthInt + " " + val.getFullYear() + " " + hours + ":" + minutes
        setStartDateInt(newStartDateInt);
        setState( prevState => {
            return {...prevState, startDate:newStartDate}
        })  
    }
    const setDuration = (value) => {
        setState( prevState => {
           return {...prevState, duration:value}
        })
    }
    const setNumberOfPeople = (value) => {
        setState( prevState => {
           return {...prevState, numberOfPeople:value}
        })
    }
    
    const setPrice = (value) => {
        setState( prevState => {
           return {...prevState, price:value}
        })
    }

    function addButton() {
        if (tagText !== ''){
            setState( prevState => {
                return {...prevState, additionalServices:[...prevState.additionalServices, {id:prevState.additionalServices.at(-1).id+1, text:tagText}]}
            })
            setTagText('')
        }
    }

    return (
        <Modal show={showModal} onHide={close} >
        <Form ref={form} noValidate validated={validated}>
            <Modal.Header>
                <Modal.Title>Dodavanje akcije</Modal.Title>
            </Modal.Header>
            <Modal.Body>
          
                <Form.Group className="mb-3" id={entity}>
                            <Form.Label>Početak važenja akcije</Form.Label>
                            <DateTimePickerComponent allowEdit={false} placeholder='Izaberite vremenski početak' format="dd MMM yyyy 10:00'h'" onChange={e => setStartDate(e.target.value)} step={15}></DateTimePickerComponent>                          
                            <p id="noDate" style={{color:"#dc3545", fontSize: "0.875em", marginLeft:"26%", display:"none"}}>Molimo Vas unesite vremenski početak.</p>        
                </Form.Group>

                <Row className="mb-3">

                    <Form.Group as={Col} >
                        <Form.Label>Broj {durationText} za koje akcija važi</Form.Label>
                        <Form.Control required type="number" min={1} name="duration" onChange={e => setDuration(e.target.value)}/>
                        <Form.Control.Feedback type="invalid">Molimo Vas unesite broj {durationText} za koje akcija važi.</Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group as={Col}>
                        <Form.Label>Maksimalan broj osoba</Form.Label>
                        <Form.Control required type="number" min={1} name="numberOfPeople" onChange={e => setNumberOfPeople(e.target.value)}/>
                        <Form.Control.Feedback type="invalid">Molimo Vas unesite broj ljudi za koje važi akcija..</Form.Control.Feedback>
                    </Form.Group>
                </Row>

             
                <Form.Group as={Col} >
                    <div className='d-flex'>
                    <Form.Label className="me-1">Cena</Form.Label>
                    <Form.Label>{priceText}</Form.Label>
                    </div>
                    <InputGroup>
                        <Form.Control required type="number" min={1} name="price" onChange={e => setPrice(e.target.value)}/>
                        <InputGroup.Text>€</InputGroup.Text>
                        <Form.Control.Feedback type="invalid">Molimo Vas unesite grad.</Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>

                <Form.Group as={Col} controlId="formGridServices">
                <Form.Label>Dodatne usluge</Form.Label>
                    <div className='d-flex justify-content-start'>
                        <TagInfo tagList={state.additionalServices} edit={true} setState={setState} entity="additionalServices"/>
                        <InputGroup className="p-0 mt-2" style={{maxWidth:"17vh", minWidth:"17vh"}}>
                            <Form.Control style={{height:"4vh"}} aria-describedby="basic-addon2" placeholder='Dodaj tag' value={tagText} onChange={e => setTagText(e.target.value)}/>        
                            <Button className="p-0 pe-2 ps-2" style={{height:"4vh"}} variant="primary" id="button-addon2" onClick={addButton}> + </Button>
                        </InputGroup>
                    </div>
                </Form.Group>

            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={close}>Zatvori</Button>
                <Button variant="primary" onClick={submit}>Dodaj</Button>
            </Modal.Footer>
        </Form>

            <MessagePopupModal
                show={showAlert}
                setShow={setShowAlert}
                message="Termin za akciju koji ste pokušali da zauzmete nije dostupan. Pogledajte kalendar zauzetosti i postojaće akcije i pokušajte ponovo."
                heading="Zauzet termin"
            />
      </Modal>
    );
}

export default AddQuickReservation;