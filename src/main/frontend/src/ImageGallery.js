import React, { Component } from 'react';
import ReactDOM from 'react-dom';

import v1 from "./images/vikendica1.jpeg"
import v2 from "./images/vikendica2.jpeg"
import v3 from "./images/vikendica3.jpeg"
import v4 from "./images/vikendica4.jpeg"
import boatnotext from "./images/boatsnotext.png";
import {Container, Card, Image} from "react-bootstrap";
import OwlCarousel from 'react-owl-carousel';
import 'owl.carousel/dist/assets/owl.carousel.css';
import 'owl.carousel/dist/assets/owl.theme.default.css';
import './ImageGallery.css';

export default function ImageGallery({images}) {
const options = {
  center: true,
  responsiveClass: true,
  responsive: {
    0: {
      items: 1
    },
    600: {
      items: 3
    },
    1000: {
      items: 3
    },
    1400: {
      items: 3
    }
  },
  autoplay: false,
  dots: false,
  nav: true
};

  return (
    <Container className= "p-0 m-0 w-100" id="gallery">
      <OwlCarousel className="owl-theme owl-theme gallery-carousel" loop {...options} navElement>
      {images.map( (image) => (
          <div className="card-container" key={image}>
          <Card className="item card">
            <Image src={v2} className="img-fluid" alt=""/>
          </Card>
        </div>
      ))}
      </OwlCarousel>
    </Container>
  );
}