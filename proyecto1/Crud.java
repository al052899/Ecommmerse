/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.proyecto1;

/**
 *
 * @author leotu
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Crud {
    private static final String INSERT_PRODUCTO = "INSERT INTO productos (nombre, descripcion, precio, cantidad) VALUES (?, ?, ?, ?)";
    private static final String DELETE_PRODUCTO = "DELETE FROM productos WHERE id = ?";
    private static final String UPDATE_PRODUCTO = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, cantidad = ? WHERE id = ?";
    private static final String SELECT_ALL_PRODUCTOS = "SELECT * FROM productos";
    private static final String SELECT_PRODUCTO_BY_ID = "SELECT * FROM productos WHERE id = ?";
    private static final String INSERT_PRODUCTO1 = "INSERT INTO carrito (id, nombre, descripcion, precio) SELECT id, nombre, descripcion, precio FROM productos WHERE id = ?";
    private static final String SELECT_ALL_PRODUCTOSCAR = "SELECT * FROM carrito";
    
    public static void agregarProducto(String nombre, String descripcion, int precio, int cantidad) {
        try (Connection conexion = Conexion.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(INSERT_PRODUCTO)) {
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, precio);
            statement.setInt(4, cantidad);
            statement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static void eliminarProducto(int id) {
        try (Connection conexion = Conexion.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(DELETE_PRODUCTO)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static void actualizarProducto(int id, String nombre, String descripcion, int precio, int cantidad) {
        try (Connection conexion = Conexion.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(UPDATE_PRODUCTO)) {
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, precio);
            statement.setInt(4, cantidad);
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        try (Connection conexion = Conexion.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(SELECT_ALL_PRODUCTOS);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Producto producto = new Producto(0,null,null,0,0);
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setCantidad(rs.getInt("cantidad"));
                productos.add(producto);
            }
        } catch (SQLException e) {
        }
        return productos;
    }

    public static Producto buscarProductoPorId(int id) {
        Producto producto = null;
        try (Connection conexion = Conexion.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(SELECT_PRODUCTO_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto(0,null,null,0,0);
                    producto.setId(rs.getInt("id"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setPrecio(rs.getInt("precio"));
                    producto.setCantidad(rs.getInt("cantidad"));
                }
            }
        } catch (SQLException e) {
        }
        return producto;
    }
    public static void agregarCarrito(int id) {
        try (Connection conexion = Conexion.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(INSERT_PRODUCTO1)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public static List<Producto1> listarProductosCar() {
        List<Producto1> productos1 = new ArrayList<>();
        try (Connection conexion = Conexion.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(SELECT_ALL_PRODUCTOSCAR);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Producto1 producto1 = new Producto1(0,null,null,0);
                producto1.setId(rs.getInt("id"));
                producto1.setNombre(rs.getString("nombre"));
                producto1.setDescripcion(rs.getString("descripcion"));
                producto1.setPrecio(rs.getInt("precio"));
                productos1.add(producto1);
            }
        } catch (SQLException e) {
        }
        return productos1;
    }
}
