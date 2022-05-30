import {boatOwner, client, fishingInstructor, vacationHouseOwner} from "./Consts";

export function isLoggedIn() {
    return localStorage.getItem('token') !== null && localStorage.getItem('token') !== "";
}

export function isMyPage(role, id) {
    let myPage;

    if (isLoggedIn()) {
        let userRole = localStorage.getItem("userRoleName");
        let userId = localStorage.getItem("userId");
        myPage = userRole === role && userId.toString() === id.toString();
    }
    return myPage;
}

export function isClient() {
    return localStorage.getItem("userRoleName") === client;
}

export function isVacationHouseOwner() {
    return localStorage.getItem("userRoleName") === vacationHouseOwner;
}

export function isBoatOwner() {
    return localStorage.getItem("userRoleName") === boatOwner;
}

export function isFishingInstructor() {
    return localStorage.getItem("userRoleName") === fishingInstructor;
}

