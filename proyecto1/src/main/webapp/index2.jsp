
<%@page import="java.util.List"%>
<%@page import="com.example.proyecto1.Crud"%>
<%@page import="com.example.proyecto1.Producto"%>
<%@page import="com.example.proyecto1.Producto1"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Producto> listaProductos = Crud.listarProductos();
    List<Producto1> listaProductos1 = Crud.listarProductosCar();
    %>
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
            #car{
                position: absolute;
                top: 10px; /* Ajusta el valor según sea necesario */
                right: 10px; /* Ajusta el valor según sea necesario */
                width: 40px; /* Ajusta el valor según sea necesario */
                height: 40px; /* Ajusta el valor según sea necesario */
              
            }
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
            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
                cursor: pointer;
            }

            .close:hover,
            .close:focus {
                color: #000;
                text-decoration: none;
                cursor: pointer;
            }
            
            body{
                font-family: "Poppins", sans-serif;
                height: 100vh;

                margin: 0;
                background-size: 100vw 100vh;
                background-image:url(fonduki_3.jpeg);
                background-attachment: fixed;
                box-sizing: border-box;
            }

       
        </style>
      
        
    </head>
    <body>
        
        <h1>Tienda</h1>
        <div id='car'>
            <a onclick="document.getElementById('mi-modal').style.display = 'block'"">
                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
                <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </svg>
            </a>
            
        </div>
        <div id="mi-modal" class="modal">
            <div class="modal-contenido">
                <span class="close" onclick="document.getElementById('mi-modal').style.display = 'none'">&times;</span>
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
                                            <% for (Producto1 producto1 : listaProductos1) { %>
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
        </div>
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
                                        <th scope="col">Cantidad</th> 
                                    </tr>
                                </thead>
                                <tbody>
				<% for (Producto producto : listaProductos) { %>
					<tr>
						<td><%= producto.getId() %></td>
						<td><%= producto.getNombre() %></td>
						<td><%= producto.getDescripcion() %></td>
						<td><%= producto.getPrecio() %></td>
						<td><%= producto.getCantidad() %></td>
                                                <td>      
                                                   
                                                    <a href="editar.jsp?id=<%= producto.getId()%>&nombre=<%= producto.getNombre()%>&descripcion=<%= producto.getDescripcion()%>&precio=<%= producto.getPrecio()%>&cantidad=<%= producto.getCantidad()%>"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                                    <a href="borrar.jsp?id=<%= producto.getId()%>&nombre=<%= producto.getNombre()%>&descripcion=<%= producto.getDescripcion()%>&precio=<%= producto.getPrecio()%>&cantidad=<%= producto.getCantidad()%>"><i class="fa fa-trash" aria-hidden="true"></i></a>
                                                    <a href="carrito.jsp?id=<%= producto.getId()%>&nombre=<%= producto.getNombre()%>&descripcion=<%= producto.getDescripcion()%>&precio=<%= producto.getPrecio()%>&cantidad=<%= producto.getCantidad()%>"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
                                                    <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                                                    </svg></a>
                                                </td>
                                        </tr>
				<% } %>
			</tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/js/bootstrap.bundle.min.js" integrity="sha384-qKXV1j0HvMUeCBQ+QVp7JcfGl760yU08IQ+GpUo5hlbpg51QRiuqHAJz8+BrxE/N" crossorigin="anonymous"></script>
    <button><a href="crear2.jsp">Agregar</a></button>
    <button><a href="buscar.jsp">Buscar</a></button>
    </body>
</html>


