package bank.springmvc.model;

public class Customer extends User {
    public Customer(String first, String last, String login, String password, int userID) {
        super(first, last, login, password, "Customer", userID);
    }

    public Customer(String first, String last, String login, String password) {
        super(first, last, login, password, "Customer");
    }
}
