package com.cydeo.service.impl;

import com.cydeo.entity.Account;
import com.cydeo.entity.Transaction;
import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.mapper.BaseMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final BaseMapper mapper;

    @Value("${under_construction}")
    private boolean underConstruction;

    public TransactionServiceImpl(AccountService accountService, TransactionRepository transactionRepository, BaseMapper mapper) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
    }

    @Override
    public void makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {
        if(!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
        /*
        after all validations completed, and money is transferred, we need to create Transaction object and
        create needed classes/method for this step, save the transaction
         */
            TransactionDTO transaction = new TransactionDTO(sender,receiver,amount,
                    message,creationDate);
            transactionRepository.save(mapper.convert(transaction,new Transaction()));
        } else{
            throw new UnderConstructionException("App is under construction, try again later");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if(checkSenderBalance(sender,amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

            AccountDTO sender1 = accountService.retrieveById(sender.getId());
            AccountDTO receiver1 = accountService.retrieveById(receiver.getId());
            sender1.setBalance(sender1.getBalance().subtract(amount));
            receiver1.setBalance(receiver1.getBalance().add(amount));
            accountService.updateAccount(sender1);
            accountService.updateAccount(receiver1);

        }else{
            //not enough balance
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {
        /*
         write an if statement that check if one of the account is saving,
        and user if of sender or receiver is not the same, throw AccountOwnershipException
         */

        if((sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING)) &&
        !sender.getUserId().equals(receiver.getUserId())){
            throw new AccountOwnershipException("If one of the account is saving, userId must be the same");

        }
    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
    /*
    - if any of the account is null
    - if account ids are the same (same account)
    - if the account exist in the database (repository)
     */

        if (sender == null || receiver == null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        if(sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than receiver");
        }

        findAccountById(sender.getId());
        findAccountById(receiver.getId());

    }

    private AccountDTO findAccountById(Long id) {
       return mapper.convert(accountService.retrieveById(id),new AccountDTO());
    }

    @Override
    public List<TransactionDTO> findAllTransaction() {

        return transactionRepository.findAll().stream().map(transaction -> mapper.convert(transaction,new TransactionDTO())).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> lastTransactionsList() {

        return transactionRepository.findLastTenTransactions().stream().map(transaction -> mapper.convert(transaction,new TransactionDTO())).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
      return  transactionRepository.findTransactionListById(id).stream().map(transaction ->  mapper.convert(transaction, new TransactionDTO())).collect(Collectors.toList());


    }
}
