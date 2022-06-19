package com.oze.hospitalmanager.integration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

        @InjectMocks
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

}
