package com.oze.hospitalmanager.integration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oze.hospitalmanager.models.Response;
import com.oze.hospitalmanager.models.Staff;
import com.oze.hospitalmanager.models.StaffDto;
import com.oze.hospitalmanager.repositories.IStaffRepository;
import com.oze.hospitalmanager.services.StaffService;
import com.oze.hospitalmanager.utils.Constants;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext
public class StaffControllerTest {

    @Spy
    private StaffService staffService;

    @Mock
    private IStaffRepository staffRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should return 400 when creating a staff with no-name")
    void whenNoNameIsPassed_ThenReturnStatus400() throws Exception {
        String body = objectMapper.writeValueAsString(new StaffDto());

        mockMvc.perform(post("/api/v1/staffs")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(false)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.message", is(Constants.NAME_IS_REQUIRED)));
    }

    @Test
    @DisplayName("should return 201 when creating a staff with valid name")
    void whenValidNameIsPassed_ThenReturnStatus201() throws Exception {
        StaffDto staffDto = new StaffDto("John Wick");
        String body = objectMapper.writeValueAsString(staffDto);

        Staff staff = Staff.builder().name(staffDto.getName())
                .uuid(UUID.randomUUID()).registrationDate(LocalDate.now())
                .build();

        when(staffService.addProfile(staffDto))
                .thenReturn(new Response<>(true, staff, Constants.DATA_SUCCESSFULLY_SAVED));

        mockMvc.perform(post("/api/v1/staffs")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.data", is(notNullValue())))
                .andExpect(jsonPath("$.data.name", is(staff.getName())))
                .andExpect(jsonPath("$.message", is(Constants.DATA_SUCCESSFULLY_SAVED)));
    }

    @Test
    @DisplayName("should return 403 when updating staff-profile with valid name and no-auth-token")
    void whenNoAuthTokenIsPassed_ThenReturnStatus403() throws Exception {
        StaffDto staffDto = new StaffDto("James Wick");
        String body = objectMapper.writeValueAsString(staffDto);

        mockMvc.perform(put("/api/v1/staffs")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("should return 403 when updating staff-profile with valid name and invalid uuid (for authentication)")
    void whenInvalidAuthTokenIsPassed_ThenReturnStatus403() throws Exception {
        StaffDto staffDto = new StaffDto("James Wick");
        String body = objectMapper.writeValueAsString(staffDto);

        mockMvc.perform(put("/api/v1/staffs")
                .header(Constants.CUSTOM_AUTH_TOKEN_NAME, "1234567890abcdefghijk")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @Disabled("the testcase is under development")
    @DisplayName("should return 204 when updating staff-profile with valid name and uuid (for authentication)")
    void whenValidAuthTokenIsPassed_ThenReturnStatus204() throws Exception {
        StaffDto staffDto = new StaffDto("James Wick");
        String body = objectMapper.writeValueAsString(staffDto);

        Staff staff = Staff.builder().uuid(UUID.randomUUID()).registrationDate(LocalDate.now()).build();
        doNothing().when(staffService).updateProfile(staff.getUuid(), staffDto);

        mockMvc.perform(put("/api/v1/staffs")
                .header(Constants.CUSTOM_AUTH_TOKEN_NAME, staff.getUuid())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
                .andExpect(status().isNoContent());
    }

}
