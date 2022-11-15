package dao;

import model.Catagory;


import java.sql.SQLException;
import java.util.List;

public interface ICatagoryDAO {
    void inserCatagory(Catagory catagory) throws SQLException, ClassNotFoundException;

    Catagory selectCatagoryById(int id) throws SQLException, ClassNotFoundException;

    List<Catagory> selectAllCatagory() throws SQLException, ClassNotFoundException;

    boolean deleteCatagory(int id) throws SQLException, ClassNotFoundException;

    boolean updateCatagory(Catagory catagory) throws SQLException, ClassNotFoundException;

    boolean checkDuplicateByCatagory(int id) throws SQLException, ClassNotFoundException;
}
