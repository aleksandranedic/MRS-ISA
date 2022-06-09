package com.project.team9.controller;

import com.project.team9.dto.IncomeReport;
import com.project.team9.dto.IncomeReportDateRange;
import com.project.team9.model.Address;
import com.project.team9.model.resource.Adventure;
import com.project.team9.service.AdministratorService;
import com.project.team9.service.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="admin")

public class AdministratorController {

    private final AdministratorService service;

    @Autowired
    public AdministratorController(AdministratorService service) {
        this.service = service;
    }

    @PostMapping(value = "fishinginstructor/getIncomeReport", produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeReport getIncomeReportInstructors(IncomeReportDateRange dateRange) {
        return service.getIncomeReportInstructors(dateRange);
    }
    @PostMapping(value = "boatowner/getIncomeReport", produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeReport getIncomeReportBoatOwners(IncomeReportDateRange dateRange) {
        return service.getIncomeReportBoatOwners(dateRange);
    }
    @PostMapping(value = "houseowner/getIncomeReport", produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeReport getIncomeReportHouseOwner(IncomeReportDateRange dateRange) {
        return service.getIncomeReportHouseOwners(dateRange);
    }
    @PostMapping(value = "all/getIncomeReport", produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeReport getIncomeReportAll(IncomeReportDateRange dateRange) {
        return service.getIncomeReportAll(dateRange);
    }
}