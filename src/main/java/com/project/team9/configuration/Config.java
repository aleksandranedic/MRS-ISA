package com.project.team9.configuration;

import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.request.DeleteRequest;
import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.reservation.VacationHouseReservation;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.resource.Boat;
import com.project.team9.model.resource.Resource;
import com.project.team9.model.user.Administrator;
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

import java.time.Month;
import java.util.*;

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
    AdministratorRepository administratorRepository;
    TestData testData;
    private Random random;

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
            DeleteRequestRepository deleteRequestRepository,
            AdministratorRepository administratorRepository,
            TestData testData
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
        this.administratorRepository = administratorRepository;
        this.testData = testData;
        this.random = new Random();
        return args -> {
            fillWithTestData();
        };
    }


    private void fillWithTestData() {


        //-----------------------------------------------------------

        Role roleAdministrator = new Role("ADMINISTRATOR");
        roleRepository.save(roleAdministrator);

        Address a1 = new Address("Novi Sad", "16", "Bulevar Kralja Petra I", "Srbija");
        addressRepository.save(a1);

        Image i1 = new Image("/images/administrators/1/admin1.jpg");
        imageRepository.save(i1);

        Administrator administrator1 = testData.createAdministrator(1L, "Milica", "Todorov", a1, i1, roleAdministrator, true);
        administratorRepository.save(administrator1);


        Address a2 = new Address("Novi Sad", "17", "Bulevar Kralja Petra I", "Srbija");
        addressRepository.save(a2);

        Image i2 = new Image("/images/administrators/2/admin2.jpg");
        imageRepository.save(i2);

        Administrator administrator2 = testData.createAdministrator(2L, "Jovan", "Smiljanski", a2, i2, roleAdministrator, false);
        administratorRepository.save(administrator2);

        Role roleClient = new Role("CLIENT");
        roleRepository.save(roleClient);

        //-------------------------------------------------------------------


        Image i3 = new Image("/images/clients/3/client_3.jpg");
        imageRepository.save(i3);

        Address a3 = new Address("Novi Sad", "3", "Kisačka", "Srbija");
        addressRepository.save(a3);

        Client client3 = testData.createClient(3L, "Verica", "Markov", a3, i3, roleClient);
        clientRepository.save(client3);


        Image i4 = new Image("/images/clients/4/client_4.jpg");
        imageRepository.save(i4);

        Address a4 = new Address("Novi Sad", "4", "Kisačka", "Srbija");
        addressRepository.save(a4);

        Client client4 = testData.createClient(4L, "Jovanka", "Prodanov", a4, i4, roleClient);
        clientRepository.save(client4);


        Image i5 = new Image("/images/clients/5/client_5.jpg");
        imageRepository.save(i5);

        Address a5 = new Address("Novi Sad", "5", "Kisačka", "Srbija");
        addressRepository.save(a5);

        Client client5 = testData.createClient(5L, "Lena", "Sudarski", a5, i5, roleClient);
        clientRepository.save(client5);


        Image i6 = new Image("/images/clients/6/client_6.jpg");
        imageRepository.save(i6);

        Address a6 = new Address("Novi Sad", "6", "Kisačka", "Srbija");
        addressRepository.save(a6);

        Client client6 = testData.createClient(6L, "Adrijana", "Radanov", a6, i6, roleClient);
        clientRepository.save(client6);

        //--------------------------------------------------------------

        Role roleVacationHouseOwner = new Role("VACATION_HOUSE_OWNER");
        roleRepository.save(roleVacationHouseOwner);

        Image i7 = new Image("/images/houseOwners/7/vacation_house_owner_7.jpg");
        imageRepository.save(i7);

        Address a7 = new Address("Novi Sad", "7", "Narodnog fronta", "Srbija");
        addressRepository.save(a7);

        VacationHouseOwner vacationHouseOwner7 = testData.createVacationHouseOwner(7L, "Sreten", "Petrov", a7, i7, roleVacationHouseOwner);
        vacationHouseOwnerRepository.save(vacationHouseOwner7);


        Image i8 = new Image("/images/houseOwners/8/vacation_house_owner_8.jpg");
        imageRepository.save(i8);

        Address a8 = new Address("Novi Sad", "8", "Narodnog fronta", "Srbija");
        addressRepository.save(a8);

        VacationHouseOwner vacationHouseOwner8 = testData.createVacationHouseOwner(8L, "Milena", "Spasojev", a8, i8, roleVacationHouseOwner);
        vacationHouseOwnerRepository.save(vacationHouseOwner8);


        Image i9 = new Image("/images/houseOwners/9/vacation_house_owner_9.jpg");
        imageRepository.save(i9);

        Address a9 = new Address("Novi Sad", "9", "Narodnog fronta", "Srbija");
        addressRepository.save(a9);

        VacationHouseOwner vacationHouseOwner9 = testData.createVacationHouseOwner(9L, "Jovana", "Marin", a9, i9, roleVacationHouseOwner);
        vacationHouseOwnerRepository.save(vacationHouseOwner9);


        Image i10 = new Image("/images/houseOwners/10/vacation_house_owner_10.jpg");
        imageRepository.save(i10);

        Address a10 = new Address("Novi Sad", "10", "Narodnog fronta", "Srbija");
        addressRepository.save(a10);

        VacationHouseOwner vacationHouseOwner10 = testData.createVacationHouseOwner(10L, "Svetlana", "Domovin", a10, i10, roleVacationHouseOwner);
        vacationHouseOwnerRepository.save(vacationHouseOwner10);

        //----------------------

        Role roleBoatOwner = new Role("BOAT_OWNER");
        roleRepository.save(roleBoatOwner);

        Image i11 = new Image("/images/boatOwners/11/boat_owner_11.jpg");
        imageRepository.save(i11);

        Address a11 = new Address("Novi Sad", "11", "Bulevar Evrope", "Srbija");
        addressRepository.save(a11);

        BoatOwner boatOwner11 = testData.createBoatOwner(11L, "Filip", "Jerkov", a11, i11, roleBoatOwner);
        boatOwnerRepository.save(boatOwner11);


        Image i12 = new Image("/images/boatOwners/12/boat_owner_12.jpg");
        imageRepository.save(i12);

        Address a12 = new Address("Novi Sad", "12", "Bulevar Evrope", "Srbija");
        addressRepository.save(a12);

        BoatOwner boatOwner12 = testData.createBoatOwner(12L, "Tomislav", "Antonijev", a12, i12, roleBoatOwner);
        boatOwnerRepository.save(boatOwner12);


        Image i13 = new Image("/images/boatOwners/13/boat_owner_13.jpg");
        imageRepository.save(i13);

        Address a13 = new Address("Novi Sad", "13", "Bulevar Evrope", "Srbija");
        addressRepository.save(a13);

        BoatOwner boatOwner13 = testData.createBoatOwner(13L, "Tatjana", "Antonijev", a13, i13, roleBoatOwner);
        boatOwnerRepository.save(boatOwner13);


        Image i14 = new Image("/images/boatOwners/14/boat_owner_14.jpg");
        imageRepository.save(i14);

        Address a14 = new Address("Novi Sad", "14", "Bulevar Evrope", "Srbija");
        addressRepository.save(a14);

        BoatOwner boatOwner14 = testData.createBoatOwner(14L, "Natalija", "Stankov", a14, i14, roleBoatOwner);
        boatOwnerRepository.save(boatOwner14);

        //----------------------------------------------

        Role roleFishingInstructor = new Role("FISHING_INSTRUCTOR");
        roleRepository.save(roleFishingInstructor);

        Image i15 = new Image("/images/instructors/15/fishing_instructor_15.jpg");
        imageRepository.save(i15);

        Address a15 = new Address("Novi Sad", "15", "Jevrejska", "Srbija");
        addressRepository.save(a15);

        FishingInstructor fishingInstructor15 = testData.createFishingInstructor(15L, "Mirko", "Grujin", "Mnogi smatraju da je pecanje monotono, ali ja sam tu da vam pokažem kako pecanje može biti energično!", a15, i15, roleFishingInstructor);
        fishingInstructorRepository.save(fishingInstructor15);


        Image i16 = new Image("/images/instructors/16/fishing_instructor_16.jpg");
        imageRepository.save(i16);

        Address a16 = new Address("Novi Sad", "16", "Jevrejska", "Srbija");
        addressRepository.save(a16);

        FishingInstructor fishingInstructor16 = testData.createFishingInstructor(16L, "Ena", "Jovin", "Ne odajem tajne, ovo će biti misterioyno novo iskustvo samo za hrabre.", a16, i16, roleFishingInstructor);
        fishingInstructorRepository.save(fishingInstructor16);


        Image i17 = new Image("/images/instructors/17/fishing_instructor_17.jpg");
        imageRepository.save(i17);

        Address a17 = new Address("Novi Sad", "17", "Jevrejska", "Srbija");
        addressRepository.save(a17);

        FishingInstructor fishingInstructor17 = testData.createFishingInstructor(17L, "Ana", "Jarkov", "Samnom je potpuno opuštena atmosfera sa puno zezanja.", a17, i17, roleFishingInstructor);
        fishingInstructorRepository.save(fishingInstructor17);


        Image i18 = new Image("/images/instructors/18/fishing_instructor_18.jpg");
        imageRepository.save(i18);

        Address a18 = new Address("Novi Sad", "18", "Jevrejska", "Srbija");
        addressRepository.save(a18);

        FishingInstructor fishingInstructor18 = testData.createFishingInstructor(18L, "Isidora", "Stamenkov", "Veoma profesialna osoba. Mada su mi neki dali epitet: 'blesava'.", a18, i18, roleFishingInstructor);
        fishingInstructorRepository.save(fishingInstructor18);

        //----------------------------------------------


        List<Tag> fishingEquipment1 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment1);
        List<Tag> additionalServices1 = testData.createTagList(Arrays.asList("Pecanje na brodu", "Dodatna oprema"));
        tagRepository.saveAll(additionalServices1);

        Address ra1 = new Address("Novi Sad", "1", "Mileve Marić", "Srbija");
        addressRepository.save(ra1);

        Pricelist pl1 = new Pricelist(20, new Date());
        pricelistRepository.save(pl1);

        List<Image> images1 = testData.createImageList(Arrays.asList(
                "/images/adventures/1/1.jpg",
                "/images/adventures/1/2.jpg",
                "/images/adventures/1/3.jpg"
        ));
        imageRepository.saveAll(images1);

        Adventure adventure1 = testData.createAdventure(
                1L,
                "Vesela avantura",
                ra1,
                "Zabavna vesela avantura za sve uzraste",
                "Samo bez tužnih lica.",
                pl1,
                0,
                fishingInstructor17,
                12,
                images1,
                fishingEquipment1,
                additionalServices1
        );
        adventureRepository.save(adventure1);

        List<Tag> fishingEquipment2 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment2);
        List<Tag> additionalServices2 = testData.createTagList(Arrays.asList("Pecanje na brodu", "Dodatna oprema"));
        tagRepository.saveAll(additionalServices2);

        Address ra2 = new Address("Novi Sad", "2", "Mileve Marić", "Srbija");
        addressRepository.save(ra2);

        Pricelist pl2 = new Pricelist(20, new Date());
        pricelistRepository.save(pl2);

        List<Image> images2 = testData.createImageList(Arrays.asList(
                "/images/adventures/2/1.jpg",
                "/images/adventures/2/2.jpg",
                "/images/adventures/2/3.jpg"
        ));
        imageRepository.saveAll(images2);

        Adventure adventure2 = testData.createAdventure(
                2L,
                "Opuštena avantura",
                ra2,
                "Avantura koja će učiniti da zaboravite na stres.",
                "Samo bez tužnih lica.",
                pl2,
                0,
                fishingInstructor17,
                12,
                images2,
                fishingEquipment2,
                additionalServices2
        );
        adventureRepository.save(adventure2);

        List<Tag> fishingEquipment3 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment3);
        List<Tag> additionalServices3 = testData.createTagList(Arrays.asList("Pecanje na brodu", "Dodatna oprema"));
        tagRepository.saveAll(additionalServices3);


        Address ra3 = new Address("Novi Sad", "3", "Mileve Marić", "Srbija");
        addressRepository.save(ra3);

        Pricelist pl3 = new Pricelist(20, new Date());
        pricelistRepository.save(pl3);

        List<Image> images3 = testData.createImageList(Arrays.asList(
                "/images/adventures/3/1.jpg",
                "/images/adventures/3/2.jpg",
                "/images/adventures/3/3.jpg"
        ));
        imageRepository.saveAll(images3);

        Adventure adventure3 = testData.createAdventure(
                3L,
                "Mračna avantura",
                ra3,
                "Ako niste pecali noću sada ćete.",
                "Tišina je krucijalna za pecanje u sumrak.",
                pl3,
                0,
                fishingInstructor16,
                12,
                images3,
                fishingEquipment3,
                additionalServices3
        );
        adventureRepository.save(adventure3);


        List<Tag> fishingEquipment4 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment4);
        List<Tag> additionalServices4 = testData.createTagList(Arrays.asList("Pecanje na brodu", "Dodatna oprema"));
        tagRepository.saveAll(additionalServices4);

        Address ra4 = new Address("Novi Sad", "4", "Mileve Marić", "Srbija");
        addressRepository.save(ra4);

        Pricelist pl4 = new Pricelist(20, new Date());
        pricelistRepository.save(pl4);

        List<Image> images4 = testData.createImageList(Arrays.asList(
                "/images/adventures/4/1.jpg",
                "/images/adventures/4/2.jpg",
                "/images/adventures/4/3.jpg"
        ));
        imageRepository.saveAll(images4);

        Adventure adventure4 = testData.createAdventure(
                4L,
                "Brodsko pecanje",
                ra4,
                "Nema boljeg pecanja nego pecanje sa broda.",
                "Obavezno nositi prsluke za spasavanje.",
                pl4,
                0,
                fishingInstructor15,
                12,
                images4,
                fishingEquipment4,
                additionalServices4
        );
        adventureRepository.save(adventure4);

        List<Tag> fishingEquipment5 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment5);
        List<Tag> additionalServices5 = testData.createTagList(Arrays.asList("Pecanje na brodu", "Dodatna oprema"));
        tagRepository.saveAll(additionalServices5);

        Address ra5 = new Address("Novi Sad", "5", "Mileve Marić", "Srbija");
        addressRepository.save(ra5);

        Pricelist pl5 = new Pricelist(20, new Date());
        pricelistRepository.save(pl5);

        List<Image> images5 = testData.createImageList(Arrays.asList(
                "/images/adventures/5/1.jpg",
                "/images/adventures/5/2.jpg",
                "/images/adventures/5/3.jpg"
        ));
        imageRepository.saveAll(images5);

        Adventure adventure5 = testData.createAdventure(
                5L,
                "Klasična avantura",
                ra5,
                "Odlična avantura za početnike",
                "Pažljivo slušati instrukcije.",
                pl5,
                0,
                fishingInstructor18,
                12,
                images5,
                fishingEquipment5,
                additionalServices5
        );
        adventureRepository.save(adventure5);

        List<Tag> fishingEquipment6 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment6);
        List<Tag> additionalServices6 = testData.createTagList(Arrays.asList("Pecanje na brodu", "Dodatna oprema"));
        tagRepository.saveAll(additionalServices6);

        Address ra6 = new Address("Novi Sad", "6", "Mileve Marić", "Srbija");
        addressRepository.save(ra6);

        Pricelist pl6 = new Pricelist(20, new Date());
        pricelistRepository.save(pl6);

        List<Image> images6 = testData.createImageList(Arrays.asList(
                "/images/adventures/6/1.jpg",
                "/images/adventures/6/2.jpg",
                "/images/adventures/6/3.jpg"
        ));
        imageRepository.saveAll(images6);

        Adventure adventure6 = testData.createAdventure(
                6L,
                "Hladna avantura",
                ra6,
                "I ako se uglavnom peca u leto, otkrijte čari pecanja kada je vreme hladnije.",
                "Pažljivo slušati instrukcije.",
                pl6,
                0,
                fishingInstructor15,
                12,
                images6,
                fishingEquipment6,
                additionalServices6
        );
        adventureRepository.save(adventure6);


        //----------------------------------------------

        List<Tag> additionalServices7 = testData.createTagList(Arrays.asList("Švedski sto", "Neograničeno piće"));
        tagRepository.saveAll(additionalServices7);
        List<Tag> navigationEquipment7 = testData.createTagList(Arrays.asList("GPS", "Radar"));
        tagRepository.saveAll(navigationEquipment7);
        List<Tag> fishingEquipment7 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment7);

        Address ra7 = new Address("Novi Sad", "7", "Bulevar Jovana Dučića", "Srbija");
        addressRepository.save(ra7);

        Pricelist pl7 = new Pricelist(195, new Date());
        pricelistRepository.save(pl7);

        List<Image> images7 = testData.createImageList(Arrays.asList(
                "/images/boats/7/1.jpg",
                "/images/boats/7/2.jpg",
                "/images/boats/7/3.jpg"
        ));
        imageRepository.saveAll(images7);

        Boat boat7 = testData.createBoat(
                7L,
                "Luksuzna jahta",
                ra7,
                "Uživajte u luksuznom krstarenju na predivnoj jahti.",
                "Zabranjeno je naginjanje preko palube.",
                pl7,
                20,
                boatOwner11,
                images7,
                additionalServices7,
                navigationEquipment7,
                fishingEquipment7,
                "Jahta",
                50.5,
                "3",
                23,
                115.5,
                72);

        boatRepository.save(boat7);


        List<Tag> additionalServices8 = testData.createTagList(Arrays.asList("Pojasevi za spasavanje", "Čas vožnje"));
        tagRepository.saveAll(additionalServices8);
        List<Tag> navigationEquipment8 = testData.createTagList(Arrays.asList("GPS", "Radar"));
        tagRepository.saveAll(navigationEquipment8);
        List<Tag> fishingEquipment8 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment8);

        Address ra8 = new Address("Novi Sad", "8", "Bulevar Jovana Dučića", "Srbija");
        addressRepository.save(ra8);

        Pricelist pl8 = new Pricelist(75, new Date());
        pricelistRepository.save(pl8);

        List<Image> images8 = testData.createImageList(Arrays.asList(
                "/images/boats/8/1.jpg",
                "/images/boats/8/2.jpg",
                "/images/boats/8/3.jpg"
        ));
        imageRepository.saveAll(images8);

        Boat boat8 = testData.createBoat(
                8L,
                "Brzi gliser",
                ra8,
                "Ako volite brzu vožnju ovo je pravo iskustvo za vas.",
                "Zabranjeno je naginjanje preko palube.",
                pl8,
                20,
                boatOwner12,
                images8,
                additionalServices8,
                navigationEquipment8,
                fishingEquipment8,
                "Gliser",
                15,
                "6",
                89,
                215,
                72);

        boatRepository.save(boat8);


        List<Tag> additionalServices9 = testData.createTagList(Arrays.asList("Švedski sto", "Neograničeno piće", "Skakanje sa broda"));
        tagRepository.saveAll(additionalServices9);
        List<Tag> navigationEquipment9 = testData.createTagList(Arrays.asList("GPS", "Radar"));
        tagRepository.saveAll(navigationEquipment9);
        List<Tag> fishingEquipment9 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment9);

        Address ra9 = new Address("Novi Sad", "9", "Bulevar Jovana Dučića", "Srbija");
        addressRepository.save(ra9);

        Pricelist pl9 = new Pricelist(70, new Date());
        pricelistRepository.save(pl9);

        List<Image> images9 = testData.createImageList(Arrays.asList(
                "/images/boats/9/1.jpg",
                "/images/boats/9/2.jpg",
                "/images/boats/9/3.jpg"
        ));
        imageRepository.saveAll(images9);

        Boat boat9 = testData.createBoat(
                9L,
                "Jahta za kruziranje",
                ra9,
                "Ako ste ikada želeli da skačete u vodu na pučini ova jahta je savršena.",
                "Zabranjeno je skakanje bez nadzora spasioca.",
                pl9,
                20,
                boatOwner13,
                images9,
                additionalServices9,
                navigationEquipment9,
                fishingEquipment9,
                "Jahta",
                20,
                "3",
                23,
                115.5,
                72);

        boatRepository.save(boat9);


        List<Tag> additionalServices10 = testData.createTagList(Arrays.asList("Švedski sto", "Neograničeno piće"));
        tagRepository.saveAll(additionalServices10);
        List<Tag> navigationEquipment10 = testData.createTagList(Arrays.asList("GPS", "Radar"));
        tagRepository.saveAll(navigationEquipment10);
        List<Tag> fishingEquipment10 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment10);

        Address ra10 = new Address("Novi Sad", "10", "Bulevar Jovana Dučića", "Srbija");
        addressRepository.save(ra10);

        Pricelist pl10 = new Pricelist(70, new Date());
        pricelistRepository.save(pl10);

        List<Image> images10 = testData.createImageList(Arrays.asList(
                "/images/boats/10/1.jpg",
                "/images/boats/10/2.jpg",
                "/images/boats/10/3.jpg"
        ));
        imageRepository.saveAll(images10);

        Boat boat10 = testData.createBoat(
                10L,
                "Opuštanje na jahti",
                ra10,
                "Odmor, relaksacija, opuštanje... Dalji opis je nepotreban.",
                "Zabranjeno je skakanje u vodu.",
                pl10,
                20,
                boatOwner14,
                images10,
                additionalServices10,
                navigationEquipment10,
                fishingEquipment10,
                "Jahta",
                20,
                "3",
                23,
                115.5,
                72);

        boatRepository.save(boat10);


        List<Tag> additionalServices11 = testData.createTagList(Arrays.asList("Vožnja broda", "Instruktor vožnje"));
        tagRepository.saveAll(additionalServices11);
        List<Tag> navigationEquipment11 = testData.createTagList(Arrays.asList("GPS", "Radar"));
        tagRepository.saveAll(navigationEquipment11);
        List<Tag> fishingEquipment11 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment11);

        Address ra11 = new Address("Novi Sad", "11", "Bulevar Jovana Dučića", "Srbija");
        addressRepository.save(ra11);

        Pricelist pl11 = new Pricelist(70, new Date());
        pricelistRepository.save(pl11);

        List<Image> images11 = testData.createImageList(Arrays.asList(
                "/images/boats/11/1.jpg",
                "/images/boats/11/2.jpg",
                "/images/boats/11/3.jpg"
        ));
        imageRepository.saveAll(images11);

        Boat boat11 = testData.createBoat(
                11L,
                "Brod za nove kapetane",
                ra11,
                "Okušajte se u vožnji ovog malog broda.",
                "Zabranjeno je skakanje u vodu.",
                pl11,
                20,
                boatOwner13,
                images11,
                additionalServices11,
                navigationEquipment11,
                fishingEquipment11,
                "Brod",
                16,
                "3",
                23,
                115.5,
                72);

        boatRepository.save(boat11);

        List<Tag> additionalServices12 = testData.createTagList(Arrays.asList("Romantična večera", "Flaša vina"));
        tagRepository.saveAll(additionalServices12);
        List<Tag> navigationEquipment12 = testData.createTagList(Arrays.asList("GPS", "Radar"));
        tagRepository.saveAll(navigationEquipment12);
        List<Tag> fishingEquipment12 = testData.createTagList(Arrays.asList("Štap za pecanje", "Udice", "Silk"));
        tagRepository.saveAll(fishingEquipment12);

        Address ra12 = new Address("Novi Sad", "12", "Bulevar Jovana Dučića", "Srbija");
        addressRepository.save(ra12);

        Pricelist pl12 = new Pricelist(70, new Date());
        pricelistRepository.save(pl12);

        List<Image> images12 = testData.createImageList(Arrays.asList(
                "/images/boats/12/1.jpg",
                "/images/boats/12/2.jpg",
                "/images/boats/12/3.jpg"
        ));
        imageRepository.saveAll(images12);

        Boat boat12 = testData.createBoat(
                12L,
                "Romantičan brodić",
                ra12,
                "Izvedite svog partnera partnerku na romantičnu vožnju",
                "Zabranjeno je skakanje u vodu.",
                pl12,
                20,
                boatOwner12,
                images12,
                additionalServices12,
                navigationEquipment12,
                fishingEquipment12,
                "Brod",
                12,
                "3",
                23,
                115.5,
                72);

        boatRepository.save(boat12);


        //-----------------------------------------------------

        List<Tag> additionalServices13 = testData.createTagList(Arrays.asList("WiFi", "Sauna"));
        tagRepository.saveAll(additionalServices13);

        Address ra13 = new Address("Novi Sad", "13", "Laze Nančića", "Srbija");
        addressRepository.save(ra13);

        Pricelist pl13 = new Pricelist(70, new Date());
        pricelistRepository.save(pl13);

        List<Image> images13 = testData.createImageList(Arrays.asList(
                "/images/houses/13/1.jpg",
                "/images/houses/13/2.jpg",
                "/images/houses/13/3.jpg"
        ));
        imageRepository.saveAll(images13);

        VacationHouse vacationHouse13 = testData.createVacationHouse(
                13L,
                "Ušuškana idila",
                ra13,
                "Idealno mesto da odmorite od skijanja pored kamina uz toplu čokoladu.",
                "Poštujte vreme prijavljivanja i odjavljivanja iz vikendice",
                pl13,
                15,
                vacationHouseOwner7,
                4,
                2,
                images13,
                additionalServices13
        );

        vacationHouseRepository.save(vacationHouse13);


        List<Tag> additionalServices14 = testData.createTagList(Arrays.asList("WiFi", "Pogled"));
        tagRepository.saveAll(additionalServices14);

        Address ra14 = new Address("Novi Sad", "14", "Laze Nančića", "Srbija");
        addressRepository.save(ra14);

        Pricelist pl14 = new Pricelist(120, new Date());
        pricelistRepository.save(pl14);

        List<Image> images14 = testData.createImageList(Arrays.asList(
                "/images/houses/14/1.jpg",
                "/images/houses/14/2.jpg",
                "/images/houses/14/3.jpg"
        ));
        imageRepository.saveAll(images14);

        VacationHouse vacationHouse14 = testData.createVacationHouse(
                14L,
                "Vodopadna oaza",
                ra14,
                "Zanimljiva lokacija i nezaborvno iskustvo sedenja dok voda oko vas teče.",
                "Poštujte vreme prijavljivanja i odjavljivanja iz vikendice",
                pl14,
                15,
                vacationHouseOwner8,
                4,
                2,
                images14,
                additionalServices14
        );

        vacationHouseRepository.save(vacationHouse14);


        List<Tag> additionalServices15 = testData.createTagList(Arrays.asList("WiFi", "Bazen", "Klima"));
        tagRepository.saveAll(additionalServices15);

        Address ra15 = new Address("Novi Sad", "15", "Laze Nančića", "Srbija");
        addressRepository.save(ra15);

        Pricelist pl15 = new Pricelist(170, new Date());
        pricelistRepository.save(pl15);

        List<Image> images15 = testData.createImageList(Arrays.asList(
                "/images/houses/15/1.jpg",
                "/images/houses/15/2.jpg",
                "/images/houses/15/3.jpg"
        ));
        imageRepository.saveAll(images15);

        VacationHouse vacationHouse15 = testData.createVacationHouse(
                15L,
                "Definicija odmora",
                ra15,
                "Saznajte šta znači pravo uživanje.",
                "Poštujte vreme prijavljivanja i odjavljivanja iz vikendice",
                pl15,
                30,
                vacationHouseOwner9,
                4,
                2,
                images15,
                additionalServices15
        );

        vacationHouseRepository.save(vacationHouse15);


        List<Tag> additionalServices16 = testData.createTagList(Arrays.asList("WiFi", "Bazen"));
        tagRepository.saveAll(additionalServices16);

        Address ra16 = new Address("Novi Sad", "13", "Laze Nančića", "Srbija");
        addressRepository.save(ra16);

        Pricelist pl16 = new Pricelist(164, new Date());
        pricelistRepository.save(pl16);

        List<Image> images16 = testData.createImageList(Arrays.asList(
                "/images/houses/16/1.jpg",
                "/images/houses/16/2.jpg",
                "/images/houses/16/3.jpg"
        ));
        imageRepository.saveAll(images16);

        VacationHouse vacationHouse16 = testData.createVacationHouse(
                16L,
                "Bela vila",
                ra16,
                "Nova vikendica sa elementima orijentalnog stila.",
                "Poštujte vreme prijavljivanja i odjavljivanja iz vikendice",
                pl16,
                12,
                vacationHouseOwner10,
                4,
                2,
                images16,
                additionalServices16
        );

        vacationHouseRepository.save(vacationHouse16);


        List<Tag> additionalServices17 = testData.createTagList(Arrays.asList("WiFi", "Sauna"));
        tagRepository.saveAll(additionalServices17);

        Address ra17 = new Address("Novi Sad", "17", "Laze Nančića", "Srbija");
        addressRepository.save(ra17);

        Pricelist pl17 = new Pricelist(90, new Date());
        pricelistRepository.save(pl17);

        List<Image> images17 = testData.createImageList(Arrays.asList(
                "/images/houses/17/1.jpg",
                "/images/houses/17/2.jpg",
                "/images/houses/17/3.jpg"
        ));
        imageRepository.saveAll(images17);

        VacationHouse vacationHouse17 = testData.createVacationHouse(
                17L,
                "Spisateljev kutak",
                ra17,
                "Pravo mesto da se izolujete dok pišete svoje novo remek-delo.",
                "Poštujte vreme prijavljivanja i odjavljivanja iz vikendice",
                pl17,
                5,
                vacationHouseOwner7,
                2,
                2,
                images17,
                additionalServices17
        );

        vacationHouseRepository.save(vacationHouse17);

        List<Tag> additionalServices18 = testData.createTagList(Arrays.asList("WiFi", "Sauna"));
        tagRepository.saveAll(additionalServices18);

        Address ra18 = new Address("Novi Sad", "18", "Laze Nančića", "Srbija");
        addressRepository.save(ra18);

        Pricelist pl18 = new Pricelist(70, new Date());
        pricelistRepository.save(pl18);

        List<Image> images18 = testData.createImageList(Arrays.asList(
                "/images/houses/18/1.jpg",
                "/images/houses/18/2.jpg",
                "/images/houses/18/3.jpg"
        ));
        imageRepository.saveAll(images18);

        VacationHouse vacationHouse18 = testData.createVacationHouse(
                18L,
                "Beg u šumu",
                ra18,
                "Ako vam je dosta gradske buke dođite u šumu i zaboravite na stres urbanog života.",
                "Poštujte vreme prijavljivanja i odjavljivanja iz vikendice",
                pl18,
                15,
                vacationHouseOwner9,
                4,
                2,
                images18,
                additionalServices18
        );

        vacationHouseRepository.save(vacationHouse18);

        //----------------------------------------
        List<VacationHouse> houses = Arrays.asList(vacationHouse13, vacationHouse14, vacationHouse15, vacationHouse16, vacationHouse17, vacationHouse18);
        List<Resource> resources = Arrays.asList(adventure1, adventure2, adventure3, adventure4, adventure5, adventure6, boat7, boat8, boat9, boat10);

        List<Client> clients = Arrays.asList(client3, client4, client5, client6);
