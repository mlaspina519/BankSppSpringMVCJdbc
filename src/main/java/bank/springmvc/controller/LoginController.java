package bank.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

  @RequestMapping(value = "/login", method = RequestMethod.GET)
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



//  @RequestMapping(value = "/login", method = RequestMethod.GET)
//  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
//    ModelAndView mav = new ModelAndView("login");
//    mav.addObject("login", new Login());
//    return mav;
//  }
//
//  @RequestMapping(value = "/login", method = RequestMethod.POST)
//  public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
//                                   @ModelAttribute("login") Login login) {
//    // TODO: implement differently
//    BankingApplication.startup();
//
//    ModelAndView mav;
//
//    if(BankingApplication.USER_MANAGER.findUser(login.getUsername()) != null) {
//      mav = new ModelAndView("welcome");
//      mav.addObject(login.getUsername());
//
//    } else {
//      mav = new ModelAndView("login");
//      mav.addObject("message", "username/password incorrect");
//    }
//
//    return mav;
//
//  }

}
