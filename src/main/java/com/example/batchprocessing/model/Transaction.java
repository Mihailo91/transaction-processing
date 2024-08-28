package com.example.batchprocessing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    private Integer id;
    private String cardHolder;
    private String cardNumber;
    private BigDecimal amount;
    private Instant timeOfTransaction;
    @Column(name = "suspicious_activity", columnDefinition = "NUMBER(1)")
    private boolean suspiciousActivity;
}
