import {React, useState, useRef, useEffect} from 'react';
import {Modal, InputGroup, Button, Form, Col, Row} from 'react-bootstrap'
import { TagInfo } from './Info';
import axios from "axios";  
import { useParams } from "react-router-dom";
import * as ReactDOM from 'react-dom';
import {DateTimePickerComponent} from '@syncfusion/ej2-react-calendars';
import './material.css'

function UpdateQuickReservation({state, setState, closeModal, showModal, entity, durationText}) {
    const [startDateInt, setStartDateInt] = useState("");
    const [originalState, setOriginalState] = useState(state);
    const [tagText, setTagText] = useState('');
    const [validated, setValidated] = useState(false);
    const form = useRef();
    const {id} = useParams();

    useEffect(() => {
        if (state.additionalServices.length === 0)
            state.additionalServices = [{id:'', text:''}]          
        setOriginalState(state);
        var dateInt = (getStartDateInt());
        console.log(dateInt)
        setStartDateInt(dateInt);
      }, []);

    const getStartDateInt = () => {
        var day =  state.startDate.split(" ")[0];
        var monthStr = state.startDate.split(" ")[1];
        var year =  state.startDate.split(" ")[2];
        var time =  state.startDate.split(" ")[3];
        if (monthStr === "Jan")
            return day +  " 01 " + year  + " " + time.substring(0, time.length-1);
        if (monthStr === "Feb")
            return day +  " 02 " + year  + " " + time.substring(0, time.length-1);
        if (monthStr === "Mar")
            return day +  " 03 " + year  + " " + time.substring(0, time.length-1);
        if (monthStr === "Apr")
            return day +  " 04 " + year  + " " + time.substring(0, time.length-1);
        if (monthStr === "May")
            return day +  " 05 " + year  + " " + time.substring(0, time.length-1);
        if (monthStr === "Jun")
            return day +  " 06 " + year  + " " + time.substring(0, time.length-1);
        if (monthStr === "Jul")
            return day +  " 07 " + year  + " " + time.substring(0, time.length-1);
        if (monthStr === "Avg")
            return day +  " 08 " + year  + " " + time.substring(0, time.length-1);
        if (monthStr === "Sep")
            return day +  " 09 " + year  + " " + time.substring(0, time.length-1);
        if (monthStr === "Oct")
            return day +  " 10 " + year  + " " + time.substring(0, time.length-1);
        if (monthStr === "Nov")
            return day +  " 11 " + year  + " " + time.substring(0, time.length-1);
        return day +  " 12 " + year  + " " + time.substring(0, time.length-1);
    }
    const submit = e => {
        e.preventDefault()
    
        if (form.current.checkValidity() === false) {
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
            data.append("reservationID", state.reservationID);
            data.append("startDate", startDateInt);
            console.log(startDateInt)
            axios
            .post("http://localhost:4444/" + entity + "/updateQuickReservation/" + id, data)
            .then(res => {
                window.location.reload();
            });
            close();
        }
      
      }
      function close(){
        if (entity === "house"){
            var icon = document.getElementsByClassName("e-time-icon");
            if (typeof icon[0] !== "undefined")
                icon[0].style.display = "none"
        }
        Reset();
        closeModal();
      }
      const Reset = () => {
        setValidated(false);
        setState(originalState);
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
        console.log(newStartDateInt)
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
                <Modal.Title>Ažuriranje akcije</Modal.Title>
            </Modal.Header>
            <Modal.Body>
          
                <Form.Group id={entity} className="mb-3">
                            <Form.Label>Početak važenja akcije</Form.Label>
                            <DateTimePickerComponent required allowEdit={false} format="dd MMM yyyy HH:mm'h'" value={state.startDate} onChange={e => setStartDate(e.target.value)} step={15}></DateTimePickerComponent>                          
                            <Form.Control.Feedback type="invalid">Molimo Vas unesite datum.</Form.Control.Feedback>               
                </Form.Group>

                <Row className="mb-3">

                    <Form.Group as={Col} >
                        <Form.Label>Broj {durationText} za koje akcija važi</Form.Label>
                        <Form.Control required type="number" min={1} name="duration" defaultValue={state.duration} onChange={e => setDuration(e.target.value)}/>
                        <Form.Control.Feedback type="invalid">Molimo Vas unesite broj {durationText} za koje akcija važi.</Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group as={Col}>
                        <Form.Label>Maksimalan broj osoba</Form.Label>
                        <Form.Control required type="number" min={1} name="numberOfPeople" defaultValue={state.numberOfPeople} onChange={e => setNumberOfPeople(e.target.value)}/>
                        <Form.Control.Feedback type="invalid">Molimo Vas unesite broj ljudi za koje važi akcija..</Form.Control.Feedback>
                    </Form.Group>
                </Row>

             
                <Form.Group as={Col} >
                    <Form.Label>Cena po noćenju</Form.Label>
                    <InputGroup>
                        <Form.Control required type="number" min={1} name="price" defaultValue={state.price} onChange={e => setPrice(e.target.value)}/>
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
            <Modal.Footer className="justify-content-between">
                <Button variant="outline-danger">Obriši</Button>
                <div>
                    <Button className="me-2" variant="secondary" onClick={close}>Nazad</Button>
                    <Button variant="primary" onClick={submit} >Sačuvaj</Button>
                </div>
                </Modal.Footer>
        </Form>
      </Modal>
    );
}

export default UpdateQuickReservation;