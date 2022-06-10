import {Sidebar} from "./Sidebar/Sidebar";
import React, {useState} from "react";
import {frontLink} from "../Consts";
import {Card} from "react-bootstrap";
import {GetAllVacationHouses} from "../VacationHousePage/AllVacationHouses";
import {GetAllVacationHouseOwners} from "../VacationHouseOwnerPage/AllVacationHouseOwners";
import {GetAllFishingInstructors} from "../FishingInstructor/AllFishingInstructors";
import {GetAllAdventures} from "../Adventure/AllAdventures";
import {GetAllBoats} from "../BoatPage/AllBoats";
import {GetAllBoatOwners} from "../BoatOwnerPage/AllBoatOwners";

export function ResourceOverview(){

    let linkHouse = frontLink + "/images/houseIncomeCopy.jpg"
    let linkBoat = frontLink + "/images/boatIncomeCopy.jpg"
    let linkAdventure = frontLink + "/images/adventureIncome.jpg"

    let vacationHouses = GetAllVacationHouses();
    let adventures = GetAllAdventures();
    let boats = GetAllBoats();

    let vacationHouseOwners = GetAllVacationHouseOwners();
    let fishingInstructors = GetAllFishingInstructors();
    let boatOwners = GetAllBoatOwners();


    const [resourceType, setResourceType] = useState("");

    return <div className="d-flex" style={{height: "100vh"}}>
        <div className="w-25" style={{backgroundColor: "#f7f8f9"}}>
            <Sidebar/>
        </div>
        <div className="w-75 overflow-auto">
            <div className='d-flex m-0 p-0 justify-content-between adminIncome' >
                <Card id="house" className="bg-white text-white h-100 border-0 m-2 ms-4" style={{borderRadius: "15PX"}} onClick={()=> setResourceType("vacationHouse")}>
                    <Card.Img src={linkHouse} alt="Card image" style={{objectFit:"cover",borderRadius: "15PX",maxWidth:"23vw", minWidth:"23vw",maxHeight:"15vh", minHeight:"15vh", opacity:"0.6"}}/>
                    <Card.ImgOverlay className="d-flex justify-content-center align-items-center">
                        <Card.Title className="display-6" style={{ fontWeight:"500"}}>Vikendice</Card.Title>
                    </Card.ImgOverlay>
                </Card>
                <Card id="boat" className="bg-white text-white h-100 border-0 m-2" style={{borderRadius: "15PX"}} onClick={()=> setResourceType("boat")}>
                    <Card.Img src={linkBoat} alt="Card image" style={{objectFit:"cover",borderRadius: "15PX",maxWidth:"23vw", minWidth:"23vw",maxHeight:"15vh", minHeight:"15vh", opacity:"0.6"}}/>
                    <Card.ImgOverlay className="d-flex justify-content-center align-items-center">
                        <Card.Title className="display-6" style={{ fontWeight:"500"}}>Brodovi</Card.Title>
                    </Card.ImgOverlay>
                </Card>
                <Card id="adventure" className="bg-white text-white h-100 border-0 m-2" style={{borderRadius: "15PX"}} onClick={()=> setResourceType("adventure")}>
                    <Card.Img src={linkAdventure} alt="Card image" style={{objectFit:"cover",borderRadius: "15PX",maxWidth:"23vw", minWidth:"23vw",maxHeight:"15vh", minHeight:"15vh", opacity:"0.7"}}/>
                    <Card.ImgOverlay className="d-flex justify-content-center align-items-center">
                        <Card.Title className="display-6" style={{ fontWeight:"500"}}>Avanture</Card.Title>
                    </Card.ImgOverlay>
                </Card>
            </div>

            <div className="m-4">

                {resourceType === "adventure" && <div>
                    {adventures.map(adventure => {
                        return <>{adventure.title}</>
                    })}
                </div>}

                {resourceType === "vacationHouse" && <div>
                    VacationHouse
                </div>}


                {resourceType === "boat" && <div>
                    Boat
                </div>}
            </div>





        </div>
    </div>
}