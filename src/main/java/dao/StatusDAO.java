package dao;

import model.Role;
import model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO {
    public StatusDAO() {
    }

    private String jdbcURL = "jdbc:mysql://localhost:3306/TTSTORE?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";

    private static final String SELECT_STATUS_SQL = "SELECT * FROM status;";

    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return connection;
    }

    public List<Status> selectStatus() throws SQLException, ClassNotFoundException {
        List<Status> listStatus = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUS_SQL);
            System.out.println(this.getClass() + "selectAllStatus: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                listStatus.add(new Status(id, name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return listStatus;
    }

    public boolean checkDuplicateStatus(int id) throws SQLException, ClassNotFoundException {
        List<Status> listStatus = selectStatus();
        for (Status status : listStatus) {
            if (status.getId() == id) {
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
