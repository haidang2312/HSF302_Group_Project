package org.example.hsf302_group1.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.hsf302_group1.entity.UserAccount;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        // Cho phép truy cập trang login và root
        if (uri.equals("/") || uri.equals("/login") || uri.startsWith("/css/") || uri.startsWith("/js/")) {
            return true;
        }

        // Kiểm tra session có tồn tại và có user
        if (session == null) {
            response.sendRedirect("/");
            return false;
        }

        UserAccount user = (UserAccount) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/");
            return false;
        }

        // Kiểm tra role: Guest (3) không được vào
        if (user.getRole() == 3) {
            request.setAttribute("error", "You have no permission to access this function!");
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        }

        return true;
    }
}