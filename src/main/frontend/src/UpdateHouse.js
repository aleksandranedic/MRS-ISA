import React from 'react';
import {Modal, Button} from 'react-bootstrap'
import UpdateHouseForm from './UpdateHouseForm';

function UpdateHouse({showModal, closeModal, vacationHouse}) {
    return (
        <Modal show={showModal} onHide={closeModal} size="lg">
        <Modal.Header>
          <Modal.Title>Ažuriranje vikendice</Modal.Title>
        </Modal.Header>
      
        <Modal.Body>
          <UpdateHouseForm house={vacationHouse}/>
        </Modal.Body>
      
        <Modal.Footer className="justify-content-between">
          <Button variant="outline-danger">Obriši</Button>
          <div>
          <Button className="me-2" variant="secondary" onClick={closeModal}>Nazad</Button>
          <Button variant="primary">Sačuvaj</Button>
          </div>
        </Modal.Footer>
      </Modal>
    );
}

export default UpdateHouse;