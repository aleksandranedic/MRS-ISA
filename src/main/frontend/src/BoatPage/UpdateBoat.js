import axios from 'axios';
import React, {useState, useRef, useEffect} from 'react';
import {Modal, Button} from 'react-bootstrap'
import UpdateBoatForm from './UpdateBoatForm';
import { useParams } from "react-router-dom";

function UpdateBoat({showModal, closeModal, boat}) {
  const form = useRef();
  const imagesRef = useRef();
  const {id} = useParams();
  const [validated, setValidated] = useState(false);
  const HOST = "http://localhost:4444";
  const [state, setState] = useState({name:'', price:'', description:'', type:'', engineStrength:'', topSpeed:'', length:'', engineNumber:'', capacity:'', rulesAndRegulations:'', street:'', number:'', city:'', country:'', navigationEquipment:[{id:0, text:''}], fishingEquipment:[{id:0, text:''}], additionalServices:[{id:0, text:''}], cancellationFee:'', imagePaths:[]});
    useEffect(() => {
      setState(boat);
    }, []);
  const submit = e => {
    e.preventDefault()
    if (form.current.checkValidity() === false || state.imagePaths.length === 0) {
      document.getElementById("noImages").style.display = "block"
      e.stopPropagation();
      setValidated(true);
    }
    else {
      var files = imagesRef.current.files;
      var data = new FormData(form.current);
      var images = []
      for (let i=0; i < files.length; i++){
        images.push(files[i])
      }
      state.tagsText = []
      for (let i=0; i < state.navigationEquipment.length; i++){
        if (state.navigationEquipment[i].text !== ''){
          state.tagsText.push(state.navigationEquipment[i].text)
        }
      }
      state.tagsAdditionalServicesText = []
      for (let i=0; i < state.additionalServices.length; i++){
        if (state.additionalServices[i].text !== ''){
          state.tagsAdditionalServicesText.push(state.additionalServices[i].text)
        }
      }
      state.tagsFishingEquipText = []
      for (let i=0; i < state.fishingEquipment.length; i++){
        if (state.fishingEquipment[i].text !== ''){
          state.tagsFishingEquipText.push(state.fishingEquipment[i].text)
        }
      }
      var imgs = []
      for (let i=0; i<state.imagePaths.length;i++){
        if (!state.imagePaths[i].includes("blob")){
          imgs.push(state.imagePaths[i].replace(HOST, ''));
        }
      }
      data.append("name", state.name)
      data.append("price", state.price)
      data.append("description", state.description)
      data.append("capacity", state.capacity)
      data.append("type", state.type)
      data.append("engineStrength", state.engineStrength)
     
      data.append("topSpeed", state.topSpeed)
      data.append("length", state.length)
      data.append("rulesAndRegulations", state.rulesAndRegulations)
      data.append("city", state.city)
      data.append("number", state.number)
      data.append("street", state.street)
      data.append("country", state.country)
      data.append("cancellationFee", state.cancellationFee)
      data.append("imagePaths", imgs)
      data.append("tagsText", state.tagsText)
      data.append("tagsAdditionalServicesText", state.tagsAdditionalServicesText)
      data.append("tagsFishingEquipText", state.tagsFishingEquipText)
      data.append("fileImage",images);
      
      axios
      .post("http://localhost:4444/boat/updateBoat/" + id, data)
      .then(res => {
        window.location.reload();
      });
      closeModal();
    }
  }
  function close(){
    Reset();
    closeModal();
  }
  const Reset = () => {
    setValidated(false);
    setState(boat);
  }
    return (
        <Modal show={showModal} onHide={close} size="lg">
        <Modal.Header>
          <Modal.Title>Ažuriranje broda</Modal.Title>
        </Modal.Header>
      
        <Modal.Body>
          <UpdateBoatForm state={state} setState={setState} reference={form} imagesRef={imagesRef} validated={validated}/>
        </Modal.Body>
      
        <Modal.Footer className="justify-content-between">
          <Button variant="outline-danger">Obriši</Button>
          <div>
          <Button className="me-2" variant="secondary" onClick={close}>Nazad</Button>
          <Button variant="primary" onClick={submit} >Sačuvaj</Button>
          </div>
        </Modal.Footer>
      </Modal>
    );
}

export default UpdateBoat;