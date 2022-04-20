import React from "react";
import {Info, AddressInfo, TagInfo} from "./Info";
import 'bootstrap/dist/css/bootstrap.css'

export default function AdventureInfo({address, description, rulesAndRegulations, numberOfClients, fishingEquipment, biography}) {
    return (
        <div className="p-5 pt-3 mt-4">
            <h4 className="fw-light">
                {description}
            </h4>

            <div className="d-flex">
                <div id="left-column" className="w-50 pe-2">
                    <Info title="Broj klijenata" text={numberOfClients}/>
                    <TagInfo title="Oprema za pecanje" tags={fishingEquipment}/>
                    <Info title="Biografija instruktora" text={biography}/>
                    <Info title="Pravila ponasanja" text={rulesAndRegulations}/>
                </div>

                <div id="right-column" className="w-50">
                    <AddressInfo title="Adresa" text={address}/>
                </div>
            </div>
        </div>


    )
}