package com.telran.bankappfirsttry.entity;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Transaction {
    private Long id;
    private LocalDateTime dateTime;
    private String type;
    private Float amount;
    private Float accountFrom;
    private Float accountTo;
}
