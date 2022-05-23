import React from 'react';
import ReviewScores from './ReviewScores';
import Reviews from './Reviews';

function Ratings({reviews}) {
    return (
        <div className='mt-5' id="reviews">
            <ReviewScores/>
            {   reviews.length > 1 ?
                <Reviews reviews={reviews}/>
                : <></>
            }
        </div>
    );
}

export default Ratings;