package com.travelbooking.controller;

import com.travelbooking.model.User;
import com.travelbooking.service.UserService;
import com.travelbooking.util.InputValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProfileServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        // Refresh from DB
        user = userService.findById(user.getIdString());
        req.getSession().setAttribute("user", user);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        if ("updateProfile".equals(action)) {
            user.setFirstName(InputValidator.clean(req.getParameter("firstName")));
            user.setLastName(InputValidator.clean(req.getParameter("lastName")));
            user.setPhone(InputValidator.clean(req.getParameter("phone")));
            user.setAddress(InputValidator.clean(req.getParameter("address")));

            if (userService.updateProfile(user)) {
                req.getSession().setAttribute("user", user);
                req.getSession().setAttribute("userName", user.getFullName());
                req.setAttribute("success", "Profile updated successfully!");
            } else {
                req.setAttribute("error", "Failed to update profile.");
            }
        } else if ("changePassword".equals(action)) {
            String oldPw = req.getParameter("oldPassword");
            String newPw = req.getParameter("newPassword");
            String confirmPw = req.getParameter("confirmPassword");

            if (!newPw.equals(confirmPw)) {
                req.setAttribute("error", "New passwords do not match.");
            } else if (!InputValidator.isValidPassword(newPw)) {
                req.setAttribute("error", "Password must be at least 6 characters.");
            } else if (userService.changePassword(user.getId(), oldPw, newPw)) {
                req.setAttribute("success", "Password changed successfully!");
            } else {
                req.setAttribute("error", "Current password is incorrect.");
            }
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }
}
