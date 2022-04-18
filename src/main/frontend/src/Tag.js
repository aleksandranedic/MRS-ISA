import React from "react";
import {Badge} from "react-bootstrap";

export default function Tag({tag}) {
    return (
        <Badge bg="light" text="primary" className="fw-normal" style={{padding:"0.4rem", margin: "0.5rem 0.1rem", fontWeight: "thin", borderStyle: "solid", borderWidth:"0.001rem"}}>
            {tag}
        </Badge>
    )
}