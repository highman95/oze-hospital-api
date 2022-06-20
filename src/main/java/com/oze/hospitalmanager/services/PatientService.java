package com.oze.hospitalmanager.services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oze.hospitalmanager.models.Patient;
import com.oze.hospitalmanager.models.Response;
import com.oze.hospitalmanager.repositories.IPatientRepository;
import com.oze.hospitalmanager.utils.Constants;

@Service
public class PatientService implements IPatientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private IPatientRepository patientRepository;

    @Override
    public Response<List<Patient>> fetchProfilesAbove2YearsInAge() {
        List<Patient> patients = patientRepository.findAllByAgeGreaterThanOrEqualTo2();
        return new Response<>(true, patients, Constants.DATA_SUCCESSFULLY_FETCHED);
    }

    @Override
    public void downloadProfile(Long id) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteProfilesBetweenDateRange(String fromDate, String toDate) {
        try {
            patientRepository.deleteAllByLastVisitDateBetween(LocalDate.parse(fromDate), LocalDate.parse(toDate));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Passed date [" + e.getParsedString() + "] is invalid");
        } catch (Exception e) {
            throw e;
        }
    }

}
