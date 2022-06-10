import {Card} from "react-bootstrap";
import {backLink} from "../Consts";
import StarRatings from "react-star-ratings";

export default function SubscriptionCard({entity}) {
    return <a><Card>
        <Card.Img src={backLink + entity.image.path} alt="Card image" />
        <Card.ImgOverlay>
            <Card.Title>{entity.title}</Card.Title>
            <StarRatings rating={entity.rating} numberOfStars={5} starDimension="27px"
                         starSpacing="1px" starRatedColor="#f4b136"/>
        </Card.ImgOverlay>


    </Card></a>
}