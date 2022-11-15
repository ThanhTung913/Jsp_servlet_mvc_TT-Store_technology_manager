package dao;

import model.User;
import ultils.SortUserByIdDESC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/TTSTORE?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";

    private final String INSERT_USER_SQL = "INSERT INTO `TTSTORE`.`Users` (`name`, `username`, `password`, `phone`, `email`, `address`, `idRole`, `idStatus`) VALUES (?, ?, ?, ?, ?, ?, ?,?);";
    private final String SELECT_USER_BY_ID = "SELECT id,name,username,password,phone,email,address,idRole,idStatus FROM Users WHERE id = ?;";
    private final String SELECT_ALL_USER = "SELECT id,name,username,password,phone,email,address,idRole,idStatus FROM Users;";
    private final String DELETE_USER_SQL = "DELETE FROM Users WHERE id =?;";
    private final String UPDATE_USER_SQL = "UPDATE Users SET name = ?, username = ?, password = ?,phone =?,email = ?, address = ?, idRole = ?, idStatus =? WHERE id = ?;";
    private final String ACTIVE_USER_SQL = "UPDATE Users SET  idStatus = 1 WHERE id = ?;";
    private final String UNACTIVE_USER_SQL = "UPDATE Users SET  idStatus = 2 WHERE id = ?;";
    private final String SELECT_USERACTIVE = "select * from item where statusItem = 'ACTIVE';";
    private final String SELECT_USERUNACTIVE = "select * from item where statusItem = 'ACTIVE';";
    private final String SELECT_USER_BY_EMAIL = "SELECT u.id, u.name, u.username, u.password, u.phone, u.email, u.address, u.idRole, u.idStatus\n" +
            "FROM Users AS u INNER JOIN status AS s ON u.idStatus = s.id \n" +
            "WHERE u.email =? ;";

    private final String SELECT_USER_BY_PHONE = "SELECT u.id, u.name, u.username, u.password, u.phone, u.email, u.address, u.idRole, u.idStatus\n" +
            "FROM Users AS u INNER JOIN status AS s ON u.idStatus = s.id \n" +
            "WHERE u.phone =? ;";

    private final String SELECT_USER_BY_USERNAME = "SELECT u.id, u.name, u.username, u.password, u.phone, u.email, u.address, u.idRole, u.idStatus\n" +
            "FROM Users AS u INNER JOIN status AS s ON u.idStatus = s.id \n" +
            "WHERE u.username =? ;";

    public UserDAO() {
    }

    private int noOfRecords;

    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return connection;
    }


//    public boolean checkDuplicateUsername(String username) throws SQLException, ClassNotFoundException {
//        List<User> usernames = selectAllUsers();
//        for (User name : usernames) {
//            if (name.getName().toLowerCase().equals(username.toLowerCase()))
//                return true;
//        }
//        return false;
//    }

    @Override
    public void inserUser(User user) throws SQLException, ClassNotFoundException {
        boolean success = false;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getPhone());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getAddress());
        preparedStatement.setInt(7, user.getRole());
        preparedStatement.setInt(8, user.getStatus());
