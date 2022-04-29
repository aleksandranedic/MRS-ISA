import {Sidebar} from "./Sidebar/Sidebar";

export function DeletionRequests() {
    return (<div className="d-flex"  style={{height: "100vh"}}>
        <div className="w-25" style={{backgroundColor: "#f7f8f9"}}>
            <Sidebar/>
        </div>
        <div className="w-75">
            <h1>
                Delete
            </h1>
        </div>
    </div>)
}