package dao;

import java.sql.*;

public class AccountDAO {
    public AccountDAO() {
    }

    private String jdbcURL = "jdbc:mysql://localhost:3306/TTSTORE?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";

    private static final String LOGIN_SQL = "SELECT username, password FROM Users Where username = ? and password = ?;";

    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return connection;
    }

    public boolean checkAccount(String username, String password) throws SQLException, ClassNotFoundException {
        boolean isValid = false;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_SQL);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            System.out.println(this.getClass() + "selectCountryById: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isValid = true;
            } else {
                isValid = false;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return isValid;
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
