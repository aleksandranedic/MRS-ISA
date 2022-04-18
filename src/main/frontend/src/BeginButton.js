import React from 'react';
import {Button} from 'react-bootstrap';
import './BeginButton.css';

function BeginButton(props) {
    return (
        <div>
            <a href="#" className='begin'>
                <Button className="btn-dark font-monospace">Početak</Button>
            </a>
        </div>
    );
}

export default BeginButton;