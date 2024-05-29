<%-- 
    Document   : editar
    Created on : 18 abr. 2023, 16:33:25
    Author     : leotu
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
        <title>Crear</title>
    </head>
    <body>
        <h1>Editar Registros</h1>
        <%
            String id1=request.getParameter("id");
            String nombre1=request.getParameter("nombre");
            String descripcion1=request.getParameter("descripcion");
            String precio1=request.getParameter("precio");
            String cantidad1=request.getParameter("cantidad");
            %>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">

                    <form action="editar.jsp" method="get">
                        <div class="form-group">
                            <label for="id1">Id</label>
                            <input type="text" class="form-control" id="id" value="<%= id1%>" name="id" placeholder="Id" readonly>
                        </div>
                        <div class="form-group">
                            <label for="nombre1">Nombre</label>
                            <input type="text" class="form-control" id="nombre" value="<%= nombre1%>" name="nombre" placeholder="Nombre" required="required">
                        </div>
                        <div class="form-group">
                            <label for="descripcion1">Descripcion</label>
                            <input type="text" class="form-control" id="descripcion" value="<%= descripcion1%>" name="descripcion" placeholder="Descripcion" required="required">
                        </div>
                        <div class="form-group">
                            <label for="precio1">Precio</label>
                            <input type="number" class="form-control" id="precio" value="<%= precio1%>" name="precio" placeholder="Precio" required="required">
                        </div>
                        <div class="form-group">
                            <label for="cantidad1">Cantidad</label>
                            <input type="number" class="form-control" id="cantidad" value="<%= cantidad1%>" name="cantidad" placeholder="Cantidad" required="required">
                        </div>
                        <input type="hidden" name="id" id="id" value="<%= id1%>">
                        <button type="submit" name="enviar" class="btn btn-primary">Guardar <i class="fa fa-floppy-o" aria-hidden="true"></i></button>
                        
                    </form>

                </div>
            </div>
        </div>
        <%
            if (request.getParameter("enviar") !=null) {
                int id = Integer.parseInt(id1);
                String nombre = request.getParameter("nombre");
                String descripcion = request.getParameter("descripcion");
                int precio = Integer.parseInt(request.getParameter("precio"));
                int cantidad=Integer.parseInt(request.getParameter("cantidad"));
                
                Crud.actualizarProducto(id, nombre, descripcion, precio, cantidad);
                request.getRequestDispatcher("index2.jsp").forward(request, response);


            }
        %>
    </body>
</html>
