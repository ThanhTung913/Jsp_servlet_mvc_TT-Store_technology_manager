package dao;

import model.Status;
import model.StatusProduct;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusProductDAO {
    public StatusProductDAO() {
    }

    private String jdbcURL = "jdbc:mysql://localhost:3306/TTSTORE?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";

    private static final String SELECT_STATUSPRODUCT_SQL = "SELECT * FROM statusproduct;";

    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return connection;
    }

    public List<StatusProduct> selectStatusProduct() throws SQLException, ClassNotFoundException {
        List<StatusProduct> listStatusProduct = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUSPRODUCT_SQL);

            System.out.println(this.getClass() + "selectAllStatusProduct: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                listStatusProduct.add(new StatusProduct(id, name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return listStatusProduct;
    }

    public boolean checkDuplicateStatusProduct(int id) throws SQLException, ClassNotFoundException {
        List<StatusProduct> listStatusProduct = selectStatusProduct();
        for (StatusProduct statusProduct : listStatusProduct) {
            if (statusProduct.getId() == id) {
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
