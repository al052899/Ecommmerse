

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.example.proyecto1.Crud"%>
<%@page import="com.example.proyecto1.Producto"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">-->
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <style>
            /* Estilo para el formulario */
            form {
              display: flex;
              flex-direction: column;
              margin: 50px auto;
              width: 300px;
              border: 1px solid #ccc;
              padding: 20px;
              border-radius: 5px;
              box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
            }
            label {
              margin-bottom: 10px;
              font-weight: bold;
            }
            input[type="number"] {
              padding: 10px;
              border-radius: 3px;
              border: 1px solid #ccc;
              margin-bottom: 20px;
            }
            button[type="submit"] {
              background-color: #007bff;
              color: #fff;
              border: none;
              padding: 10px 20px;
              border-radius: 3px;
              cursor: pointer;
            }
            button[type="submit"]:hover {
              background-color: #0069d9;
            }
            /* Estilo para los resultados */
            .resultado {
              margin: 50px auto;
              width: 500px;
              border: 1px solid #ccc;
              padding: 20px;
              border-radius: 5px;
              box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
            }
            .resultado p {
              margin-bottom: 10px;
              font-weight: bold;
            }
      </style>
    </head>
    <body>
        <h1>Buscar por Id</h1>
      
        <%
            // Verifica si se ha enviado el formulario
            if (request.getMethod().equals("POST")) {
              // Obtiene el ID del producto del formulario
              int id = Integer.parseInt(request.getParameter("id"));
              // Llama a la función buscarProductoPorId para obtener el producto correspondiente
              Producto producto = Crud.buscarProductoPorId(id);
              // Muestra los resultados en la página
              if (producto != null) {
        %>
            <div class="resultado">
              <p>ID: <%= producto.getId() %></p>
              <p>Nombre: <%= producto.getNombre() %></p>
              <p>Descripción: <%= producto.getDescripcion() %></p>
              <p>Precio: <%= producto.getPrecio() %></p>
              <p>Cantidad: <%= producto.getCantidad() %></p>
            </div>
        <%
          } else {
        %>
            <div class="resultado">
              <p>No se encontró ningún producto con ese ID</p>
            </div>
        <%
          }
        }
        %>

        <form method="post">
          <label for="id">ID del producto:</label>
          <input type="number" name="id" id="id">
          <button type="submit">Buscar</button>
        </form>
    </body>
</html>