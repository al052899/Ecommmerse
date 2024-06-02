package modelo;

public class ArticuloPreparado {
    private int id, id_usuario, id_articulo, cantidad, cantidad_disponible;
    private String titulo, galeria_fotos;
    private Double precio;

    public ArticuloPreparado(int id, int id_usuario, int id_articulo, int cantidad, String titulo, Double precio, String galeria_fotos, int cantidad_disponible) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_articulo = id_articulo;
        this.cantidad = cantidad;
        this.titulo = titulo;
        this.precio = precio;
        this.galeria_fotos = galeria_fotos;
        this.cantidad_disponible = cantidad_disponible;
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

    public String getTitulo() {
        return titulo;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getGaleriaFotos() {
        return galeria_fotos;
    }

    public int getCantidadDisponible() {
        return cantidad_disponible;
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setGaleriaFotos(String galeria_fotos) {
        this.galeria_fotos = galeria_fotos;
    }

    public void setCantidadDisponible(int cantidad_disponible) {
        this.cantidad_disponible = cantidad_disponible;
    }
}
