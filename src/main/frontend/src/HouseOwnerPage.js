import React from 'react';
import Banner from './Banner';
import BeginButton from './BeginButton';
import Navigation from './Navigation';
import OwnerInfo from './OwnerInfo';
import OwnerHouses from './OwnerHouses';
import AddVacationHouse from './AddVacationHouse';

function HouseOwnerPage(props) {
    return (
        <>
            <Banner caption={"Lepa Sojic"}/>
            <Navigation/>
            <AddVacationHouse/>
            <div className='p-5 pt-0'>
                <OwnerInfo bio = {"Lorem ipsum dolor, sit amet consectetur adipisicing elit. Accusamus repellendus dicta excepturi sed aliquam consequatur magnam nihil. Delectus eos velit amet deserunt natus soluta cum, illo necessitatibus dolorum vel unde repellendus molestias aspernatur. Ipsum cupiditate perspiciatis ullam provident delectus quam, accusamus ad exercitationem aspernatur repellat accusantium. Sit aperiam velit minima itaque neque omnis veritatis harum error minus? Unde, exercitationem saepe?"}
                    name={"Lepa Sojic"}
                    rate = {4.5}
                    email={"pepaprase@gmail.com"}
                    phoneNum={"555-333"}
                    address={"Karađorđa Petrovića 238/19"}
                    />
                <hr/>
                <OwnerHouses/>
                <hr/>
               
            </div>
        <BeginButton/>
        </>
    );
}

export default HouseOwnerPage;