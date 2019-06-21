package bank.springmvc.controller;

import bank.springmvc.controllerimpl.ControllerMethods;
import bank.springmvc.model.Account;
import bank.springmvc.model.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller

public class EmployeeController {

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String showEmployeeMenu(Model model) {

        // Saves first name of user, and accounts user currently has
        String user = BankingApplication.CURRENT_USER.getFirstName();
        ArrayList<Account> accounts = BankingApplication.ACCOUNT_MANAGER.findAccountByUserID(BankingApplication.CURRENT_USER.getUserID());

        // Stores values to model to be displayed by JSP
        model.addAttribute("firstName", user);
        model.addAttribute("accounts", accounts);

        return "employee";
    }

    @RequestMapping(value= "updateEmployeeAccount", method = RequestMethod.POST)
    public String processEmployeeAction(@RequestParam BigDecimal amount, @RequestParam String account) {
        // Updates balanced of specified account for currently logged in user
        ControllerMethods.updateAccountBalance(amount, account, BankingApplication.CURRENT_USER);

        return "redirect:/employee";
    }

    @RequestMapping(value="/manageEmployeeAccounts", method = RequestMethod.POST)
    public String manageEmployeeAccounts(@RequestParam String accountOperation) {

        // Opens/Closes specified account for currently logged in user
        ControllerMethods.manageAccount(BankingApplication.CURRENT_USER, accountOperation);

        return "redirect:/employee";
    }

    @RequestMapping(value="/manageOtherAccounts", method = RequestMethod.POST)
    public String manageOtherAccounts(@RequestParam String customerLogin, @RequestParam String accountOperation) {

        // Opens/Closes specified account for currently logged in user
        ControllerMethods.manageAccount(BankingApplication.USER_MANAGER.findUser(customerLogin), accountOperation);

        return "redirect:/employee";
    }

    @RequestMapping(value="/employeeTransactions", method = RequestMethod.GET)
    public String showEmployeeTransactions(Model model) {

        // Displays transactions for currently logged in user
        ControllerMethods.displayTransactions(BankingApplication.CURRENT_USER, BankingApplication.CURRENT_USER, model);

        return "transactions";
    }

    @RequestMapping(value="/otherTransactions", method = RequestMethod.GET)
    public String showCustomerTransactions(Model model, @RequestParam String customerLogin) {

        // Displays transactions for customer
        ControllerMethods.displayTransactions(BankingApplication.CURRENT_USER, BankingApplication.USER_MANAGER.findUser(customerLogin), model);

        return "transactions";
    }

    @RequestMapping(value="/updateEmployeeInfo", method = RequestMethod.POST)
    public String updateEmployeeInfo(@RequestParam String first, @RequestParam String last,
                                     @RequestParam String login, @RequestParam String pass) {

        // Updates Employee info
        ControllerMethods.updateInfo(BankingApplication.CURRENT_USER, first, last, login, pass);

        return "redirect:/employee";
    }

    @RequestMapping(value="/updateInfoForCustomer", method = RequestMethod.POST)
    public String updateInfoForCustomer(@RequestParam String customer, @RequestParam String first, @RequestParam String last,
                                     @RequestParam String login, @RequestParam String pass) {

        // Updates Employee info
        ControllerMethods.updateInfo(BankingApplication.USER_MANAGER.findUser(customer), first, last, login, pass);

        return "redirect:/employee";
    }

    @RequestMapping(value="/createCustomer", method = RequestMethod.POST)
    public String createCustomer(@RequestParam String first, @RequestParam String last,
                                        @RequestParam String login, @RequestParam String pass) {

        // Create new Customer
        ControllerMethods.createCustomer(first, last, login, pass);

        return "redirect:/employee";
    }

    @RequestMapping(value="/removeCustomer", method = RequestMethod.POST)
    public String removeCustomer(@RequestParam String customer) {

        // Remove existing Customer
        ControllerMethods.removeCustomer(customer);

        return "redirect:/employee";
    }

    @RequestMapping(value="/employeeTransfer", method = RequestMethod.POST)
    public String employeeTransfer(@RequestParam BigDecimal amount, @RequestParam String operation,
                                   @RequestParam String transferUser) {

        // Transfers money to another User, or between accounts
        if(operation.equals("Transfer To User")) {
            ControllerMethods.transferMoney(amount, transferUser);
        } else {
            ControllerMethods.transferMoney(amount, operation);
        }

        return "redirect:/employee";
    }
}
