package com.oze.hospitalmanager.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.oze.hospitalmanager.models.Patient;

public interface IPatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "SELECT p FROM Patient p WHERE p.age >= 2")
    List<Patient> findAllByAgeGreaterThanOrEqualTo2();

    @Transactional
    void deleteAllByLastVisitDateBetween(LocalDate fromDate, LocalDate toDate);
}
