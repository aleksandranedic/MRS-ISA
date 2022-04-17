import React from "react";
import {Badge} from "react-bootstrap";

export default function Tag({tag}) {
    return (
        <Badge pill bg="light" text="primary" style={{padding:"0.6rem", margin: "0.5rem 0.1rem", fontWeight: "thin", borderStyle: "solid", borderWidth:"0.001rem"}}>
            {tag}
        </Badge>
    )
}