package com.cydeo.controller;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("make-transfer")
    public String makeTransfer(Model model){

        model.addAttribute("accounts",accountService.listAllActiveAccounts());
        model.addAttribute("transaction", new TransactionDTO());
        model.addAttribute("lastTransactions",transactionService.lastTransactionsList());


        return "/transaction/make-transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@Valid TransactionDTO transaction, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("accounts",accountService.listAllActiveAccounts());
            return "/transaction/make-transfer";
        }

        transactionService.makeTransfer(accountService.retrieveById(transaction.getSender().getId()), accountService.retrieveById(transaction.getReceiver().getId()),
        transaction.getAmount(),new Date(), transaction.getMessage());

        return "redirect:/make-transfer";

    }

    @GetMapping("/transaction/{id}")
    public String getTransactions(@PathVariable("id")Long id,Model model){

        System.out.println(id);
        model.addAttribute("transactions", transactionService.findTransactionListById(id));
        return "/transaction/transactions";
    }
}
