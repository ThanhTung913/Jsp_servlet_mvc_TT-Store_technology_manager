package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    void inserUser(User user) throws SQLException, ClassNotFoundException;

    User selectUserById(int id) throws SQLException, ClassNotFoundException;

    List<User> selectAllUsers() throws SQLException, ClassNotFoundException;

    List<User> selectUserActive();

    List<User> selectUserUnactive() throws ClassNotFoundException;

    boolean deleteUser(int id) throws SQLException, ClassNotFoundException;

    boolean updateUser(User user) throws SQLException, ClassNotFoundException;

    User selectUserByEmail(String email) throws SQLException, ClassNotFoundException;

    User selectUserByPhone(String phone) throws SQLException, ClassNotFoundException;

    User selectUserByUserName(String username) throws SQLException, ClassNotFoundException;

    List<User> getNumberPage(int offset, int noOfRecords) throws SQLException, ClassNotFoundException;

    List<User> getNumberPage(int offset, int noOfRecords, String name) throws ClassNotFoundException, SQLException;

    List<User> getNumberPageActive(int offset, int noOfRecords, String name) throws ClassNotFoundException, SQLException;

    List<User> getNumberPageUnActive(int offset, int noOfRecords, String name) throws ClassNotFoundException, SQLException;

    int getNoOfRecords();

    List<User> searchByName(String name) throws SQLException, ClassNotFoundException;

    List<User> searchUserActive(String name) throws SQLException, ClassNotFoundException;

    List<User> searchUserUnActive(String name) throws SQLException, ClassNotFoundException;

    User unactiveUser(int id) throws SQLException, ClassNotFoundException;

    User activeUser(int id) throws SQLException, ClassNotFoundException;

    boolean checkDuplicatePhone(String phone) throws SQLException, ClassNotFoundException;

    boolean checkDuplicateEmail(String email) throws SQLException, ClassNotFoundException;

    boolean checkDuplicateUserName(String userName) throws SQLException, ClassNotFoundException;

    boolean checkDuplicateById(int id) throws SQLException, ClassNotFoundException;

}
