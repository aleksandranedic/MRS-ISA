import {AiOutlineCalendar, AiOutlineClockCircle} from "react-icons/ai";
import React from "react";

export function TimeFooter({reservation}) {

    let startDateArray = reservation.appointments.at(0).startTime;
    let endDateArray = reservation.appointments.at(reservation.appointments.length - 1).endTime;

    let startDate = startDateArray.at(2).toString().padStart(2, '0') +
        "."
        + startDateArray.at(1).toString().padStart(2, '0') +
        "."
        + startDateArray.at(0).toString() +
        ".";
    let startTime = startDateArray.at(3).toString().padStart(2, '0') +
        ":"
        + startDateArray.at(4).toString().padStart(2, '0');

    let endDate = endDateArray.at(2).toString().padStart(2, '0') +
        "."
        + endDateArray.at(1).toString().padStart(2, '0') +
        "."
        + endDateArray.at(0).toString() +
        ".";
    let endTime = endDateArray.at(3).toString().padStart(2, '0') +
        ":"
        + endDateArray.at(4).toString().padStart(2, '0');


    let html =
        <div className="d-flex align-items-center">
            <p className="p-0 m-0">
                <AiOutlineCalendar className="ms-1 me-1"/>
                {startDate}
                <AiOutlineClockCircle className="ms-1 me-1"/>
                {startTime}
                -
                <AiOutlineCalendar className="ms-1 me-1"/>
                {endDate}
                <AiOutlineClockCircle className="ms-1 me-1"/>
                {endTime}
            </p>
        </div>;

    if (startDate === endDate) {
        html =
            <div className="d-flex align-items-center">
                <AiOutlineCalendar className="ms-1 me-1"/>
                <p className="p-0 m-0">
                    {startDate}
                </p>
                <AiOutlineClockCircle className="ms-1 me-1"/>
                {startTime}-{endTime}
            </div>
    }

    if (startTime === endTime) {
        html = <div className="d-flex align-items-center">
            <AiOutlineCalendar className="ms-1 me-1"/>
            <p className="p-0 m-0">
                {startDate}-{endDate}
            </p>
            <AiOutlineClockCircle className="ms-1 me-1"/>
            {startTime}
        </div>
    }

    return html;
}