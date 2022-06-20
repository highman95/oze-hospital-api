package com.oze.hospitalmanager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oze.hospitalmanager.services.IPatientService;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private IPatientService patientService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> fetchProfilesAbove2YearsInAge() {
        LOGGER.info("getting patients above 2-years in age...");
        return ResponseEntity.ok().body(patientService.fetchProfilesAbove2YearsInAge());
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteProfilesBetweenDateRange(@RequestParam String fromDate,
            @RequestParam String toDate) {
        LOGGER.info("deleting patients within date-range...");
        patientService.deleteProfilesBetweenDateRange(fromDate, toDate);
        return ResponseEntity.ok().build();
    }
}
