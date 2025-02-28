package database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrudSelecteProductosIT {  // IT = Integration Test

    private Connection connection;
    private CrudSelecteProductos crudSelecteProductos;

    @BeforeEach
    void setUp() throws SQLException {
        // Conexión a la base de datos real (Asegúrate de que la DB está creada)
        String url = "jdbc:mysql://localhost:3306/pruebaingesoft?serverTimezone=UTC"; // Cambia el nombre de la base de datos
        String user = "root"; // Usuario de la BD
        String password = "957846Oso"; // Contraseña de la BD

        connection = DriverManager.getConnection(url, user, password);
        crudSelecteProductos = new CrudSelecteProductos(connection);
    }

    @Test
    void testAllProducts() {
        ObservableList<CrudSelecteProductos> productos = crudSelecteProductos.allProducts();
        assertNotNull(productos);
        assertTrue(productos.size() > 0, "Debe haber al menos un producto en la base de datos.");
    }

    @Test
    void testSearch() {
        String nombre = "Producto 1"; // Asegúrate de que este producto existe en la BD
        String codigo = "P001";

        ObservableList<CrudSelecteProductos> productos = crudSelecteProductos.search(nombre, codigo);
        assertNotNull(productos);
        assertFalse(productos.isEmpty(), "Debe haber al menos un producto con este código o nombre.");
        assertEquals(codigo, productos.get(0).getCodigo(), "El código del producto debe coincidir.");
    }
}
