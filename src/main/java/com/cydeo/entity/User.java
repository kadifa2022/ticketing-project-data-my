package com.cydeo.entity;

import com.cydeo.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")//hibernate
//@Where(clause = "is_deleted=false")     //any repo.which is using the user entity all the queries will be combined
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    @Column(unique = true)//unique
    private String userName;

    private String passWord;
    private boolean enabled;
    private String phone;

    @ManyToOne        //relationship c
    private Role role;
    @Enumerated(EnumType.STRING)//should be saved as string female male
    private Gender gender;



}
