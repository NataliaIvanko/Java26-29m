package com.telran.bankappfirsttry.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id //primary key. Чтобы создать таблицу в бд с уникальным полем, досаточно аннотации
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private TransactionType type;
    private Float amount;
    private Integer accountFrom;
    private Integer accountTo;



}
