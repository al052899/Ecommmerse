package com.example.samplejakarta;

import java.io.*;
import java.util.List;

import com.google.gson.Gson;
import dao.ArticuloDAO;
import dao.CarritoDAO;
import modelo.Articulo;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import modelo.Carrito;

@WebServlet("/articulos/*")
public class ArticulosServlet  extends  HttpServlet{
    private final JsonResponse jResp = new JsonResponse();
    private ArticuloDAO articuloDAO;
    private CarritoDAO carritoDAO;

    public ArticulosServlet(){
        this.articuloDAO = new ArticuloDAO();
        this.carritoDAO = new CarritoDAO();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        addCorsHeaders(resp);

        resp.setContentType("application/json");
        //  (GET)   ->  /api/articulos  <-
        if(req.getPathInfo() == null){
            List<Articulo> articulos = articuloDAO.consultar();
            jResp.success(req, resp, "Obtener todos los articulos", articulos);
            return;
        }

        String[] path = req.getPathInfo().split("/");

        //  (GET)   ->  /api/articulos/{id}  <-
        if(path.length == 2){
            if(path[1].matches("-?\\d+")){
                //obtener el id del endpoint
                String pathInfo = req.getPathInfo();
                String idStr = pathInfo.substring(1); // Elimina la barra inicial
                int id = Integer.parseInt(idStr);

                Articulo articulo = articuloDAO.consultar(id);

                jResp.success(req, resp, "Obtener un solo articulo", articulo);
                return;
            }else{
                //error cuando el id no es numerico
                jResp.failed(req, resp, "El ID debe ser numérico", HttpServletResponse.SC_BAD_REQUEST);
            }
        }else{
            jResp.failed(req, resp, "recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        addCorsHeaders(resp);

        jResp.failed(req, resp, "recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
    }

    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        addCorsHeaders(resp);

        resp.setContentType("application/json");
        //  NO DISPONIBLE
        //  (PUT)   ->  /api/articulos  <-
        if(req.getPathInfo() == null){
            jResp.failed(req, resp, "recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String[] path = req.getPathInfo().split("/");

        /*
            AGREGAR UN ARTICULO AL CARRITO DE UN USUARIO
            id = id_articulo

            <informacion requerrida>
            -   id del articulo a insertar -> obtenido de la url
            -   id del usuario -> obtenido del body
            -   cantidad por defecto 1 -> obtenido del body
         */
        //  (PUT)   ->  /api/articulos/{id}  <-
        if(path.length == 2){
            if(path[1].matches("-?\\d+")){
                //obtener informacion
                Gson gson = new Gson();
                Carrito carrito = gson.fromJson(req.getReader(), Carrito.class);

                //obtener el id del endpoint
                String pathInfo = req.getPathInfo();
                String idStr = pathInfo.substring(1); // Elimina la barra inicial
                int id = Integer.parseInt(idStr);

                //saber si el usuario ya tiene este articulo en su carrito
                if(!carritoDAO.consultar(carrito.getIdUsuario(), id)){
                    //saber si el articulo cuenta con stock
                    Articulo articulo = articuloDAO.consultar(id);

                    if (articuloDisponible(articulo)){
                        //true
                        carrito.setIdArticulo(id); //establecer el id del articulo
                        //agregar el articulo al carrito
                        carritoDAO.agregar(carrito);

                        //actualizar el stock del producto en -1
                        Integer nuevoStock = articulo.getCantidadDisponible() - carrito.getCantidad();
                        articuloDAO.actualizarStock(id, nuevoStock);

                        //consultar el nuevo stock
                        articulo = articuloDAO.consultar(id); //obtener la actualizacion del stock

                        jResp.success(req, resp, "Articulo añadido correctamente", articulo);
                    }else{
                        //false
                        jResp.failed(req,resp,"El articulo no tiene stock",HttpServletResponse.SC_CONFLICT);
                    }
                }else{
                    jResp.failed(req,resp,"Ya tienes este articulo en el carrito",HttpServletResponse.SC_CONFLICT);
                }
            }else{
                //error cuando el id no es numerico
                jResp.failed(req, resp, "El ID debe ser numérico", HttpServletResponse.SC_BAD_REQUEST);
            }
        }else{
            jResp.failed(req, resp, "recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        addCorsHeaders(resp);

        jResp.failed(req, resp, "recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
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

    public boolean articuloDisponible(Articulo articulo){
        if(articulo.getCantidadDisponible() > 0){
            return true;
        }
        return false;
    }
}