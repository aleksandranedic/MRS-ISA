package com.project.team9.service;

import com.project.team9.dto.AdventureDTO;
import com.project.team9.dto.AdventureQuickReservationDTO;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.repo.AdventureRepository;
import com.project.team9.repo.AdventureReservationRepository;
import com.project.team9.repo.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AdventureService {
    private final AdventureRepository repository;
    private final FishingInstructorService fishingInstructorService;
    private final TagService tagService;
    private final AddressService addressService;
    private final PricelistService pricelistService;
    private final ImageService imageService;
    private final AdventureReservationRepository reservationRepository;
    private final AppointmentRepository appointmentRepository;

    final String IMAGES_PATH = "/images/adventures/";

    @Autowired
    public AdventureService(AdventureRepository adventureRepository, FishingInstructorService fishingInstructorService, TagService tagService, AddressService addressService, PricelistService pricelistService, ImageService imageService, AdventureReservationRepository reservationRepository, AppointmentRepository appointmentRepository) {
        this.repository = adventureRepository;
        this.fishingInstructorService = fishingInstructorService;
        this.tagService = tagService;
        this.addressService = addressService;
        this.pricelistService = pricelistService;
        this.imageService = imageService;
        this.reservationRepository = reservationRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<AdventureQuickReservationDTO> getQuickReservations(String id) {
        Adventure adv = this.getById(id);
        return getQuickReservations(adv);
    }

    private List<AdventureQuickReservationDTO> getQuickReservations(Adventure adv){
        List<AdventureQuickReservationDTO> quickReservations = new ArrayList<AdventureQuickReservationDTO>();
        for (AdventureReservation reservation :  adv.getQuickReservations()){
            if (reservation.getPrice() < adv.getPricelist().getPrice() && reservation.getClient() == null)
                quickReservations.add(createAdventureReservationDTO(adv.getPricelist().getPrice(), reservation));
        }
        return quickReservations;
    }

    public Boolean addQuickReservation(String id, AdventureQuickReservationDTO quickReservationDTO){
        Adventure adventure = this.getById(id);
        AdventureReservation reservation = getReservationFromDTO(quickReservationDTO);
        reservation.setResource(adventure);
        reservationRepository.save(reservation);
        adventure.addQuickReservations(reservation);
        this.addAdventure(adventure);
        return true;
    }

    private AdventureReservation getReservationFromDTO(AdventureQuickReservationDTO dto){
        List<Appointment> appointments = new ArrayList<Appointment>();
        String[] splitDate = dto.getStartDate().split(" ");
        String[] splitTime = splitDate[3].split(":");
        Appointment startDateAppointment = Appointment.getBoatAppointment(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]), Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
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
        AdventureReservation reservation = new AdventureReservation(dto.getNumberOfPeople(), dto.getPrice());
        reservation.setClient(null);
        reservation.setAdditionalServices(tags);
        reservation.setAppointments(appointments);
        reservation.setQuickReservation(true);
        return reservation;
    }

    public Boolean updateQuickReservation(String id, AdventureQuickReservationDTO quickReservationDTO){
        Adventure adventure = this.getById(id);
        AdventureReservation newReservation = getReservationFromDTO(quickReservationDTO);
        AdventureReservation originalReservation = reservationRepository.getById(quickReservationDTO.getReservationID());
        updateQuickReservation(originalReservation, newReservation);
        reservationRepository.save(originalReservation);
        this.addAdventure(adventure);
        return true;
    }
    private void updateQuickReservation(AdventureReservation originalReservation, AdventureReservation newReservation){
        originalReservation.setAppointments(newReservation.getAppointments());
        originalReservation.setAdditionalServices(newReservation.getAdditionalServices());
        originalReservation.setNumberOfClients(newReservation.getNumberOfClients());
        originalReservation.setPrice(newReservation.getPrice());
    }

    private AdventureQuickReservationDTO createAdventureReservationDTO(int boatPrice, AdventureReservation reservation){
        Appointment firstAppointment = getFirstAppointment(reservation.getAppointments());
        LocalDateTime startDate = firstAppointment.getStartTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm'h'");
        String strDate = startDate.format(formatter);
        int numberOfPeople = reservation.getNumberOfClients();
        List<Tag> additionalServices = reservation.getAdditionalServices();
        int duration = reservation.getAppointments().size();
        int price = reservation.getPrice();
        int discount = 100 - ( 100 * price / boatPrice);
        return new AdventureQuickReservationDTO(reservation.getId(), strDate, numberOfPeople, additionalServices, duration, price, discount);
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




    public List<Adventure> getAdventures() {
        return repository.findAll();
    }

    public void addAdventure(Adventure adventure) {
        repository.save(adventure);
    }

    public Adventure getById(String id) {
        return repository.getById(Long.parseLong(id));
    }

    public AdventureDTO getDTOById(String id) {
        return new AdventureDTO(repository.getById(Long.parseLong(id)));
    }

    public void deleteById(String id) {
        repository.deleteById(Long.parseLong(id));
    }

    private void updateAdventure(Adventure oldAdventure, Adventure newAdventure) {
        oldAdventure.setTitle(newAdventure.getTitle());
        oldAdventure.setAddress(newAdventure.getAddress());
        oldAdventure.setDescription(newAdventure.getDescription());
        oldAdventure.setImages(newAdventure.getImages());
        oldAdventure.setAdditionalServices(newAdventure.getAdditionalServices());
        oldAdventure.setRulesAndRegulations(newAdventure.getRulesAndRegulations());
        oldAdventure.setPricelist(newAdventure.getPricelist());
        oldAdventure.setCancellationFee(newAdventure.getCancellationFee());
        oldAdventure.setOwner(newAdventure.getOwner());
        oldAdventure.setNumberOfClients(newAdventure.getNumberOfClients());
        oldAdventure.setFishingEquipment(newAdventure.getFishingEquipment());
    }

    public List<Adventure> findAdventuresWithOwner(String ownerId){
        return repository.findAdventuresWithOwner(Long.parseLong(ownerId));
    }

    public Long createAdventure(AdventureDTO adventure, MultipartFile[] multipartFiles) throws IOException {

        Adventure newAdventure = createAdventureFromDTO(adventure);
        repository.save(newAdventure);
        addImagesToAdventure(multipartFiles, newAdventure);
        return newAdventure.getId();
    }

    private Adventure createAdventureFromDTO(AdventureDTO dto) {
        Pricelist pricelist = new Pricelist(dto.getPrice(), new Date());
        pricelistService.addPriceList(pricelist);

        Address address = new Address(dto.getPlace(), dto.getNumber(),dto.getStreet() , dto.getCountry());
        addressService.addAddress(address);

        FishingInstructor owner = fishingInstructorService.getById(dto.getOwnerId().toString());

        Adventure adventure = new Adventure(
                dto.getTitle(),
                address,
                dto.getDescription(),
                dto.getRulesAndRegulations(),
                pricelist,
                dto.getCancellationFee(),
                owner,
                dto.getNumberOfClients()
        );

        for (String text: dto.getAdditionalServicesText()) {
            Tag tag = new Tag(text);
            tagService.addTag(tag);
            adventure.addAdditionalService(tag);
        }

        for (String text: dto.getFishingEquipmentText()) {
            Tag tag = new Tag(text);
            tagService.addTag(tag);
            adventure.addFishingEquipment(tag);
        }

        List<Image> images = getExistingImages(dto);
        adventure.setImages(images);

        return adventure;
    }

    private List<Image> getExistingImages(AdventureDTO dto) {
        List<Image> images = new ArrayList<Image>();
        if (dto.getImagePaths() != null){
            for (String path : dto.getImagePaths()) {
                Optional<Image> optImage = imageService.getImageByPath(path);
                optImage.ifPresent(images::add);
            }
        }
        return images;
    }

    public Adventure editAdventure(String id, AdventureDTO dto, MultipartFile[] multipartFiles) throws IOException {
        Adventure originalAdventure = getById(id);
        updateAdventure(originalAdventure, createAdventureFromDTO(dto));
        repository.save(originalAdventure);
        addImagesToAdventure(multipartFiles, originalAdventure);
        return originalAdventure;
    }

    private void addImagesToAdventure(MultipartFile[] multipartFiles, Adventure adventure) throws IOException {
        List<String> paths = imageService.saveImages(adventure.getId(), multipartFiles, IMAGES_PATH, adventure.getImages());
        List<Image> images = imageService.getImages(paths);
        adventure.setImages(images);
        repository.save(adventure);
    }
}
