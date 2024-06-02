package dao;

import conexion.Conexion;
import modelo.Articulo;

import java.util.ArrayList;

public class ArticuloDAO implements DAOGeneral<Integer, Articulo>{

    private final Conexion<Articulo> c;

    public ArticuloDAO() {
        c = new Conexion<Articulo>();
    }

    @Override
    public int agregar(Articulo articulo){
        //NO SE USA
        String query = "INSERT INTO articulos(titulo, galeria_fotos, precio, especificaciones, descripcion, cantidad_disponible) VALUES (?, ?, ?, ?, ?, ?)";
        return c.ejecutarActualizacion(query, articulo.getAll());
    }

    @Override
    public ArrayList<Articulo> consultar(){
        //Obtener todos los articulos
        String query = "SELECT * FROM articulos";

        ArrayList<ArrayList<String>> res = c.ejecutarConsulta(query, new String[]{});
        ArrayList<Articulo> articulos = new ArrayList<Articulo>();

        for (ArrayList<String> r: res){
            articulos.add(new Articulo(
                            Integer.parseInt(r.get(0)), //id
                            r.get(1), //titulo
                            r.get(2), //galeria de fotos
                            Double.parseDouble(r.get(3)), //precio
                            r.get(4), //especificaciones
                            r.get(5), //descripcion
                            Integer.parseInt(r.get(6)) //cantidad disponible
                    )
            );
        }
        return articulos;
    }

    @Override
    public Articulo consultar(Integer id){
        //Obtener la vista de un solo articulo
        String query = "SELECT * FROM articulos WHERE id = ?";
        ArrayList<ArrayList<String>> res = c.ejecutarConsulta(query, new String[]{id.toString()});

        if(!res.isEmpty()){
            ArrayList<String> r = res.get(0);
            int articuloId = Integer.parseInt(r.get(0)); //id
            String titulo = r.get(1); //titulo
            String galeriaFotos = r.get(2); //galeria de fotos
            Double precio = Double.parseDouble(r.get(3)); //precio
            String especificaciones = r.get(4); //especificaciones
            String descripcion = r.get(5); //descripcion
            int cantidad = Integer.parseInt(r.get(6)); //cantida disponible

            Articulo articulo = new Articulo(
                    articuloId,
                    titulo,
                    galeriaFotos,
                    precio,
                    especificaciones,
                    descripcion,
                    cantidad
            );
            return articulo;
        }
        return null;
    }

    @Override
    public int actualizar(Integer id, Articulo articulo){
        //NO SE USA
        return 0;
    }

    @Override
    public int eliminar(Integer id){
        //NO SE USA
        String query = "DELETE FROM articulos WHERE id = ?";
        Object[] valoresPreparados = {id};
        return c.ejecutarActualizacion(query, valoresPreparados);
    }

    public int actualizarStock(Integer id, Integer cantidad){
        //Actualizar el stock del articulo cuando este ha sido comprado / o agregado al carrito
        String query = "UPDATE articulos SET cantidad_disponible=? WHERE id=?";
        Object[] valoresPreparados = new Object[]{
                cantidad,
                id
        };
        return  c.ejecutarActualizacion(query, valoresPreparados);
    }
}