package ru.t1.java.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction extends AbstractPersistable<Long> {

    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "account_id", nullable = false)
    private Long accountId; // Связь с аккаунтом

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

}