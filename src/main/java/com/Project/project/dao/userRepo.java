package com.Project.project.dao;

import com.Project.project.services.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface userRepo extends JpaRepository<User,Long> {

    //@Query("select u from User u where u.email=:email")
   Optional<User> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.isEnable = TRUE WHERE a.email = ?1")
    int enableUser(String email);


    User findByEmailIgnoreCase(String email);
}
