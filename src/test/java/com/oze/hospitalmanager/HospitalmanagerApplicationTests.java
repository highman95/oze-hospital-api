package com.oze.hospitalmanager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.oze.hospitalmanager.controllers.StaffController;

@SpringBootTest
@DirtiesContext
class HospitalmanagerApplicationTests {

	@Autowired
	private StaffController staffController;

	@Test
	void contextLoads() {
		assertNotNull(staffController);
	}

}
