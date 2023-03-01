package com.cydeo.service;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    void makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message);

    List<TransactionDTO> findAllTransaction();

    Object lastTransactionsList();

    List<TransactionDTO> findTransactionListById(Long id);
}
