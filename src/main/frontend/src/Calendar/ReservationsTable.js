import {Table} from "react-bootstrap";
import Tag from "../Tag";
import React from "react";
import {getEndTime, getStartTime} from "./ReservationDateConverter";


export function ReservationsTable(props) {
    let reservations = props.reservations;
    return (
        <div className="m-5">
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
                {reservations.map((reservation) => {
                    return (
                        <tr key={reservation.id}>
                            <td>{reservation.id}</td>
                            <td>{getStartTime(reservation)}</td>
                            <td>{getEndTime(reservation)}</td>
                            <td>{reservation.client.firstName + " " + reservation.client.lastName}</td>
                            {props.showResource && <td>{reservation.resource.title}</td>}
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