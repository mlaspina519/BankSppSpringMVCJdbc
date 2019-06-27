package bank.springmvc.controller;

import bank.springmvc.BankingApplication;
import bank.springmvc.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

  @Autowired
  UserDao userDao;

  @GetMapping(value="/login")
  public String showLogin() {

    return "login";
  }

  @PostMapping(value = "/login")
  public String loginProcess(@RequestParam String user, @RequestParam String pass) {

    // Checks that login/password aren't empty values
    // Validates the login information with Oracle DB
    // If they exist, redirect to appropriate menu (Employee/Customer)
    // Else, return to login screen
    if(!user.equals("") && !pass.equals("")) {
        if(validateUser(user, pass)) {
          // If user exists, sets Current User
          BankingApplication.CURRENT_USER = userDao.findUser(user);


          // Sends user to a menu page based on Employee/Customer type
          if(BankingApplication.CURRENT_USER.getUserType().equals("Employee")) {
            return "redirect:/employee";
          } else if(BankingApplication.CURRENT_USER.getUserType().equals("Customer")) {
            return "redirect:/customer";
          }
        }
    }

    // Reload login page
    return "login";
  }

  // Searches DB for username/password combo, true if it exists, false otherwise
  private boolean validateUser(String login, String pass) {
    return userDao.findUser(login) != null && userDao.findUser(login).getPassword().equals(pass);
  }
}
