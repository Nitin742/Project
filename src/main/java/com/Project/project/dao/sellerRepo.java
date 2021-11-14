package com.Project.project.dao;

import com.Project.project.services.Seller;

import com.Project.project.services.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface sellerRepo extends JpaRepository<Seller,Long> {
    @Query("select u from User u where u.email=:email")
    public User getUserByUserName(@Param("email")String email);
}
