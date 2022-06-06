import {Sidebar} from "./Sidebar/Sidebar";
import React, {useState} from "react";
import {Badge, Button, Card, Form, Modal} from "react-bootstrap";
import {BsArrowRight} from "react-icons/bs";
import StarRatings from "react-star-ratings";


export function Complaints() {

    //TODO: Napraviti complaintDTO koji sadrzi sledece podatke, moze i dodatne ako ti treba



    const [requests, setRequests] = useState([
        {
            userFullName: "Ana Nikolic",
            comment: "Ne mogu da nadjem dugme za akcije!"
        },
        {
            userFullName: "Mirko Markic",
            comment: "Navigacija vam je realno onako"
        }

    ]);


    return (<div className="d-flex" style={{height: "100vh"}}>
        <div className="w-25" style={{backgroundColor: "#f7f8f9"}}>
            <Sidebar/>
        </div>
        <div className="w-75 overflow-auto">

            {requests.map((request, index) => {
                return (
                    <Complaint key={index} request={request}/>
                )
            })}

        </div>
    </div>)
}

function Complaint({request}) {
    const [show, setShow] = useState(false);

    let variant = "secondary";


    return (
        <Card className="m-3" border={variant}>
            <Card.Header className="d-flex align-items-center">


                <h5 className="mt-2">{request.userFullName}</h5>


            </Card.Header>
            <Card.Body className="d-flex align-items-center">
                <div className="flex-column">

                    <div>{request.comment}</div>

                </div>

                <Button className="ms-auto m-1" variant="outline-secondary"
                        onClick={() => setShow(true)}>Odgovori</Button>
                <ComplaintModal request={request} show={show} setShow={setShow}/>
            </Card.Body>
        </Card>
    )
}

function ComplaintModal({request, show, setShow}) {

    const [response, setResponse] = useState("");

    return <Modal show={show} onHide={() => setShow(false)}>
        <Modal.Header closeButton className="d-flex align-items-center">
            <h5 className="mt-2">{request.userFullName}</h5>

        </Modal.Header>
        <Modal.Body>

            <Form>
                <Form.Label>Odgovor:</Form.Label>
                <Form.Control as="textarea" rows={3} name="response" value={response}
                              onChange={e => setResponse(e.target.value)}/>
            </Form>

        </Modal.Body>

        <Modal.Footer className="d-flex justify-content-end">
            <Button className="ms-auto m-1" variant="outline-secondary">Odgovori</Button>
        </Modal.Footer>
    </Modal>
}