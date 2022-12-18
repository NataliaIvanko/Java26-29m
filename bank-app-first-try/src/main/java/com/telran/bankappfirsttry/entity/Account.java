package com.telran.bankappfirsttry.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Account {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String email;
    private LocalDateTime creationDate;
    private Float balance;
  //  @ManyToMany
    private List<Long> transactions;

    public void addTransactionToList(Long id){
        this.transactions.add(id);
    }

   // private String userLogin;
  //  private String userPassword;
// private Long UserTypeId;
}
