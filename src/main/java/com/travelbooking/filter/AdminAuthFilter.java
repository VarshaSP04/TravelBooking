package com.travelbooking.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Protects admin routes. Allows /admin/login through. */
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();
        // Allow admin login page through
        if (uri.endsWith("/admin/login")) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);
        boolean isAdmin = session != null && session.getAttribute("admin") != null;

        if (isAdmin) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/login");
        }
    }
}
