import React, {useState} from 'react';
import {Modal, Button} from 'react-bootstrap'
import HouseForm from './HouseForm';

function AddVacationHouse({showModal, closeModal}) {
    return (
        <Modal show={showModal} onHide={closeModal} size="lg">
        <Modal.Header>
          <Modal.Title>Dodavanje vikendica</Modal.Title>
        </Modal.Header>
      
        <Modal.Body>
          <HouseForm/>
        </Modal.Body>
      
        <Modal.Footer>
          <Button variant="secondary" onClick={closeModal}>Close</Button>
          <Button variant="primary">Add</Button>
        </Modal.Footer>
      </Modal>
    );
}

export default AddVacationHouse;