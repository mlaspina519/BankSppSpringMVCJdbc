package bank.springmvc.controller;

import bank.springmvc.BankingApplication;
import bank.springmvc.controllerimpl.BankServices;
import bank.springmvc.dao.AccountDao;
import bank.springmvc.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
public class CustomerController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    BankServices bankServices;

    @GetMapping(value="/customer")
    public String showCustomerMenu(Model model) {

        // Saves first name of user, accounts user currently has
        String user = BankingApplication.CURRENT_USER.getFirstName();
        ArrayList<Account> accounts = accountDao.findAccountByUserID(BankingApplication.CURRENT_USER.getUserID());

        // Stores values to model to be displayed by JSP
        model.addAttribute("firstName", user);
        model.addAttribute("accounts", accounts);

        return "customer";
    }

    @PostMapping(value="/updateCustomerAccount")
    public String updateCustomerAccount(@RequestParam BigDecimal amount, @RequestParam String account) {

        // Updates balanced of specified account for currently logged in user
        bankServices.updateAccountBalance(amount, account, BankingApplication.CURRENT_USER);

        return "redirect:/customer";
    }

    @PostMapping(value="/manageCustomerAccounts")
    public String manageCustomerAccounts(@RequestParam String accountOperation) {

        // Opens/Closes specified account for currently logged in user
        bankServices.manageAccount(BankingApplication.CURRENT_USER, accountOperation);

        return "redirect:/customer";
    }

    @GetMapping(value="/customerTransactions")
    public String showCustomerTransactions(Model model) {

        // Displays transactions for currently logged in user
        bankServices.displayTransactions(BankingApplication.CURRENT_USER, BankingApplication.CURRENT_USER, model);

        return "transactions";
    }

    @PostMapping(value="/updateCustomerInfo")
    public String updateCustomerInfo(@RequestParam String first, @RequestParam String last,
                                     @RequestParam String login, @RequestParam String pass) {

        // Updates customer info
        bankServices.updateInfo(BankingApplication.CURRENT_USER, first, last, login, pass);

        return "redirect:/customer";
    }

    @PostMapping(value="/customerTransfer")
    public String customerTransfer(@RequestParam BigDecimal amount, @RequestParam String operation,
                                   @RequestParam String transferUser) {

        // Transfers money to another User, or between accounts
        if(operation.equals("Transfer To User")) {
            bankServices.transferMoney(amount, transferUser);
        } else {
            bankServices.transferMoney(amount, operation);
        }

        return "redirect:/customer";
    }
}