//        addAdventureAndBoatReservations(resources, clients);
//        addVacationHouseReservations(houses, clients);

        Address a19 = new Address("Novi Sad", "19", "Laze Nančića", "Srbija");
        addressRepository.save(a19);
        RegistrationRequest registrationRequest = testData.createRegistrationRequest(
                "U ponudi ima 3 avanture", "", "aleksa123", "Aleksu", "Aleksić", "aleksa.aleksić@gmail.com", "06398765421", "Novi Sad", "19", "Laze Nančića", "Srbija", "FISHING_INSTRUCTOR", "Živim na lepom plavom Dunavu.", ""
        );
        registrationRequestRepository.save(registrationRequest);
        registrationRequest = testData.createRegistrationRequest(
                "U ponudi ima 2 vikendice", "", "julia123", "Julia", "Annie", "jula.annie@gmail.com", "06398765421", "Novi Sad", "20", "Laze Nančića", "Srbija", "VACATION_HOUSE_OWNER", "Volim da uživam na lepoj vikendici uz čašu vina.", ""
        );
        registrationRequestRepository.save(registrationRequest);

        DeleteRequest deleteRequest=testData.createDeleteRequest("Želim da mi se nalog obriše","","6","CLIENT");
        deleteRequestRepository.delete(deleteRequest);
        deleteRequest=testData.createDeleteRequest("Želim da mi se nalog obriše","","7","VACATION_HOUSE_OWNER");
        deleteRequestRepository.delete(deleteRequest);
    }

    private void addAdventureAndBoatReservations(List<Resource> resources, List<Client> clients) {
        List<Appointment> dayAppointments = new ArrayList<Appointment>();

        for (int i = 1; i < 30; i++) {
            for (int j = 5; j < 20; j++) {
                dayAppointments.add(Appointment.getHourAppointment(2022, 4, i, j, 0));
                dayAppointments.add(Appointment.getHourAppointment(2022, 6, i, j, 0));
            }

        }
        for (int i = 1; i < 31; i++) {
            for (int j = 5; j < 20; j++) {
                dayAppointments.add(Appointment.getHourAppointment(2022, 5, i, j, 0));
                dayAppointments.add(Appointment.getHourAppointment(2022, 7, i, j, 0));
            }
        }

        int numberOfQuickReservations = 0;
        int appointmentIndex = 0;
        int numberOfAppointments;
        int numberOfClients;
        double discount;
        boolean isQuickReservation;
        ArrayList<Appointment> reservationAppointments;

        while (appointmentIndex < dayAppointments.size()) {
            numberOfAppointments = random.nextInt(5) + 2;

            if (numberOfQuickReservations < 35 && dayAppointments.get(appointmentIndex).getStartTime().getMonth() == Month.JULY) {
                discount = (0.4) * random.nextDouble();
            } else {
                discount = 0;
            }

            if (discount == 0) {
                isQuickReservation = false;

            } else {
                isQuickReservation = true;
                numberOfQuickReservations++;
            }

            Resource resource = resources.get(random.nextInt(resources.size()));
            Client client = clients.get(random.nextInt(clients.size()));

            reservationAppointments = new ArrayList<Appointment>();

            for (int i = appointmentIndex; i < appointmentIndex + numberOfAppointments - 1; i++) {
                reservationAppointments.add(dayAppointments.get(i));
            }
            appointmentRepository.saveAll(reservationAppointments);
            if (resource.getClass() == Adventure.class) {
                numberOfClients = random.nextInt(((Adventure) resource).getNumberOfClients() - 1) + 1;

                AdventureReservation r1 = testData.createAdventureReservation(
                        reservationAppointments,
                        numberOfClients,
                        new ArrayList<>(),
                        (int) (resource.getPricelist().getPrice() * numberOfAppointments * (1 - discount)),
                        client,
                        (Adventure) resource,
                        isQuickReservation
                );
                adventureReservationRepository.save(r1);
            } else if (resource.getClass() == Boat.class) {
                numberOfClients = random.nextInt(((Boat) resource).getCapacity() - 1) + 1;
            }


            appointmentIndex += numberOfAppointments;

        }
    }

    private void addVacationHouseReservations(List<VacationHouse> houses, List<Client> clients) {
        List<Appointment> dayAppointments = new ArrayList<Appointment>();

        for (int i = 1; i < 30; i++) {
            dayAppointments.add(Appointment.getDayAppointment(2022, 4, i));
            dayAppointments.add(Appointment.getDayAppointment(2022, 6, i));
        }
        for (int i = 1; i < 31; i++) {
            dayAppointments.add(Appointment.getDayAppointment(2022, 5, i));
            dayAppointments.add(Appointment.getDayAppointment(2022, 7, i));
        }

        int numberOfQuickReservations = 0;
        int appointmentIndex = 0;
        int numberOfAppointments;
        int numberOfClients;
        double discount;
        boolean isQuickReservation;
        ArrayList<Appointment> reservationAppointments;

        while (appointmentIndex < dayAppointments.size()) {
            numberOfAppointments = random.nextInt(5) + 2;

            if (numberOfQuickReservations < 35 && dayAppointments.get(appointmentIndex).getStartTime().getMonth() == Month.JULY) {
                discount = (0.4) * random.nextDouble();
            } else {
                discount = 0;
            }

            if (discount == 0) {
                isQuickReservation = false;

            } else {
                isQuickReservation = true;
                numberOfQuickReservations++;
            }

            VacationHouse house = houses.get(random.nextInt(houses.size()));
            Client client = clients.get(random.nextInt(clients.size()));

            numberOfClients = random.nextInt(house.getNumberOfRooms() * house.getNumberOfBedsPerRoom() - 1) + 1;

            reservationAppointments = new ArrayList<Appointment>();

            for (int i = appointmentIndex; i < appointmentIndex + numberOfAppointments - 1; i++) {
                reservationAppointments.add(dayAppointments.get(i));
            }
            appointmentRepository.saveAll(reservationAppointments);

            VacationHouseReservation r1 = testData.createVacationHouseReservation(
                    reservationAppointments,
                    numberOfClients,
                    new ArrayList<>(),
                    (int) (house.getPricelist().getPrice() * numberOfAppointments * (1 - discount)),
                    client,
                    house,
                    isQuickReservation

            );
            vacationHouseReservationRepository.save(r1);

            if (isQuickReservation) {
                house.addReservation(r1);
                vacationHouseRepository.save(house);
            }

            appointmentIndex += numberOfAppointments;


        }
    }

}
