package com.pocketchef.servlet;

import com.pocketchef.dao.IngredientDAO;
import com.pocketchef.model.Ingredient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ingredients")
public class IngredientServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        IngredientDAO ingredientDAO =
                new IngredientDAO();

        List<Ingredient> ingredients =
                ingredientDAO.getAllIngredients();

        request.setAttribute(
                "ingredients",
                ingredients
        );

        request.getRequestDispatcher(
                "ingredients.jsp"
        ).forward(request, response);
    }
}