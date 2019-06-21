package bank.springmvc.model;

public class Employee extends User{
    public Employee(String first, String last, String login, String password) {
        super(first, last, login, password, "Employee");
    }

    public Employee(String first, String last, String login, String password, int userID) {
        super(first, last, login, password, "Employee", userID);
    }
}
