package database;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CrudSelecteProductosTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private CrudSelecteProductos crudSelecteProductos;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        crudSelecteProductos = new CrudSelecteProductos(mockConnection);
    }

    @Test
    void testAllProducts() throws SQLException {
        // Simular comportamiento del Statement y ResultSet
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("select * from productos;")).thenReturn(mockResultSet);

        // Simular datos en el ResultSet
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString(2)).thenReturn("P001");
        when(mockResultSet.getString(1)).thenReturn("Producto 1");
        when(mockResultSet.getFloat(4)).thenReturn(10.0f);
        when(mockResultSet.getFloat(6)).thenReturn(500.0f);
        when(mockResultSet.getDate(5)).thenReturn(java.sql.Date.valueOf("2024-12-31"));
        when(mockResultSet.getFloat(3)).thenReturn(300.0f);
        when(mockResultSet.getInt(7)).thenReturn(1);

        // Ejecutar método
        ObservableList<CrudSelecteProductos> productos = crudSelecteProductos.allProducts();

        // Verificar resultados
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals("P001", productos.get(0).getCodigo());
        assertEquals("Producto 1", productos.get(0).getName());
    }

    @Test
    void testSearch() throws SQLException {
        String testNombre = "Producto 1";
        String testCodigo = "P001";

        // Simular comportamiento del PreparedStatement y ResultSet
        when(mockConnection.prepareStatement("select * from productos where name=? or codigo=?;"))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Simular datos en el ResultSet
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString(2)).thenReturn(testCodigo);
        when(mockResultSet.getString(1)).thenReturn(testNombre);
        when(mockResultSet.getFloat(4)).thenReturn(10.0f);
        when(mockResultSet.getFloat(6)).thenReturn(500.0f);
        when(mockResultSet.getDate(5)).thenReturn(java.sql.Date.valueOf("2024-12-31"));
        when(mockResultSet.getFloat(3)).thenReturn(300.0f);
        when(mockResultSet.getInt(7)).thenReturn(1);

        // Ejecutar método
        ObservableList<CrudSelecteProductos> productos = crudSelecteProductos.search(testNombre, testCodigo);

        // Verificar resultados
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals(testCodigo, productos.get(0).getCodigo());
        assertEquals(testNombre, productos.get(0).getName());
    }
}
