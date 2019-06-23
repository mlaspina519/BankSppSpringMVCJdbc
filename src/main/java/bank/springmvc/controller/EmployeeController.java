package bank.springmvc.controller;

import bank.springmvc.BankingApplication;
import bank.springmvc.controllerimpl.ControllerMethods;
import bank.springmvc.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
public class EmployeeController {

    @GetMapping(value="/employee")
    public String showEmployeeMenu(Model model) {

        // Saves first name of user, and accounts user currently has
        String user = BankingApplication.CURRENT_USER.getFirstName();
        ArrayList<Account> accounts = BankingApplication.ACCOUNT_MANAGER.findAccountByUserID(BankingApplication.CURRENT_USER.getUserID());

        // Stores values to model to be displayed by JSP
        model.addAttribute("firstName", user);
        model.addAttribute("accounts", accounts);

        return "employee";
    }

    @PostMapping(value="updateEmployeeAccount")
    public String processEmployeeAction(@RequestParam BigDecimal amount, @RequestParam String account) {
        // Updates balanced of specified account for currently logged in user
        ControllerMethods.updateAccountBalance(amount, account, BankingApplication.CURRENT_USER);

        return "redirect:/employee";
    }

    @PostMapping(value="manageEmployeeAccounts")
    public String manageEmployeeAccounts(@RequestParam String accountOperation) {

        // Opens/Closes specified account for currently logged in user
        ControllerMethods.manageAccount(BankingApplication.CURRENT_USER, accountOperation);

        return "redirect:/employee";
    }

    @PostMapping(value="manageOtherAccounts")
    public String manageOtherAccounts(@RequestParam String customerLogin, @RequestParam String accountOperation) {

        // Opens/Closes specified account for currently logged in user
        ControllerMethods.manageAccount(BankingApplication.USER_MANAGER.findUser(customerLogin), accountOperation);

        return "redirect:/employee";
    }

    @GetMapping(value="employeeTransactions")
    public String showEmployeeTransactions(Model model) {

        // Displays transactions for currently logged in user
        ControllerMethods.displayTransactions(BankingApplication.CURRENT_USER, BankingApplication.CURRENT_USER, model);

        return "transactions";
    }

    @GetMapping(value="otherTransactions")
    public String showCustomerTransactions(Model model, @RequestParam String customerLogin) {

        // Displays transactions for customer
        ControllerMethods.displayTransactions(BankingApplication.CURRENT_USER, BankingApplication.USER_MANAGER.findUser(customerLogin), model);

        return "transactions";
    }

    @PostMapping(value="updateEmployeeInfo")
    public String updateEmployeeInfo(@RequestParam String first, @RequestParam String last,
                                     @RequestParam String login, @RequestParam String pass) {

        // Updates Employee info
        ControllerMethods.updateInfo(BankingApplication.CURRENT_USER, first, last, login, pass);

        return "redirect:/employee";
    }

    @PostMapping(value="updateInfoForCustomer")
    public String updateInfoForCustomer(@RequestParam String customer, @RequestParam String first, @RequestParam String last,
                                     @RequestParam String login, @RequestParam String pass) {

        // Updates Employee info
        ControllerMethods.updateInfo(BankingApplication.USER_MANAGER.findUser(customer), first, last, login, pass);

        return "redirect:/employee";
    }

    @PostMapping(value="createCustomer")
    public String createCustomer(@RequestParam String first, @RequestParam String last,
                                        @RequestParam String login, @RequestParam String pass) {

        // Create new Customer
        ControllerMethods.createCustomer(first, last, login, pass);

        return "redirect:/employee";
    }

    @PostMapping(value="removeCustomer")
    public String removeCustomer(@RequestParam String customer) {

        // Remove existing Customer
        ControllerMethods.removeCustomer(customer);

        return "redirect:/employee";
    }

    @PostMapping(value="employeeTransfer")
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
