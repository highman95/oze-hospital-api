package com.oze.hospitalmanager.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
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
    public ByteArrayInputStream downloadProfile(Long id) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter printer = new CSVPrinter(new PrintWriter(out), format);) {
            printer.printRecord("id", "name", "age", "last-visit-date");

            List<Patient> patients = List.of(patientRepository.findById(id).orElseGet(Patient::new));

            for (Patient patient : patients) {
                if (patient.getId() == null)
                    continue;

                printer.printRecord(String.valueOf(patient.getId()), patient.getName(),
                        String.valueOf(patient.getAge()), String.valueOf(patient.getLastVisitDate()));
            }
            printer.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            LOGGER.error("Fail to import data to CSV file: {}", e.getMessage());
            e.printStackTrace();
            return new ByteArrayInputStream("".getBytes());
        }
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
