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

export function LinkInfo({title, text, link}) {
    return (
        <div className="m-3">
            <p className="lead fw-normal m-0 p-0">{title}</p>
            <a href={link} style={{textDecoration: "None", color: "#212529", cursor: "pointer"}}><p className="fw-light">{text}</p></a>
        </div>


    )
}

export function AddressInfo({title, address}) {
    return (
        <div className="m-3">
            <p className="lead fw-normal m-0 p-0">{title}</p>
            <p className="fw-light m-0">{address["street"] + " " + address["number"]}</p>
            <p className="fw-light">{address["place"] + ", " + address["country"]}</p>
        </div>
    )
}

export function TagInfo({title, tagList, edit, setState}) {
    function removeTag(id){
        var object = tagList.find((element) => {return element.id === id;})
        var newTagList = tagList.filter(function(tag) { return tag !== object })
        if (newTagList.length === 0){
            newTagList = [{id:0, text:''}]
        }
        setState( prevState => {
            if (typeof prevState.additionalServices != "undefined")
                return {...prevState, additionalServices:newTagList}
            else    
            return {...prevState, navigationEquipment:newTagList}
        })
    }
    return (
        <div className="m-0">
            <p className="lead fw-normal m-0 p-0">{title}</p>
            {tagList.map((tagData)=> {
                return <Tag key={tagData.id} tag={tagData.text} edit={edit} id={tagData.id} remove={removeTag}/>
            })}
        </div>
    )
}