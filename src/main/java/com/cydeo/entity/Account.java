package com.cydeo.entity;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue()
    private Long id;

    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(columnDefinition = "DATE")
    private Date creationDate;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}
