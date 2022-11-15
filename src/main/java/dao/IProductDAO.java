package dao;

import model.Product;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IProductDAO {
    void inserProduct(Product product) throws SQLException, ClassNotFoundException;

    Product selectProductById(int id) throws SQLException, ClassNotFoundException;

    List<Product> selectAllProduct() throws SQLException, ClassNotFoundException;

    boolean deleteProduct(int id) throws SQLException, ClassNotFoundException;

    boolean updateProduct(Product product) throws SQLException, ClassNotFoundException;

    int getNoOfRecords();

    List<Product> searchByName(String name) throws SQLException, ClassNotFoundException;


    List<Product> getNumberPage(int offset, int noOfRecords, String product) throws ClassNotFoundException, SQLException;

    boolean checkDuplicateUserNameProduct(String name) throws SQLException, ClassNotFoundException;

    boolean checkDuplicateImage(String image) throws SQLException, ClassNotFoundException;

    boolean checkDuplicateById(int id) throws SQLException, ClassNotFoundException;

}
