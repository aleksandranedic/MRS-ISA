import React from 'react'
import {Button, Modal} from "react-bootstrap";
import {Info} from "./Info";

export default function DeleteClientPopUp({showDeleteClient,handleCloseDeleteClient,handleDeleteAccount,handleClose}) {
  return (
      <Modal show={showDeleteClient} onHide={handleCloseDeleteClient} size="sm" className="mt-lg-5">
          <Modal.Header closeButton>
              <Modal.Title>Obavestenje</Modal.Title>
          </Modal.Header>
          <Modal.Body>
              <Info text={"Da li ste sigurni da zelite da obrisete nalog?"}/>
          </Modal.Body>
          <Modal.Footer>
              <Button className="me-auto" variant="btn btn-outline-danger" onClick={function (){
                  handleDeleteAccount()
                  handleCloseDeleteClient()
                  handleClose()
              }}>
                  DA
              </Button>
              <Button variant="btn btn-outline-success" onClick={handleCloseDeleteClient}>
                  NE
              </Button>
          </Modal.Footer>
      </Modal>
  )
}
