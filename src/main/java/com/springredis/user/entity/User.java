package com.springredis.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "T_USER")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_USER_SEQ")
    @SequenceGenerator(initialValue = 1, name = "T_USER_SEQ")
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE")
    private String mobile;

    public User(String name, String description, String email, String mobile) {
        this.name = name;
        this.description = description;
        this.email = email;
        this.mobile = mobile;
    }

    public static User makeUser(String name, String description, String email, String mobile) {
        return new User(name, description, email, mobile);
    }
}
