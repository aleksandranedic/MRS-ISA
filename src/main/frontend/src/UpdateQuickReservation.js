import {React, useState, useRef, useEffect} from 'react';
import {Modal, InputGroup, Button, Form, Col, Row} from 'react-bootstrap'
import { TagInfo } from './Info';
import axios from "axios";  
import { useParams } from "react-router-dom";
import './material.css'

function UpdateQuickReservation({state, setState, closeModal, showModal, entity, durationText}) {
    const [startDateInt, setStartDateInt] = useState("");
    const [startTimeInt, setStartTimeInt] = useState("");
    const [originalState, setOriginalState] = useState(state);
    const [tagText, setTagText] = useState('');
    const [validated, setValidated] = useState(false);
    const form = useRef();
    const {id} = useParams();

    useEffect(() => {
        if (state.additionalServices.length === 0)
            state.additionalServices = [{id:'', text:''}]          
        setOriginalState(state);
        setDateTimeInt();
      }, []);

    const setDateTimeInt = () => {
        var day =  state.startDate.split(" ")[0];
        var monthStr = state.startDate.split(" ")[1];
        var year =  state.startDate.split(" ")[2];
        var time =  state.startDate.split(" ")[3];
        setStartTimeInt(time.substring(0, time.length-1));
        if (monthStr === "Jan")
            setStartDateInt(year + "-" + "01-" + day); 
        if (monthStr === "Feb")
            setStartDateInt(year + "-" + "02-" + day);
        if (monthStr === "Mar")
        setStartDateInt(year + "-" + "03-" + day);
        if (monthStr === "Apr")
            setStartDateInt(year + "-" + "04-" + day); 
        if (monthStr === "May")
            setStartDateInt(year + "-" + "05-" + day); 
        if (monthStr === "Jun")
            setStartDateInt(year + "-" + "06-" + day); 
        if (monthStr === "Jul")
            setStartDateInt(year + "-" + "07-" + day); 
        if (monthStr === "Avg")
            setStartDateInt(year + "-" + "08-" + day); 
        if (monthStr === "Sep")
            setStartDateInt(year + "-" + "09-" + day); 
        if (monthStr === "Oct")
            setStartDateInt(year + "-" + "10-" + day);  
        if (monthStr === "Nov")
            setStartDateInt(year + "-" + "11-" + day);  
        if (monthStr === "Dec")
            setStartDateInt(year + "-" + "12-" + day);
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
            data.append("startDate", state.startDate);
            axios
            .post("http://localhost:4444/" + entity + "/updateQuickReservation/" + id, data)
            .then(res => {
                window.location.reload();
            });
            close();
        }
      
      }
      function close(){
        Reset();
        closeModal();
      }
      const Reset = () => {
        setValidated(false);
        setState(originalState);
      }

      const setStartDate = (val) => {
          var sd = state.startDate;
          var date = val.split('-')
          var time = sd.split(" ")[3]
          if(time.substring(time.length - 1) === 'h') {
              time = time.substring(0, time.length-1)
          }
          var newStartDate = date[2] + " " + date[1] + " " + date[0] + " " + time
        setState( prevState => {
            return {...prevState, startDate:newStartDate}
        })
    }
    const setStartTime = (val) => {
        var sd = state.startDate;
        var date = sd.split(" ");
        var day = date[0];
        var month = date[1];
        var year = date[2];
        if (month === "Jan")
            month = ("01"); 
        if (month === "Feb")
            month =("02");
        if (month === "Mar")
            month =("03");
        if (month === "Apr")
            month =("04"); 
        if (month === "May")
            month =("05"); 
        if (month === "Jun")
            month =("06"); 
        if (month === "Jul")
            month =("07"); 
        if (month === "Avg")
            month =("08"); 
        if (month === "Sep")
            month =("09"); 
        if (month === "Oct")
            month =("10");  
        if (month === "Nov")
            month =("11");  
        if (month === "Dec")
            month =("12");
        var newStartDate = day + " " + month + " " + year + " " + val;
        console.log(newStartDate)
        setState( prevState => {
            return {...prevState, startDate:newStartDate}})
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
          
            <Row className="mb-3">

            <Form.Group as={Col} >
                <Form.Label>Datum početka važenja akcije</Form.Label>
                <Form.Control required type="date" name="date" defaultValue={startDateInt} onChange={e => setStartDate(e.target.value)}/>
                <Form.Control.Feedback type="invalid">Molimo Vas unesite početni datum za koje akcija važi.</Form.Control.Feedback>
            </Form.Group>

            {entity !== "house" &&
            <Form.Group as={Col}>
                <Form.Label>Vreme početka važenja akcije</Form.Label>
                <Form.Control required type="time" name="time" defaultValue={startTimeInt} onChange={e => setStartTime(e.target.value)}/>
                <Form.Control.Feedback type="invalid">Molimo Vas unesite početno vreme za koje akcija važi.</Form.Control.Feedback>
            </Form.Group>
            }
            </Row>

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