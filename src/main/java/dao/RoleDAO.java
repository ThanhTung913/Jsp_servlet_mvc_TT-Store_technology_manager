package dao;

import model.Catagory;
import model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    public RoleDAO() {
    }

    private String jdbcURL = "jdbc:mysql://localhost:3306/TTSTORE?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";

    private static final String SELECT_ROLE_SQL = "SELECT * FROM role;";

    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return connection;
    }

    public List<Role> selectRole() throws SQLException, ClassNotFoundException {
        List<Role> listRole = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_SQL);

            System.out.println(this.getClass() + "selectAllRole: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                listRole.add(new Role(id, name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return listRole;
    }

    public boolean checkDuplicateByRole(int id) throws SQLException, ClassNotFoundException {
        List<Role> listRole = selectRole();
        for (Role role : listRole) {
            if (role.getId() == id) {
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
