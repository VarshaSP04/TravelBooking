package com.travelbooking.controller.admin;

import com.travelbooking.model.User;
import com.travelbooking.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AdminUserServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("id");

        if ("delete".equals(action) && id != null) {
            userService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        List<User> users = userService.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/views/admin/users.jsp").forward(req, resp);
    }
}
