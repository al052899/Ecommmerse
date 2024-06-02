package com.example.samplejakarta;

import com.google.gson.Gson;
import dao.UserDAO;
import modelo.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    public LoginServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(req, resp);

        Gson gson = new Gson();
        User userRequest = gson.fromJson(req.getReader(), User.class);

        String email = userRequest.getEmail();
        String password = userRequest.getPassword();

        User user = userDAO.getUserByEmail(email);

        JsonResponse jsonResponse = new JsonResponse();

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());

            // Agrega el id del usuario a la respuesta
            userRequest.setId(user.getId());
            jsonResponse.success(req, resp, "Sesión exitosa", user);
        } else {
            jsonResponse.failed(req, resp, "No se puede acceder al sistema por no tener las credenciales de acceso correctas", HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(req, resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void addCorsHeaders(HttpServletRequest request, HttpServletResponse response) {
        String origin = request.getHeader("Origin");

        // Lista de orígenes permitidos
        List<String> allowedOrigins = Arrays.asList(
                "http://127.0.0.1:5500",
                "http://localhost:5500",
                "http://localhost:3000",
                "http://localhost:8080",
                "http://localhost"
                // Agrega otros orígenes permitidos aquí
        );

        if (allowedOrigins.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
