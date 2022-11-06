package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")//hibranate
@Where(clause = "is_deleted=false")//any repo.which is using the user entity all the queies will be combined
public class User extends BaseEntity {

    private String firstName;//colums
    private String lastName;
    private String userName;
    private String passWord;
    private boolean enabled;
    private String phone;
    @ManyToOne        //relationship c
    private Role role;
    @Enumerated(EnumType.STRING)//should be saved as string female male
    private Gender gender;



}
