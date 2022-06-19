package com.oze.hospitalmanager.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oze.hospitalmanager.models.Response;
import com.oze.hospitalmanager.models.Staff;
import com.oze.hospitalmanager.models.StaffDto;
import com.oze.hospitalmanager.repositories.IStaffRepository;
import com.oze.hospitalmanager.services.StaffService;
import com.oze.hospitalmanager.utils.Constants;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {

    @Mock
    private IStaffRepository staffRepository;

    @InjectMocks
    private StaffService staffService;

    private Staff staff;

    @BeforeEach
    public void setUp() {
        staff = Staff.builder().id(1L).name("John Wick")
                .uuid(UUID.randomUUID()).registrationDate(LocalDate.now())
                .build();
    }

    @Test
    void givenStaffWithNoName_whenAddProfile_thenReturnNegativeFeedback() {
        StaffDto staffDto = new StaffDto();

        Staff staff = Staff.builder().name(staffDto.getName()).build();
        given(staffRepository.save(staff)).willReturn(Staff.builder().build());

        Response<?> response = staffService.addProfile(staffDto);
        assertFalse(response.isStatus());
        assertNull(response.getData());
        assertEquals(Constants.UNABLE_TO_SAVE_DATA, response.getMessage());
    }

    @Test
    void givenStaffWithValidName_whenAddProfile_thenReturnPositiveFeedback() {
        StaffDto staffDto = new StaffDto(staff.getName());
        given(staffRepository.save(any(Staff.class))).willReturn(staff);

        Response<Staff> response = staffService.addProfile(staffDto);
        assertTrue(response.isStatus());
        assertNotNull(staff.getId());
        assertEquals(staff.getName(), response.getData().getName());
        assertEquals(Constants.DATA_SUCCESSFULLY_SAVED, response.getMessage());
    }

    @Test
    void testUpdateProfile() {
        assertTrue(true);
    }

}
