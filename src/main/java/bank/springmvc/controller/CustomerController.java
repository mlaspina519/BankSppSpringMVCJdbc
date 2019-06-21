package bank.springmvc.controller;

import bank.springmvc.controllerimpl.ControllerMethods;
import bank.springmvc.model.Account;
import bank.springmvc.model.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
public class CustomerController {

    @RequestMapping(value ="/customer", method = RequestMethod.GET)
    public String showCustomerMenu(Model model) {

        // Saves first name of user, accounts user currently has
        String user = BankingApplication.CURRENT_USER.getFirstName();
        ArrayList<Account> accounts = BankingApplication.ACCOUNT_MANAGER.findAccountByUserID(BankingApplication.CURRENT_USER.getUserID());

        // Stores values to model to be displayed by JSP
        model.addAttribute("firstName", user);
        model.addAttribute("accounts", accounts);

        return "customer";
    }

    @RequestMapping(value="/updateCustomerAccount", method = RequestMethod.POST)
    public String updateCustomerAccount(@RequestParam BigDecimal amount, @RequestParam String account) {

        // Updates balanced of specified account for currently logged in user
        ControllerMethods.updateAccountBalance(amount, account, BankingApplication.CURRENT_USER);

        return "redirect:/customer";
    }

    @RequestMapping(value="/manageCustomerAccounts", method = RequestMethod.POST)
    public String manageCustomerAccounts(@RequestParam String accountOperation) {

        // Opens/Closes specified account for currently logged in user
        ControllerMethods.manageAccount(BankingApplication.CURRENT_USER, accountOperation);

        return "redirect:/customer";
    }

    @RequestMapping(value="/customerTransactions", method = RequestMethod.GET)
    public String showCustomerTransactions(Model model) {

        // Displays transactions for currently logged in user
        ControllerMethods.displayTransactions(BankingApplication.CURRENT_USER, BankingApplication.CURRENT_USER, model);

        return "transactions";
    }

    @RequestMapping(value="/updateCustomerInfo", method = RequestMethod.POST)
    public String updateCustomerInfo(@RequestParam String first, @RequestParam String last,
                                     @RequestParam String login, @RequestParam String pass) {

        // Updates customer info
        ControllerMethods.updateInfo(BankingApplication.CURRENT_USER, first, last, login, pass);

        return "redirect:/customer";
    }

    @RequestMapping(value="/customerTransfer", method = RequestMethod.POST)
    public String customerTransfer(@RequestParam BigDecimal amount, @RequestParam String operation,
                                   @RequestParam String transferUser) {

        // Transfers money to another User, or between accounts
        if(operation.equals("Transfer To User")) {
            ControllerMethods.transferMoney(amount, transferUser);
        } else {
            ControllerMethods.transferMoney(amount, operation);
        }

        return "redirect:/customer";
    }
}
