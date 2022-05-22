import React, { useState, useEffect } from 'react';
import axios from "axios";    
import Banner from '../Banner';
import BeginButton from '../BeginButton';

import OwnerInfo from '../OwnerInfo';
import OwnerHouses from './OwnerHouses';
import AddVacationHouse from './AddVacationHouse';
import HouseOwnerForm from "./HouseOwnerForm";
import { useParams } from "react-router-dom";
import Navigation from "../Navigation/Navigation";
import { profilePicturePlaceholder } from '../Consts';

const  UpdateOwner = ({show, setShow, owner}) => {
    if (typeof owner.firstName !== "undefined"){
        return <HouseOwnerForm show={show} setShow={setShow} owner={owner} />
    }
    else {
        return <></>
    }
}

function HouseOwnerPage() {
    const {id} = useParams();
    const [houseOwner, setHouseOwner] = useState({address:'', profileImg:{path:""}});
    let [ownerHouses, setOwnerHouses] = useState([]);

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    
    const HOST = "http://localhost:4444";  
    const fetchOwnerHouses = () => {
      axios
      .get("http://localhost:4444/house/getownerhouses/" + id)
      .then(res => {
          var houses = res.data;
          for (let house of houses){
              if (!house.thumbnailPath.includes(HOST)){
                  house.thumbnailPath = HOST + house.thumbnailPath;
              }
          }
          setOwnerHouses(res.data);
        });
    };
    const fetchHouseOwner = () => {
        axios
        .get("http://localhost:4444/houseowner/" + id)
        .then(res => {
            setHouseOwner(res.data);
        });
    };
    useEffect(() => {
        fetchHouseOwner();
        fetchOwnerHouses();
    }, [ownerHouses]);
    console.log(houseOwner)
    return (
        <>
            <Banner caption={houseOwner.firstName + " " + houseOwner.lastName}/>
            <Navigation buttons={
                [
                    {text: "Osnovne informacije", path: "#info"},
                    {text: "Vikendice", path: "#houses"},
                    {text: "Rezervacije", path: "#sales"},
                    {text: "Izveštaji", path: "#reports"}
                ]}
                        editable={true} editFunction={handleShow} searchable={true} showProfile={true}/>
            <AddVacationHouse/>
            <UpdateOwner show={show} setShow={setShow} owner={houseOwner}/>
            <div className='p-5 pt-0'>
                { houseOwner.profileImg !== null ? 

                <OwnerInfo 
                    name={houseOwner.firstName + " " + houseOwner.lastName}
                    rate = {4.5}
                    email={houseOwner.email}
                    phoneNum={houseOwner.phoneNumber}
                    address={houseOwner.address}
                    profileImg = {HOST + houseOwner.profileImg.path}
                    />
                :
                <OwnerInfo 
                    name={houseOwner.firstName + " " + houseOwner.lastName}
                    rate = {4.5}
                    email={houseOwner.email}
                    phoneNum={houseOwner.phoneNumber}
                    address={houseOwner.address}
                    profileImg = {profilePicturePlaceholder}
                    />
                }
                <hr/>
                <OwnerHouses houses={ownerHouses}/>
                <hr/>
               
            </div>
        <BeginButton/>
        </>
    );
}

export default HouseOwnerPage;