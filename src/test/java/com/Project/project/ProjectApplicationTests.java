package com.Project.project;

import com.Project.project.dao.userRepo;
import com.Project.project.services.Customer;
import com.Project.project.services.Role;
import com.Project.project.services.UserAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ProjectApplicationTests {
	@Autowired
	userRepo repo;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
	}

	@Test
	void createCustomer() {
		Customer customer = new Customer();
		// User user = new User();


		customer.setContact(998888888L);
		customer.setEmail("abc@gamil.com");
		customer.setFirstName("parth");
		customer.setMiddleName("--");
		customer.setLastName("sharma");
		customer.setPassword(this.passwordEncoder.encode("12345"));
//        customer.setActive(true);
//        customer.setDeleted(false);
//        customer.setExpired(false);
//        customer.setLocked(true);
		customer.setInvalid_attempt_count(1);

		List<Role> role = new ArrayList<>();
		Role roles = new Role();
		roles.setAuthority("ROLE_CUSTOMER");
		role.add(roles);
		customer.setRole(role);

		List<UserAddress> address = new ArrayList<>();
		UserAddress userAddress = new UserAddress();


		userAddress.setCity("Noida");
		userAddress.setState("UP");
		userAddress.setCountry("India");
		userAddress.setAddress_line("153 sector");
		userAddress.setZip_code("201310");
		userAddress.setLabel("Pari chowk");
		address.add(userAddress);
		//  userAddress.setUser(customer);
		customer.setAddress(address);

		repo.save(customer);


	}


}
