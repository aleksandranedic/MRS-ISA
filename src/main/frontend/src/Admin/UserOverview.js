import {Sidebar} from "./Sidebar/Sidebar";
import React from "react";

export function UserOverview() {
    return <div className="d-flex" style={{height: "100vh"}}>
        <div className="w-25" style={{backgroundColor: "#f7f8f9"}}>
            <Sidebar/>
        </div>
        <div className="w-75 overflow-auto">

            USER

        </div>
    </div>
}