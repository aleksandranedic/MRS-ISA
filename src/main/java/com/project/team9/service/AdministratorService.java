package com.project.team9.service;

import com.project.team9.dto.IncomeReport;
import com.project.team9.dto.IncomeReportDateRange;
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

    public AdministratorService(AdministratorRepository administratorRepository, FishingInstructorService fishingInstructorService, VacationHouseOwnerService vacationHouseOwnerService, BoatOwnerService boatOwnerService) {
        this.administratorRepository = administratorRepository;
        this.fishingInstructorService = fishingInstructorService;
        this.vacationHouseOwnerService = vacationHouseOwnerService;
        this.boatOwnerService = boatOwnerService;
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
}
