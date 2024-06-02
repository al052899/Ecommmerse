package modelo;

public class Articulo {
    private int id, cantidadDisponible;
    private String titulo, galeriaFotos, especificaciones, descripcion;
    private double precio;

    public Articulo(int id, String titulo, String galeriaFotos, double precio, String especificaciones, String descripcion, int cantidadDisponible) {
        this.id = id;
        this.titulo = titulo;
        this.galeriaFotos = galeriaFotos;
        this.precio = precio;
        this.especificaciones = especificaciones;
        this.descripcion = descripcion;
        this.cantidadDisponible = cantidadDisponible;
    }
    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGaleriaFotos(String galeriaFotos) {
        this.galeriaFotos = galeriaFotos;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGaleriaFotos() {
        return galeriaFotos;
    }

    public double getPrecio() {
        return precio;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public Object[] getAll(){
        return new Object[]{
                getTitulo(),
                getGaleriaFotos(),
                getPrecio(),
                getEspecificaciones(),
                getDescripcion(),
                getCantidadDisponible()
        };
    }
}