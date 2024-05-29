<%-- 
    Document   : carrito
    Created on : 13 may 2023, 22:43:06
    Author     : Half
--%>

<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.example.proyecto1.Crud"%>
<%@page import="com.example.proyecto1.Producto"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
        <title>Eliminar</title>
    </head>
    <body>
        <h1>Agregar al Carrito</h1>
        <%
            String id1=request.getParameter("id");
            String nombre=request.getParameter("nombre");
            String descripcion=request.getParameter("descripcion");
            String precio=request.getParameter("precio");
            String cantidad=request.getParameter("cantidad");

        %>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">
                    <%-- Checar siempre el action--%>
                    <form action="carrito.jsp" method="get">
                        <div class="form-group">
                            <label for="id1">Id</label>
                            <input type="text" class="form-control" id="id" value="<%= id1%>" name="id" placeholder="Id" readonly>
                        </div>
                        <div class="form-group">
                            <label for="nombre">Nombre</label>
                            <input type="text" class="form-control" id="nombre" value="<%= nombre%>" name="nombre" placeholder="Nombre" readonly>
                        </div>
                        <div class="form-group">
                            <label for="descripcion">Descripcion</label>
                            <input type="text" class="form-control" id="descripcion" value="<%= descripcion%>" name="descripcion" placeholder="Descripcion" readonly>
                        </div>
                        <div class="form-group">
                            <label for="precio">Precio</label>
                            <input type="number" class="form-control" id="precio" value="<%= precio%>" name="precio" placeholder="Precio" readonly>
                        </div>
                        <div class="form-group">
                            <label for="cantidad">Cantidad</label>
                            <input type="number" class="form-control" id="cantidad" value="<%= cantidad%>" name="cantidad" placeholder="Cantidad" readonly>
                        </div>
                        <input type="hidden" name="id" id="id" value="<%= id1%>">
                        <button type="submit" name="agregar" class="btn btn-primary">Agregar <i class="fa fa-floppy-o" aria-hidden="true"></i></button>

                    </form>

                </div>
            </div>
        </div>
        <%
            if (request.getParameter("agregar") !=null) {
                int id = Integer.parseInt(id1);
            
                Crud.agregarCarrito(id);
                request.getRequestDispatcher("index2.jsp").forward(request, response);
               
               
            }
        %>

    </body>
</html>
