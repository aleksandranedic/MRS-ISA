import React from 'react';

function ProfileInfo({icon, label, text}) {
    return (
        <div >
            <p className="lead fw-normal m-0 p-0">
                {React.createElement(icon, {'viewBox': '0 0 19 19'})}
                {label}
            </p>
            <p className="fw-light mb-4">{text}</p>
        </div>
    );
}

export default ProfileInfo;