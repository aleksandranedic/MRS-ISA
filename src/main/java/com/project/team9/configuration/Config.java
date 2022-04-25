package com.project.team9.configuration;

import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.user.Client;
import com.project.team9.model.user.Client;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.model.user.vendor.RegistrationType;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
public class Config {
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
            UserAccountRepository userAccountRepository
    ) {
        return args -> {
            Address fishingInstructorAddress = new Address("Novi Sad", "23", "Bulevar Cara Lazara", "Srbija");
            addressRepository.save(fishingInstructorAddress);
            FishingInstructor fishingInstructor = new FishingInstructor(
                    "peraribar",
                    "Petar",
                    "Jovanovic",
                    "petar.jovanovic@email.com",
                    "0601233215",
                    fishingInstructorAddress,
                    RegistrationType.FishingInstructor,
                    "Imam zavrseni pecaroski fakultet.",
                    "Jos sam bio savim mlad, neke barske ptice sam lovio tad, kad je dosla da se kupa lepa protina kci."
            );

            Address adventureAddress = new Address("Novi Sad", "52a", "Dunav", "Srbija");
            addressRepository.save(adventureAddress);

            Pricelist adventurePricelist = new Pricelist(75, new Date());
            pricelistRepository.save(adventurePricelist);
            
            ArrayList<Tag> fishingEquipment =new ArrayList<Tag>();
            fishingEquipment.add(new Tag("Stap za pecanje marke BestFishing rod"));
            fishingEquipment.add(new Tag("250g crva"));
            fishingEquipment.add(new Tag("3 udice"));

            tagRepository.saveAll(fishingEquipment);

            Image image1 = new Image("./images/fishing1.jpg");
            Image image2 = new Image("./images/fishing2.jpg");
            Image image3 = new Image("./images/fishing3.jpg");

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
                    2,
                    fishingEquipment
            );

            bigAdventure.addImage(image1);
            bigAdventure.addImage(image2);
            bigAdventure.addImage(image3);

            fishingInstructor.addAdventure(bigAdventure);
            fishingInstructorRepository.save(fishingInstructor);
            adventureRepository.save(bigAdventure);

            Address ownerAddress =  new Address("Novi Sad","21","Kralja Petra I","Srbija");
            addressRepository.save(ownerAddress);
            VacationHouseOwner owner = new VacationHouseOwner("1", "Pera", "Peric", "pericpera@gmail.com", "0600651", ownerAddress, RegistrationType.VacationHouseOwner ,"registrationRationale");
            vacationHouseOwnerRepository.save(owner);
            Address houseAddress = new Address("Novi Sad","7","Braće Krkljuš","Srbija");
            addressRepository.save(houseAddress);
            Pricelist housePriceList = new Pricelist(100, new Date());
            pricelistRepository.save(housePriceList);
            VacationHouse vacationHouse = new VacationHouse("Lepa Brena", houseAddress, "lepa, velika, zuta zgrada blizu centra", "Dozvoljeno slušanje Čole, zyabranjeno smaranje",housePriceList,10,owner,5,3);
            owner.addVacationHouse(vacationHouse);
            Image vhImg1 = new Image("./images/vikendica1.jpeg");
            Image vhImg2 = new Image("./images/vikendica2.jpeg");
            Image vhImg3 = new Image("./images/vikendica3.jpeg");
            ArrayList<Tag> additionalServices =new ArrayList<Tag>();
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
            vacationHouseRepository.save(vacationHouse);

            Address clientAddress=new Address("Novi Sad", "16", "Puskinova", "Srbija");
            addressRepository.save(clientAddress);
            Client client = new Client(
                    "petar123",
                    "Petar",
                    "Peric",
                    "perap@gmail.com",
                    "0601233215",
                    clientAddress
            );
            clientRepository.save(client);
        };
    }
}
