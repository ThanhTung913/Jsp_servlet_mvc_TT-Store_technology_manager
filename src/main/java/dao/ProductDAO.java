package dao;

import model.Product;
import model.User;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/TTSTORE?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";

    private final String INSERT_PRODUCT_SQL = "INSERT INTO `TTSTORE`.`Products` (`name`, `price`, `idCatagory`, `description`, `image`,`idStatusProduct`) VALUES (?,?, ?, ?, ?,?);";
    private final String SELECT_PRODUCT_BY_ID = "SELECT id, name, price, idCatagory, description, image, idStatusProduct FROM Products WHERE id = ?;";
    private final String SELECT_ALL_PRODUCT = "SELECT * FROM Products ORDER BY `id` DESC ;";
    private final String DELETE_PRODUCT_SQL = "DELETE FROM Products WHERE id =?;";
    private final String UPDATE_PRODUCT_SQL = "UPDATE Products SET name = ?, price=? ,idCatagory =?  ,description=? ,image= ?,idStatusProduct=? WHERE id =?;";

    public ProductDAO() {
    }

    private int noOfRecords;

    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return connection;
    }

    @Override
    public void inserProduct(Product product) throws SQLException, ClassNotFoundException {
        System.out.println(INSERT_PRODUCT_SQL);
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setInt(2, product.getPrice());
        preparedStatement.setInt(3, product.getCatagory());
        preparedStatement.setString(4, product.getDescription());
        preparedStatement.setString(5, product.getImage());
        preparedStatement.setInt(6, product.getStatus());
        preparedStatement.executeUpdate();
        connection.close();
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException, ClassNotFoundException {
        boolean rowUpdate = false;
        boolean success = false;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getCatagory());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setString(5, product.getImage());
            preparedStatement.setInt(6, product.getStatus());
            preparedStatement.setInt(7, product.getId());
            rowUpdate = preparedStatement.executeUpdate() > 0;
            connection.close();
            success = true;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return success;
    }

    @Override
    public Product selectProductById(int id) throws SQLException, ClassNotFoundException {
        Product product = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = Integer.parseInt(resultSet.getString("price"));
                int catagory = Integer.parseInt(resultSet.getString("idCatagory"));
                String description = resultSet.getString("description");
                String image = resultSet.getString("image");
                int status = Integer.parseInt(resultSet.getString("idStatusproduct"));
                product = new Product(id, name, price, catagory, description, image, status);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }

    @Override
    public List<Product> selectAllProduct() throws SQLException, ClassNotFoundException {
        List<Product> listProduct = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int catagory = resultSet.getInt("idCatagory");
                String description = resultSet.getString("description");
                String image = resultSet.getString("image");
                int status = resultSet.getInt("idStatusProduct");
                listProduct.add(new Product(id, name, price, catagory, description, image, status));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return listProduct;
    }

    @Override
    public boolean deleteProduct(int id) throws SQLException, ClassNotFoundException {
        boolean rowDelete = false;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL);
            preparedStatement.setInt(1, id);
            rowDelete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }

        return rowDelete;
    }


    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public List<Product> searchByName(String name) throws SQLException, ClassNotFoundException {
        List<Product> listProduct = selectAllProduct();
        List<Product> listSearch = new ArrayList<>();

        for (Product product : listProduct) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                listSearch.add(product);
            }
        }
        return listSearch;
    }

    @Override
    public List<Product> getNumberPage(int offset, int noOfRecords, String searchproduct) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        System.out.println("numberpage");

        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM Products where name LIKE ? limit " + offset + ", " + noOfRecords;
        List<Product> listSearchProduct = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, '%' + searchproduct + '%');
        System.out.println(this.getClass() + " getNumberPage() query: " + ps);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            product.setCatagory(rs.getInt("idCatagory"));
            product.setDescription(rs.getString("description"));
            product.setImage(rs.getString("image"));
            product.setStatus(rs.getInt("idStatusProduct"));
            listSearchProduct.add(product);

        }
        rs = ps.executeQuery("SELECT FOUND_ROWS()");
        if (rs.next()) {
            this.noOfRecords = rs.getInt(1);
        }
        connection.close();
        return listSearchProduct;
    }

    @Override
    public boolean checkDuplicateUserNameProduct(String name) throws SQLException, ClassNotFoundException {
        List<Product> listProduct = selectAllProduct();
        for (Product product : listProduct) {
            if (product.getName().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkDuplicateImage(String image) throws SQLException, ClassNotFoundException {
        List<Product> listProduct = selectAllProduct();
        for (Product product : listProduct) {
            if (product.getImage().equals(image.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkDuplicateById(int id) throws SQLException, ClassNotFoundException {
        List<Product> listProduct = selectAllProduct();
        for (Product product : listProduct) {
            if (product.getId() == id) {
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
