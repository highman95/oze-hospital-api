package com.oze.hospitalmanager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oze.hospitalmanager.controllers.StaffController;

@SpringBootTest
class HospitalmanagerApplicationTests {

	@Autowired
	private StaffController staffController;

	@Test
	void contextLoads() {
		assertNotNull(staffController);
	}

}
