package com.oze.hospitalmanager.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oze.hospitalmanager.models.Staff;

@Repository
public interface IStaffRepository extends JpaRepository<Staff, Long> {
    Staff findByUuid(UUID uuid);
}
