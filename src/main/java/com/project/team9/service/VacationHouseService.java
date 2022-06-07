package com.project.team9.service;

import com.project.team9.dto.*;
import com.project.team9.exceptions.ReservationNotAvailableException;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.reservation.BoatReservation;
import com.project.team9.model.reservation.VacationHouseReservation;
import com.project.team9.model.resource.Boat;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.user.Client;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.VacationHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VacationHouseService {
    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/houses/";

    private final VacationHouseRepository repository;
    private final AddressService addressService;
    private final PricelistService pricelistService;
    private final TagService tagService;
    private final ImageService imageService;
    private final VacationHouseReservationService vacationHouseReservationService;
    private final ClientReviewService clientReviewService;
    private final AppointmentService appointmentService;
    private final ClientService clientService;
    private final ReservationService reservationService;


    @Autowired
    public VacationHouseService(VacationHouseRepository vacationHouseRepository, AddressService addressService, PricelistService pricelistService, TagService tagService, ImageService imageService, VacationHouseReservationService vacationHouseReservationService, ClientReviewService clientReviewService, AppointmentService appointmentService, ClientService clientService, ReservationService reservationService) {
        this.repository = vacationHouseRepository;
        this.addressService = addressService;
        this.pricelistService = pricelistService;
        this.tagService = tagService;
        this.imageService = imageService;
        this.vacationHouseReservationService = vacationHouseReservationService;
        this.clientReviewService = clientReviewService;
        this.appointmentService = appointmentService;
        this.clientService = clientService;
        this.reservationService = reservationService;
    }

    public List<VacationHouse> getVacationHouses() {
        return repository.findAll();
    }

    public HouseCardDTO getVacationHouseCard(Long id) {
        VacationHouse vh = getVacationHouse(id);
        String address = vh.getAddress().getStreet() + " " + vh.getAddress().getNumber() + ", " + vh.getAddress().getPlace() + ", " + vh.getAddress().getCountry();
        return new HouseCardDTO(vh.getId(), vh.getImages().get(0).getPath(), vh.getTitle(), vh.getDescription(), address);
    }

    public List<HouseCardDTO> getOwnerHouses(Long owner_id) {
        List<VacationHouse> houses = repository.findByOwnerId(owner_id);
        List<HouseCardDTO> houseCards = new ArrayList<HouseCardDTO>();
        for (VacationHouse house : houses) {
            String address = house.getAddress().getStreet() + " " + house.getAddress().getNumber() + ", " + house.getAddress().getPlace() + ", " + house.getAddress().getCountry();
            String thumbnail = "./images/housenotext.png";
            if (house.getImages().size() > 0) {
                thumbnail = house.getImages().get(0).getPath();
            }
            houseCards.add(new HouseCardDTO(house.getId(), thumbnail, house.getTitle(), house.getDescription(), address));
        }
        return houseCards;
    }

    public List<VacationHouse> getOwnersHouses(Long owner_id){
        return repository.findByOwnerId(owner_id);
    }
    public List<VacationHouseReservation> getHouseReservations(Long house_id){
        return vacationHouseReservationService.getReservationsByVacationHouseId(house_id);
    }

    public List<ResourceReportDTO> getOwnerResources(Long owner_id) {
        List<VacationHouse> houses = repository.findByOwnerId(owner_id);
        List<ResourceReportDTO> resources = new ArrayList<ResourceReportDTO>();
        for (VacationHouse resource : houses) {
            Image img = resource.getImages().get(0);
            resources.add(new ResourceReportDTO(resource.getId(), resource.getTitle(), img, reviewService.getRating(resource.getId(), "resource")));
        }
        return resources;
    }

    public VacationHouse getVacationHouse(Long id) {
        return repository.getById(id);
    }

    public double getRatingForHouse(Long id) {
        ReviewScoresDTO reviews = clientReviewService.getReviewScores(id, "resource");
        double sum = reviews.getFiveStars() * 5 + reviews.getFourStars() * 4 + reviews.getThreeStars() * 3 + reviews.getTwoStars() * 2 + reviews.getOneStars();
        double num = reviews.getFiveStars() + reviews.getFourStars() + reviews.getThreeStars() + reviews.getTwoStars() + reviews.getOneStars();
        double result = sum / num;
        double scale = Math.pow(10, 1);
        return Math.round(result * scale) / scale;
    }

    public VacationHouseDTO getVacationHouseDTO(Long id) {
        VacationHouse vh = repository.getById(id);
        String address = vh.getAddress().getStreet() + " " + vh.getAddress().getNumber() + ", " + vh.getAddress().getPlace() + ", " + vh.getAddress().getCountry();
        List<String> images = new ArrayList<String>();
        for (Image img : vh.getImages()) {
            images.add(img.getPath());
        }
        int capacity = vh.getNumberOfBedsPerRoom() * vh.getNumberOfRooms();
        List<VacationHouseQuickReservationDTO> quickReservations = getQuickReservations(vh);
        return new VacationHouseDTO(vh.getId(), vh.getTitle(), address, vh.getAddress().getNumber(), vh.getAddress().getStreet(), vh.getAddress().getPlace(), vh.getAddress().getCountry(), vh.getDescription(), images, vh.getRulesAndRegulations(), vh.getAdditionalServices(), vh.getPricelist().getPrice(), vh.getCancellationFee(), vh.getNumberOfRooms(), capacity, quickReservations);
    }

    private List<VacationHouseQuickReservationDTO> getQuickReservations(VacationHouse vh) {
        List<VacationHouseQuickReservationDTO> quickReservations = new ArrayList<VacationHouseQuickReservationDTO>();
        for (VacationHouseReservation reservation : vh.getReservations()) {
            if (reservation.getPrice() < vh.getPricelist().getPrice() && reservation.getClient() == null && reservation.isQuickReservation())
                quickReservations.add(createVacationHouseQuickReservationDTO(vh.getPricelist().getPrice(), reservation));
        }
        return quickReservations;
    }

    public List<ReservationDTO> getReservations(Long id) {
        VacationHouse house = this.getVacationHouse(id);
        List<ReservationDTO> reservations = new ArrayList<ReservationDTO>();

        for (VacationHouseReservation houseReservation : house.getReservations()) {
            if (!houseReservation.isQuickReservation() && !houseReservation.isBusyPeriod()) {
                reservations.add(createDTOFromReservation(houseReservation));
            }
        }
        return reservations;
    }

    private VacationHouseQuickReservationDTO createVacationHouseQuickReservationDTO(int vacationHousePrice, VacationHouseReservation reservation) {
        Appointment firstAppointment = getFirstAppointment(reservation.getAppointments());
        LocalDateTime startDate = firstAppointment.getStartTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm'h'");
        String strDate = startDate.format(formatter);
        int numberOfPeople = reservation.getNumberOfClients();
        List<Tag> additionalServices = reservation.getAdditionalServices();
        int duration = reservation.getAppointments().size();
        int price = reservation.getPrice();
        int discount = 100 - (100 * price / vacationHousePrice);
        return new VacationHouseQuickReservationDTO(reservation.getId(), strDate, numberOfPeople, additionalServices, duration, price, discount);
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

    public Boolean addQuickReservation(Long id, VacationHouseQuickReservationDTO quickReservationDTO) {
        VacationHouse house = this.getVacationHouse(id);
        VacationHouseReservation reservation = getReservationFromDTO(quickReservationDTO, true);
        reservation.setResource(house);
        vacationHouseReservationService.addReservation(reservation);
        house.addReservation(reservation);
        this.save(house);
        return true;
    }

    public Boolean updateQuickReservation(Long id, VacationHouseQuickReservationDTO quickReservationDTO) {
        VacationHouse house = this.getVacationHouse(id);
        VacationHouseReservation newReservation = getReservationFromDTO(quickReservationDTO, true);
        VacationHouseReservation originalReservation = vacationHouseReservationService.getVacationHouseReservation(quickReservationDTO.getReservationID());
        updateQuickReservation(originalReservation, newReservation);
        vacationHouseReservationService.addReservation(originalReservation);
        this.save(house);
        return true;
    }

    public Boolean deleteQuickReservation(Long id, VacationHouseQuickReservationDTO quickReservationDTO) {
        VacationHouse house = this.getVacationHouse(id);
        vacationHouseReservationService.deleteById(quickReservationDTO.getReservationID());
        //izbaci se reservation iz house
        this.save(house);
        return true;
    }

    public ResourceOwnerDTO getOwner(Long id) {
        VacationHouse house = this.getVacationHouse(id);
        VacationHouseOwner owner = house.getOwner();
        return new ResourceOwnerDTO(owner.getId(), owner.getName(), owner.getProfileImg());
    }

    private VacationHouseReservation getReservationFromDTO(VacationHouseQuickReservationDTO dto, Boolean isQuick){
        List<Appointment> appointments = new ArrayList<Appointment>();
        String[] splitDate = dto.getStartDate().split(" ");
        Appointment startDateAppointment = Appointment.getDayAppointment(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]));
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
        VacationHouseReservation reservation = new VacationHouseReservation(dto.getNumberOfPeople(), dto.getPrice());
        reservation.setClient(null);
        reservation.setAdditionalServices(tags);
        reservation.setAppointments(appointments);
        reservation.setQuickReservation(isQuick);
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

    private void updateQuickReservation(VacationHouseReservation originalReservation, VacationHouseReservation newReservation) {
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
        if (vh.getImages() != null && vh.getImages().size() > 0) {
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
        for (String tagText : house.getTagsText()) {
            Tag tag = new Tag(tagText);
            tagService.addTag(tag);
            tags.add(tag);
        }
        vh.setAdditionalServices(tags);
        List<Image> images = new ArrayList<Image>();
        if (house.getImagePaths() != null) {
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

    public List<ReservationDTO> getReservationsForOwner(Long id) {
        List<ReservationDTO> reservations = new ArrayList<ReservationDTO>();

        for (VacationHouseReservation vhr : vacationHouseReservationService.getAll()) {
            if (Objects.equals(vhr.getResource().getOwner().getId(), id) && !vhr.isQuickReservation() && !vhr.isBusyPeriod()) {
                reservations.add(createDTOFromReservation(vhr));
            }
        }

        return reservations;
    }

    public List<ReservationDTO> getReservationsForVacationHouse(Long id) {
        List<ReservationDTO> reservations = new ArrayList<ReservationDTO>();

        for (VacationHouseReservation vhr : vacationHouseReservationService.getAll()) {
            if (Objects.equals(vhr.getResource().getId(), id) && !vhr.isQuickReservation() && !vhr.isBusyPeriod()) {
                reservations.add(createDTOFromReservation(vhr));
            }
        }
        return reservations;

    }

    public List<ReservationDTO> getReservationsForClient(Long id) {
        List<ReservationDTO> reservations = new ArrayList<ReservationDTO>();

        for (VacationHouseReservation vhr : vacationHouseReservationService.getStandardReservations()) {
            if (Objects.equals(vhr.getClient().getId(), id) && !vhr.isQuickReservation() && !vhr.isBusyPeriod()) {

                reservations.add(createDTOFromReservation(vhr));
            }
        }
        return reservations;

    }

    private ReservationDTO createDTOFromReservation(VacationHouseReservation vhr) {
        return new ReservationDTO(
                vhr.getAppointments(),
                vhr.getNumberOfClients(),
                vhr.getAdditionalServices(),
                vhr.getPrice(),
                vhr.getClient(),
                vhr.getResource().getTitle(),
                vhr.isBusyPeriod(),
                vhr.isQuickReservation(),
                vhr.getResource().getId(),
                vhr.getId()
        );
    }

    public Long createReservation(NewReservationDTO dto) throws ReservationNotAvailableException {
        VacationHouseReservation reservation = createFromDTO(dto);

        List<VacationHouseReservation> reservations = vacationHouseReservationService.getPossibleCollisionReservations(reservation.getResource().getId());
        for (VacationHouseReservation r : reservations) {
            for (Appointment a : r.getAppointments()) {
                for (Appointment newAppointment : reservation.getAppointments()) {
                    reservationService.checkAppointmentCollision(a, newAppointment);
                }
            }
        }

        vacationHouseReservationService.save(reservation);
        return reservation.getId();
    }


    private VacationHouseReservation createFromDTO(NewReservationDTO dto) {

        List<Appointment> appointments = new ArrayList<Appointment>();

        LocalDateTime startTime = LocalDateTime.of(dto.getStartYear(), Month.of(dto.getStartMonth()), dto.getStartDay(), 10, 0);
        LocalDateTime endTime = startTime.plusDays(1);

        while (startTime.isBefore(LocalDateTime.of(dto.getEndYear(), Month.of(dto.getEndMonth()), dto.getEndDay(), 10, 0))) {
            appointments.add(new Appointment(startTime, endTime));
            startTime = endTime;
            endTime = startTime.plusDays(1);
        }
        appointmentService.saveAll(appointments);

        Client client = clientService.getById(dto.getClientId().toString());
        Long id = dto.getResourceId();
        VacationHouse vacationHouse = this.getVacationHouse(id);

        int price = vacationHouse.getPricelist().getPrice() * appointments.size();

        List<Tag> tags = new ArrayList<Tag>();
        for (String text : dto.getAdditionalServicesStrings()) {
            Tag tag = new Tag(text);
            tags.add(tag);
        }

        tagService.saveAll(tags);

        return new VacationHouseReservation(
                appointments,
                dto.getNumberOfClients(),
                tags,
                price,
                client,
                vacationHouse,
                dto.isBusyPeriod(), dto.isQuickReservation());
    }

    public List<ReservationDTO> getBusyPeriodForVacationHouse(Long id) {
        List<ReservationDTO> periods = new ArrayList<ReservationDTO>();

        for (VacationHouseReservation ar : vacationHouseReservationService.getBusyPeriodForVacationHouse(id)) {
            periods.add(createDTOFromReservation(ar));
        }

        return periods;
    }

    public Long createBusyPeriod(NewBusyPeriodDTO dto) {

        VacationHouseReservation reservation = createBusyPeriodReservationFromDTO(dto);

        List<VacationHouseReservation> reservations = vacationHouseReservationService.getPossibleCollisionReservations(reservation.getResource().getId());
        for (VacationHouseReservation r : reservations) {
            for (Appointment a : r.getAppointments()) {
                for (Appointment newAppointment : reservation.getAppointments()) {
                    reservationService.checkAppointmentCollision(a, newAppointment);
                    reservationService.checkAppointmentCollision(newAppointment, a);
                }
            }
        }
        vacationHouseReservationService.save(reservation);
        return reservation.getId();
    }

    private VacationHouseReservation createBusyPeriodReservationFromDTO(NewBusyPeriodDTO dto) {

        List<Appointment> appointments = new ArrayList<Appointment>();

        LocalDateTime startTime = LocalDateTime.of(dto.getStartYear(), Month.of(dto.getStartMonth()), dto.getStartDay(), 0, 0);
        LocalDateTime endTime = startTime.plusDays(1);

        while (startTime.isBefore(LocalDateTime.of(dto.getEndYear(), Month.of(dto.getEndMonth()), dto.getEndDay(), 23, 59))) {
            appointments.add(new Appointment(startTime, endTime));
            startTime = endTime;
            endTime = startTime.plusHours(1);
        }
        appointmentService.saveAll(appointments);

        Long id = dto.getResourceId();
        VacationHouse vacationHouse = this.getVacationHouse(id);

        return new VacationHouseReservation(
                appointments,
                0,
                null,
                0,
                null,
                vacationHouse,
                true,
                false

        );
    }

    public boolean clientCanReview(Long resourceId, Long clientId) {
        return hasReservations(resourceId, clientId);
    }

    public boolean hasReservations(Long resourceId, Long clientId) {
        return vacationHouseReservationService.clientHasReservations(resourceId, clientId);
    }

    public List<String> getVacationHouseAddress() {
        List<String> address = new ArrayList<>();
        String fullName = "";
        for (VacationHouse vacationHouse :
                repository.findAll()) {
            fullName = vacationHouse.getAddress().getFullAddressName();
            if (!address.contains(fullName)) {
                address.add(fullName);
            }
        }
        return address;
    }

    public List<VacationHouse> getFilteredVacationHouses(VacationHouseFilterDTO vacationHouseFilterDTO) {
        if (vacationHouseFilterDTO.isVacationHousesChecked()) {
            ArrayList<VacationHouse> vacationHouses = new ArrayList<>();//treba da prodjes i kroz brze rezervacije
            for (VacationHouse vacationHouse : repository.findAll()) {
                if (checkNumberOfVacationHouseRooms(vacationHouseFilterDTO, vacationHouse) &&
                        checkNumberOfVacationHouseBeds(vacationHouseFilterDTO, vacationHouse) &&
                        checkOwnerName(vacationHouseFilterDTO, vacationHouse) &&
                        checkReviewRating(vacationHouseFilterDTO, vacationHouse) &&
                        checkLocation(vacationHouseFilterDTO, vacationHouse) &&
                        checkCancellationFee(vacationHouseFilterDTO, vacationHouse)
                )
                    vacationHouses.add(vacationHouse);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
            String datetime = vacationHouseFilterDTO.getStartDate() + " " + vacationHouseFilterDTO.getStartTime();
            LocalDateTime startDateTime = LocalDateTime.parse(datetime, formatter);//ovde puca
            datetime = vacationHouseFilterDTO.getEndDate() + " " + vacationHouseFilterDTO.getEndTime();
            LocalDateTime endDateTime = LocalDateTime.parse(datetime, formatter);

            ArrayList<VacationHouse> housesToDelete = new ArrayList<>();
            int numberOfDays = (int) ChronoUnit.DAYS.between(startDateTime.toLocalDate(), endDateTime.toLocalDate()) == 0 ? 1 : (int) ChronoUnit.DAYS.between(startDateTime.toLocalDate(), endDateTime.toLocalDate());
            HashMap<LocalDateTime, Integer> listOfDatesBusyness = new HashMap<>();
            boolean remove = true;
            for (VacationHouse vacationHouse : vacationHouses) {
                if (!checkPrice(vacationHouseFilterDTO, vacationHouse.getPricelist().getPrice() * numberOfDays))  //of days ce da bude za vikendice
                    vacationHouses.remove(vacationHouse);
                for (int i = 0; i <= numberOfDays; i++) {
                    listOfDatesBusyness.put(startDateTime.plusDays(i), 0);
                }
                for (VacationHouseReservation vacationHouseReservation : vacationHouseReservationService.getReservationsByVacationHouseId(vacationHouse.getId())) {
                    LocalDateTime startAppointment = vacationHouseReservation.getAppointments().get(0).getStartTime();
                    LocalDateTime endAppointment = vacationHouseReservation.getAppointments().get(vacationHouseReservation.getAppointments().size() - 1).getEndTime();
                    for (LocalDateTime date : listOfDatesBusyness.keySet()) {
                        if ((startAppointment.toLocalDate().isBefore(date.toLocalDate()) || startAppointment.toLocalDate().isEqual(date.toLocalDate())) &&
                                (endAppointment.toLocalDate().isAfter(date.toLocalDate()) || endAppointment.toLocalDate().isEqual(date.toLocalDate())))
                            listOfDatesBusyness.replace(date, 1);
                    }
                }
                for (int i : listOfDatesBusyness.values()) {
                    if (i == 0) {
                        remove = false;
                        break;
                    }
                }
                if (remove)
                    housesToDelete.add(vacationHouse);
                listOfDatesBusyness.clear();
            }
            for (VacationHouse vacationHouse :
                    housesToDelete) {
                vacationHouses.remove(vacationHouse);
            }
            return vacationHouses;
        } else {
            return new ArrayList<>();
        }
    }

    private boolean checkPrice(VacationHouseFilterDTO vacationHouseFilterDTO, int price) {
        return vacationHouseFilterDTO.getPriceRange().isEmpty() || (vacationHouseFilterDTO.getPriceRange().get(0) <= price && price <= vacationHouseFilterDTO.getPriceRange().get(1));
    }

    private boolean checkCancellationFee(VacationHouseFilterDTO vacationHouseFilterDTO, VacationHouse vacationHouse) {
        if (vacationHouseFilterDTO.isCancellationFee() && vacationHouse.getCancellationFee() == 0)
            return true;
        else if (!vacationHouseFilterDTO.isCancellationFee() && vacationHouse.getCancellationFee() != 0)
            return true;
        return false;
    }

    private boolean checkLocation(VacationHouseFilterDTO vacationHouseFilterDTO, VacationHouse vacationHouse) {
        if (vacationHouseFilterDTO.getLocation().isEmpty())
            return true;
        Address location = new Address(vacationHouseFilterDTO.getLocation());
        return vacationHouse.getAddress().getStreet().equals(location.getStreet()) &&
                vacationHouse.getAddress().getPlace().equals(location.getPlace()) &&
                vacationHouse.getAddress().getNumber().equals(location.getNumber()) &&
                vacationHouse.getAddress().getCountry().equals(location.getCountry());
    }

    private boolean checkReviewRating(VacationHouseFilterDTO vacationHouseFilterDTO, VacationHouse vacationHouse) {
        List<ClientReviewDTO> list = clientReviewService.getResourceReviews(vacationHouse.getId());
        if (list.isEmpty() && (vacationHouseFilterDTO.getReviewRating().isEmpty() || vacationHouseFilterDTO.getReviewRating().equals("0")))
            return true;
        double score = list.stream().mapToDouble(ClientReviewDTO::getRating).sum() / list.size();
        return (vacationHouseFilterDTO.getReviewRating().isEmpty() || Double.parseDouble(vacationHouseFilterDTO.getReviewRating()) <= score);
    }

    private boolean checkOwnerName(VacationHouseFilterDTO vacationHouseFilterDTO, VacationHouse vacationHouse) {
        return vacationHouseFilterDTO.getVacationHouseOwnerName().isEmpty() || (vacationHouse.getOwner().getFirstName() + " " + vacationHouse.getOwner().getFirstName()).equals(vacationHouseFilterDTO.getVacationHouseOwnerName());
    }

    private boolean checkNumberOfVacationHouseBeds(VacationHouseFilterDTO vacationHouseFilterDTO, VacationHouse vacationHouse) {
        return vacationHouseFilterDTO.getNumOfVacationHouseBeds().isEmpty() || Integer.parseInt(vacationHouseFilterDTO.getNumOfVacationHouseBeds()) == vacationHouse.getNumberOfBedsPerRoom();
    }

    private boolean checkNumberOfVacationHouseRooms(VacationHouseFilterDTO vacationHouseFilterDTO, VacationHouse vacationHouse) {
        return vacationHouseFilterDTO.getNumOfVacationHouseRooms().isEmpty() || Integer.parseInt(vacationHouseFilterDTO.getNumOfVacationHouseRooms()) == vacationHouse.getNumberOfRooms();
    }

    public List<ReservationDTO> getReservationsForReview(Long id) {
        List<ReservationDTO> reservations = new ArrayList<ReservationDTO>();
        for (VacationHouseReservation r : vacationHouseReservationService.getStandardReservations()) {
            if (!clientReviewService.reservationHasReview(r.getId())) {
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

    public Long reserveQuickReservation(VacationHouseQuickReservationDTO dto) {
        VacationHouseReservation quickReservation = vacationHouseReservationService.getVacationHouseReservation(dto.getReservationID());
        Client client = clientService.getById(dto.getClientID().toString());

        quickReservation.getResource().removeQuickReservation(quickReservation);
        quickReservation.setClient(client);
        quickReservation.setQuickReservation(false);
        return vacationHouseReservationService.save(quickReservation);
    }

    public boolean clientCanReviewVendor(Long vendorId, Long clientId) {
        return vacationHouseReservationService.clientCanReviewVendor(vendorId, clientId);
    }
}