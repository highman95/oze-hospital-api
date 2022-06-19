package com.oze.hospitalmanager.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oze.hospitalmanager.models.Response;
import com.oze.hospitalmanager.models.Staff;
import com.oze.hospitalmanager.models.StaffDto;
import com.oze.hospitalmanager.repositories.IStaffRepository;
import com.oze.hospitalmanager.utils.Constants;

@Service
public class StaffService implements IStaffService {

    public static final Logger LOGGER = LoggerFactory.getLogger(StaffService.class);

    @Autowired
    private IStaffRepository staffRepository;

    @Override
    public Response<Staff> addProfile(StaffDto staffDto) {
        Staff newStaff = Staff.builder().name(staffDto.getName()).build();

        Response<Staff> response = new Response<>();
        try {
            LOGGER.info("saving the staff profile...");
            newStaff = staffRepository.save(newStaff);

            response.setData(newStaff);
            response.setStatus(true);
            response.setMessage(Constants.DATA_SUCCESSFULLY_SAVED);
        } catch (Exception e) {
            response.setMessage(Constants.UNABLE_TO_SAVE_DATA);
        }

        return response;
    }

    @Override
    public void updateProfile(Long id, StaffDto staffDto) {
        // TODO Auto-generated method stub
    }

}
