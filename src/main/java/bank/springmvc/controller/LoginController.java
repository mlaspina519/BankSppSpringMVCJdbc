package bank.springmvc.controller;

import bank.springmvc.BankingApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

  @GetMapping(value="/login")
  public String showLogin() {

    // Initializes static variables for Bank App to run
    // TODO: Find a better way to implement this
    if(!BankingApplication.initialized) {
      BankingApplication.startup();
    }

    return "login";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String loginProcess(@RequestParam String user, @RequestParam String pass) {

    // Checks that login/password aren't empty values
    // Validates the login information with Oracle DB
    // If they exist, redirect to appropriate menu (Employee/Customer)
    // Else, return to login screen
    if(!user.equals("") && !pass.equals("")) {
      if(validateUser(user, pass)) {
        // If user exists, sets Current User
        BankingApplication.CURRENT_USER = BankingApplication.USER_MANAGER.findUser(user);

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
    return BankingApplication.USER_MANAGER.findUser(login) != null &&
            BankingApplication.USER_MANAGER.findUser(login).getPassword().equals(pass);
  }

}
