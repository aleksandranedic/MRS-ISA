import {toast} from "react-toastify";

export const missingDataErrors = {
    firstName: "Morate uneti ime.",
    lastName: "Morate uneti prezime.",
    phoneNumber: "Morate uneti broj telefona.",
    address: {
        "street": "Morate uneti ulicu.",
        "number": "Morate uneti broj objekta.",
        "place": "Morate uneti mesto.",
        "country": "Morate uneti državu."
    },
    title: "Morate uneti naslov.",
    description: "Morate uneti opis.",
    numberOfClients: "Morate uneti broj klijenata",
    price: "Morate uneti cenu.",
    cancellationFee: "Morate uneti naknadu za otkazivanje.",
    rulesAndRegulations: "Morate uneti pravila ponašanja."
}

export const frontLink = "http://localhost:3000/"
export const backLink = "http://localhost:4444"
export const profilePicturePlaceholder = "http://localhost:3000/images/profilePicturePlaceholder.jpg"
export const locationPlaceholder = {iconUrl:"http://localhost:3000/images/location.png", iconSize:[38,38]}
export const NominatimBaseUrl = "https://nominatim.openstreetmap.org/search?"

export const responsive = {
    superLargeDesktop: {
        breakpoint: {max: 4000, min: 3000},
        items: 5
    },
    desktop: {
        breakpoint: {max: 3000, min: 1400},
        items: 4
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
export const notifySuccess = (message) => {
    toast.success(message, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
    });
}
export const notifyError = (message) => {
    toast.error(message, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
    });
}
export const loadingToast = () => {
    let id=toast.loading("Podaci se obrađuju...");
    return id;
}
export const updateForFetchedDataSuccess = (message, id) => {
    toast.update(id, {render: message, type: "success", isLoading: false, autoClose: 5000});
}

export const updateForFetchedDataError = (message, id) => {
    toast.update(id, {render: message, type: "error", isLoading: false, autoClose: 5000});
}

export function isLoggedIn() {
    return localStorage.getItem('token') !== null && localStorage.getItem('token') !== "";
}

export const logOut = () => {
    localStorage.setItem('token', "");
    window.location.reload();
}