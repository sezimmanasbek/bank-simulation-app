package com.cydeo.service;

import com.cydeo.enums.AccountType;
import com.cydeo.dto.AccountDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public interface AccountService {

    void createNewAccount(AccountDTO accountDTO);

    List<AccountDTO> listAllAccount();

    void deleteById(Long id);

    AccountDTO retrieveById(Long id);

    List<AccountDTO> listAllActiveAccounts();

    void updateAccount(AccountDTO sender1);
}
