import React from "react";
import 'bootstrap/dist/css/bootstrap.css'
import Tag from "./Tag";

export function Info({title, text}) {
    return (
        <div className="m-3">
            <p className="lead fw-normal m-0 p-0">{title}</p>
            <p className="fw-light">{text}</p>
        </div>


    )
}

export function AddressInfo({title, address}) {
    console.log(address)
    return (
        <div className="m-3">
            <p className="lead fw-normal m-0 p-0">{title}</p>
            <p className="fw-light m-0">{address["street"] + " " + address["number"]}</p>
            <p className="fw-light">{address["place"] + ", " + address["country"]}</p>
        </div>
    )
}

export function TagInfo({title, tagList}) {
    return (
        <div className="m-3">
            <p className="lead fw-normal m-0 p-0">{title}</p>
            {tagList.map((tagData)=> {
                return <Tag key={tagData.id} tag={tagData.text}/>
            })}
        </div>
    )
}