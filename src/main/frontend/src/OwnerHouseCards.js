import React from 'react';
import OwnerHouseCard from "./OwnerHouseCard";

function OwnerHouseCards({houses}) {
    return (
        <>
        {houses.map( (house) => (
            <div className='house-container' key={house.id}>
                    <OwnerHouseCard
                        id={house.id}
                        pic={house.thumbnailPath}
                        title={house.title}
                        text={house.description}
                        address={house.address}
                        />
            </div>               
            )   
        )}
         </>
    );
}

export default OwnerHouseCards;