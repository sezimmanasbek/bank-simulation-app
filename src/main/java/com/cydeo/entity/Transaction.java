package com.cydeo.entity;

import com.cydeo.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue()
    private Long id;
    @ManyToOne
    private Account receiver;
    @ManyToOne
    private Account sender;
    private BigDecimal amount;
    private String message;
    @Column(columnDefinition = "DATE")
    private Date creationDate;
}
