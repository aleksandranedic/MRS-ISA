import React from "react";
import {Info, AddressInfo} from "./Info";
import 'bootstrap/dist/css/bootstrap.css'

export default function HouseInfo({description, rooms, capacity, rulesAndRegulations, additionalServices, address}) {
    return (
        <div className="p-5 pt-3 mt-4">
            <h4 className="fw-light">
                {description}
            </h4>
            <hr/>
            <div className="d-flex">
                <div id="left-column" className="w-50 pe-2">
                    <Info title="Broj soba" text={rooms}/>
                    <Info title="Kapacitet" text={capacity}/>
                    <Info title="Pravila ponasanja" text={rulesAndRegulations}/>
                    <Info title="Dodatne usluge" text={additionalServices}/>
                </div>

                <div id="right-column" className="w-50">
                    {/*<AddressInfo title="Adresa" text={address}/>*/}
                </div>
            </div>
        </div>


    )
}