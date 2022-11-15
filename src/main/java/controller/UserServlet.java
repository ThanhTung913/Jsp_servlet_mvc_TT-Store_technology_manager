package controller;

import com.mysql.cj.util.StringUtils;
import dao.IUserDAO;
import dao.RoleDAO;
import dao.StatusDAO;
import dao.UserDAO;
import model.Status;
import model.User;
import ultils.SortUserByIdDESC;
import ultils.ValidateUltils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.validation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.function.BiConsumer;

@WebServlet(name = "userServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserDAO iUserDAO;
    private RoleDAO roleDAO;
    private StatusDAO statusDAO;
    private String errors = "";
    static boolean sec = false;
    static String messSuccsec = "";

    public void init() {
        iUserDAO = new UserDAO();
        roleDAO = new RoleDAO();
        statusDAO = new StatusDAO();

        if (this.getServletContext().getAttribute("listRole") == null) {
            try {
                this.getServletContext().setAttribute("listRole", roleDAO.selectRole());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (this.getServletContext().getAttribute("listStatus") == null) {
            try {
                this.getServletContext().setAttribute("listStatus", statusDAO.selectStatus());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "menu":
                    showMenu(req, resp);
                    break;
                case "create":
                    showCreateUser(req, resp);
                    break;
                case "edit":
                    showEditUser(req, resp);
                    break;
                case "delete":
                    deleteUser(req, resp);
                    break;
                case "userActive":
                    showUserActive(req, resp);
                    break;
                case "userUnActive":
                    showUserUnActive(req, resp);
                    break;
                default:
                    listNumberPage(req, resp);
//                    listUser(req, resp);
                    break;
            }

        } catch (Exception ex) {
            throw new ServletException(ex);
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(req, resp);
                    break;
                case "edit":
                    updateUser(req, resp);
                    break;
                case "userActive":
                    userActive(req, resp);
                    break;
                default:
                    listNumberPage(req, resp);
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void showMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/index.jsp");
        requestDispatcher.forward(req, resp);
    }


    private void userActive(HttpServletRequest req, HttpServletResponse resp) {


    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        User user = null;
        RequestDispatcher dispatcher = req.getRequestDispatcher("/user/edit.jsp");

        String id = req.getParameter("id");
        String name = req.getParameter("name").trim();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String email = req.getParameter("email").trim();
        String phone = req.getParameter("phone").trim();
        String address = req.getParameter("address").trim();

//        int role = Integer.parseInt(req.getParameter("role"));
//        int status = Integer.parseInt(req.getParameter("status"));

        List<String> errors = new ArrayList<>();
        boolean isPhone = ValidateUltils.isPhoneValid(phone);
        boolean isEmail = ValidateUltils.isEmailValid(email);
        boolean isPassword = ValidateUltils.isPasswordValid(password);
        boolean isName = ValidateUltils.isNameValid(name);

        String roleRow = req.getParameter("role");
        int role = 0;
        try {
            role = Integer.parseInt(roleRow);

            if (!roleDAO.checkDuplicateByRole(role)) {
                throw new IOException();
            }

        } catch (NumberFormatException | IOException e) {
            errors.add("Role không tồn tại");
        }

        String statusRow = req.getParameter("status");
        int status = 0;
        try {
            status = Integer.parseInt(statusRow);

            if (!statusDAO.checkDuplicateStatus(status)) {
                throw new IOException();
            }

        } catch (NumberFormatException | IOException e) {
            errors.add("Status không tồn tại");
        }

        if (!ValidateUltils.isNumber(id) || !iUserDAO.checkDuplicateById(Integer.parseInt(id))) {
            req.setAttribute("error", "Không xác định được id");
            req.setAttribute("check", true);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/errorEdit.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            user = new User(Integer.parseInt(id), name, username, password, email, phone, address, role, status);
            User userName = iUserDAO.selectUserById(Integer.parseInt(id));
            String checkUserName = userName.getUsername();
            List<String> errors1 = new ArrayList<>();
            if (id.isEmpty() || Integer.parseInt(id) < 0) {
                errors1.add("id không tồn tại");
            }
            if (ValidateUltils.isNumber(id)) {
                errors1.add("Id không đúng định dạng");
            }

            if (name.isEmpty()) {
                errors.add("Tên người dùng không được để trống");
            } else if (!isName) {
                errors.add("Tên người dùng không đúng định dạng (ex: Thanh Tung)");
            }

            if (username.isEmpty()) {
                errors.add("Tên đăng nhập không được để trống");
            } else if (iUserDAO.checkDuplicateUserName(username) && !username.equals(checkUserName)) {
                errors.add("Tên người đùng đã tồn tại");
            }

            if (address.isEmpty()) {
                errors.add("Address không được để trống");
            }

            if (!isPassword && StringUtils.isNullOrEmpty(password)) {
                errors.add("Password không đúng định dạng (ví dụ: tung123 )");
            } else if (password.isEmpty()) {
                errors.add("password không được để trống");
            }

            User userEmail = iUserDAO.selectUserById(Integer.parseInt(id));
//        User userEmail = iUserDAO.selectUserById(id);
            String checkEmail = userEmail.getEmail();
            if (email.isEmpty()) {
                errors.add("Email không được để trống");
            } else if (iUserDAO.checkDuplicateEmail(email) && !email.equals(checkEmail)) {
                errors.add("Email đã tồn tại");
            } else if (!isEmail) {
                errors.add("Email không đúng định dạng (ví dụ: thanhtung@gamil.com)");
            }
//        User userPhone = iUserDAO.selectUserById(id);
            User userPhone = iUserDAO.selectUserById(Integer.parseInt(id));
            String checkPhone = userPhone.getPhone();
            if (iUserDAO.checkDuplicatePhone(phone) && !phone.equals(checkPhone)) {
                errors.add("Số điện thoại đã tồn tại");
            } else if (!isPhone) {
                errors.add("Số điện thoại cần bắt đầu bằng 03 hoặc 09 và có 10 chữ số");
            } else if (phone.isEmpty()) {
                errors.add("Số điện thoại không được để trống");
            }


            if (errors.size() == 0) {
//            user = new User(id, name, username, password, email, phone, address, role, status);
                user = new User(Integer.parseInt(id), name, username, password, email, phone, address, role, status);
                boolean success = false;
                success = iUserDAO.updateUser(user);
                if (success) {
                    req.setAttribute("user", user);
                    req.setAttribute("success", true);
//                req.getRequestDispatcher("/users");
                } else {
                    req.setAttribute("user", user);
                    req.setAttribute("errors", true);
                    errors.add("Invalid data, Please check again!");
                }
            }
            if (errors.size() > 0) {
                req.setAttribute("errors", errors);
                req.setAttribute("user", user);
            }
            List<User> listUser = iUserDAO.selectAllUsers();
            req.setAttribute("listUser", listUser);
            req.getRequestDispatcher("/users?action=edit&id=" + id);
            dispatcher.forward(req, resp);
        }
    }


    private void insertUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");

        User user = null;

        RequestDispatcher dispatcher = req.getRequestDispatcher("/user/create.jsp");
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name").trim();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String email = req.getParameter("email").trim();
        String phone = req.getParameter("phone").trim();
        String address = req.getParameter("address").trim();
        int role = Integer.parseInt(req.getParameter("role"));
        int status = Integer.parseInt(req.getParameter("status"));
        List<String> errors = new ArrayList<>();
        boolean isPhone = ValidateUltils.isPhoneValid(phone);
        boolean isEmail = ValidateUltils.isEmailValid(email);
        boolean isPassword = ValidateUltils.isPasswordValid(password);
        boolean isName = ValidateUltils.isNameValid(name);

        user = new User(id, name, username, password, email, phone, address, role, status);


        if (name.isEmpty()) {
            errors.add("Tên người dùng không được để trống");
        } else if (!isName) {
            errors.add("Tên người dùng không đúng định dạng (ex: Thanh Tung)");
        }

        if (username.isEmpty()) {
            errors.add("Tên đăng nhập không được để trống");
        } else if (iUserDAO.checkDuplicateUserName(username)) {
            errors.add("Tên người đùng đã tồn tại");
        }

        if (address.isEmpty()) {
            errors.add("Address không được để trống");
        }

        if (!isPassword && StringUtils.isNullOrEmpty(password)) {
            errors.add("Password không đúng định dạng (ví dụ: tung123 )");
        } else if (password.isEmpty()) {
            errors.add("password không được để trống");
        }

        if (email.isEmpty()) {
            errors.add("Email không được để trống");
        } else if (iUserDAO.checkDuplicateEmail(email)) {
            errors.add("Email đã tồn tại");
        } else if (!isEmail) {
            errors.add("Email không đúng định dạng (ví dụ: thanhtung@gamil.com)");
        }


        if (iUserDAO.checkDuplicatePhone(phone)) {
            errors.add("Số điện thoại đã tồn tại");
        } else if (!isPhone) {
            errors.add("Số điện thoại cần bắt đầu bằng 03 hoặc 09 và có 10 chữ số");
        } else if (phone.isEmpty()) {
            errors.add("Số điện thoại không được để trống");
        }


        if (errors.size() == 0) {
            user = new User(id, name, username, password, email, phone, address, role, status);
            boolean success = true;
            iUserDAO.inserUser(user);
            if (success) {
                req.setAttribute("user", user);
                req.setAttribute("success", true);
//                req.getRequestDispatcher("/users");
            } else {
                req.setAttribute("user", user);
                req.setAttribute("errors", true);
                errors.add("Invalid data, Please check again!");
            }
        }
        if (errors.size() > 0) {
            req.setAttribute("user", user);
            req.setAttribute("errors", errors);
        }
//        List<User> listUser = iUserDAO.selectAllUsers();
//        req.setAttribute("listUser", listUser);

        dispatcher.forward(req, resp);
//
    }

    private void showEditUser(HttpServletRequest req, HttpServletResponse resp) throws
            SQLException, ClassNotFoundException, ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");
        User user = new User();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/user/edit.jsp");
        List<String> errors1 = new ArrayList<>();
        String id = req.getParameter("id");
        if (!ValidateUltils.isNumber(id)) {
            errors1.add("ID không hợp lệ");
        } else {
            if (!iUserDAO.checkDuplicateById(Integer.parseInt(id))) {
                errors1.add("Id không tồn tại");
            }
        }
        if (errors1.size() == 0) {
            user = iUserDAO.selectUserById(Integer.parseInt(id));
            req.setAttribute("user", user);
        }
        if (errors1.size() > 0) {
            req.setAttribute("errors1", errors1);
        }
        dispatcher.forward(req, resp);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws
            SQLException, ClassNotFoundException, ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");

        int id = Integer.parseInt(req.getParameter("id"));
        iUserDAO.deleteUser(id);
        List<User> listUser = iUserDAO.selectAllUsers();
        req.setAttribute("listUser", listUser);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/list.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws
            SQLException, ClassNotFoundException, ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");
        List<User> listUser = iUserDAO.selectAllUsers();
        req.setAttribute("listUser", listUser);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/listUser.jsp");
        requestDispatcher.forward(req, resp);
    }


    private void showUserUnActive(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        String searchUserUnActive = "";
        if (req.getParameter("searchUserUnActive") != null) {
            searchUserUnActive = req.getParameter("searchUserUnActive");
        }
        List<User> listUserUnActive = iUserDAO.getNumberPageUnActive((page - 1) * recordsPerPage, recordsPerPage, searchUserUnActive);
        int noOfRecords = iUserDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("listUserUnActive", listUserUnActive);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchUserUnActive", searchUserUnActive);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/listUserUnactive.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showUserActive(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        String searchUserActive = "";
        if (req.getParameter("searchUserActive") != null) {
            searchUserActive = req.getParameter("searchUserActive");
        }
        List<User> listUserActive = iUserDAO.getNumberPageActive((page - 1) * recordsPerPage, recordsPerPage, searchUserActive);
        int noOfRecords = iUserDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("listUserActive", listUserActive);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchUserActive", searchUserActive);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/listUserActive.jsp");
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
        String searchUser = "";
        if (req.getParameter("searchUser") != null) {
            searchUser = req.getParameter("searchUser");
        }
        List<User> listUser = iUserDAO.getNumberPage((page - 1) * recordsPerPage, recordsPerPage, searchUser);
        int noOfRecords = iUserDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("listUser", listUser);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchUser", searchUser);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/listUser.jsp");
        requestDispatcher.forward(req, resp);

    }

    private void showCreateUser(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");
        User user = new User();
        req.setAttribute("user", user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/create.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void searchUserByName(HttpServletRequest req, HttpServletResponse resp) throws
            SQLException, ClassNotFoundException, ServletException, IOException {
        String searchUser = req.getParameter("searchUser");
        List<User> listUser = iUserDAO.searchByName(searchUser);
        req.setAttribute("listUser", listUser);
        listNumberPage(req, resp);
//        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/listUser.jsp");
//        requestDispatcher.forward(req, resp);
//        resp.sendRedirect("/user/listUser.jsp");
    }

}
