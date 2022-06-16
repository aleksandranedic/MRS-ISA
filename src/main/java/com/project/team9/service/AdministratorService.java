package com.project.team9.service;

import com.project.team9.dto.AdminDTO;
import com.project.team9.dto.IncomeReport;
import com.project.team9.dto.IncomeReportDateRange;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.user.Administrator;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.AdministratorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministratorService {
    private final AdministratorRepository administratorRepository;
    private final FishingInstructorService fishingInstructorService;
    private final VacationHouseOwnerService vacationHouseOwnerService;
    private final BoatOwnerService boatOwnerService;
    private final AddressService addressService;
    private final ImageService imageService;

    public AdministratorService(AdministratorRepository administratorRepository, FishingInstructorService fishingInstructorService, VacationHouseOwnerService vacationHouseOwnerService, BoatOwnerService boatOwnerService, AddressService addressService, ImageService imageService) {
        this.administratorRepository = administratorRepository;
        this.fishingInstructorService = fishingInstructorService;
        this.vacationHouseOwnerService = vacationHouseOwnerService;
        this.boatOwnerService = boatOwnerService;
        this.addressService = addressService;
        this.imageService = imageService;
    }
    public Administrator addAdmin(Administrator administrator){
       return administratorRepository.save(administrator);
    }

    public Administrator getAdministratorByEmail(String username) {
        return administratorRepository.findByEmail(username);
    }
    public IncomeReport getIncomeReportInstructors(IncomeReportDateRange dataRange){
        List<FishingInstructor> instructors = fishingInstructorService.getFishingInstructors();
        List<IncomeReport> reports = new ArrayList<>();
        for (FishingInstructor fishingInstructor : instructors ) {
            reports.add(fishingInstructorService.getIncomeReport(fishingInstructor.getId(), dataRange));
        }
        return mergeReports(reports);
    }
    public IncomeReport getIncomeReportBoatOwners(IncomeReportDateRange dataRange){
        List<BoatOwner> owners = boatOwnerService.getBoatOwners();
        List<IncomeReport> reports = new ArrayList<>();
        for (BoatOwner boatOwner : owners ) {
            reports.add(boatOwnerService.getIncomeReport(boatOwner.getId(), dataRange));
        }
        return mergeReports(reports);
    }
    public IncomeReport getIncomeReportHouseOwners(IncomeReportDateRange dataRange){
        List<VacationHouseOwner> owners = vacationHouseOwnerService.getVacationHouseOwners();
        List<IncomeReport> reports = new ArrayList<>();
        for (VacationHouseOwner vacationHouseOwner : owners ) {
            reports.add(vacationHouseOwnerService.getIncomeReport(vacationHouseOwner.getId(), dataRange));
        }
        return mergeReports(reports);
    }
    public IncomeReport getIncomeReportAll(IncomeReportDateRange dataRange){
        List<IncomeReport> reports = new ArrayList<>();
        reports.add(getIncomeReportInstructors(dataRange));
        reports.add(getIncomeReportBoatOwners(dataRange));
        reports.add(getIncomeReportHouseOwners(dataRange));
        return mergeReports(reports);
    }

    private IncomeReport mergeReports(List<IncomeReport> reports){
        IncomeReport incomeReport = new IncomeReport();
        for (IncomeReport report : reports){
            for (int i=0; i<report.getDates().size(); i++) {
                int index = incomeReport.getDates().indexOf(report.getDates().get(i));
                if (index > -1) {
                    int newVal = incomeReport.getIncomes().get(index) + report.getIncomes().get(i);
                    incomeReport.getIncomes().set(index, newVal);
                }
                else{
                    incomeReport.getDates().add(report.getDates().get(i));
                    incomeReport.getIncomes().add(report.getIncomes().get(i));
                }
            }
        }
        return incomeReport.sort(false);
    }

    public List<Administrator> getAdministrators() {
        return administratorRepository.findAll();
    }

    public Long deleteById(Long id) {
        Administrator administrator = administratorRepository.getById(id);
        administrator.setDeleted(true);
        return administratorRepository.save(administrator).getId();
    }

    public Administrator getAdmin(Long id) {
        return administratorRepository.getAdminById(id);
    }

    public void save(Administrator administrator) {
        addressService.addAddress(administrator.getAddress());
        Image image = new Image();
        imageService.save(image);
        administrator.setProfileImg(image);
        administratorRepository.save(administrator);
    }

    public Long edit(AdminDTO dto) {

        Administrator administrator = administratorRepository.getAdminById(dto.getId());

        Address address = new Address(dto.getPlace(), dto.getNumber(), dto.getStreet(), dto.getCountry());
        addressService.addAddress(address);
        administrator.setFirstName(dto.getFirstName());
        administrator.setLastName(dto.getLastName());
        administrator.setEmail(dto.getEmail());
        administrator.setPhoneNumber(dto.getPhoneNumber());
        administrator.setAddress(address);

        return administratorRepository.save(administrator).getId();

    }


}
