import React, {useEffect, useState} from "react";

import axios from "axios";
import ImageGallery from "./ImageGallery";
import QuickReservations from "./QuickReservations";
import Banner from "./Banner";
import Navigation from "./Navigation";
import HouseInfo from "./HouseInfo";

const Houses = () => {

    // const [houses, setHouses] = useState([]);

    // const fetchHouses = () => {
    //     axios.get("http://localhost:4444/house").then(res => {
    //         console.log(res);
    //         setHouses(res.data);
    //     });
    // };

    // useEffect(() => {
    //     fetchHouses();
    // }, []);

    // return houses.map((house, index) => {
        return <div>
            <Navigation/>
            <HouseInfo 
                    description = {"Opis"}
                    rooms={"5 spavace i 1 dnevna"}
                    capacity={"30 osoba"}
                    rulesAndRegulations={"Dozvoljeno: zezanje i puštanje Zdravka Čolića Zabranjeno: smaranje i pričanje o obavezama"}
                    additionalServices={"Pet friendly, poseduje WiFi, poseduje bazen"}
                    address={"Karađorđa Petrovića 238/19"}
                           />
        </div>
    // })
};


export function VacationHousePage() {
    return (
    <>
        <Banner caption={"Naziv vikendice"}/>
        <Houses/>
        <div className='p-5 pt-0'>
            <hr/>
            <ImageGallery/>
            <hr/>
            <QuickReservations/>
        </div>
    </>

    )
}
export default VacationHousePage;