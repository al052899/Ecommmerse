package dao;

import conexion.Conexion;
import modelo.ArticuloPreparado;
import modelo.Carrito;

import java.util.ArrayList;

public class CarritoDAO implements DAOGeneral<Integer,Carrito>{
    private final Conexion c;

    public CarritoDAO(){
        c = new Conexion<Carrito>();
    }

    @Override
    public int agregar(Carrito carrito){
        //Guardar un articulo en el carrito
        String query = "INSERT INTO carrito(id_usuario, id_articulo, cantidad) VALUES (?, ?, ?)";
        return c.ejecutarActualizacion(query, carrito.getAll());
    }

    @Override
    public ArrayList<Carrito> consultar(){
        //NO SE USA
        return null;
    }

    @Override
    public Carrito consultar(Integer id){//clave primaria de la tabla carrito
        //Obtener un carrito por su clave primaria
        String query = "SELECT * FROM carrito WHERE id = ?";
        ArrayList<ArrayList<String>> res = c.ejecutarConsulta(query, new String[]{id.toString()});

        if(!res.isEmpty()){
            ArrayList<String> r = res.get(0);
            int clavePrimaria = Integer.parseInt(r.get(0)); //id
            int id_usuario = Integer.parseInt(r.get(1)); //id_usuario
            int id_articulo = Integer.parseInt(r.get(2)); //id_articulo
            int cantidad = Integer.parseInt(r.get(3)); //cantidad

            Carrito carrito = new Carrito(
                    clavePrimaria,
                    id_usuario,
                    id_articulo,
                    cantidad
            );

            return carrito;
        }
        return null;
    }

    @Override
    public int actualizar(Integer id_usuario, Carrito carrito){
        //Actualizar la cantidad de articulos dentro del carrito
        String query = "UPDATE carrito SET cantidad = ? WHERE id = ? AND id_usuario = ?";
        Object[] valoresPreparados = new Object[]{
                carrito.getCantidad(),
                carrito.getId(),
                id_usuario
        };
        return c.ejecutarActualizacion(query, valoresPreparados);
    }

    @Override
    public int eliminar(Integer id_usuario){
        //ELIMINAR TODOS LOS REGISTROS DEL CARRITO DE UN USUARIO
        String query = "DELETE FROM carrito WHERE id_usuario = ?";
        Object[] valoresPreparados = {id_usuario};
        return c.executeUpdate(query, valoresPreparados);
    }

    public ArrayList<ArticuloPreparado> consultaPreparada(Integer id){//id del usuario
        //Consultar el carrito de un usuario
        String query = "SELECT carrito.id, carrito.id_usuario, carrito.id_articulo, carrito.cantidad," +
                "articulos.titulo, articulos.precio, articulos.galeria_fotos, articulos.cantidad_disponible " +
                "FROM carrito " +
                "JOIN articulos " +
                "ON carrito.id_articulo = articulos.id " +
                "WHERE carrito.id_usuario = ?";
        ArrayList<ArrayList<String>> res = c.ejecutarConsulta(query, new String[]{id.toString()}); //id del usuario
        ArrayList<ArticuloPreparado> articulos = new ArrayList<ArticuloPreparado>();

        for(ArrayList<String> r: res){
            articulos.add(
                    new ArticuloPreparado(
                            Integer.parseInt(r.get(0)), //id
                            Integer.parseInt(r.get(1)), //id_usuario
                            Integer.parseInt(r.get(2)), //id_articulo
                            Integer.parseInt(r.get(3)), //cantidad
                            r.get(4), //titulo
                            Double.parseDouble(r.get(5)), //precio
                            r.get(6), //galeria_fotos
                            Integer.parseInt(r.get(7)) //cantidad_disponible
                    )
            );
        }
        return articulos;
    }

    public int eliminar(Integer id, Integer id_usuario){
        String query = "DELETE FROM carrito WHERE id = ? AND id_usuario = ?";
        Object[] valoresPreparados = {id, id_usuario};
        return c.executeUpdate(query, valoresPreparados);
    }

    public boolean consultar(Integer idUsuario, Integer idArticulo){
        //Obtener un carrito por su clave primaria
        String query = "SELECT * FROM carrito WHERE id_usuario = ? AND id_articulo = ?";
        ArrayList<ArrayList<String>> res = c.ejecutarConsulta(query, new String[]{idUsuario.toString(), idArticulo.toString()});

        if(!res.isEmpty()){
            ArrayList<String> r = res.get(0);
            int clavePrimaria = Integer.parseInt(r.get(0)); //id
            int id_usuario = Integer.parseInt(r.get(1)); //id_usuario
            int id_articulo = Integer.parseInt(r.get(2)); //id_articulo
            int cantidad = Integer.parseInt(r.get(3)); //cantidad

            Carrito carrito = new Carrito(
                    clavePrimaria,
                    id_usuario,
                    id_articulo,
                    cantidad
            );
            return true;
        }
        return false;
    }
}