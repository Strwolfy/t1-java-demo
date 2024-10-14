package ru.t1.java.demo.model;

import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BigDecimal balance;


}
