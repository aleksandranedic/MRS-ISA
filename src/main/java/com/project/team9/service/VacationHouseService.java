package com.project.team9.service;

import com.project.team9.dto.HouseCardDTO;
import com.project.team9.dto.ReviewScoresDTO;
import com.project.team9.dto.VacationHouseDTO;
import com.project.team9.dto.VacationHouseQuickReservationDTO;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.reservation.VacationHouseReservation;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.repo.AppointmentRepository;
import com.project.team9.repo.VacationHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VacationHouseService {

    private final VacationHouseRepository repository;
    private final AddressService addressService;
    private final PricelistService pricelistService;
    private final TagService tagService;
    private final ImageService imageService;
    private final VacationHouseReservationService reservationService;
    private final ReviewService reviewService;
    private final AppointmentRepository appointmentRepository;

    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/houses/";

    @Autowired
    public VacationHouseService(VacationHouseRepository vacationHouseRepository, AddressService addressService, PricelistService pricelistService, TagService tagService, ImageService imageService, VacationHouseReservationService reservationService, ReviewService reviewService, AppointmentRepository appointmentRepository) {
        this.repository = vacationHouseRepository;
        this.addressService = addressService;
        this.pricelistService = pricelistService;
        this.tagService = tagService;
        this.imageService = imageService;
        this.reservationService = reservationService;
        this.reviewService = reviewService;
        this.appointmentRepository = appointmentRepository;
    }

    public List<VacationHouse> getVacationHouses() {
        return repository.findAll();
    }

    public HouseCardDTO getVacationHouseCard(Long id){
        VacationHouse vh = getVacationHouse(id);
        String address = vh.getAddress().getStreet() + " " + vh.getAddress().getNumber() + ", " + vh.getAddress().getPlace() + ", " + vh.getAddress().getCountry();
        return new HouseCardDTO(vh.getId(), vh.getImages().get(0).getPath(), vh.getTitle(), vh.getDescription(), address);
    }
    public List<HouseCardDTO> getOwnerHouses(Long owner_id) {
        List<VacationHouse> houses = repository.findByOwnerId(owner_id);
        List<HouseCardDTO> houseCards = new ArrayList<HouseCardDTO>();
        for(VacationHouse house : houses){
            String address = house.getAddress().getStreet() + " " + house.getAddress().getNumber() + ", " + house.getAddress().getPlace() + ", " + house.getAddress().getCountry();
            String thumbnail = "./images/housenotext.png";
            if (house.getImages().size() > 0){
                thumbnail = house.getImages().get(0).getPath();
            }
            houseCards.add(new HouseCardDTO(house.getId(), thumbnail, house.getTitle(), house.getDescription(), address));
        }
        return houseCards;
    }

    public VacationHouse getVacationHouse(Long id) {
        return repository.getById(id);
    }

    public double getRatingForHouse(Long id){
        ReviewScoresDTO reviews = reviewService.getReviewScores(id);
        double sum = reviews.getFiveStars() * 5 + reviews.getFourStars() * 4 + reviews.getThreeStars() * 3 + reviews.getTwoStars() * 2 + reviews.getOneStars();
        double num = reviews.getFiveStars()  + reviews.getFourStars() + reviews.getThreeStars() + reviews.getTwoStars() + reviews.getOneStars();
        double result = sum / num;
        double scale = Math.pow(10, 1);
        return Math.round(result * scale) / scale;
    }
    public VacationHouseDTO getVacationHouseDTO(Long id) {
        VacationHouse vh = repository.getById(id);
        String address = vh.getAddress().getStreet() + " " + vh.getAddress().getNumber() + ", " + vh.getAddress().getPlace()  + ", " + vh.getAddress().getCountry();
        List<String> images = new ArrayList<String>();
        for (Image img : vh.getImages()) {
            images.add(img.getPath());
        }
        int capacity = vh.getNumberOfBedsPerRoom() * vh.getNumberOfRooms();
        List<VacationHouseQuickReservationDTO> quickReservations = getQuickReservations(vh);
        return new VacationHouseDTO(vh.getId(), vh.getTitle(), address, vh.getAddress().getNumber(), vh.getAddress().getStreet(), vh.getAddress().getPlace(), vh.getAddress().getCountry(), vh.getDescription(), images, vh.getRulesAndRegulations(), vh.getAdditionalServices(), vh.getPricelist().getPrice(), vh.getCancellationFee(), vh.getNumberOfRooms(), capacity, quickReservations);
    }

    private List<VacationHouseQuickReservationDTO> getQuickReservations(VacationHouse vh){
        List<VacationHouseQuickReservationDTO> quickReservations = new ArrayList<VacationHouseQuickReservationDTO>();
        for (VacationHouseReservation reservation :  vh.getQuickReservations()){
            if (reservation.getPrice() < vh.getPricelist().getPrice() && reservation.getClient() == null)
                quickReservations.add(createVacationHouseQuickReservationDTO(vh.getPricelist().getPrice(), reservation));
        }
        return quickReservations;
    }

    private VacationHouseQuickReservationDTO createVacationHouseQuickReservationDTO(int vacationHousePrice, VacationHouseReservation reservation){
        Appointment firstAppointment = getFirstAppointment(reservation.getAppointments());
        LocalDateTime startDate = firstAppointment.getStartTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm'h'");
        String strDate = startDate.format(formatter);
        int numberOfPeople = reservation.getNumberOfClients();
        List<Tag> additionalServices = reservation.getAdditionalServices();
        int duration = reservation.getAppointments().size();
        int price = reservation.getPrice();
        int discount = 100 - ( 100 * price / vacationHousePrice);
        return new VacationHouseQuickReservationDTO(reservation.getId(), strDate, numberOfPeople, additionalServices, duration, price, discount);
    }
    private Appointment getFirstAppointment(List<Appointment> appointments){
        List<Appointment> sortedAppointments = getSortedAppointments(appointments);
        return sortedAppointments.get(0);
    }

    private List<Appointment> getSortedAppointments(List<Appointment> appointments) {
        Collections.sort(appointments, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment a1, Appointment a2) {
                return a1.getStartTime().compareTo(a2.getStartTime());
            }
        });
        return appointments;
    }

    public Boolean addQuickReservation(Long id, VacationHouseQuickReservationDTO quickReservationDTO){
        VacationHouse house = this.getVacationHouse(id);
        VacationHouseReservation reservation = getReservationFromDTO(quickReservationDTO);
        reservation.setResource(house);
        reservationService.addReservation(reservation);
        house.addQuickReservations(reservation);
        this.save(house);
        return true;
    }

    public Boolean updateQuickReservation(Long id, VacationHouseQuickReservationDTO quickReservationDTO){
        VacationHouse house = this.getVacationHouse(id);
        VacationHouseReservation newReservation = getReservationFromDTO(quickReservationDTO);
        VacationHouseReservation originalReservation = reservationService.getVacationHouseReservation(quickReservationDTO.getReservationID());
        updateQuickReservation(originalReservation, newReservation);
        reservationService.addReservation(originalReservation);
        this.save(house);
        return true;
    }

    public Boolean deleteQuickReservation(Long id, VacationHouseQuickReservationDTO quickReservationDTO){
        VacationHouse house = this.getVacationHouse(id);
        reservationService.deleteById(quickReservationDTO.getReservationID());
        //izbaci se reservation iz house
        this.save(house);
        return true;
    }
    private VacationHouseReservation getReservationFromDTO(VacationHouseQuickReservationDTO dto){
        List<Appointment> appointments = new ArrayList<Appointment>();
        String[] splitDate = dto.getStartDate().split(" ");
        Appointment startDateAppointment = Appointment.getVacationHouseAppointment(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]));
        appointments.add(startDateAppointment);
        appointmentRepository.save(startDateAppointment);
        Appointment currApp = startDateAppointment;
        for (int i=0; i < dto.getDuration() - 1; i++){
            LocalDateTime startDate = currApp.getEndTime();
            LocalDateTime endDate = startDate.plusDays(1);
            currApp = new Appointment(startDate, endDate);
            appointmentRepository.save(currApp);
            appointments.add(currApp);
        }
        List<Tag> tags = new ArrayList<Tag>();
        for (String tagText : dto.getTagsText()){
            Tag tag = new Tag(tagText);
            tagService.addTag(tag);
            tags.add(tag);
        }
        VacationHouseReservation reservation = new VacationHouseReservation(dto.getNumberOfPeople(), dto.getPrice());
        reservation.setClient(null);
        reservation.setAdditionalServices(tags);
        reservation.setAppointments(appointments);
        return reservation;
    }
    public void addVacationHouses(VacationHouse house) {
        repository.save(house);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Long createHouse(VacationHouseDTO house, MultipartFile[] multipartFiles) throws IOException {
        VacationHouse vh = getHouseFromDTO(house);
        this.save(vh);
        List<String> paths = saveImages(vh, multipartFiles);
        List<Image> images = getImages(paths);
        vh.setImages(images);
        this.save(vh);
        return vh.getId();
    }


    public VacationHouseDTO updateHouse(String id, VacationHouseDTO house, MultipartFile[] multipartFiles) throws IOException {
        VacationHouse originalHouse = this.getVacationHouse(Long.parseLong(id));
        VacationHouse newVacationHouse = getHouseFromDTO(house);
        updateVacationHouse(originalHouse, newVacationHouse);
        this.save(originalHouse);
        List<String> paths = saveImages(originalHouse, multipartFiles);
        List<Image> images = getImages(paths);
        originalHouse.setImages(images);
        this.save(originalHouse);
        return this.getVacationHouseDTO(originalHouse.getId());
    }

    private List<Image> getImages(List<String> paths) {
        List<Image> images = new ArrayList<Image>();
        for (String path : paths) {
            Optional<Image> optImg = imageService.getImageByPath(path);
            Image img;
            img = optImg.orElseGet(() -> new Image(path));
            imageService.save(img);
            images.add(img);
        }
        return images;
    }

    private void updateVacationHouse(VacationHouse originalHouse, VacationHouse newVacationHouse) {
        originalHouse.setTitle(newVacationHouse.getTitle());
        originalHouse.setPricelist(newVacationHouse.getPricelist());
        originalHouse.setDescription(newVacationHouse.getDescription());
        originalHouse.setNumberOfRooms(newVacationHouse.getNumberOfRooms());
        originalHouse.setNumberOfBedsPerRoom(newVacationHouse.getNumberOfBedsPerRoom());
        originalHouse.setRulesAndRegulations(newVacationHouse.getRulesAndRegulations());
        originalHouse.setAddress(newVacationHouse.getAddress());
        originalHouse.setAdditionalServices(newVacationHouse.getAdditionalServices());
        originalHouse.setCancellationFee(newVacationHouse.getCancellationFee());
        originalHouse.setImages(newVacationHouse.getImages());
    }
    private void updateQuickReservation(VacationHouseReservation originalReservation, VacationHouseReservation newReservation){
        originalReservation.setAppointments(newReservation.getAppointments());
        originalReservation.setAdditionalServices(newReservation.getAdditionalServices());
        originalReservation.setNumberOfClients(newReservation.getNumberOfClients());
        originalReservation.setPrice(newReservation.getPrice());
    }
    private List<String> saveImages(VacationHouse vh, MultipartFile[] multipartFiles) throws IOException {
        List<String> paths = new ArrayList<>();
        if (multipartFiles == null) {
            return paths;
        }
        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + vh.getId());
        Path path_target = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + vh.getId());
        savePicturesOnPath(vh, multipartFiles, paths, path);
        savePicturesOnPath(vh, multipartFiles, paths, path_target);
        if (vh.getImages() != null && vh.getImages().size() > 0){
            for (Image image : vh.getImages()) {
                paths.add(image.getPath());
            }
        }
        return paths.stream().distinct().collect(Collectors.toList());
    }

    private void savePicturesOnPath(VacationHouse vh, MultipartFile[] multipartFiles, List<String> paths, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        for (MultipartFile mpf : multipartFiles) {
            String fileName = mpf.getOriginalFilename();
            try (InputStream inputStream = mpf.getInputStream()) {
                Path filePath = path.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                paths.add(IMAGES_PATH + vh.getId() + "/" + fileName);
            } catch (DirectoryNotEmptyException dnee) {
                continue;
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
    }

    private VacationHouse getHouseFromDTO(VacationHouseDTO house) {
        Pricelist pl = new Pricelist(house.getPrice(), new Date());
        pricelistService.addPriceList(pl);
        int numberOfBedsPerRoom = house.getCapacity() / house.getNumberOfRooms();
        Address adr = new Address(house.getCity(), house.getNumber(), house.getStreet(), house.getCountry());
        addressService.addAddress(adr);
        VacationHouse vh = new VacationHouse(house.getName(), adr, house.getDescription(), house.getRulesAndRegulations(), pl, house.getCancellationFee(), null, house.getNumberOfRooms(), numberOfBedsPerRoom);
        List<Tag> tags = new ArrayList<Tag>();
        for (String tagText : house.getTagsText()){
            Tag tag = new Tag(tagText);
            tagService.addTag(tag);
            tags.add(tag);
        }
        vh.setAdditionalServices(tags);
        List<Image> images = new ArrayList<Image>();
        if (house.getImagePaths() != null){
            for (String path : house.getImagePaths()) {
                Optional<Image> optImage = imageService.getImageByPath(path);
                optImage.ifPresent(images::add);
            }
        }
        vh.setImages(images);
        return vh;
    }
    public VacationHouse save(VacationHouse house) {
        return repository.save(house);
    }
}