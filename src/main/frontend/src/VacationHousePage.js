import React, {useEffect, useState} from "react";

import axios from "axios";
import ImageGallery from "./ImageGallery";
import QuickReservations from "./QuickReservations";
import Banner from "./Banner";
import Navigation from "./Navigation";
import HouseInfo from "./HouseInfo";
import UpdateHouse from "./UpdateHouse"

import BeginButton from "./BeginButton";
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
            <Navigation buttons={
                [
                    {text: "Osnovne informacije", path: "#"},
                    {text: "Fotografije", path: "#"},
                    {text: "Akcije", path: "#"},
                    {text: "Kalendar zauzetosti", path: "#"}
                ]}
                        editable={true} editFunction={""}/>
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
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const house = {
        title: 'Lepa Brena',
        price: '100e',
        description: 'Ovo je opis vikendice ona je jako lepa!!! :))',
        rules_and_regulations: 'Dozvoljen Cola nije dozvoljeno smaranje.',
        number_of_rooms: '5 soba',
        number_of_beds_per_room: '15',
        address: 'Brace Krkljus 7/59',
        city: 'Novi Sad',
        country: 'Srbija',
        services: 'Pet-friendy, bazen'
    }
    return (
    <>
        <Banner caption={"Naziv vikendice"}/>
        <Navigation showUpdateModal = {handleShow}/>
        <Houses/>
        <UpdateHouse closeModal={handleClose} showModal={show} vacationHouse = {house}/>
        <div className='p-5 pt-0'>
            <hr/>
            <ImageGallery/>
            <hr/>
            <QuickReservations/>
        </div>
        <BeginButton/>
    </>

    )
}
export default VacationHousePage;