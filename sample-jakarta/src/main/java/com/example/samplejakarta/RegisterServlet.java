package com.example.samplejakarta;

import com.google.gson.Gson;
import dao.UserDAO;
import modelo.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final int SC_UNPROCESSABLE_ENTITY = 422;
    private UserDAO userDAO;
    private final JsonResponse jResp = new JsonResponse();

    public RegisterServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);

        Gson gson = new Gson();
        User user = gson.fromJson(req.getReader(), User.class);

        // Verificar si el correo electrónico ya está registrado
        User existingUser = userDAO.getUserByEmail(user.getEmail());
        if (existingUser != null) {
            jResp.failed(req, resp, "El correo electrónico ya está registrado", SC_UNPROCESSABLE_ENTITY);
            return;
        }

        int success = userDAO.agregar(user);

        if (success != -1) {
            jResp.success(req, resp, "Usuario creado con éxito", null);
        } else {
            jResp.failed(req, resp, "Error al crear el usuario", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // Método para agregar los encabezados CORS
    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*"); // Permitir todas las solicitudes de origen
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    // Manejar las solicitudes OPTIONS para CORS
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
