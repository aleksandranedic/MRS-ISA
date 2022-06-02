package com.project.team9.service;

import com.project.team9.dto.LoginDTO;
import com.project.team9.dto.ReservationDTO;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.reservation.*;
import com.project.team9.model.user.Client;
import com.project.team9.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/clients/";
    private final ClientRepository clientRepository;
    private final ImageService imageService;
    private final AdventureReservationService adventureReservationService;
    private final BoatReservationService boatReservationService;
    private final VacationHouseReservationService vacationHouseReservationService ;

    @Autowired
    public ClientService(ClientRepository clientRepository, ImageService imageService, AdventureReservationService adventureReservationService, BoatReservationService boatReservationService, VacationHouseReservationService vacationHouseReservationService) {
        this.clientRepository = clientRepository;
        this.imageService = imageService;
        this.adventureReservationService = adventureReservationService;
        this.boatReservationService = boatReservationService;
        this.vacationHouseReservationService = vacationHouseReservationService;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getById(String id) {
        return clientRepository.getById(Long.parseLong(id));
    }

    public void deleteById(Long id) {
        clientRepository.findById(id).ifPresent(client -> {
            client.setDeleted(true);
            addClient(client);
        });

    }

    public Boolean changeProfilePicture(String id, MultipartFile multipartFile) throws IOException {
        try{
            Client client = this.getById(id);
            String path = saveImage(client, multipartFile);
            Image image = getImage(path);
            client.setProfileImg(image);
            this.addClient(client);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private Image getImage(String path) {
        Image image;
        Optional<Image> optImg = imageService.getImageByPath(path);
        image = optImg.orElseGet(() -> new Image(path));
        imageService.save(image);
        return image;
    }

    private String saveImage(Client client, MultipartFile multipartFile) throws IOException {
        String pathStr = "";
        if (multipartFile == null) {
            return pathStr;
        }
        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + client.getId());
        Path path_target = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + client.getId());
        savePictureOnPath(client, multipartFile, pathStr, path);
        pathStr = savePictureOnPath(client, multipartFile, pathStr, path_target);
        return pathStr;
    }

    private String savePictureOnPath(Client client, MultipartFile mpf, String pathStr, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        String fileName = mpf.getOriginalFilename();
        try (InputStream inputStream = mpf.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            pathStr = IMAGES_PATH + client.getId() + "/" + fileName;
        } catch (DirectoryNotEmptyException dnee) {
            throw new IOException("Directory Not Empty Exception: " + fileName, dnee);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
        return pathStr;
    }

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public Client getClientByEmailAndPassword(LoginDTO loginDTO){
        for (Client client :
                clientRepository.findAll()) {
            if(client.getPassword().equals(loginDTO.getPassword()) && client.getEmail().equals(loginDTO.getUsername())){
                return client;
            }
        }
        return null;
    }
    public Client getClientByEmail(String email) {return clientRepository.findByEmail(email);}
    public List<ReservationDTO> getReservations(Long id) {
        List<ReservationDTO> reservations = new ArrayList<>();
        for (AdventureReservation reservation: adventureReservationService.getAdventureReservationsForClientId(id)) {
            reservations.add(new ReservationDTO(reservation.getAppointments(), reservation.getNumberOfClients(), reservation.getAdditionalServices(), reservation.getPrice(), reservation.getClient(), reservation.getResource().getTitle(), reservation.isBusyPeriod(), reservation.isQuickReservation(), reservation.getResource().getId()));
        }
        for (BoatReservation reservation: boatReservationService.getBoatReservationsForClientId(id)) {
            reservations.add(new ReservationDTO(reservation.getAppointments(), reservation.getNumberOfClients(), reservation.getAdditionalServices(), reservation.getPrice(), reservation.getClient(), reservation.getResource().getTitle(), reservation.isBusyPeriod(), reservation.isQuickReservation(), reservation.getResource().getId()));
        }
        for (VacationHouseReservation reservation: vacationHouseReservationService.getVacationHouseReservationsForClienId(id)) {
            reservations.add(new ReservationDTO(reservation.getAppointments(), reservation.getNumberOfClients(), reservation.getAdditionalServices(), reservation.getPrice(), reservation.getClient(), reservation.getResource().getTitle(), reservation.isBusyPeriod(), reservation.isQuickReservation(), reservation.getResource().getId()));
        }
        return reservations;
    }
}
