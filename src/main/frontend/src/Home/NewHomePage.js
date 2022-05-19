import "./NewHomePage.css"
import {Button, Card} from "react-bootstrap";
import React from "react";
import {GetAllVacationHouses} from "../VacationHousePage/AllVacationHouses";
import {GetAllAdventures} from "../Adventure/AllAdventures";
import {GetAllBoats} from "../BoatPage/AllBoats";
import {backLink, frontLink, responsive} from "../Consts";

import Carousel from "react-multi-carousel";

export function NewHomePage() {



    let vacationHouses = GetAllVacationHouses();
    if (vacationHouses.length > 6) {
        vacationHouses = vacationHouses.subarray(0, 6);
    }
    let adventures = GetAllAdventures();
    if (adventures.length > 6) {
        adventures = adventures.subarray(0, 6);
    }
    let boats = GetAllBoats();
    if (boats.length > 6) {
        boats = boats.subarray(0, 6);
    }

    return (<div className="page">
        <div id="hero" className="d-flex justify-content-center">

            <div className="resources d-flex mt-auto mb-2">
                <div className="resource d-flex">
                    <img className="resource-img" src={require("../images/homepage/pexels-pixabay-290518.jpg")}/>
                    <div>
                        <h1>VIKENDICE</h1>

                        <p>Pronađite najbolje vile, vikendice, kuće i splavove za iznajmljivanje, za vaš savršeni odmor,
                            okupljanje ili događaj.</p>


                        <div className="d-flex w-100 justify-content-end">
                            <Button variant="outline-secondary">PREGLEDAJ</Button>
                        </div>

                    </div>

                </div>
                <div className="resource d-flex">
                    <img className="resource-img" src={require("../images/homepage/pexels-bianca-754355.jpg")}/>
                    <div>
                        <h1>BRODOVI</h1>
                        <p>Ukoliko vaša ideja savršenog odmora uključuje boravak na vodenoj površini,
                            pronađite jahtu, čamac ili brod koji bi ispunio vaš ideal.</p>
                        <div className="d-flex w-100 justify-content-end">
                            <Button variant="outline-secondary">PREGLEDAJ</Button>
                        </div>
                    </div>

                </div>
                <div className="resource d-flex">
                    <img className="resource-img" src={require("../images/homepage/pexels-pixabay-39854.jpg")}/>
                    <div>
                        <h1>AVANTURE</h1>
                        <p>Želite stručno mišljenje i da vas provede kroz najrazličitije pecaroške destinacije?
                            Pogledajte avanture koje vam nude baš to iskustvo.</p>
                        <div className="d-flex w-100 justify-content-end">
                            <Button variant="outline-secondary">PREGLEDAJ</Button>
                        </div>

                    </div>
                </div>

            </div>

        </div>
        <div className="main-content">

            <div id="#vacationHomes" className="links">
                <div className="background">
                    <p className="bg-text">VIKENDICE</p>
                </div>
                <div className="content d-flex justify-content-center align-items-center w-100">
                    {vacationHouses.map(vacationHouse => {
                        return <HomePageCard key={vacationHouse.id} id={vacationHouse.id} title={vacationHouse.title} images={vacationHouse.images}/>

                    })}
                </div>
            </div>
            <div id="#boats" className="links">
                <div className="background">
                    <p className="bg-text">BRODOVI</p>
                </div>
                <div className="content d-flex justify-content-center align-items-center w-100">
                    {boats.map(boat => {
                        return <HomePageCard key={boat.id} id={boat.id} title={boat.title} images={boat.images}/>

                    })}
                </div>
            </div>
            <div id="#adventures" className="links">
                <div className="background">
                    <p className="bg-text">AVANTURE</p>
                </div>
                <div className="content d-flex justify-content-center align-items-center w-100">
                    {adventures.map(adventure => {
                        return <HomePageCard key={adventure.id} id={adventure.id} title={adventure.title} images={adventure.images}/>

                    })}
                </div>
            </div>


        </div>
        <div className="page filler"/>
    </div>)
}

function HomePageCard({images, id, title}) {
    return <Card className="bg-dark text-white links-card">
        <Card.Img src={backLink + images.at(0).path} alt="Card image"/>
        <Card.ImgOverlay>
            <Card.Title><a
                href={frontLink + "house/" + id}>{title}</a></Card.Title>
        </Card.ImgOverlay>
    </Card>
}