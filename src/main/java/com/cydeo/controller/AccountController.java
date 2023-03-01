package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.dto.AccountDTO;
import com.cydeo.service.AccountService;
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
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("accountList", accountService.listAllAccount());
        return "account/index";
    }

    @GetMapping("create-form")
    public String createAccount(Model model){
        model.addAttribute("account", new AccountDTO());
        model.addAttribute("accountTypes", AccountType.values());

        return "account/create-account";
    }

    @PostMapping("/create")
    public String insertAccount(@Valid AccountDTO account, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){

            model.addAttribute("accountTypes", AccountType.values());
            return "account/create-account";
        }
        accountService.createNewAccount(account);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Long id){
       accountService.deleteById(id);

        return "redirect:/index";

    }
}
