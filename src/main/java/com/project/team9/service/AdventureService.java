package com.project.team9.service;

import com.project.team9.dto.*;
import com.project.team9.exceptions.ReservationNotAvailableException;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.user.Client;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.repo.AdventureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
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
    private final AppointmentService appointmentService;
    private final ClientService clientService;
    private final AdventureReservationService adventureReservationService;
    private final ReservationService reservationService;
    private final ReviewService reviewService;
    private final ReviewRequestService reviewRequestService;

    final String IMAGES_PATH = "/images/adventures/";

    @Autowired
    public AdventureService(AdventureRepository adventureRepository, FishingInstructorService fishingInstructorService, TagService tagService, AddressService addressService, PricelistService pricelistService, ImageService imageService, AppointmentService appointmentService, ClientService clientService, AdventureReservationService adventureReservationService, ReservationService reservationService, ReviewService reviewService, ReviewRequestService reviewRequestService) {
        this.repository = adventureRepository;
        this.fishingInstructorService = fishingInstructorService;
        this.tagService = tagService;
        this.addressService = addressService;
        this.pricelistService = pricelistService;
        this.imageService = imageService;
        this.appointmentService = appointmentService;
        this.clientService = clientService;
        this.adventureReservationService = adventureReservationService;
        this.reservationService = reservationService;
        this.reviewService = reviewService;
        this.reviewRequestService = reviewRequestService;
    }

    public List<AdventureQuickReservationDTO> getQuickReservations(String id) {
        Adventure adv = this.getById(id);
        return getQuickReservations(adv);
    }

    private List<AdventureQuickReservationDTO> getQuickReservations(Adventure adv) {
        List<AdventureQuickReservationDTO> quickReservations = new ArrayList<AdventureQuickReservationDTO>();
        for (AdventureReservation reservation : adv.getQuickReservations()) {
            if (reservation.isQuickReservation())
                quickReservations.add(createAdventureReservationDTO(adv.getPricelist().getPrice(), reservation));
        }
        return quickReservations;
    }

    public Boolean addQuickReservation(String id, AdventureQuickReservationDTO quickReservationDTO) {
        Adventure adventure = this.getById(id);
        AdventureReservation reservation = getReservationFromDTO(quickReservationDTO);
        reservation.setResource(adventure);
        adventureReservationService.save(reservation);
        adventure.addQuickReservations(reservation);
        this.addAdventure(adventure);
        return true;
    }

    private AdventureReservation getReservationFromDTO(AdventureQuickReservationDTO dto) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        String[] splitDate = dto.getStartDate().split(" ");
        String[] splitTime = splitDate[3].split(":");
        Appointment startDateAppointment = Appointment.getBoatAppointment(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]), Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
        appointments.add(startDateAppointment);
        appointmentService.save(startDateAppointment);
        Appointment currApp = startDateAppointment;
        for (int i = 0; i < dto.getDuration() - 1; i++) {
            LocalDateTime startDate = currApp.getEndTime();
            LocalDateTime endDate = startDate.plusDays(1);
            currApp = new Appointment(startDate, endDate);
            appointmentService.save(currApp);
            appointments.add(currApp);
        }
        List<Tag> tags = new ArrayList<Tag>();
        for (String tagText : dto.getTagsText()) {
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

    public Boolean updateQuickReservation(String id, AdventureQuickReservationDTO quickReservationDTO) {
        Adventure adventure = this.getById(id);
        AdventureReservation newReservation = getReservationFromDTO(quickReservationDTO);
        AdventureReservation originalReservation = adventureReservationService.getById(quickReservationDTO.getReservationID());
        updateQuickReservation(originalReservation, newReservation);
        adventureReservationService.save(originalReservation);
        this.addAdventure(adventure);
        return true;
    }

    private void updateQuickReservation(AdventureReservation originalReservation, AdventureReservation newReservation) {
        originalReservation.setAppointments(newReservation.getAppointments());
        originalReservation.setAdditionalServices(newReservation.getAdditionalServices());
        originalReservation.setNumberOfClients(newReservation.getNumberOfClients());
        originalReservation.setPrice(newReservation.getPrice());
    }

    private AdventureQuickReservationDTO createAdventureReservationDTO(int boatPrice, AdventureReservation reservation) {
        Appointment firstAppointment = getFirstAppointment(reservation.getAppointments());
        LocalDateTime startDate = firstAppointment.getStartTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm'h'");
        String strDate = startDate.format(formatter);
        int numberOfPeople = reservation.getNumberOfClients();
        List<Tag> additionalServices = reservation.getAdditionalServices();
        int duration = reservation.getAppointments().size();
        int price = reservation.getPrice();
        int discount = 100 - (100 * price / boatPrice);
        return new AdventureQuickReservationDTO(reservation.getId(), strDate, numberOfPeople, additionalServices, duration, price, discount);
    }

    private Appointment getFirstAppointment(List<Appointment> appointments) {
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

    private Adventure updateAdventure(Adventure oldAdventure, Adventure newAdventure) {
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
        return oldAdventure;
    }

    public List<Adventure> findAdventuresWithOwner(String ownerId) {
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

        Address address = new Address(dto.getPlace(), dto.getNumber(), dto.getStreet(), dto.getCountry());
        addressService.addAddress(address);

        FishingInstructor owner = fishingInstructorService.getById(dto.getOwnerId().toString());

        Adventure adventure = new Adventure(dto.getTitle(), address, dto.getDescription(), dto.getRulesAndRegulations(), pricelist, dto.getCancellationFee(), owner, dto.getNumberOfClients());

        for (String text : dto.getAdditionalServicesText()) {
            Tag tag = new Tag(text);
            tagService.addTag(tag);
            adventure.addAdditionalService(tag);
        }

        for (String text : dto.getFishingEquipmentText()) {
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
        if (dto.getImagePaths() != null) {
            for (String path : dto.getImagePaths()) {
                Optional<Image> optImage = imageService.getImageByPath(path);
                optImage.ifPresent(images::add);
            }
        }
        return images;
    }

    public Adventure editAdventure(String id, AdventureDTO dto, MultipartFile[] multipartFiles) throws IOException {
        Adventure adventure = createAdventureFromDTO(dto);
        adventure.setId(Long.parseLong(id));
        addImagesToAdventure(multipartFiles, adventure);
        repository.save(adventure);

        return adventure;
    }

    private void addImagesToAdventure(MultipartFile[] multipartFiles, Adventure adventure) throws IOException {
        List<String> paths = imageService.saveImages(adventure.getId(), multipartFiles, IMAGES_PATH, adventure.getImages());
        List<Image> images = imageService.getImages(paths);
        adventure.setImages(images);
        repository.save(adventure);
    }

    public List<ReservationDTO> getReservationsForAdventure(Long id) {
        List<ReservationDTO> reservations = new ArrayList<ReservationDTO>();

        for (AdventureReservation ar : adventureReservationService.getStandardReservations()) {
            if (Objects.equals(ar.getResource().getId(), id)) {
                reservations.add(createDTOFromReservation(ar));

            }
        }


        return reservations;
    }

    private ReservationDTO createDTOFromReservation(AdventureReservation reservation) {
        return new ReservationDTO(reservation.getAppointments(), reservation.getNumberOfClients(), reservation.getAdditionalServices(), reservation.getPrice(), reservation.getClient(), reservation.getResource().getTitle(), reservation.isBusyPeriod(), reservation.isQuickReservation(), reservation.getResource().getId(), reservation.getId());
    }

    public List<ReservationDTO> getReservationsForFishingInstructor(Long id) {
        List<ReservationDTO> reservations = new ArrayList<ReservationDTO>();

        for (Adventure a : this.findAdventuresWithOwner(id.toString())) {
            for (AdventureReservation ar : adventureReservationService.getStandardReservations()) {
                if (Objects.equals(ar.getResource().getId(), a.getId())) {
                    reservations.add(createDTOFromReservation(ar));
                }
            }
        }

        return reservations;
    }

    public List<ReservationDTO> getReservationsForClient(Long id) {

        List<ReservationDTO> reservations = new ArrayList<ReservationDTO>();

        for (AdventureReservation ar : adventureReservationService.getStandardReservations()) {
            if (Objects.equals(ar.getClient().getId(), id)) {
                reservations.add(createDTOFromReservation(ar));
            }
        }

        return reservations;
    }

    public Long createReservation(NewReservationDTO dto) throws ReservationNotAvailableException {
        AdventureReservation reservation = createFromDTO(dto);

        List<AdventureReservation> reservations = adventureReservationService.getPossibleCollisionReservations(reservation.getResource().getId(), reservation.getResource().getOwner().getId());
        for (AdventureReservation r : reservations) {
            for (Appointment a : r.getAppointments()) {
                for (Appointment newAppointment : reservation.getAppointments()) {
                    reservationService.checkAppointmentCollision(a, newAppointment);
                }
            }
        }

        adventureReservationService.save(reservation);
        return reservation.getId();
    }

    private AdventureReservation createFromDTO(NewReservationDTO dto) {

        List<Appointment> appointments = new ArrayList<Appointment>();

        LocalDateTime startTime = LocalDateTime.of(dto.getStartYear(), Month.of(dto.getStartMonth()), dto.getStartDay(), dto.getStartHour(), dto.getStartMinute());
        LocalDateTime endTime = startTime.plusHours(1);

        LocalDateTime finalTime = LocalDateTime.of(dto.getEndYear(), Month.of(dto.getEndMonth()), dto.getEndDay(), dto.getEndHour(), dto.getEndMinute());

        while (endTime.isBefore(finalTime)) {
            appointments.add(new Appointment(startTime, endTime));
            startTime = endTime;
            endTime = startTime.plusHours(1);
        }

        appointments.add(new Appointment(startTime, finalTime));
        appointmentService.saveAll(appointments);

        Client client = clientService.getById(dto.getClientId().toString());
        String id = dto.getResourceId().toString();
        Adventure adventure = this.getById(id);

        int price = adventure.getPricelist().getPrice() * appointments.size();

        List<Tag> tags = new ArrayList<Tag>();
        for (String text : dto.getAdditionalServicesStrings()) {
            Tag tag = new Tag(text);
            tags.add(tag);
        }

        tagService.saveAll(tags);

        return new AdventureReservation(appointments, dto.getNumberOfClients(), tags, price, client, adventure, dto.isBusyPeriod(), dto.isQuickReservation());
    }

    public List<ReservationDTO> getBusyPeriodsForAdventure(Long id) {
        List<ReservationDTO> periods = new ArrayList<ReservationDTO>();

        Adventure adventure = getById(id.toString());

        for (AdventureReservation ar : adventureReservationService.getBusyPeriodsForAdventure(id, adventure.getOwner().getId())) {
            periods.add(createDTOFromReservation(ar));
        }

        return periods;
    }

    public List<ReservationDTO> getBusyPeriodsForFishingInstructor(Long id) {
        List<ReservationDTO> periods = new ArrayList<ReservationDTO>();

        for (AdventureReservation ar : adventureReservationService.getBusyPeriodsForFishingInstructor(id)) {
            periods.add(createDTOFromReservation(ar));
        }

        return periods;
    }

    public Long createBusyPeriod(NewBusyPeriodDTO dto) throws ReservationNotAvailableException {

        AdventureReservation reservation = createBusyPeriodReservationFromDTO(dto);

        List<AdventureReservation> reservations = adventureReservationService.getPossibleCollisionReservations(reservation.getResource().getId(), reservation.getResource().getOwner().getId());
        for (AdventureReservation r : reservations) {
            for (Appointment a : r.getAppointments()) {
                for (Appointment newAppointment : reservation.getAppointments()) {
                    reservationService.checkAppointmentCollision(a, newAppointment);
                }
            }
        }

        adventureReservationService.save(reservation);
        return reservation.getId();
    }

    private AdventureReservation createBusyPeriodReservationFromDTO(NewBusyPeriodDTO dto) {
        List<Appointment> appointments = new ArrayList<Appointment>();

        LocalDateTime startTime = LocalDateTime.of(dto.getStartYear(), Month.of(dto.getStartMonth()), dto.getStartDay(), 0, 0);
        LocalDateTime endTime = startTime.plusDays(1);

        while (startTime.isBefore(LocalDateTime.of(dto.getEndYear(), Month.of(dto.getEndMonth()), dto.getEndDay(), 23, 59))) {
            appointments.add(new Appointment(startTime, endTime));
            startTime = endTime;
            endTime = startTime.plusHours(1);
        }
        appointmentService.saveAll(appointments);

        String id = dto.getResourceId().toString();
        Adventure adventure = this.getById(id);

        return new AdventureReservation(appointments, 0, null, 0, null, adventure, true, false

        );
    }

    public boolean clientCanReview(Long resourceId, Long clientId) {
        return hasReservations(resourceId, clientId);
    }

    public boolean hasReservations(Long resourceId, Long clientId) {
        return adventureReservationService.clientHasReservations(resourceId, clientId);
    }

    public List<ReservationDTO> getReservationsForReview(Long id) {
        List<ReservationDTO> reservations = new ArrayList<ReservationDTO>();
        for (AdventureReservation r : adventureReservationService.getStandardReservations()) {
            if (!reviewService.reservationHasReview(r.getId())) {
                if (Objects.equals(r.getResource().getOwner().getId(), id)) {
                    int index = r.getAppointments().size() - 1;
                    LocalDateTime time = r.getAppointments().get(index).getEndTime();
                    if (time.isBefore(LocalDateTime.now())) {
                        reservations.add(createDTOFromReservation(r));
                    }

                }
            }


        }
        return reservations;
    }


    public Long reserveQuickReservation(AdventureQuickReservationDTO dto) {
        AdventureReservation quickReservation = adventureReservationService.getById(dto.getReservationID());
        Client client = clientService.getById(dto.getClientID().toString());
        quickReservation.getResource().removeQuickReservation(quickReservation);
        quickReservation.setClient(client);
        quickReservation.setQuickReservation(false);
        return adventureReservationService.save(quickReservation);
    }

    public boolean clientCanReviewVendor(Long vendorId, Long clientId) {
        return adventureReservationService.clientHasReservationsWithVendor(vendorId, clientId);
    }
}
