package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealsInMemory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealsEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/editMeal.jsp";
    private static String MEALS_LIST = "/meals.jsp";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private MealsInMemory mealsInMemory = MealsInMemory.getInstance();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealsInMemory.delete(id);
            forward = MEALS_LIST;
            request.setAttribute("meals", mealsInMemory.getAll());
        } else if (action.equalsIgnoreCase("update")) {
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealsInMemory.read(id);
            request.setAttribute("meal", meal);
            request.setAttribute("action", "update");
            request.setAttribute("pattern", FORMATTER);
        } else if (action.equalsIgnoreCase("create")) {
            forward = INSERT_OR_EDIT;
            request.setAttribute("meal", new Meal(LocalDateTime.now(), "", 0));
            request.setAttribute("action", "create");
            request.setAttribute("pattern", FORMATTER);
        } else {
            forward = MEALS_LIST;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("localDateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
//        MealsInMemory mealsInMemory = MealsInMemory.getInstance();

        int id = Integer.parseInt(request.getParameter("id"));
        if (id == 0) {
            mealsInMemory.create(new Meal(dateTime, description, calories));
        } else {
            mealsInMemory.update(new Meal(id, dateTime, description, calories));
        }
        response.sendRedirect(MEALS_LIST);
    }
}
