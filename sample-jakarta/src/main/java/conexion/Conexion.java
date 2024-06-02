package conexion;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;

public final class Conexion<T> {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String bd = dotenv.get("DB_NAME");
    private static final String usuario = dotenv.get("DB_USER");
    private static final String password = dotenv.get("DB_PASSWORD");
    private static final String host = dotenv.get("DB_HOST");
    private static final String puerto = dotenv.get("DB_PORT");
    private final String url;

    private Connection conexion;

    public Conexion() {
        url = "jdbc:mysql://" + host + ":" + puerto + "/" + bd;
    }

    public boolean abrir () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    public Connection obtener() {
        return conexion;
    }

    public boolean cerrar() {
        try {
            conexion.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ArrayList<ArrayList<String>> ejecutarConsulta(String query, String[] params) {
        if (abrir()) {
            try {
                PreparedStatement pst = this.conexion.prepareStatement(query);
                for (int i = 0; i < params.length; i++) {
                    pst.setString(i + 1, params[i]);
                }
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData metadata = rs.getMetaData();

                int numColumnas = metadata.getColumnCount();
                String[] columnas = new String[numColumnas];
                for (int i = 1; i <= numColumnas; i++) {
                    columnas[i-1] = metadata.getColumnName(i);
                }

                ArrayList<ArrayList<String>> registros = new ArrayList<ArrayList<String>>();
                while (rs.next()) {
                    ArrayList<String> registro = new ArrayList<String>();
                    for (String columna: columnas) {
                        registro.add(rs.getString(columna));
                    }
                    registros.add(registro);
                }

                return registros;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                cerrar();
            }
        }

        return null;
    }

    public int ejecutarActualizacion (String query, Object[] values) {
        if (this.abrir()) {
            try {
                PreparedStatement pstm = this.conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1;

                for (Object val: values) {
                    switch (val.getClass().getName()) {
                        case "java.lang.Integer":
                            pstm.setInt(index, (Integer) val);
                            break;
                        case "java.lang.String":
                            pstm.setString(index, (String) val);
                            break;
                    }
                    index++;
                }

                pstm.executeUpdate();
                ResultSet rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                cerrar();
            }
        }

        return -1;
    }

    /**
     * Metodo necesario para ejecutar la eliminacion, esta devuelve las lineas afectadas
     * @param query la consulta sql
     * @param values los valores a cambiar
     * @return
     */
    public int executeUpdate(String query, Object[] values) {
        if (this.abrir()) {
            try {
                PreparedStatement pstm = this.conexion.prepareStatement(query);
                int index = 1;

                for (Object val: values) {
                    switch (val.getClass().getName()) {
                        case "java.lang.Integer":
                            pstm.setInt(index, (Integer) val);
                            break;
                        case "java.lang.String":
                            pstm.setString(index, (String) val);
                            break;
                    }
                    index++;
                }

                int affectedRows = pstm.executeUpdate(); // Obtiene el número de filas afectadas
                return affectedRows; // Devuelve el número de filas afectadas
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                cerrar();
            }
        }

        return -1;
    }



    public boolean verificacionConsulta(String query, String param) {
        try {
            if (conexion == null) {
                abrir();
            }
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, param);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}