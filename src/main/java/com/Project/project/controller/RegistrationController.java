package com.Project.project.controller;


import com.Project.project.Service.RegistrationService;
import com.Project.project.services.Customer;
import com.Project.project.services.Seller;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private final RegistrationService registrationService;



    //Seller Register
    @PostMapping(path = "seller")
    public String registerSeller(@RequestBody Seller seller) throws IllegalAccessException {
        return registrationService.registerSeller(seller);
    }


    //Customer Register
    @PostMapping(path = "customer")
    public String registerCustomer(@RequestBody Customer customer) throws IllegalAccessException {
        return registrationService.registerCustomer(customer);
    }

    @GetMapping(path = "conform")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
