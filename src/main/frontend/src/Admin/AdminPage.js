import {Sidebar} from "./Sidebar/Sidebar";
import {useEffect, useState} from "react";
import {isLoggedIn} from "../Autentification";
import axios from "axios";
import {backLink} from "../Consts";

export function AdminPage() {
    const [user, setUser] = useState(null);

    useEffect(()=> {
        if (isLoggedIn()) {
            getLoggedUser();
        }
    }, [])

    const getLoggedUser = () => {
        axios.get(backLink + "/getLoggedUser").then(
            response => {
                setUser(response.data);

            }
        )
    }

    let html = ""
    if (user !== null){
        html = <>
            <div className="d-flex" style={{height: "100vh"}}>
                <div className="w-25" style={{backgroundColor: "#f7f8f9"}}>
                    <Sidebar/>
                </div>
                <div className="w-75">

                </div>
            </div>
        </>;
    }


    return html;


}