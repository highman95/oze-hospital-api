package com.oze.hospitalmanager.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.oze.hospitalmanager.models.Staff;

public interface IStaffRepository extends JpaRepository<Staff, Long> {
    Staff findByUuid(UUID uuid);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE Staff s SET s.name = ?2 WHERE s.uuid = ?1")
    void updateProfile(UUID uuid, String name);
}
