import {React, useEffect, useState} from 'react';
import {Col, Card} from 'react-bootstrap'
import StarRatings from 'react-star-ratings';

function Review({review}) {
    const HOST = "http://localhost:4444";
    const [profileImg, setProfileImg] = useState("");
    
    
    useEffect(() => {
        setProfileImg(HOST + review.profilePicture);
    }, []);

    function visitClient(){
        window.location.href = "http://localhost:3000/client/" + review.clientId;
    }
    return (
        <Col>
            <Card className="text-center">
                <Card.Body>
                    <Card.Title className="d-flex justify-content-start">
                    <img className="rounded-circle" style={{objectFit: "cover", maxWidth: "7vh", minWidth: "7vh", maxHeight: "7vh", minHeight: "7vh", cursor:"pointer"}} src={profileImg} onClick={e => visitClient()}/>
                       <div className='d-flex flex-column align-items-start ms-2'>
                            <p className="p-0 m-0" onClick={e => visitClient()} style={{cursor:"pointer"}}>{review.name}</p>
                            <StarRatings rating={review.rating} starDimension="14px" starSpacing="1px" starRatedColor="#f4b136"/>
                       </div>
                    </Card.Title>
                    <Card.Text className="d-flex justify-content-start">
                        {review.comment}
                    </Card.Text>
                </Card.Body>
                </Card>
          </Col>
    );
}

export default Review;