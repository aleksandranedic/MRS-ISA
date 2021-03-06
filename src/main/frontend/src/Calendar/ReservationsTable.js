import {Table} from "react-bootstrap";
import Tag from "../Tag";
import React from "react";
import {convertToDate, getEndTime, getStartTime} from "./ReservationDateConverter";
import { frontLink } from "../Consts";


export function ReservationsTable(props) {
    let reservations = [];


    for (let index in props.reservations) {
        let reservation = props.reservations.at(index);

        if (reservation.busyPeriod === false && reservation.quickReservation === false ) {
            reservations.push(reservation);
        }
    }

    function visitClient(clientId){
        window.location.href = frontLink  + "client/" + clientId;
    }

    return (
        <div className="m-4">
            <Table striped bordered hover size="sm">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Vreme početka</th>
                    <th>Vreme završetka</th>
                    <th>Klijent</th>
                    {props.showResource && <th>Resurs</th>}
                    <th>Cena</th>
                    <th>Broj osoba</th>
                    <th>Dodatne usluge</th>
                </tr>
                </thead>
                <tbody>
                {reservations.map((reservation, index) => {
                    return (
                        <tr key={index}>
                            <td>{reservation.id}</td>
                            <td>{getStartTime(reservation)}</td>
                            <td>{getEndTime(reservation)}</td>
                            <td onClick={e => visitClient(reservation.client.id)} style={{cursor:"pointer"}}>{reservation.client.firstName + " " + reservation.client.lastName}</td>
                            {props.showResource && <td>{reservation.resourceTitle}</td>}
                            <td>{reservation.price}</td>
                            <td>{reservation.numberOfClients}</td>
                            <td>
                                {reservation.additionalServices !== [] && reservation.additionalServices.map((tagData) => {
                                    return <Tag key={tagData.id} tag={tagData.text} id={tagData.id}/>
                                })}
                            </td>

                        </tr>
                    )
                })}

                </tbody>
            </Table>
        </div>

    )
}