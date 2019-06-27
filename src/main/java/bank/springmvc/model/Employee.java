package bank.springmvc.model;

public class Employee extends User{
    /**
     * Calls super (User) to create an Employee
     * @param first First name
     * @param last Last name
     * @param login User login
     * @param password User password
     */
    public Employee(String first, String last, String login, String password) {
        super(first, last, login, password, "Employee");
    }

    /**
     * Calls super (User) to create an Employee
     * @param first First name
     * @param last Last name
     * @param login User login
     * @param password User password
     * @param userID Unique user ID
     */
    public Employee(String first, String last, String login, String password, int userID) {
        super(first, last, login, password, "Employee", userID);
    }
}