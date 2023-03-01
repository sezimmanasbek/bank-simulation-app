package com.cydeo.service.impl;

import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.dto.AccountDTO;
import com.cydeo.mapper.BaseMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    BaseMapper mapper;

    public AccountServiceImpl(AccountRepository accountRepository, BaseMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {

        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        accountDTO.setCreationDate(new Date());

        accountRepository.save(mapper.convert(accountDTO,new Account()));
    }

    @Override
    public List<AccountDTO> listAllAccount() {

        return accountRepository.findAll().stream().map(a->mapper.convert(a,new AccountDTO())).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id).get();
        account.setAccountStatus(AccountStatus.DELETED);
        accountRepository.save(account);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        return mapper.convert(accountRepository.findById(id),new AccountDTO());
    }

    @Override
    public List<AccountDTO> listAllActiveAccounts() {
        return accountRepository.findAllByAccountStatus_Active().stream().map(a-> mapper.convert(a,new AccountDTO())).collect(Collectors.toList());
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        accountRepository.save(mapper.convert(accountDTO,new Account()));
    }
}