//        System.out.println(this.getClass() + " inserUser(): query: " + preparedStatement);
        preparedStatement.executeUpdate();
        connection.close();
    }

    @Override
    public User selectUserById(int id) throws SQLException, ClassNotFoundException {
        User user = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                int role = Integer.parseInt(resultSet.getString("idRole"));
                int status = Integer.parseInt(resultSet.getString("idStatus"));
                user = new User(id, name, username, password, email, phone, address, role, status);
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() throws SQLException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                int role = Integer.parseInt(resultSet.getString("idRole"));
                int status = Integer.parseInt(resultSet.getString("idStatus"));
                users.add(new User(id, name, username, password, email, phone, address, role, status));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        users.sort(new SortUserByIdDESC());
        return users;
    }

    @Override
    public List<User> selectUserActive() {
        List<User> listUserActive = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERUNACTIVE)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                int role = Integer.parseInt(resultSet.getString("idRole"));
                int status = Integer.parseInt(resultSet.getString("idStatus"));
                listUserActive.add(new User(id, name, username, password, email, phone, address, role, status));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listUserActive;
    }

    @Override
    public List<User> selectUserUnactive() throws ClassNotFoundException {
        List<User> listUserUnActive = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERUNACTIVE)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                int role = Integer.parseInt(resultSet.getString("idRole"));
                int status = Integer.parseInt(resultSet.getString("idStatus"));
                listUserUnActive.add(new User(id, name, username, password, email, phone, address, role, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUserUnActive;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException, ClassNotFoundException {
        boolean rowDelete = false;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL);
            preparedStatement.setInt(1, id);
            rowDelete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }

        return rowDelete;
    }

    @Override
    public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        boolean success = false;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setInt(7, user.getRole());
            preparedStatement.setInt(8, user.getStatus());
            preparedStatement.setInt(9, user.getId());
            preparedStatement.executeUpdate();
            success = true;
        } catch (SQLException e) {
            printSQLException(e);
        }
//        return false;
        return success;
    }

    @Override
    public User selectUserByEmail(String email) throws SQLException, ClassNotFoundException {
        User user = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                //String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                int role = Integer.parseInt(resultSet.getString("idRole"));
                int status = Integer.parseInt(resultSet.getString("idStatus"));
                user = new User(id, name, username, password, email, phone, address, role, status);
                return user;
            }
            connection.close();
            return null;
        } catch (SQLException e) {

            printSQLException(e);
            return null;
        }
    }

    @Override
    public User selectUserByPhone(String phone) throws SQLException, ClassNotFoundException {
        User user = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_PHONE);
            preparedStatement.setString(1, phone);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
//                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                int role = Integer.parseInt(resultSet.getString("idRole"));
                int status = Integer.parseInt(resultSet.getString("idStatus"));
                user = new User(id, name, username, password, email, phone, address, role, status);
                return user;
            }
            return null;
        } catch (SQLException e) {
            printSQLException(e);
            return null;
        }
    }

    @Override
    public User selectUserByUserName(String username) throws SQLException, ClassNotFoundException {
        User user = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME);
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
//                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                int role = Integer.parseInt(resultSet.getString("idRole"));
                int status = Integer.parseInt(resultSet.getString("idStatus"));
                user = new User(id, name, username, password, email, phone, address, role, status);
                return user;
            }
            return null;
        } catch (SQLException e) {
            printSQLException(e);
            return null;
        }
    }


    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public List<User> getNumberPage(int offset, int noOfRecords) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
