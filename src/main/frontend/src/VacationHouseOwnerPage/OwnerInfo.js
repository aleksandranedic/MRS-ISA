import React from 'react';
import StarRatings from 'react-star-ratings';
import ProfileInfo from '../ProfileInfo'
import {BsEnvelope, BsTelephone, BsGeoAlt} from 'react-icons/bs'

function OwnerInfo({name, rate, bio, email, phoneNum, address}) {
    return (
        <div className = "d-flex ps-2" id="info">
            <div className="w-50 d-flex flex-column">
                <h2 className="fw-normal align-self-start pt-4">{name}</h2>
                <div className="d-flex align-itens-center">     
                <p className="fw-bold m-0 mt-1 me-2 p-0"> {rate} </p>
                <StarRatings rating={rate} starDimension="17px" starSpacing="1px" starRatedColor="#f4b136"/>
                </div>
                <p className="mt-4 pe-5"  style={{ textAlign: 'justify', textJustify: 'inter-word'}}>
                    {bio}
                </p>
            </div>
            <div className='w-50 pt-5 ps-0'>
                <ProfileInfo icon={BsEnvelope} label={"Email"} text={email}/>
                <ProfileInfo icon={BsTelephone} label={"Broj telefona"} text={phoneNum}/>
                <ProfileInfo icon={BsGeoAlt} label={"Adresa"} text={address.street + " " + address.number + ", " + address.place +", " + address.country}/>
            </div>
        </div>
    );
}

export default OwnerInfo;