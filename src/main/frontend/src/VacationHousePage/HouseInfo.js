import React from "react";
import {Info, TagInfo} from "../Info";
import 'bootstrap/dist/css/bootstrap.css'

export default function HouseInfo({house}) {
    if (typeof house.additionalServices !== 'undefined'){
    return (
        <div className="p-5 pt-3 mt-4" id="info">
            <h4 className="fw-light">
                {house.description}
            </h4>
            <hr/>
            <div className="d-flex">
                <div id="left-column" className="w-50 pe-2">
                    <Info title="Broj soba" text={house.numberOfRooms}/>
                    <Info title="Kapacitet" text={house.capacity}/>
                    <Info title="Pravila ponasanja" text={house.rulesAndRegulations}/>
                    <div className="m-3">
                        <TagInfo title="Dodatne usluge" tagList={house.additionalServices}/>
                    </div>
                </div>

                <div id="right-column" className="w-50">
                <Info title="Adresa" text={house.address}/>
                </div>
            </div>
        </div>
    )
    }
    else {
        return <></>
    }


    
}