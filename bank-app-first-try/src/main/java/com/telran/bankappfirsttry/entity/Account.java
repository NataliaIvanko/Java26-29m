package com.telran.bankappfirsttry.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Account {

    private Integer userId;
   // private Long UserTypeId;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private LocalDateTime creationDate;
    List<Long> transactions;

   // private String userLogin;
  //  private String userPassword;

}
