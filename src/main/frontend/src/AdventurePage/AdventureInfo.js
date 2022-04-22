import React from "react";
import {Info, AddressInfo, TagInfo} from "../Info";
import 'bootstrap/dist/css/bootstrap.css'

export default function AdventureInfo({adventure}) {

    return (
        <div className="p-5 pt-3 mt-4">
            <h4 className="fw-light">
                {adventure.description}
            </h4>

            <div className="d-flex">
                <div id="left-column" className="w-50 pe-2">
                    <Info title="Broj klijenata" text={adventure?.numberOfClients}/>
                    <div className="m-3">
                        <TagInfo title="Oprema za pecanje" tagList={adventure?.fishingEquipment}/>
                    </div>

                    <Info title="Pravila ponasanja" text={adventure?.rulesAndRegulations}/>
                </div>

                <div id="right-column" className="w-50">
                    <AddressInfo title="Adresa" address={adventure?.address}/>
                </div>
            </div>
        </div>


    )
}