package controller;

import dao.*;
import model.Product;
import model.User;

import com.mysql.cj.util.StringUtils;
import ultils.ValidateUltils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "productServlet", urlPatterns = "/products")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB


public class ProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IProductDAO iProductDAO;
    private ICatagoryDAO iCatagoryDAO;
    private StatusProductDAO statusProductDAO;
    private RoleDAO roleDAO;
    private String errors = "";

    public void init() {
        iProductDAO = new ProductDAO();
        iCatagoryDAO = new CatagoryDAO();
        statusProductDAO = new StatusProductDAO();

        if (this.getServletContext().getAttribute("listCatagory") == null) {
            try {
                this.getServletContext().setAttribute("listCatagory", iCatagoryDAO.selectAllCatagory());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (this.getServletContext().getAttribute("listStatusProduct") == null) {
            try {
                this.getServletContext().setAttribute("listStatusProduct", statusProductDAO.selectStatusProduct());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreateForm(req, resp);
                break;
            case "edit":
                try {
                    showEditFormProduct(req, resp);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
//                    listProduct(req, resp);
                    listNumberPage(req, resp);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                try {
                    insertProduct(req, resp);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "edit":
                try {
                    editProduct(req, resp);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "search":
                try {
                    searchUserByName(req, resp);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                try {
                    deleteProduct(req, resp);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }


    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        List<String> errors = new ArrayList<>();
        if (!iProductDAO.checkDuplicateById(id)) {
            errors.add("ID không tồn tại");
        }
        if (errors.size() == 0) {
            iProductDAO.deleteProduct(id);
            List<Product> listProduct = iProductDAO.selectAllProduct();
            req.setAttribute("listProduct", listProduct);
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/listProduct.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void searchUserByName(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String searchProduct = req.getParameter("searchProduct");
        List<Product> listProduct = iProductDAO.searchByName(searchProduct);
        req.setAttribute("listProduct", listProduct);
        listNumberPage(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("Server part: " + this.getServletContext().getRealPath("/"));
        Product product = new Product();
        req.setAttribute("product", product);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/product/create.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showEditFormProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ClassNotFoundException, ServletException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/product/edit.jsp");
        Product product = new Product();
        List<String> errors1 = new ArrayList<>();
        String id = req.getParameter("id");
        if (!ValidateUltils.isNumber(id)) {
            errors1.add("ID không hợp lệ");
        } else {
            if (!iProductDAO.checkDuplicateById(Integer.parseInt(id))) {
                errors1.add("Id không tồn tại");
            }
        }
        if (errors1.size() == 0) {
            product = iProductDAO.selectProductById(Integer.parseInt(id));
            req.setAttribute("product", product);
        }
        if (errors1.size() > 0) {
            req.setAttribute("errors1", errors1);
        }
        dispatcher.forward(req, resp);

    }

    private void editProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ClassNotFoundException, ServletException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/product/edit.jsp");
        Product product = null;
        List<String> errors = new ArrayList<>();
        String id = req.getParameter("id");

        String name = req.getParameter("name");

        String priceRow = req.getParameter("price");

        int price = 0;
        try {
            price = Integer.parseInt(priceRow);
            if (!ValidateUltils.isNumber(priceRow)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errors.add("Giá sản phẩm phải là một số");
        }
        String catagoryRaw = req.getParameter("catagory");

        int catagory = 0;
        try {
            catagory = Integer.parseInt(catagoryRaw);
            if (priceRow.isEmpty()) {
                errors.add("Giá sản phẩm không được để trống");
            } else if (!iCatagoryDAO.checkDuplicateByCatagory(catagory)) {
                throw new IOException();

            } else if (price < 1000000 || price > 100000000) {
                errors.add("Giá sản phẩm phải lớn hơn 100K và nhỏ hơn 100 Triệu");
            }

        } catch (NumberFormatException | IOException e) {
            errors.add("Catagory không tồn tại");
        }


        String statusRaw = req.getParameter("status");

        int status = 0;
        try {
            status = Integer.parseInt(statusRaw);

            if (!statusProductDAO.checkDuplicateStatusProduct(status)) {
                throw new IOException();
            }

        } catch (NumberFormatException | IOException e) {
            errors.add("Status không tồn tại");
        }

        String description = req.getParameter("description");
        String image = null;

        image = req.getParameter("image");
        System.out.println("Anh: " + image);
        for (Part part : req.getParts()) {

            if (part.getName().equals("image")) {
                String fileName = extractFileName(part);
                // refines the fileName in case it is an absolute path
                fileName = new File(fileName).getName();
                if (fileName.equals("")) {

                    image = req.getParameter("image");

                } else {
                    part.write("/Users/prom1/Desktop/CODEGYM/MODULE3/TTStore/src/main/webapp/image/" + fileName);
                    String servletRealPath = this.getServletContext().getRealPath("/") + "/image/" + fileName;
                    part.write(servletRealPath);
                    image = "/image/" + fileName;

                }
            }
        }

        if (!ValidateUltils.isNumber(id) || !iProductDAO.checkDuplicateById(Integer.parseInt(id))) {
            req.setAttribute("error", "Không xác định được id");
            req.setAttribute("check", true);

            RequestDispatcher requestDispatcher1 = req.getRequestDispatcher("/product/errorEdit.jsp");
            requestDispatcher1.forward(req, resp);
        } else {
            List<String> errors1 = new ArrayList<>();
            product = new Product(Integer.parseInt(id), name, price, catagory, description, image, status);
            if (id.isEmpty() || Integer.parseInt(id) < 0) {
                errors1.add("id không tồn tại");
            }
            if (ValidateUltils.isNumber(id)) {
                errors1.add("Id không đúng định dạng");
            }


            if (name.isEmpty()) {
                errors.add("Tên không được để trống");
            }
            if (image == null || image == "") {
                image = req.getParameter("image");
            }
            Product nameProduct = iProductDAO.selectProductById(Integer.parseInt(id));
            String checkname = nameProduct.getName();
            if (iProductDAO.checkDuplicateUserNameProduct(name) && !name.equals(checkname)) {
                errors.add("Tên sản phẩm đã tồn tại");
            }
            if (errors.size() == 0) {
                product = new Product(Integer.parseInt(id), name, price, catagory, description, image, status);
                boolean success = false;
                success = iProductDAO.updateProduct(product);
                if (success) {
                    req.setAttribute("product", product);
                    req.setAttribute("success", true);

                } else {
                    req.setAttribute("product", product);
                    req.setAttribute("errors", true);
                    errors.add("Chọn giùm em một cái ảnh!!");
                }
            }
            if (errors.size() > 0) {
                req.setAttribute("errors", errors);
                req.setAttribute("product", product);
            }
            List<Product> listPrpduct = iProductDAO.selectAllProduct();
            req.setAttribute("listProduct", listPrpduct);
            req.getRequestDispatcher("/products");
            requestDispatcher.forward(req, resp);

        }

    }

    private void insertProduct(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException, SQLException, ClassNotFoundException {
        List<String> errors = new ArrayList<>();
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/product/create.jsp");

        Product product = null;

        int id = Integer.parseInt(req.getParameter("id"));

        String name = req.getParameter("name");

        String priceRow = req.getParameter("price");

        int price = 0;
        try {
            price = Integer.parseInt(priceRow);
            if (!ValidateUltils.isNumber(priceRow)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errors.add("Giá sản phẩm phải là một số");
        }

        String catagoryRaw = req.getParameter("catagory");

        int catagory = 0;
        try {
            catagory = Integer.parseInt(catagoryRaw);

            if (!iCatagoryDAO.checkDuplicateByCatagory(catagory)) {
                throw new IOException();
            }

        } catch (NumberFormatException | IOException e) {
            errors.add("Catagory không tồn tại");
        }

        String description = req.getParameter("description");
        String image = null;


        for (Part part : req.getParts()) {

            if (part.getName().equals("image")) {
                String fileName = extractFileName(part);
                // refines the fileName in case it is an absolute path
                fileName = new File(fileName).getName();
                if (fileName.equals("")) {
                    image = "/image/iphone13pro.jpeg";
                } else {
                    part.write("/Users/prom1/Desktop/CODEGYM/MODULE3/TTStore/src/main/webapp/image/" + fileName);
                    String servletRealPath = this.getServletContext().getRealPath("/") + "/image/" + fileName;
                    System.out.println("servletRealPath :" + servletRealPath);
                    part.write(servletRealPath);
                    image = "/image/" + fileName;
                }
            }
        }

        String statusRaw = req.getParameter("status");
        int status = 0;
        try {
            status = Integer.parseInt(statusRaw);

            if (!statusProductDAO.checkDuplicateStatusProduct(status)) {
                throw new IOException();
            }

        } catch (NumberFormatException | IOException e) {
            errors.add("Status không tồn tại");
        }


        product = new Product(id, name, price, catagory, description, image, status);

        if (price < 1000000 || price > 100000000) {
            errors.add("Giá sản phẩm phải lớn hơn 100 Triệu và nhỏ hơn 100 Triệu");
        }
        if (priceRow.isEmpty()) {
            errors.add("Giá sản phẩm không được để trống");
        }

        if (name.isEmpty()) {
            errors.add("Tên không được để trống");
        }

        if (iProductDAO.checkDuplicateUserNameProduct(name)) {
            errors.add("Tên sản phẩm đã tồn tại");
        }
        if (errors.size() == 0) {
            product = new Product(id, name, price, catagory, description, image, status);
            String success = "Thêm sản phẩm thành công";
            iProductDAO.inserProduct(product);
            req.setAttribute("product", product);
            req.setAttribute("success", success);
            req.setAttribute("check", true);

        }
        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
            req.setAttribute("product", product);
        }

        requestDispatcher.forward(req, resp);
    }

    private void listNumberPage(HttpServletRequest req, HttpServletResponse resp) throws
            SQLException, ClassNotFoundException, ServletException, IOException {
        System.out.println("numberPage");
        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        String searchProduct = "";
        if (req.getParameter("searchProduct") != null) {
            searchProduct = req.getParameter("searchProduct");
        }
        List<Product> listProduct = iProductDAO.getNumberPage((page - 1) * recordsPerPage, recordsPerPage, searchProduct);
        int noOfRecords = iProductDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("listProduct", listProduct);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchProduct", searchProduct);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/product/listProduct.jsp");
        requestDispatcher.forward(req, resp);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    public File getFolderUpload() {
        File folderUpload = new File(System.getProperty("user.home") + "/Uploads");
        System.out.println(System.getProperty("user.home") + "/Uploads");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }
///                       Users/prom1/Uploads
}
