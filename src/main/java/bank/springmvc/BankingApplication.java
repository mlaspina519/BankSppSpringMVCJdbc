package bank.springmvc;

import bank.springmvc.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BankingApplication extends SpringBootServletInitializer {
    public static User CURRENT_USER;          // User currently logged in to bank

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }
}