import React from "react";
import {Info, TagInfo} from "../Info";
import 'bootstrap/dist/css/bootstrap.css'

export default function BoatInfo({boat}) {
    if (typeof boat.navigationEquipment !== 'undefined'){
    return (
        <div className="p-5 pt-3 mt-4" id="info">
            <h4 className="fw-light">
                {boat.description}
            </h4>
            <hr/>
            <div className="d-flex">
                <div id="left-column" className="w-50 pe-2">
                    <Info title="Tip broda" text={boat.type}/>
                    <Info title="Dužina broda" text={boat.length}/>
                    <Info title="Kapacitet" text={boat.capacity}/>
                    <Info title="Broj motora" text={boat.engineNumber}/>
                    <Info title="Snaga motora" text={boat.engineStrength}/>
                    <Info title="Maksimalna brzina broda" text={boat.topSpeed}/>
                    <Info title="Pravila ponasanja" text={boat.rulesAndRegulations}/>
                </div>

                <div id="right-column" className="w-50">
                    <div className="m-3">
                        <TagInfo title="Navigaciona oprema" tagList={boat.navigationEquipment}/>
                    </div>
                    <div className="m-3">
                        <TagInfo title="Oprema za pecanje" tagList={boat.fishingEquipment}/>
                    </div>
                    <div className="m-3">
                        <TagInfo title="Dodatne usluge" tagList={boat.additionalServices}/>
                    </div>
                    <Info title="Adresa" text={boat.address}/>
                </div>
            </div>
        </div>
    )
    }
    else {
        return <></>
    }   
}