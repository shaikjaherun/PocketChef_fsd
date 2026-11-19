package com.pocketchef.servlet;

import com.pocketchef.dao.SearchHistoryDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/search-history")
public class SearchHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session =
                request.getSession(false);

        // Check whether user is logged in
        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect("login.html");
            return;
        }

        int userId =
                (Integer) session.getAttribute("userId");

        SearchHistoryDAO searchHistoryDAO =
                new SearchHistoryDAO();

        List<String[]> history =
                searchHistoryDAO.getSearchHistory(userId);

        request.setAttribute(
                "history",
                history
        );

        // Your actual JSP filename
        request.getRequestDispatcher(
                "search-history.jsp"
        ).forward(request, response);
    }
}