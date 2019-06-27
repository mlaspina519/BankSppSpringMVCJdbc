package bank.springmvc.controller;

import bank.springmvc.BankingApplication;
import bank.springmvc.controllerimpl.ControllerMethods;
import bank.springmvc.dao.AccountDao;
import bank.springmvc.dao.UserDao;
import bank.springmvc.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
public class EmployeeController {

    @Autowired
    ControllerMethods controllerMethods;

    @Autowired
    AccountDao accountDao;

    @Autowired
    UserDao userDao;

    @GetMapping(value="/employee")
    public String showEmployeeMenu(Model model) {

        // Saves first name of user, and accounts user currently has
        String user = BankingApplication.CURRENT_USER.getFirstName();
        ArrayList<Account> accounts = accountDao.findAccountByUserID(BankingApplication.CURRENT_USER.getUserID());

        // Stores values to model to be displayed by JSP
        model.addAttribute("firstName", user);
        model.addAttribute("accounts", accounts);

        return "employee";
    }

    @PostMapping(value="updateEmployeeAccount")
    public String processEmployeeAction(@RequestParam BigDecimal amount, @RequestParam String account) {
        // Updates balanced of specified account for currently logged in user
        controllerMethods.updateAccountBalance(amount, account, BankingApplication.CURRENT_USER);

        return "redirect:/employee";
    }

    @PostMapping(value="manageEmployeeAccounts")
    public String manageEmployeeAccounts(@RequestParam String accountOperation) {

        // Opens/Closes specified account for currently logged in user
        controllerMethods.manageAccount(BankingApplication.CURRENT_USER, accountOperation);

        return "redirect:/employee";
    }

    @PostMapping(value="manageOtherAccounts")
    public String manageOtherAccounts(@RequestParam String customerLogin, @RequestParam String accountOperation) {

        // Opens/Closes specified account for currently logged in user
        //ControllerMethods.manageAccount(BankingApplication.USER_MANAGER.findUser(customerLogin), accountOperation);

        return "redirect:/employee";
    }

    @GetMapping(value="employeeTransactions")
    public String showEmployeeTransactions(Model model) {

        // Displays transactions for currently logged in user
        controllerMethods.displayTransactions(BankingApplication.CURRENT_USER, BankingApplication.CURRENT_USER, model);

        return "transactions";
    }

    @GetMapping(value="otherTransactions")
    public String showCustomerTransactions(Model model, @RequestParam String customerLogin) {

        // Displays transactions for customer
        controllerMethods.displayTransactions(BankingApplication.CURRENT_USER, userDao.findUser(customerLogin), model);

        return "transactions";
    }

    @PostMapping(value="updateEmployeeInfo")
    public String updateEmployeeInfo(@RequestParam String first, @RequestParam String last,
                                     @RequestParam String login, @RequestParam String pass) {

        // Updates Employee info
        controllerMethods.updateInfo(BankingApplication.CURRENT_USER, first, last, login, pass);

        return "redirect:/employee";
    }

    @PostMapping(value="updateInfoForCustomer")
    public String updateInfoForCustomer(@RequestParam String customer, @RequestParam String first, @RequestParam String last,
                                     @RequestParam String login, @RequestParam String pass) {

        // Updates Employee info
        controllerMethods.updateInfo(userDao.findUser(customer), first, last, login, pass);

        return "redirect:/employee";
    }

    @PostMapping(value="createCustomer")
    public String createCustomer(@RequestParam String first, @RequestParam String last,
                                        @RequestParam String login, @RequestParam String pass) {

        // Create new Customer
        controllerMethods.createCustomer(first, last, login, pass);

        return "redirect:/employee";
    }

    @PostMapping(value="removeCustomer")
    public String removeCustomer(@RequestParam String customer) {

        // Remove existing Customer
        //controllerMethods.removeCustomer(customer);

        return "redirect:/employee";
    }

    @PostMapping(value="employeeTransfer")
    public String employeeTransfer(@RequestParam BigDecimal amount, @RequestParam String operation,
                                   @RequestParam String transferUser) {

        // Transfers money to another User, or between accounts
        if(operation.equals("Transfer To User")) {
            controllerMethods.transferMoney(amount, transferUser);
        } else {
            controllerMethods.transferMoney(amount, operation);
        }

        return "redirect:/employee";
    }
}
