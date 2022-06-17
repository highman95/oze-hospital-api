package com.oze.hospitalmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oze.hospitalmanager.models.Patient;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {
}
