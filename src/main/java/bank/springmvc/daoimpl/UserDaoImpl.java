package bank.springmvc.daoimpl;

import bank.springmvc.dao.UserDao;
import bank.springmvc.model.Customer;
import bank.springmvc.model.Employee;
import bank.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Contains methods to act upon Users in the Bank
 * Remove, Add, Find, Update
 */
@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Removes a User from the Bank
     * Deletes their Accounts, and then deletes the User
     * @param userLogin Login name of the user to be removed
     */
    @Override
    public void removeUser(String userLogin) {
        jdbcTemplate.update(
                "DELETE FROM bank_accounts " +
                "where user_id = " + findUser(userLogin).getUserID());
        jdbcTemplate.update(
                "DELETE FROM bank_users " +
                "WHERE user_login='" + userLogin + "'");
    }

    /**
     * Adds a User to the Bank
     * @param user User object to be added
     */
    @Override
    public void addUser(User user) {
        jdbcTemplate.update(
                "INSERT INTO bank_users " +
                "VALUES('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getUserLogin() + "', '" + user.getPassword()
                + "', '" + user.getUserType() + "', '" + user.getUserID() + "')");
    }

    /**
     * Finds a User based on specified user login
     * @param userLogin Login name of User to be looked up
     * @return User object if found, null if not
     */
    @Override
    public User findUser(String userLogin) {
        String userType;

        try {
            // Gets user type (Customer/Employee) so that object may be created, as User is null
            userType = jdbcTemplate.queryForObject(
                    "SELECT user_type " +
                    "FROM bank_users " +
                    "WHERE user_login = '" + userLogin + "'",
                    String.class);

        } catch (Exception e) {
            return null;
        }

        // If User exists for userLogin, return new Customer or Employee, else return null
        if (userType != null) {
            if(userType.equals("Customer")) {

                // Return new Customer
                return jdbcTemplate.queryForObject(
                        "SELECT * " +
                        "FROM bank_users " +
                        "WHERE user_login = '" + userLogin + "'",
                        (rs,columnNum) -> new Customer(rs.getString("first_name"), rs.getString("last_name"), rs.getString("user_login"), rs.getString("user_pass"), rs.getInt("user_id")));
            } else if(userType.equals("Employee")) {

                // Return new Employee
                return jdbcTemplate.queryForObject(
                        "SELECT * " +
                        "FROM bank_users " +
                        "WHERE user_login = '" + userLogin + "'",
                        (rs,columnNum) -> new Employee(rs.getString("first_name"), rs.getString("last_name"), rs.getString("user_login"), rs.getString("user_pass"), rs.getInt("user_id")));
            }
        }

        return null;
    }

    /**
     * Allows a User to update all of their personal info
     * Cannot update User ID (identifier value) or User Type (Customer/Employee)
     * @param first New first name
     * @param last New last name
     * @param userLogin New login
     * @param pass New password
     * @param currentUserLogin Current user login (this is how the current user is found)
     */
    @Override
    public void updateUserInfo(String first, String last, String userLogin, String pass, String currentUserLogin) {
        jdbcTemplate.update(
                "UPDATE bank_users " +
                "SET first_name = ?, last_name = ?, user_login = ?, user_pass = ? " +
                "WHERE user_login = ?",
                first, last, userLogin, pass, currentUserLogin);
    }
}