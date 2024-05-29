<%-- 
    Document   : modalCarrito
    Created on : 13 may 2023, 22:55:21
    Author     : Half
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.example.proyecto1.Crud"%>
<%@page import="com.example.proyecto1.Producto"%>
<%
    List<Producto> listaProductos = Crud.listarProductosCar();
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Modal</title>
        <style>
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0,0,0,0.4);
            }

            .modal-contenido {
                background-color: #fefefe;
                margin: 10% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
            }
        </style>
    </head>
    <body>

        <div id="mi-modal" class="modal">
            <div class="modal-contenido">
                <div class="container mt-5">
                    <div class="row">
                        <div>
                            <div class="col-sm">
                                <div class="table-responsive">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th scope="col">Id</th>
                                                <th scope="col">Nombre</th>
                                                <th scope="col">Descripcion</th>
                                                <th scope="col">Precio</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (Producto1 producto1 : listaProductos) { %>
                                            <tr>
                                                <td><%= producto1.getId() %></td>
                                                <td><%= producto1.getNombre() %></td>
                                                <td><%= producto1.getDescripcion() %></td>
                                                <td><%= producto1.getPrecio() %></td>

                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/js/bootstrap.bundle.min.js" integrity="sha384-qKXV1j0HvMUeCBQ+QVp7JcfGl760yU08IQ+GpUo5hlbpg51QRiuqHAJz8+BrxE/N" crossorigin="anonymous"></script>
            </div>
        </div>

        <button onclick="document.getElementById('mi-modal').style.display = 'block'">Abrir modal</button>


    </body>
</html>

