package com.Project.project.dao;

import com.Project.project.services.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface customerRepo extends JpaRepository<Customer, Long> {

////@Query("select new Customer(firstName) from  Customer  ")
//     List<Customer> getCustomer();



}