//
        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM Users limit " + offset + "," + noOfRecords;
        List<User> listUser = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setPhone(resultSet.getString("phone"));
            user.setAddress(resultSet.getString("address"));
            user.setRole(resultSet.getInt("idRole"));
            user.setStatus(resultSet.getInt("idStatus"));
            listUser.add(user);
        }
        resultSet = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
        if (resultSet.next()) {
            this.noOfRecords = resultSet.getInt(1);
        }
        connection.close();
        return listUser;
    }

    public List<User> getNumberPage(int offset, int noOfRecords, String name) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        System.out.println("numberpage");

        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM Users where name LIKE ? OR email LIKE ? OR username LIKE ? limit " + offset + ", " + noOfRecords;
        List<User> list = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, '%' + name + '%');
        ps.setString(2, '%' + name + '%');
        ps.setString(3, '%' + name + '%');

        System.out.println(this.getClass() + " getNumberPage() query: " + ps);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setAddress(rs.getString("address"));
            user.setRole(rs.getInt("idRole"));
            user.setStatus(rs.getInt("idStatus"));
            list.add(user);

        }
        rs = ps.executeQuery("SELECT FOUND_ROWS()");
        if (rs.next()) {
            this.noOfRecords = rs.getInt(1);
        }
        connection.close();
        return list;
    }

    @Override
    public List<User> getNumberPageUnActive(int offset, int noOfRecords, String name) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        System.out.println("numberpage");

        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM Users WHERE (name LIKE ? OR email LIKE ? OR username LIKE ?) AND idStatus=2 limit " + offset + ", " + noOfRecords;
        List<User> list = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, '%' + name + '%');
        ps.setString(2, '%' + name + '%');
        ps.setString(3, '%' + name + '%');

        System.out.println(this.getClass() + " getNumberPage() query: " + ps);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setAddress(rs.getString("address"));
            user.setRole(rs.getInt("idRole"));
            user.setStatus(rs.getInt("idStatus"));
            list.add(user);

        }
        rs = ps.executeQuery("SELECT FOUND_ROWS()");
        if (rs.next()) {
            this.noOfRecords = rs.getInt(1);
        }
        connection.close();
        return list;
    }

    @Override
    public List<User> getNumberPageActive(int offset, int noOfRecords, String name) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        System.out.println("numberpage");

        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM Users WHERE (name LIKE ? OR email LIKE ? OR username LIKE ?) AND idStatus=2 limit " + offset + ", " + noOfRecords;
        List<User> list = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, '%' + name + '%');
        ps.setString(2, '%' + name + '%');
        ps.setString(3, '%' + name + '%');

        System.out.println(this.getClass() + " getNumberPage() query: " + ps);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setAddress(rs.getString("address"));
            user.setRole(rs.getInt("idRole"));
            user.setStatus(rs.getInt("idStatus"));
            list.add(user);

        }
        rs = ps.executeQuery("SELECT FOUND_ROWS()");
        if (rs.next()) {
            this.noOfRecords = rs.getInt(1);
        }
        connection.close();
        return list;
    }

    @Override
    public List<User> searchByName(String name) throws SQLException, ClassNotFoundException {
        List<User> listUser = selectAllUsers();
        List<User> listSearch = new ArrayList<>();

        for (User user : listUser) {
            if (user.getName().toLowerCase().contains(name.toLowerCase())) {
                listSearch.add(user);
            }
        }
        return listSearch;
    }

    @Override
    public List<User> searchUserActive(String name) throws SQLException, ClassNotFoundException {
        List<User> listUserActive = selectUserActive();
        List<User> listSearch = new ArrayList<>();

        for (User user : listUserActive) {
            if (user.getName().toLowerCase().contains(name.toLowerCase())) {
                listSearch.add(user);
            }
        }
        return listSearch;
    }

    @Override
    public List<User> searchUserUnActive(String name) throws SQLException, ClassNotFoundException {
        List<User> listUserUnActive = selectAllUsers();
        List<User> listSearch = new ArrayList<>();

        for (User user : listUserUnActive) {
            if (user.getName().toLowerCase().contains(name.toLowerCase())) {
                listSearch.add(user);
            }
        }
        return listSearch;
    }

    @Override
    public User unactiveUser(int id) throws SQLException, ClassNotFoundException {
        User user = selectUserById(id);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UNACTIVE_USER_SQL);
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User activeUser(int id) throws SQLException, ClassNotFoundException {
        User user = selectUserById(id);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ACTIVE_USER_SQL);
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean checkDuplicatePhone(String phone) throws SQLException, ClassNotFoundException {
        List<User> listUser = selectAllUsers();
        for (User user : listUser) {
            if (user.getPhone().equals(phone.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean checkDuplicateEmail(String email) throws SQLException, ClassNotFoundException {
        List<User> listUser = selectAllUsers();
        for (User user : listUser) {
            if (user.getEmail().equals(email.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkDuplicateUserName(String userName) throws SQLException, ClassNotFoundException {
        List<User> listUser = selectAllUsers();
        for (User user : listUser) {
            if (user.getUsername().equals(userName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkDuplicateById(int id) throws SQLException, ClassNotFoundException {
        List<User> listUser = selectAllUsers();
        for (User user : listUser) {
            if (user.getId() == id) {
                return true;
            }
        }
        return false;
    }


    private void printSQLException(SQLException exception) {
        for (Throwable e : exception) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = exception.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
