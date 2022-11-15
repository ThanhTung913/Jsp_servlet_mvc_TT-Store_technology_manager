package dao;

import model.Catagory;
import model.Product;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatagoryDAO implements ICatagoryDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/TTSTORE?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";

    private final String INSERT_CATAGORY_SQL = "INSERT INTO `TTSTORE`.`catagory` (`id`, `name`) VALUES (?, ?);";
    private final String SELECT_CATAGORY_BY_ID = "SELECT id,name FROM catagory WHERE id = ?;";
    private final String SELECT_ALL_CATAGORY = "SELECT id,name FROM catagory;";
    private final String DELETE_CATAGORY_SQL = "DELETE FROM catagory WHERE id =?;";
    private final String UPDATE_CATAGORY_SQL = "UPDATE Users SET name = ? WHERE id = ?;";

    public CatagoryDAO() {
    }

    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return connection;
    }

    @Override
    public void inserCatagory(Catagory catagory) throws SQLException, ClassNotFoundException {
        try {
            System.out.println(INSERT_CATAGORY_SQL);
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATAGORY_SQL);
            preparedStatement.setString(1, catagory.getName());
            System.out.println(this.getClass() + "inserCatagory" + preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Catagory selectCatagoryById(int id) throws SQLException, ClassNotFoundException {
        Catagory catagory = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATAGORY_BY_ID);
            preparedStatement.setInt(1, id);
            System.out.println(this.getClass() + "selectCatagoryById: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                catagory = new Catagory(id, name);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return catagory;
    }

    @Override
    public List<Catagory> selectAllCatagory() throws SQLException, ClassNotFoundException {
        List<Catagory> listCatagory = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATAGORY);

            System.out.println(this.getClass() + "selectAllCatagory: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                listCatagory.add(new Catagory(id, name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return listCatagory;
    }

    @Override
    public boolean deleteCatagory(int id) throws SQLException, ClassNotFoundException {
        boolean rowDelete = false;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CATAGORY_SQL);
            preparedStatement.setInt(1, id);
            rowDelete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDelete;
    }

    @Override
    public boolean updateCatagory(Catagory catagory) throws SQLException, ClassNotFoundException {
        boolean rowUpdate = false;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CATAGORY_SQL);
            preparedStatement.setString(1, catagory.getName());
            preparedStatement.setInt(2, catagory.getId());

            System.out.println(this.getClass() + "updateCountry: " + preparedStatement);
            rowUpdate = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdate;
    }

    @Override
    public boolean checkDuplicateByCatagory(int id) throws SQLException, ClassNotFoundException {
        List<Catagory> listCatagory = selectAllCatagory();
        for (Catagory catagory : listCatagory) {
            if (catagory.getId() == id) {
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
