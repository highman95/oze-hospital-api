package com.oze.hospitalmanager.services;

import java.util.List;

import com.oze.hospitalmanager.models.Patient;
import com.oze.hospitalmanager.models.Response;

public interface IPatientService {
    Response<List<Patient>> fetchProfilesAbove2YearsInAge();

    void downloadProfile(Long id);

    void deleteProfilesBetweenDateRange(String fromDate, String toDate);
}
