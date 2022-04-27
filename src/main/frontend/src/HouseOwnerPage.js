import React, { useState, useEffect } from 'react';
import axios from "axios";    
import Banner from './Banner';
import BeginButton from './BeginButton';
import Navigation from './Navigation';
import OwnerInfo from './OwnerInfo';
import OwnerHouses from './OwnerHouses';
import AddVacationHouse from './AddVacationHouse';
import { useParams } from "react-router-dom";

function HouseOwnerPage() {
    const {id} = useParams();
    const [houseOwner, setHouseOwner] = useState({address:''});
    let [ownerHouses, setOwnerHouses] = useState([]);
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
                        editable={true}/>
            <AddVacationHouse/>
            <div className='p-5 pt-0'>
                <OwnerInfo bio = {houseOwner.registrationRationale}
                    name={houseOwner.firstName + " " + houseOwner.lastName}
                    rate = {4.5}
                    email={houseOwner.email}
                    phoneNum={houseOwner.phoneNumber}
                    address={houseOwner.address}
                    />
                <hr/>
                <OwnerHouses houses={ownerHouses}/>
                <hr/>
               
            </div>
        <BeginButton/>
        </>
    );
}

export default HouseOwnerPage;