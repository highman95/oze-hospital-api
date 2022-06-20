package com.oze.hospitalmanager.services;

import java.util.UUID;

import com.oze.hospitalmanager.models.Response;
import com.oze.hospitalmanager.models.Staff;
import com.oze.hospitalmanager.models.StaffDto;

public interface IStaffService {
    Response<Staff> addProfile(StaffDto staffDto);

    void updateProfile(UUID uuid, StaffDto staffDto);
}
