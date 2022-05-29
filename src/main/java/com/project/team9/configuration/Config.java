package com.project.team9.configuration;

import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.request.DeleteRequest;
import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.reservation.BoatReservation;
import com.project.team9.model.reservation.VacationHouseReservation;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.resource.Boat;
import com.project.team9.model.review.ClientReview;
import com.project.team9.model.user.Client;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.user.Role;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class Config {
    AdventureRepository adventureRepository;
    FishingInstructorRepository fishingInstructorRepository;
    PricelistRepository pricelistRepository;
    AddressRepository addressRepository;
    TagRepository tagRepository;
    AdventureReservationRepository adventureReservationRepository;
    AppointmentRepository appointmentRepository;
    ImageRepository imageRepository;
    ClientRepository clientRepository;
    VacationHouseOwnerRepository vacationHouseOwnerRepository;
    VacationHouseRepository vacationHouseRepository;
    RoleRepository roleRepository;
    BoatOwnerRepository boatOwnerRepository;
    BoatRepository boatRepository;
    VacationHouseReservationRepository vacationHouseReservationRepository;
    BoatReservationRepository boatReservationRepository;
    ReviewRepository reviewRepository;
    RegistrationRequestRepository registrationRequestRepository;
    DeleteRequestRepository deleteRequestRepository;

    @Bean
    CommandLineRunner configureTestData(
            AdventureRepository adventureRepository,
            FishingInstructorRepository fishingInstructorRepository,
            PricelistRepository pricelistRepository,
            AddressRepository addressRepository,
            TagRepository tagRepository,
            AdventureReservationRepository adventureReservationRepository,
            AppointmentRepository appointmentRepository,
            ImageRepository imageRepository,
            ClientRepository clientRepository,
            VacationHouseOwnerRepository vacationHouseOwnerRepository,
            VacationHouseRepository vacationHouseRepository,
            RoleRepository roleRepository,
            BoatOwnerRepository boatOwnerRepository,
            BoatRepository boatRepository,
            VacationHouseReservationRepository vacationHouseReservationRepository,
            BoatReservationRepository boatReservationRepository,
            ReviewRepository reviewRepository,
            RegistrationRequestRepository registrationRequestRepository,
            DeleteRequestRepository deleteRequestRepository
    ) {
        this.adventureRepository = adventureRepository;
        this.fishingInstructorRepository = fishingInstructorRepository;
        this.pricelistRepository = pricelistRepository;
        this.addressRepository = addressRepository;
        this.tagRepository = tagRepository;
        this.adventureReservationRepository = adventureReservationRepository;
        this.appointmentRepository = appointmentRepository;
        this.imageRepository = imageRepository;
        this.clientRepository = clientRepository;
        this.vacationHouseOwnerRepository = vacationHouseOwnerRepository;
        this.vacationHouseRepository = vacationHouseRepository;
        this.roleRepository = roleRepository;
        this.boatOwnerRepository = boatOwnerRepository;
        this.boatRepository = boatRepository;
        this.vacationHouseReservationRepository = vacationHouseReservationRepository;
        this.boatReservationRepository = boatReservationRepository;
        this.reviewRepository = reviewRepository;
        this.registrationRequestRepository = registrationRequestRepository;
        this.deleteRequestRepository = deleteRequestRepository;
        return args -> {
            fillData();
        };
    }

    private void fillData() {
        Client client = addClient();

        FishingInstructor fishingInstructor = getFishingInstructor();
        fishingInstructorRepository.save(fishingInstructor);
        Adventure bigAdventure = getBigAdventure(fishingInstructor);
        adventureRepository.save(bigAdventure);
        addAdventureReservations(client, bigAdventure);

        AdventureReservation adventureQuickReservation = getAdventureQuickReservation(bigAdventure);

        adventureReservationRepository.save(adventureQuickReservation);
        bigAdventure.addQuickReservations(adventureQuickReservation);
        adventureRepository.save(bigAdventure);


        VacationHouseOwner owner = getVacationHouseOwner();
        vacationHouseOwnerRepository.save(owner);
        VacationHouse vacationHouse = getVacationHouse(owner);
        vacationHouseRepository.save(vacationHouse);

        VacationHouseReservation vacationHouseQuickReservation = getVacationHouseQuickReservation(vacationHouse);
        vacationHouseReservationRepository.save(vacationHouseQuickReservation);
        vacationHouse.addReservation(vacationHouseQuickReservation);
        vacationHouseRepository.save(vacationHouse);

        VacationHouseReservation vacationHouseReservation = getVacationHouseReservation(vacationHouse, client);
        vacationHouseReservationRepository.save(vacationHouseReservation);
        vacationHouse.addReservation(vacationHouseReservation);
        vacationHouseRepository.save(vacationHouse);

        addVacationHouseReservation(client, vacationHouse);


        BoatOwner boatOwner = getBoatOwner();
        Boat boat = getBoat(boatOwner);
        boatOwner.addBoat(boat);
        boatRepository.save(boat);
        boatOwnerRepository.save(boatOwner);
        BoatReservation boatQuickReservation = getBoatQuickReservation(boat);
        boatReservationRepository.save(boatQuickReservation);
        boat.addReservation(boatQuickReservation);
        boatRepository.save(boat);

        BoatReservation boatReservation = getBoatReservation(boat, client);
        boatReservationRepository.save(boatReservation);
        boat.addReservation(boatReservation);
        boatRepository.save(boat);

        addBoatReservation(client, boat);


        RegistrationRequest registrationRequest = addRegistrationRequest();
        registrationRequestRepository.save(registrationRequest);

        DeleteRequest deleteRequest = addDeleteRequest();
        deleteRequestRepository.save(deleteRequest);

        ClientReview clientReview = getClientReview(vacationHouse.getId(), client.getId(), 4, "Odlična vikendica, prijatan boravak, malo udaljena od centra.");
        reviewRepository.save(clientReview);
        ClientReview clientReview1 = getClientReview(vacationHouse.getId(), client.getId(), 5, "Čista i sjajna vikendica, sve preporuke!.");
        reviewRepository.save(clientReview1);
        ClientReview clientReview2 = getClientReview(vacationHouse.getId(), client.getId(), 4, "Vikendica je super, gazda takođe. Potrudili su se maksimalno. Stara stolarija.");
        reviewRepository.save(clientReview2);
    }

    private void addBoatReservation(Client client, Boat boat) {
        BoatReservation br = new BoatReservation(5, 100);

        List<Appointment> appointments1 = new ArrayList<Appointment>();
        appointments1.add(Appointment.getBoatAppointment(2022, 6, 5, 12, 0));
        appointments1.add(Appointment.getBoatAppointment(2022, 6, 5, 13, 0));
        appointments1.add(Appointment.getBoatAppointment(2022, 6, 5, 14, 0));
        appointments1.add(Appointment.getBoatAppointment(2022, 6, 5, 15, 0));
        appointmentRepository.saveAll(appointments1);
        br.setAppointments(appointments1);


        List<Tag> additionalServices1 = new ArrayList<Tag>();
        additionalServices1.add(new Tag("Pecanje"));
        tagRepository.saveAll(additionalServices1);
        br.setAdditionalServices(additionalServices1);

        br.setQuickReservation(false);
        br.setBusyPeriod(false);
        br.setResource(boat);
        br.setClient(client);

        boatReservationRepository.save(br);
    }

    private void addVacationHouseReservation(Client client, VacationHouse vacationHouse) {
        VacationHouseReservation vr = new VacationHouseReservation(5, 150);
        List<Appointment> appointments1 = new ArrayList<Appointment>();
        appointments1.add(Appointment.getVacationHouseAppointment(2022, 6, 3));
        appointments1.add(Appointment.getVacationHouseAppointment(2022, 6, 4));
        appointmentRepository.saveAll(appointments1);
        vr.setAppointments(appointments1);


        List<Tag> additionalServices1 = new ArrayList<Tag>();
        additionalServices1.add(new Tag("WiFi"));
        tagRepository.saveAll(additionalServices1);
        vr.setAdditionalServices(additionalServices1);

        vr.setQuickReservation(false);
        vr.setBusyPeriod(false);
        vr.setResource(vacationHouse);
        vr.setClient(client);

        vacationHouseReservationRepository.save(vr);
    }

    private DeleteRequest addDeleteRequest() {
        return new DeleteRequest(
                "Korisnik je los",
                "",
                "1",
                "CLIENT");
    }

    private RegistrationRequest addRegistrationRequest() {
        return new RegistrationRequest(
                "Zelim registraciju",
                "",
                "zika123",
                "Zika",
                "Zikic",
                "zika@gmail.com",
                "0658632153",
                "Novi Sad",
                "16",
                "Puskinova",
                "Srbija",
                "FISHING_INSTRUCTOR",
                "Cool tip",
                ""
        );
    }

    private ClientReview getClientReview(Long resourceID, Long clientID, int rating, String text) {
        return new ClientReview(resourceID, null, rating, text, clientID);
    }

    private void addAdventureReservations(Client client, Adventure adventure) {
        List<Appointment> appointments1 = new ArrayList<Appointment>();
        appointments1.add(Appointment.getAdventureAppointment(2022, 6, 1, 6, 0));
        appointments1.add(Appointment.getAdventureAppointment(2022, 6, 1, 7, 0));
        appointments1.add(Appointment.getAdventureAppointment(2022, 6, 1, 8, 0));
        appointmentRepository.saveAll(appointments1);

        List<Tag> additionalServices1 = new ArrayList<Tag>();
        additionalServices1.add(new Tag("Pecanje na brodu"));
        tagRepository.saveAll(additionalServices1);

        AdventureReservation res = new AdventureReservation(3, 100);
        res.setQuickReservation(false);
        res.setBusyPeriod(false);
        res.setAdditionalServices(additionalServices1);
        res.setAppointments(appointments1);
        res.setResource(adventure);
        res.setClient(client);

        AdventureReservation reservation1 = new AdventureReservation(
                appointments1,
                3,
                additionalServices1,
                100,
                client,
                adventure,
                false,
                false
        );

        adventureReservationRepository.save(res);
        adventure.addQuickReservations(res);
        adventureRepository.save(adventure);
    }

    private Client addClient() {
        Role roleClient = new Role("CLIENT");
        roleRepository.save(roleClient);

        Address clientAddress = new Address("Novi Sad", "16", "Puskinova", "Srbija");
        addressRepository.save(clientAddress);
        Image clientProfileImg = new Image("/images/clients/1/client.jpg");
        imageRepository.save(clientProfileImg);
        Client client = new Client(
                clientProfileImg,
                "$2a$10$SJZLMGQt7FmIuCl.Ea/wXuGX9xd7BOfC5/0BMd95Qke.xG.eQFwfG",//petar123
                "Petar",
                "Peric",
                "perap@gmail.com",
                "0601233215",
                clientAddress,
                false,
                roleClient);
        client.setEnabled(true);
        clientRepository.save(client);
        return client;
    }

    private Boat getBoat(BoatOwner boatOwner) {
        Address boatAddress = new Address("Novi Sad", "7", "Ribarsko ostrvo", "Srbija");
        addressRepository.save(boatAddress);
        Pricelist boatPriceList = new Pricelist(70, new Date());
        pricelistRepository.save(boatPriceList);
        List<Tag> navigationEquipment = new ArrayList<Tag>();
        navigationEquipment.add(new Tag("GPS"));
        navigationEquipment.add(new Tag("Radar"));
        tagRepository.saveAll(navigationEquipment);
        List<Tag> fishingEquipmentBoat = new ArrayList<Tag>();
        fishingEquipmentBoat.add(new Tag("Štap za pecanje"));
        fishingEquipmentBoat.add(new Tag("2 udice"));
        tagRepository.saveAll(fishingEquipmentBoat);
        List<Tag> additionalServicesBoat = new ArrayList<Tag>();
        additionalServicesBoat.add(new Tag("Bazen"));
        additionalServicesBoat.add(new Tag("Ručak"));
        tagRepository.saveAll(additionalServicesBoat);
        Boat boat = new Boat("Bela ladja", boatAddress, "Veliki, beli, lepi brod kao na filmu", "Dozvoljeno unosene hrane, zabranjeno skakanje sa broda", boatPriceList, 12, boatOwner, "Jahta", 50.5, "tri motora", 23, 115.5, navigationEquipment, fishingEquipmentBoat, additionalServicesBoat, 72);

        Image boatImg1 = new Image("/images/boats/3/boat1.jpg");
        Image boatImg2 = new Image("/images/boats/3/boat2.jpg");
        Image boatImg3 = new Image("/images/boats/3/boat3.jpg");
        imageRepository.save(boatImg1);
        imageRepository.save(boatImg2);
        imageRepository.save(boatImg3);
        boat.addImage(boatImg1);
        boat.addImage(boatImg2);
        boat.addImage(boatImg3);
        return boat;
    }

    private BoatOwner getBoatOwner() {
        Role roleBoatOwner = new Role("BOAT_OWNER");
        roleRepository.save(roleBoatOwner);

        Address boatOwnerAddress = new Address("Novi Sad", "21", "Kralja Milutina", "Srbija");
        addressRepository.save(boatOwnerAddress);
        Image boatProfileImg = new Image("/images/boatOwners/4/boatOwner.jpg");
        imageRepository.save(boatProfileImg);
        BoatOwner boatOwner1 = new BoatOwner(boatProfileImg, "1", "Lena", "Leric", "lericlena@gmail.com", "0651525", boatOwnerAddress, false, "registrationRationale", new ArrayList<>(), roleBoatOwner);
        BoatOwner boatOwner = new BoatOwner(
                boatProfileImg,
                "$2a$10$btgxT6xNRyIqn8wf3Lo48OWmcwegTOlzKk9UL38nq/SUXA/v0WTL6", //1
                "Lena",
                "Leric",
                "lericlena@gmail.com",
                "0651525",
                boatOwnerAddress,
                false,
                "registrationRationale",
                new ArrayList<>(), roleBoatOwner);
        boatOwnerRepository.save(boatOwner);
        return boatOwner;
    }

    private VacationHouse getVacationHouse(VacationHouseOwner owner) {
        Address houseAddress = new Address("Novi Sad", "7", "Braće Krkljuš", "Srbija");
        addressRepository.save(houseAddress);
        Pricelist housePriceList = new Pricelist(100, new Date());
        pricelistRepository.save(housePriceList);
        VacationHouse vacationHouse = new VacationHouse("Lepa Brena", houseAddress, "lepa, velika, zuta zgrada blizu centra", "Dozvoljeno slušanje Čole, zyabranjeno smaranje", housePriceList, 10, owner, 5, 3);
        owner.addVacationHouse(vacationHouse);
        Image vhImg1 = new Image("/images/houses/2/vikendica1.jpeg");
        Image vhImg2 = new Image("/images/houses/2/vikendica2.jpeg");
        Image vhImg3 = new Image("/images/houses/2/vikendica3.jpeg");
        ArrayList<Tag> additionalServices = new ArrayList<Tag>();
        additionalServices.add(new Tag("Bazen"));
        additionalServices.add(new Tag("Pet-friendly"));
        additionalServices.add(new Tag("WiFi"));
        additionalServices.add(new Tag("Parking"));
        tagRepository.saveAll(additionalServices);
        imageRepository.save(vhImg1);
        imageRepository.save(vhImg2);
        imageRepository.save(vhImg3);
        vacationHouse.addImage(vhImg1);
        vacationHouse.addImage(vhImg2);
        vacationHouse.addImage(vhImg3);
        vacationHouse.setAdditionalServices(additionalServices);
        return vacationHouse;
    }

    private VacationHouseReservation getVacationHouseQuickReservation(VacationHouse vacationHouse) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.add(Appointment.getVacationHouseAppointment(2022, 4, 9));
        appointments.add(Appointment.getVacationHouseAppointment(2022, 4, 10));
        appointments.add(Appointment.getVacationHouseAppointment(2022, 4, 11));
        appointments.add(Appointment.getVacationHouseAppointment(2022, 4, 12));
        appointmentRepository.saveAll(appointments);

        ArrayList<Tag> additionalServices = new ArrayList<Tag>();
        additionalServices.add(new Tag("Bazen"));
        additionalServices.add(new Tag("Pet-friendly"));
        additionalServices.add(new Tag("WiFi"));
        tagRepository.saveAll(additionalServices);

        VacationHouseReservation vr = new VacationHouseReservation(7, 30);
        vr.setAdditionalServices(additionalServices);
        vr.setAppointments(appointments);
        vr.setResource(vacationHouse);
        vr.setClient(null);
        vr.setQuickReservation(true);
        return vr;
    }

    private VacationHouseReservation getVacationHouseReservation(VacationHouse vacationHouse, Client client) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.add(Appointment.getVacationHouseAppointment(2022, 5, 19));
        appointments.add(Appointment.getVacationHouseAppointment(2022, 5, 20));
        appointments.add(Appointment.getVacationHouseAppointment(2022, 5, 21));
        appointments.add(Appointment.getVacationHouseAppointment(2022, 5, 22));
        appointments.add(Appointment.getVacationHouseAppointment(2022, 5, 23));
        appointments.add(Appointment.getVacationHouseAppointment(2022, 5, 24));
        appointmentRepository.saveAll(appointments);

        ArrayList<Tag> additionalServices = new ArrayList<Tag>();
        additionalServices.add(new Tag("Plazma TV"));
        additionalServices.add(new Tag("WiFi"));
        tagRepository.saveAll(additionalServices);

        VacationHouseReservation vr = new VacationHouseReservation(7, 30);
        vr.setAdditionalServices(additionalServices);
        vr.setAppointments(appointments);
        vr.setResource(vacationHouse);
        vr.setClient(client);
        vr.setQuickReservation(false);
        vr.setBusyPeriod(false);
        return vr;
    }

    private AdventureReservation getAdventureQuickReservation(Adventure adventure) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.add(Appointment.getAdventureAppointment(2022, 6, 6, 15, 15));
        appointments.add(Appointment.getAdventureAppointment(2022, 6, 6, 16, 15));
        appointmentRepository.saveAll(appointments);

        ArrayList<Tag> additionalServices = new ArrayList<Tag>();
        additionalServices.add(new Tag("Minibar"));
        tagRepository.saveAll(additionalServices);

        AdventureReservation adv = new AdventureReservation(3, 60);
        adv.setQuickReservation(true);

        adv.setAdditionalServices(additionalServices);
        adv.setAppointments(appointments);
        adv.setResource(adventure);
        adv.setClient(null);
        return adv;
    }

    private BoatReservation getBoatQuickReservation(Boat boat) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.add(Appointment.getBoatAppointment(2022, 9, 9, 15, 15));
        appointments.add(Appointment.getBoatAppointment(2022, 9, 9, 15, 45));
        appointments.add(Appointment.getBoatAppointment(2022, 9, 9, 16, 15));
        appointmentRepository.saveAll(appointments);

        ArrayList<Tag> additionalServices = new ArrayList<Tag>();
        additionalServices.add(new Tag("Minibar"));
        tagRepository.saveAll(additionalServices);

        BoatReservation br = new BoatReservation(3, 60);
        br.setQuickReservation(true);

        br.setAdditionalServices(additionalServices);
        br.setAppointments(appointments);
        br.setResource(boat);
        br.setClient(null);
        return br;
    }

    private BoatReservation getBoatReservation(Boat boat, Client client) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.add(Appointment.getBoatAppointment(2022, 10, 22, 8, 15));
        appointments.add(Appointment.getBoatAppointment(2022, 10, 9, 9, 45));
        appointmentRepository.saveAll(appointments);

        ArrayList<Tag> additionalServices = new ArrayList<Tag>();
        additionalServices.add(new Tag("Bilijar"));
        tagRepository.saveAll(additionalServices);

        BoatReservation br = new BoatReservation(3, 60);
        br.setQuickReservation(false);
        br.setBusyPeriod(false);

        br.setAdditionalServices(additionalServices);
        br.setAppointments(appointments);
        br.setResource(boat);
        br.setClient(client);
        return br;
    }

    private VacationHouseOwner getVacationHouseOwner() {

        Role roleVacationHouseOwner = new Role("VACATION_HOUSE_OWNER");
        roleRepository.save(roleVacationHouseOwner);

        Address ownerAddress = new Address("Novi Sad", "21", "Kralja Petra I", "Srbija");
        addressRepository.save(ownerAddress);
        Image houseProfileImg = new Image("/images/houseOwners/3/houseOwner.jpg");
        imageRepository.save(houseProfileImg);
        return new VacationHouseOwner(
                houseProfileImg,
                "$2a$10$41FbB.1FsCwJZ5O8ex.hiODT6SQNVORd4usEqMGZ2mNYxjFP/HErG",//11
                "Pera",
                "Peric",
                "pericpera@gmail.com",
                "0600651",
                ownerAddress,
                false,
                "registrationRationale",
                roleVacationHouseOwner);
    }

    private FishingInstructor getFishingInstructor() {
        Role roleFishingInstructor = new Role("FISHING_INSTRUCTOR");
        roleRepository.save(roleFishingInstructor);

        Address fishingInstructorAddress = new Address("Novi Sad", "23", "Bulevar Cara Lazara", "Srbija");
        addressRepository.save(fishingInstructorAddress);
        Image instructorProfileImg = new Image("/images/instructors/2/instructor.jpg");
        imageRepository.save(instructorProfileImg);
        FishingInstructor fishingInstructor = new FishingInstructor(
                instructorProfileImg,
                "$2a$10$GApodrEZn/6BZEgOMsKV8uiCWsoGgFbERoLs1w.VsPFij9YZsQJKa",//""peraribar",
                "Petar",
                "Jovanovic",
                "petar.jovanovic@email.com",
                "0601233215",
                fishingInstructorAddress,
                false,
                "Imam zavrseni pecaroski fakultet.",
                "Jos sam bio savim mlad, neke barske ptice sam lovio tad, kad je dosla da se kupa lepa protina kci.",
                roleFishingInstructor,
                new ArrayList<Adventure>()
        );

        return fishingInstructor;

    }

    private Adventure getBigAdventure(FishingInstructor fishingInstructor) {
        Address adventureAddress = new Address("Novi Sad", "52a", "Dunav", "Srbija");
        addressRepository.save(adventureAddress);

        Pricelist adventurePricelist = new Pricelist(75, new Date());
        pricelistRepository.save(adventurePricelist);

        ArrayList<Tag> fishingEquipment = new ArrayList<Tag>();
        fishingEquipment.add(new Tag("Stap za pecanje marke BestFishing rod"));
        fishingEquipment.add(new Tag("3 udice"));

        tagRepository.saveAll(fishingEquipment);

        Image image1 = new Image("/images/adventures/1/fishing1.jpg");
        Image image2 = new Image("/images/adventures/1/fishing2.jpg");
        Image image3 = new Image("/images/adventures/1/fishing3.jpg");

        imageRepository.save(image1);
        imageRepository.save(image2);
        imageRepository.save(image3);


        Adventure bigAdventure = new Adventure(
                "Velika ribarska avantura",
                adventureAddress,
                "Ovo je jedna zahtevna i izazovna avanutura u kojoj pecamo samo najvece sarane i somove.",
                "Obavezno se pojavite na vreme, a vreme je uvek rano.",
                adventurePricelist,
                10,
                fishingInstructor,
                2

        );

        bigAdventure.setFishingEquipment(fishingEquipment);

        bigAdventure.addImage(image1);
        bigAdventure.addImage(image2);
        bigAdventure.addImage(image3);

        return bigAdventure;
    }
}
