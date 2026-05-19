package com.travelbooking.controller;

import com.travelbooking.model.User;
import com.travelbooking.service.UserService;
import com.travelbooking.util.InputValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String firstName = InputValidator.clean(req.getParameter("firstName"));
        String lastName = InputValidator.clean(req.getParameter("lastName"));
        String email = InputValidator.clean(req.getParameter("email"));
        String phone = InputValidator.clean(req.getParameter("phone"));
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        // Validation
        StringBuilder errors = new StringBuilder();
        if (!InputValidator.isValidName(firstName)) errors.append("Valid first name required. ");
        if (!InputValidator.isValidName(lastName)) errors.append("Valid last name required. ");
        if (!InputValidator.isValidEmailStrict(email)) errors.append("Please enter a valid, real email address. ");
        if (!InputValidator.isValidPhone(phone)) errors.append("Valid phone required. ");
        if (!InputValidator.isValidPassword(password)) errors.append("Password must be at least 6 characters. ");
        if (!password.equals(confirmPassword)) errors.append("Passwords do not match. ");

        if (errors.length() > 0) {
            req.setAttribute("error", errors.toString().trim());
            req.setAttribute("firstName", firstName);
            req.setAttribute("lastName", lastName);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        User user = userService.register(firstName, lastName, email, password, phone);
        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getIdString());
            session.setAttribute("userName", user.getFullName());
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            req.setAttribute("error", "An account with this email already exists.");
            req.setAttribute("firstName", firstName);
            req.setAttribute("lastName", lastName);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}
