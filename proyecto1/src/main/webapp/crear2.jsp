

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
        <h1>Insert Registros</h1>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">

                    <form action="crear2.jsp" method="post">
                        <div class="form-group">
                            <label for="nombre">Nombre</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required="required">
                        </div>
                        <div class="form-group">
                            <label for="direccion">Descripcion</label>
                            <input type="text" class="form-control" id="descripcion" name="descripcion" placeholder="Descripcion" required="required">
                        </div>
                        <div class="form-group">
                            <label for="telefono">Precio</label>
                            <input type="text" class="form-control" id="precio" name="precio" placeholder="0" required="required">
                        </div>
                        <div class="form-group">
                            <label for="telefono">Cantidad</label>
                            <input type="text" class="form-control" id="cantidad" name="cantidad" placeholder="0" required="required">
                        </div>
                        <button type="submit" name="enviar" class="btn btn-primary">Guardar <i class="fa fa-floppy-o" aria-hidden="true"></i></button>
                    </form>

                </div>
            </div>
        </div>
                <%
            
            if (request.getParameter("enviar") != null) {
                String nombre = request.getParameter("nombre");
                String descripcion = request.getParameter("descripcion");
                int precio = Integer.parseInt(request.getParameter("precio"));
                int cantidad=Integer.parseInt(request.getParameter("cantidad"));
                
                Crud.agregarProducto(nombre, descripcion, precio, cantidad);
                request.getRequestDispatcher("index2.jsp").forward(request, response);

            }
        %>
    </body>
</html>
