package com.project.team9.configuration;

import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.reservation.BoatReservation;
import com.project.team9.model.reservation.VacationHouseReservation;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.resource.Boat;
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

import java.sql.Timestamp;
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
            BoatReservationRepository boatReservationRepository
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

        return args -> {
            fillData();
        };
    }

    private void fillData() {
        Client client  = addClient();

        FishingInstructor fishingInstructor =  getFishingInstructor();
        fishingInstructorRepository.save(fishingInstructor);
        Adventure bigAdventure = getBigAdventure(fishingInstructor);
        adventureRepository.save(bigAdventure);
        addAdventureReservations(client, bigAdventure);

        VacationHouseOwner owner = getVacationHouseOwner();
        vacationHouseOwnerRepository.save(owner);
        VacationHouse vacationHouse = getVacationHouse(owner);
        vacationHouseRepository.save(vacationHouse);
        VacationHouseReservation vacationHouseReservation = VacationHouseReservation(vacationHouse);
        vacationHouseReservationRepository.save(vacationHouseReservation);
        vacationHouse.addQuickReservations(vacationHouseReservation);
        vacationHouseRepository.save(vacationHouse);

        BoatOwner boatOwner = getBoatOwner();
        Boat boat = getBoat(boatOwner);
        boatOwner.addBoat(boat);
        boatRepository.save(boat);
        boatOwnerRepository.save(boatOwner);
        BoatReservation boatReservationReservation = getBoatReservation(boat);
        boatReservationRepository.save(boatReservationReservation);
        boat.addQuickReservations(boatReservationReservation);
        boatRepository.save(boat);
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

        adventureReservationRepository.save(reservation1);
    }

        private Client addClient(){
            Role roleClient = new Role("CLIENT");
            roleRepository.save(roleClient);

            Address clientAddress = new Address("Novi Sad", "16", "Puskinova", "Srbija");
            addressRepository.save(clientAddress);
            Client client = new Client(
                    "petar123",
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

        private Boat getBoat (BoatOwner boatOwner){
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

        private BoatOwner getBoatOwner (){
            Role roleBoatOwner = new Role("BOAT_OWNER");
            roleRepository.save(roleBoatOwner);

            Address boatOwnerAddress = new Address("Novi Sad", "21", "Kralja Milutina", "Srbija");
            addressRepository.save(boatOwnerAddress);
            BoatOwner boatOwner = new BoatOwner("1", "Lena", "Leric", "lericlena@gmail.com", "0651525", boatOwnerAddress, false, "registrationRationale", new ArrayList<>(), roleBoatOwner);
            boatOwnerRepository.save(boatOwner);
            return boatOwner;
        }

        private VacationHouse getVacationHouse (VacationHouseOwner owner){
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

        private VacationHouseReservation VacationHouseReservation (VacationHouse vacationHouse){
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


        private BoatReservation getBoatReservation (Boat boat){
            List<Appointment> appointments = new ArrayList<Appointment>();
            appointments.add(Appointment.getVacationHouseAppointment(2022, 9, 9));
            appointments.add(Appointment.getVacationHouseAppointment(2022, 9, 10));
            appointments.add(Appointment.getVacationHouseAppointment(2022, 9, 11));
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

        private VacationHouseOwner getVacationHouseOwner (){

            Role roleVacationHouseOwner = new Role("VACATION_HOUSE_OWNER");
            roleRepository.save(roleVacationHouseOwner);

            Address ownerAddress = new Address("Novi Sad", "21", "Kralja Petra I", "Srbija");
            addressRepository.save(ownerAddress);
            return new VacationHouseOwner(
                    "1",
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
            FishingInstructor fishingInstructor = new FishingInstructor(
                    "peraribar",
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

        private Adventure getBigAdventure(FishingInstructor fishingInstructor){
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
