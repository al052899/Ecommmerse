package dao;

import conexion.Conexion;
import modelo.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO implements DAOGeneral<Integer, User> {
    private final Conexion c;

    public UserDAO() {
        c = new Conexion<User>();
    }

    @Override
    public int agregar(User user) {
        //Registrar un nuevo usuario a la bd
        String query = "INSERT INTO users(nombres, apellidos, email, username, password) VALUES (?, ?, ?, ?, ?)";
        return c.ejecutarActualizacion(query, user.getAll());
    }

    @Override
    public ArrayList<User> consultar() {
        //NO SE USA
        String query = "SELECT * FROM users";

        ArrayList<ArrayList<String>> res = c.ejecutarConsulta(query, new String[]{});
        ArrayList<User> users = new ArrayList<User>();

        for (ArrayList<String> r: res) {
            users.add(
                    new User(
                            Integer.parseInt(r.get(0)), //id
                            r.get(1), //nombres
                            r.get(2), //apellidos
                            r.get(3), //email
                            r.get(4), //username
                            r.get(5) //password
                    )
            );
        }
        return users;
    }

    @Override
    public int actualizar(Integer id, User user) {
        //NO SE USA
        String query = "UPDATE users SET nombres = ?, apellidos = ?, email = ?, username = ?, password = ? WHERE id = ?";
        return c.ejecutarActualizacion(query, user.getAll());
    }

    @Override
    public int eliminar(Integer id) {
        //NO SE USA
        String query = "DELETE FROM users WHERE id = ?";
        Object[] values = {id};
        return c.ejecutarActualizacion(query, values);
    }

    public User consultar(Integer id){
        //Obtener un usuario por su id
        String query = "SELECT * FROM users WHERE id = ?";
        ArrayList<ArrayList<String>> res = c.ejecutarConsulta(query, new String[]{id.toString()});

        if (!res.isEmpty()) {
            ArrayList<String> r = res.get(0);
            int userId = Integer.parseInt(r.get(0)); //id
            String nombres = r.get(1); //nombres
            String apellidos = r.get(2); //apellidos
            String email = r.get(3); //email
            String username = r.get(4); //username
            String password = r.get(5); //password

            User user = new User(
                    userId,
                    nombres,
                    apellidos,
                    email,
                    username,
                    password
            );
            return user;
        }
        return null;
    }

    public User getUserByEmail(String email) {
        //Obtener un usuario por su email
        if (c.abrir()) {
            try {
                String query = "SELECT * FROM users WHERE email = ?";
                PreparedStatement pstm = c.obtener().prepareStatement(query);
                pstm.setString(1, email);
                ResultSet rs = pstm.executeQuery();

                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("nombres"), rs.getString("apellidos"), rs.getString("email"), rs.getString("username"), rs.getString("password"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                c.cerrar();
            }
        }

        return null;
    }
}