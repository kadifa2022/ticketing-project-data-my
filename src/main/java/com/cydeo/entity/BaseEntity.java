package com.cydeo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isDeleted=false;//we are adding to table, because we want to see in DB not  user to see it UI
    @Column(nullable = false,updatable = false)
    private LocalDateTime insertDateTime;
    @Column(nullable = false,updatable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    private Long lastUpdateUserId;

    @PrePersist
    private void onPrePersist(){
        this.insertDateTime=LocalDateTime.now();
        this.lastUpdateDateTime=LocalDateTime.now();
        this.insertUserId=1L;
        this.lastUpdateUserId=1L;

    }
    @PreUpdate
    private void onPreUpdate(){
        this.lastUpdateDateTime=LocalDateTime.now();
        this.lastUpdateUserId=1L;

    }


}
