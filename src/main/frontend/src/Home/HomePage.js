import Navigation from "../Navigation/Navigation";
import React, {useState} from "react";
import "./HomePage.css"
import AdventureCarousel from "../Adventure/AdventureCarousel";
import {GetAllAdventures} from "../Adventure/AllAdventures";
import FishingInstructorCarousel from "../FishingInstructor/FishingInstructorCarousel";
import {GetAllFishingInstructors} from "../FishingInstructor/AllFishingInstructors";
import BoatOwnerCarousel from "../BoatOwnerPage/BoatOwnerCarousel";
import {GetAllBoatOwners} from "../BoatOwnerPage/AllBoatOwners";
import VacationHouseOwnerCarousel from "../VacationHouseOwnerPage/VacationHouseOwnerCarousel";
import {GetAllVacationHouseOwners} from "../VacationHouseOwnerPage/AllVacationHouseOwners";
import {GetAllVacationHouses} from "../VacationHousePage/AllVacationHouses";
import VacationHouseCarousel from "../VacationHousePage/VacationHouseCarousel";
import {Button, Form} from "react-bootstrap";
import {BsSearch} from "react-icons/bs";
import {backLink, frontLink} from "../Consts";
import BoatCarousel from "../BoatPage/BoatCarousel";
import {GetAllBoats} from "../BoatPage/AllBoats";

export function HomePage() {

    const [searchTerm, setSearchTerm] = useState("searchTerm");

    return (
        <>
            <Navigation buttons={
                [
                    {text: "Vikendice", path: "#vacationHouses"},
                    {text: "Vlasnici vikendice", path: "#vacationHouseOwners"},
                    {text: "Brodovi", path: "#boats"},
                    {text: "Vlasnici broda", path: "#boatOwners"},
                    {text: "Avanture", path: "#adventures"},
                    {text: "Instruktori pecanja", path: "#fishingInstructors"}
                ]}
                        editable={false} searchable={false} loggedIn={false}
            />
            <img className="hero-img" src={require("../images/Home1.jpg")} alt={"banner"}/>

            <div className="title">
                <h1>VikendISA</h1>
            </div>

            <div className="search d-flex justify-content-center">
                <Form.Control type="text" style={{width: "50vw", borderRadius: "50px"}}
                              onChange={(e) => setSearchTerm(e.target.value)}

                />
                <Button variant="outline-light"
                        className="border-0 p-0 d-flex justify-content-right align-items-center"
                        style={{marginLeft: "1rem", marginRight: "1rem"}} width="3rem"
                        height="5rem"
                        href={frontLink + "search/" + searchTerm}>
                    <BsSearch
                              style={{width: '1.5rem', height: '3rem', color: "white"}}/>
                </Button>
            </div>

            <div className="main">

                <div className="items">
                    <div id="vacationHouses">
                        <h3 className="ms-5">Vikendice</h3>
                        <hr className="ms-5"/>
                        <VacationHouseCarousel add={false} vacationHouses={GetAllVacationHouses()}/>
                    </div>
                    <div id="vacationHouseOwners">
                        <h3 className="ms-5">Vlasnici vikendice</h3>
                        <hr className="ms-5"/>
                        <VacationHouseOwnerCarousel add={false} vacationHouseOwners={GetAllVacationHouseOwners()}/>
                    </div>

                    <div id="boats">
                        <h3 className="ms-5">Brodovi</h3>
                        <hr className="ms-5"/>
                        <BoatCarousel add={false} boats={GetAllBoats()}/>
                    </div>

                    <div id="boatOwners">
                        <h3 className="ms-5">Vlasnici broda</h3>
                        <hr className="ms-5"/>
                        <BoatOwnerCarousel add={false} boatOwners={GetAllBoatOwners()}/>
                    </div>

                    <div id="adventures">
                        <h3 className="ms-5">Avanture</h3>
                        <hr className="ms-5"/>
                        <AdventureCarousel add={false} adventures={GetAllAdventures()}/>
                    </div>

                    <div id="fishingInstructors">
                        <h3 className="ms-5">Instruktori pecanja</h3>
                        <hr className="ms-5"/>
                        <FishingInstructorCarousel add={false} fishingInstructors={GetAllFishingInstructors()}/>
                    </div>
                </div>


            </div>




        </>

    )
}