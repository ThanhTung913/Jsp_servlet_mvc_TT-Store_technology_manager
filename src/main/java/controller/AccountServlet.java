package controller;

import dao.AccountDAO;
import dao.IUserDAO;
import dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "accountServlet", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AccountDAO accountDAO;


    public void init() {
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "login":
                    showFormLogin(req, resp);
                    break;
                default:
                    showFormLogin(req, resp);
            }

        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void showFormLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/account/account.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "login":
                    checkAccount(req, resp);
                    break;

            }

        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void checkAccount(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException, IOException, ServletException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            boolean isValid = accountDAO.checkAccount(username, password);
            if (isValid) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user?");
                requestDispatcher.forward(req, resp);
            } else {
                String error = "Username hoặc password không chính xác";
                req.setAttribute("check", true);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/account/account.jsp");
                requestDispatcher.forward(req, resp);
            }

        } finally {
            out.close();
        }
    }
}
