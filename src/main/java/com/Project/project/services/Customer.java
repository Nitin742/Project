package com.Project.project.services;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Component
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User{




   private Long contact;

//
//    public long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(long userId) {
//        this.userId = userId;
//    }

//    public Customer() {
//
//    }
//
//    public Customer(String firstName) {
//
//       super(firstName);
//
//    }



    public Long  getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }


}
