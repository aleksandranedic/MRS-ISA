import React from 'react';
import {Container, Card, Image} from "react-bootstrap";
import Carousel from 'react-multi-carousel'
import OwlCarousel from 'react-owl-carousel';
import 'owl.carousel/dist/assets/owl.carousel.css';
import 'owl.carousel/dist/assets/owl.theme.default.css';
import './ImageGallery.css';

export default function ImageGallery({images}) {
  const responsive = {
    superLargeDesktop: {
        breakpoint: {max: 4000, min: 3000},
        items: 3
    },
    desktop: {
        breakpoint: {max: 3000, min: 1400},
        items: 3
    },
    desktop2: {
        breakpoint: {max: 1400, min: 1024},
        items: 3
    },
    tablet: {
        breakpoint: {max: 1024, min: 700},
        items: 2
    },
    mobile: {
        breakpoint: {max: 700, min: 0},
        items: 1
    }
};
  return (
    <Container className= "p-0 m-0 w-100" id="gallery">
      <Carousel responsive={responsive} interval="250000" className='w-100 h-100 gallery-carousel d-flex' autoPlay={false} autoPlaySpeed={9000000}>    
      
      {images.map( (image) => (
          <div className="card-container" key={image}>
          <Card className="item card">
            <Image src={image} className="img-fluid" alt=""/>
          </Card>
        </div>
      ))}
      </Carousel>
    </Container>
  );
}