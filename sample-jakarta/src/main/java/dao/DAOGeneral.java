package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAOGeneral<K, E> {

    /**
     * CREAR
     * @param elemento
     * @return
     * @throws SQLException
     */
    public int agregar(E elemento) throws SQLException;

    /**
     * LEER
     * @return
     * @throws SQLException
     */
    public ArrayList<E> consultar() throws SQLException;

    /**
     * LEER - solo 1
     * @param id
     * @return
     * @throws SQLException
     */
    public E consultar(K id) throws SQLException;

    /**
     * ACTUALIZAR
     * @param id
     * @param elemento
     * @return
     * @throws SQLException
     */
    public int actualizar(K id, E elemento) throws SQLException;

    /**
     * ELIMINAR
     * @param id
     * @return
     * @throws SQLException
     */
    public int eliminar(K id) throws SQLException;
}