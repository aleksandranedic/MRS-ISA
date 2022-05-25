import React from 'react';
import ReviewScores from './ReviewScores';
import Reviews from './Reviews';
import {AddReview} from "./AddReview";

function Ratings({reviews, type}) {
    return (
        <div id="reviews">
            <div className="d-flex">
                <ReviewScores/>
                <AddReview type={type}/>
            </div>
            {reviews.length > 1 ?
                <Reviews reviews={reviews}/>
                : <></>
            }
        </div>
    );
}

export default Ratings;