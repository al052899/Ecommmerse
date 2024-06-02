package com.example.samplejakarta;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import dao.ArticuloDAO;
import dao.CarritoDAO;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import modelo.Articulo;
import modelo.ArticuloPreparado;
import modelo.Carrito;
import modelo.User;

@WebServlet("/carrito/*")
public class CarritoServlet extends  HttpServlet{
    private final JsonResponse jResp = new JsonResponse();
    private CarritoDAO carritoDAO;
    private ArticuloDAO articuloDAO;

    public CarritoServlet(){
        this.carritoDAO = new CarritoDAO();
        this.articuloDAO = new ArticuloDAO();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        addCorsHeaders(resp);
        resp.setContentType("application/json");
        /*
            OBTENER EL CARRITO DE UN USUARIO

            <informacion requerrida>
            - id del usuario -> obtenida del body
         */
        //  (GET)   ->  /api/carrito  <-
        if(req.getPathInfo() == null){
            //obtener informacion
            int id_usuario = Integer.parseInt(req.getParameter("id_usuario"));

            //obtener la id del usuario
            List<ArticuloPreparado> articulos = carritoDAO.consultaPreparada(id_usuario);
            jResp.success(req, resp, "Obtener articulos del carrito", articulos);
            return;
        }else{
            jResp.failed(req, resp, "recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        addCorsHeaders(resp);

        resp.setContentType("application/json");
        /*
            PROCEDER A REALIZAR LA COMPRA

            <informacion requerrida>
            -   id_usuario -> recuperado del body
         */
        //  (POST)  ->  /api/carrito  <-
        if(req.getPathInfo() == null){
            Gson gson = new Gson();
            Carrito carrito = gson.fromJson(req.getReader(), Carrito.class);

            int success = carritoDAO.eliminar(carrito.getIdUsuario());
            if(success > 0){
                jResp.success(req, resp, "Compra realizada con exito", null);
            }else{
                jResp.failed(req, resp, "No tienes articulos en tu carrito", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, success);
            }
            return;
        }else{
            jResp.failed(req, resp, "recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        addCorsHeaders(resp);

        resp.setContentType("application/json");
        //  (PUT)   ->  /api/carrito  <-
        if(req.getPathInfo() == null){
            jResp.failed(req, resp, "recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String[] path = req.getPathInfo().split("/");

        /*
            ACTUALIZAR LA CANTIDAD DE ARTICULOS DEL CARRITO DE UNA PERSONA
            id = id de la tabla carrito

            <informacion requerrida>
            -   id -> recuperado del url (clave primaria de la tabla carrito)
            -   id_usuario -> recuperado del body
            -   cantidad -> recuperado del body
            -   id_articulo -> recuperdado del body
         */
        //  (PUT)   ->  /api/carrito/{id}  <-
        if(path.length == 2){
            if(path[1].matches("-?\\d+")){
                //obtener informacion
                Gson gson = new Gson();
                Carrito carrito = gson.fromJson(req.getReader(), Carrito.class);

                //obtener el id del endpoint
                String pathInfo = req.getPathInfo();
                String idStr = pathInfo.substring(1); // Elimina la barra inicial
                int id = Integer.parseInt(idStr); //clave primaria de la tabla carrito
                carrito.setId(id);

                //saber si el articulo cuenta con stock
                Articulo articulo = articuloDAO.consultar(carrito.getIdArticulo());

                if(articuloDisponible(articulo)){
                    //true

                    //actualizar la cantidad
                    if(carrito.getCantidad() == 1){
                        //aumentar la cantidad del carrito
                        carrito = carritoDAO.consultar(carrito.getId()); // obtener la cantidad actual
                        carrito.setCantidad(carrito.getCantidad() + 1);

                        carritoDAO.actualizar(carrito.getIdUsuario(), carrito);
                        //disminuir la cantidad del stock
                        articulo = articuloDAO.consultar(carrito.getIdArticulo()); // obtener cantidad actual

                        articuloDAO.actualizarStock(carrito.getIdArticulo(), articulo.getCantidadDisponible() - 1);

                        //reflejar cantidades actualizadas
                        carrito = carritoDAO.consultar(carrito.getId()); // obtener la cantidad actual
                        articulo = articuloDAO.consultar(carrito.getIdArticulo()); // obtener cantidad actual

                        Map<String, Number> data = new HashMap<>();
                        data.put("precioArticulo", articulo.getPrecio());
                        data.put("cantidadCarrito", carrito.getCantidad());
                        data.put("cantidadDisponible", articulo.getCantidadDisponible());

                        jResp.success(req, resp, "cantidad aumentada", data);
                    }else if(carrito.getCantidad() == -1){
                        carrito = carritoDAO.consultar(carrito.getId()); // obtener la cantidad actual

                        //verificar que hayan articulos antes de disminuir
                        if(carrito.getCantidad() != 1){
                            //disminuir la cantidad del carrito
                            carrito.setCantidad(carrito.getCantidad() - 1);

                            carritoDAO.actualizar(carrito.getIdUsuario(), carrito);
                            //aumentar la cantidad del stock
                            articulo = articuloDAO.consultar(carrito.getIdArticulo()); // obtener cantidad actual

                            articuloDAO.actualizarStock(carrito.getIdArticulo(), articulo.getCantidadDisponible() + 1);

                            //reflejar cantidades actualizadas
                            carrito = carritoDAO.consultar(carrito.getId()); // obtener la cantidad actual
                            articulo = articuloDAO.consultar(carrito.getIdArticulo()); // obtener cantidad actual

                            Map<String, Number> data = new HashMap<>();
                            data.put("precioArticulo", articulo.getPrecio());
                            data.put("cantidadCarrito", carrito.getCantidad());
                            data.put("cantidadDisponible", articulo.getCantidadDisponible());

                            jResp.success(req, resp, "cantidad disminuida", data);
                        }else{
                            jResp.failed(req,resp,"ya no puedes disminuir mas",HttpServletResponse.SC_CONFLICT);
                        }
                    }else{
                        //false
                        jResp.failed(req,resp," Solo se puede aumentar de 1 en 1",HttpServletResponse.SC_CONFLICT);
                    }

                }else{
                    if(articulo.getCantidadDisponible() == 0 && carrito.getCantidad() == -1){
                        //disminuir la cantidad del carrito
                        carrito = carritoDAO.consultar(carrito.getId()); // obtener la cantidad actual
                        carrito.setCantidad(carrito.getCantidad() - 1);

                        carritoDAO.actualizar(carrito.getIdUsuario(), carrito);
                        //aumentar la cantidad del stock
                        articulo = articuloDAO.consultar(carrito.getIdArticulo()); // obtener cantidad actual

                        articuloDAO.actualizarStock(carrito.getIdArticulo(), articulo.getCantidadDisponible() + 1);

                        //reflejar cantidades actualizadas
                        carrito = carritoDAO.consultar(carrito.getId()); // obtener la cantidad actual
                        articulo = articuloDAO.consultar(carrito.getIdArticulo()); // obtener cantidad actual

                        Map<String, Number> data = new HashMap<>();
                        data.put("cantidadCarrito", carrito.getCantidad());
                        data.put("cantidadDisponible", articulo.getCantidadDisponible());
                        data.put("precioArticulo", articulo.getPrecio());

                        jResp.success(req, resp, "cantidad disminuida", data);
                    }else{
                        //false
                        jResp.failed(req,resp,"El articulo no tiene stock",HttpServletResponse.SC_CONFLICT);
                    }
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

        resp.setContentType("application/json");
        //  (DELETE)   ->  /api/carrito  <-
        if(req.getPathInfo() == null){
            jResp.failed(req, resp, "recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String[] path = req.getPathInfo().split("/");

        /*
            ELIMINAR UN ARTICULO DEL CARRITO DE UN USUARIO
            id = id de la tabla carrito

            <informacion requerrida>
            -   id -> recuperado del url
            -   id_usuario -> recuperado del body
         */
        //  (DELETE)   ->  /api/carrito/{id}  <-
        if(path.length == 2){
            if(path[1].matches("-?\\d+")){
                //obtener informacion
                Gson gson = new Gson();
                Carrito carrito = gson.fromJson(req.getReader(), Carrito.class);

                //obtener el id del endpoint
                String pathInfo = req.getPathInfo();
                String idStr = pathInfo.substring(1); // Elimina la barra inicial
                int id = Integer.parseInt(idStr);

                //realizar la eliminacion
                int success = carritoDAO.eliminar(id, carrito.getIdUsuario());

                if(success > 0){
                    //actualizar el stock porque se libero cierta cantidad
                    Articulo articulo = articuloDAO.consultar(carrito.getIdArticulo());
                    int nuevaCantidad = articulo.getCantidadDisponible() + carrito.getCantidad();
                    articuloDAO.actualizarStock(articulo.getId(), nuevaCantidad);

                    articulo = articuloDAO.consultar(carrito.getIdArticulo());

                    jResp.success(req, resp, "Articulo eliminado con exito", articulo);
                }else{
                    jResp.failed(req, resp, "Error al eliminar articulo", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, success);
                }
            }else{
                //error cuando el id no es numerico
                jResp.failed(req, resp, "El ID debe ser numérico", HttpServletResponse.SC_BAD_REQUEST);
            }
        }else{
            jResp.failed(req, resp, "recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
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

    public boolean articuloDisponible(Articulo articulo){
        if(articulo.getCantidadDisponible() > 0){
            return true;
        }
        return false;
    }
}