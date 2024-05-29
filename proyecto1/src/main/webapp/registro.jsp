

<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
        <title>Crear</title>
    </head>
    <body>
        <h1>Registrarse</h1>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">

                    <form action="registro.jsp" method="post">
                        <div class="form-group">
                            <label for="nombre">Usuario</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required="required">
                        </div>
                        <div class="form-group">
                            <label for="email">email</label>
                            <input type="text" class="form-control" id="email" name="email" placeholder="Email" required="required">
                        </div>
                        
                        <button type="submit" name="enviar" class="btn btn-primary">Guardar <i class="fa fa-floppy-o" aria-hidden="true"></i></button>
                    </form>

                </div>
            </div>
        </div>
                <%
            if (request.getParameter("enviar") != null) {
                String nombre = request.getParameter("nombre");
                String email = request.getParameter("email");

                try {
                    Connection con=null;
                    Statement st=null;
                    
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con=DriverManager.getConnection("jdbc:mysql://localhost/almacen?user=root&password=");
                    st=con.createStatement();
                    st.executeUpdate("insert into usuarios (user,email) values('"+nombre+"','"+email+"');");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } catch (Exception e) {
                    out.print(e);
                }

            }
        %>
    </body>
</html>
