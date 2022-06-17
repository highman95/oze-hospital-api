package com.oze.hospitalmanager.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oze.hospitalmanager.models.StaffDto;
import com.oze.hospitalmanager.services.IStaffService;

@RestController
@RequestMapping(value = "/api/v1/staffs")
public class StaffController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private IStaffService staffService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addProfile(@RequestBody @Valid StaffDto staffDto) {
        LOGGER.info("inside the controller ---> add-profile");
        return new ResponseEntity<>(staffService.addProfile(staffDto), HttpStatus.CREATED);
    }

}
