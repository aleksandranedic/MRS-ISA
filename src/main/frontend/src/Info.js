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

export function AddressInfo({title, text}) {
    return (
        <div className="m-3">
            <p className="lead fw-normal m-0 p-0">{title}</p>
            <p className="fw-light">{text}</p>
        </div>
    )
}

export function TagInfo({title, tags}) {
    return (
        <div className="m-3">
            <p className="lead fw-normal m-0 p-0">{title}</p>
            <TagList tags={tags}/>
        </div>
    )
}

export function TagList({tags}) {
    return (
        tags.map((tag, key) => {
            return <Tag tag={tag.text}/>}
        )
    )
}