package modelo;

public class Carrito {
    private int id, id_usuario, id_articulo, cantidad;

    public Carrito(int id, int id_usuario, int id_articulo, int cantidad){
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_articulo = id_articulo;
        this.cantidad = cantidad;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public int getIdArticulo() {
        return id_articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setIdArticulo(int id_articulo) {
        this.id_articulo = id_articulo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Object[] getAll(){
        return new Object[]{
                getIdUsuario(),
                getIdArticulo(),
                getCantidad()
        };
    }
}