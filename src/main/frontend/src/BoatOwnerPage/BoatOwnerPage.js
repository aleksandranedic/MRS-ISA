import React, { useState, useEffect } from 'react';
import axios from "axios";    
import Banner from '../Banner';
import BeginButton from '../BeginButton';

import BoatOwnerForm from './BoatOwnerForm'
import OwnerInfo from '../OwnerInfo';
import OwnerBoats from './OwnerBoats';
import AddBoat from './AddBoat';
import { useParams } from "react-router-dom";
import Navigation from "../Navigation/Navigation";


const  UpdateOwner = ({show, setShow, owner}) => {
    if (typeof owner.firstName !== "undefined"){
        return <BoatOwnerForm show={show} setShow={setShow} owner={owner} />
    }
    else {
        return <></>
    }
}

function BoatOwnerPage() {
    const {id} = useParams();

    const [boatOwner, setboatOwner] = useState({address:''});
    let [ownerBoats, setOwnerBoats] = useState([]);

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);

    const HOST = "http://localhost:4444";  
    const fetchOwnerBoats = () => {
      axios
      .get("http://localhost:4444/boat/getownerboats/" + id)
      .then(res => {
          var boats = res.data;
          for (let boat of boats){
              if (!boat.thumbnailPath.includes(HOST)){
                boat.thumbnailPath = HOST + boat.thumbnailPath;
              }
          }
          setOwnerBoats(res.data);
        });
    };
    const fetchboatOwner = () => {
        axios
        .get("http://localhost:4444/boatowner/" + id)
        .then(res => {
            setboatOwner(res.data);
        });
    };
    useEffect(() => {
        fetchboatOwner();
        fetchOwnerBoats();
    }, [ownerBoats]);
    return (
        <>
            <Banner caption={boatOwner.firstName + " " + boatOwner.lastName}/>
            <Navigation buttons={
                [
                    {text: "Osnovne informacije", path: "#info"},
                    {text: "Brodovi", path: "#boats"},
                    {text: "Rezervacije", path: "#sales"},
                    {text: "Izveštaji", path: "#reports"}
                ]}
                        editable={true} editFunction={handleShow} searchable={true} showProfile={true}/>
            <AddBoat/>
            <UpdateOwner show={show} setShow={setShow} owner={boatOwner}/>
            <div className='p-5 pt-0'>
                <OwnerInfo bio = {boatOwner.registrationRationale}
                    name={boatOwner.firstName + " " + boatOwner.lastName}
                    rate = {4.5}
                    email={boatOwner.email}
                    phoneNum={boatOwner.phoneNumber}
                    address={boatOwner.address}
                    />
                <hr/>
                <OwnerBoats boats={ownerBoats}/>
                <hr/>
               
            </div>
        <BeginButton/>
        </>
    );
}

export default BoatOwnerPage;