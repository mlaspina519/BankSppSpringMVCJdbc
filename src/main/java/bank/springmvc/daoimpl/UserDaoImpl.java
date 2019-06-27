package bank.springmvc.daoimpl;

import bank.springmvc.dao.UserDao;
import bank.springmvc.model.Customer;
import bank.springmvc.model.Employee;
import bank.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void removeUser(String userLogin) {
        jdbcTemplate.update("delete from bank_accounts where user_id = " + findUser(userLogin).getUserID());
        jdbcTemplate.update("delete from bank_users" + " where user_login='" + userLogin + "'");
    }

    // Updates a user with new first, last, login, pass
    // currentUserLogin is used to find the user to update
    @Override
    public void addUser(User user) {
        jdbcTemplate.update("insert into bank_users " + "values('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getUserLogin() + "', '" + user.getPassword()
                    + "', '" + user.getUserType() + "', '" + user.getUserID() + "')");
    }

    // Finds user based on login name, returns User object
    @Override
    public User findUser(String userLogin) {
        String userType;

        try {
            userType = jdbcTemplate.queryForObject("select user_type from bank_users where user_login = '" + userLogin + "'", String.class);
        } catch (Exception e) {
            return null;
        }

        // If user exists for userLogin, return Customer/Employee, else return null

        if(userType.equals("Customer")) {
            return jdbcTemplate.queryForObject("select * from bank_users where user_login = '" + userLogin + "'", (rs,columnNum) ->
                    new Customer(rs.getString("first_name"), rs.getString("last_name"), rs.getString("user_login"), rs.getString("user_pass"), rs.getInt("user_id")));
        } else if(userType.equals("Employee")) {
            return jdbcTemplate.queryForObject("select * from bank_users where user_login = '" + userLogin + "'", (rs,columnNum) ->
                    new Employee(rs.getString("first_name"), rs.getString("last_name"), rs.getString("user_login"), rs.getString("user_pass"), rs.getInt("user_id")));
        }

        return null;
    }

    @Override
    public void updateUserInfo(String first, String last, String userLogin, String pass, String currentUserLogin) {
        jdbcTemplate.update("update bank_users set first_name = ?, last_name = ?, user_login = ?, user_pass = ? where user_login = ?",
                first, last, userLogin, pass, currentUserLogin);
    }
}
