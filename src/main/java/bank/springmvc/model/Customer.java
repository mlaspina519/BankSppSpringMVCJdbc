package bank.springmvc.model;

public class Customer extends User {
    /**
     * Calls super (User) to create a Customer
     * @param first First name
     * @param last Last name
     * @param login User login
     * @param password User password
     * @param userID Unique user ID
     */
    public Customer(String first, String last, String login, String password, int userID) {
        super(first, last, login, password, "Customer", userID);
    }

    /**
     * Calls super (User) to create a Customer
     * @param first First name
     * @param last Last name
     * @param login User login
     * @param password User password
     */
    public Customer(String first, String last, String login, String password) {
        super(first, last, login, password, "Customer");
    }
}