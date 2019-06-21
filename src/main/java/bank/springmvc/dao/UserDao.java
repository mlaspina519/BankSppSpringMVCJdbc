package bank.springmvc.dao;

import bank.springmvc.model.User;

public interface UserDao {
    void removeUser(String userLogin);
    void addUser(User user);
    User findUser(String userLogin);
    boolean updateUserInfo(String first, String last, String userLogin, String pass, String currentUserLogin);
}
