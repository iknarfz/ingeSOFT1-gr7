package database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

class CrudUpadateIT {

    private Connection connection;
    private CrudUpadate crudUpadate;

    @BeforeEach
    void setUp() throws SQLException {
        // Conexión a la base de datos de prueba
        String url = "jdbc:mysql://localhost:3306/pruebaingesoft?serverTimezone=UTC"; 
        String user = "root";
        String password = "957846Oso";

        connection = DriverManager.getConnection(url, user, password);
        crudUpadate = new CrudUpadate(connection);

        // Insertar un usuario de prueba
        String insertUser = "INSERT INTO usuarios (usuario, password, jefe) VALUES (?, ?, ?)";
        PreparedStatement stmtUser = connection.prepareStatement(insertUser);
        stmtUser.setString(1, "testuser");
        stmtUser.setString(2, "oldpass");
        stmtUser.setBoolean(3, false);
        stmtUser.executeUpdate();
        stmtUser.close();

        // Insertar un producto de prueba (corregido)
        String insertProduct = "INSERT INTO productos (codigo, name, precioCompra, precio, cantidad, vencimiento, lote) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmtProduct = connection.prepareStatement(insertProduct);
        stmtProduct.setString(1, "P001");
        stmtProduct.setString(2, "Producto Test");
        stmtProduct.setFloat(3, 50.0f);
        stmtProduct.setFloat(4, 100.0f); // Corregido
        stmtProduct.setFloat(5, 20.0f); // Corregido
        stmtProduct.setDate(6, Date.valueOf(LocalDate.now().plusDays(30)));
        stmtProduct.setInt(7, 1);
        stmtProduct.executeUpdate();
        stmtProduct.close();
    }

    @Test
    void testUpdatePass() throws SQLException {
        crudUpadate.updatePass("testuser", "newpass");

        String sql = "SELECT password FROM usuarios WHERE usuario = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, "testuser");
        ResultSet rs = stmt.executeQuery();

        assertTrue(rs.next(), "El usuario debería existir.");
        assertEquals("newpass", rs.getString("password"), "La contraseña debería haberse actualizado.");

        rs.close();
        stmt.close();
    }

    @Test
    void testHacerVenta() {
        ObservableList<CrudUpadate> venta = crudUpadate.hacerVenta("P001", 1, 2.0f, "Venta test");

        assertNotNull(venta);
        assertEquals(1, venta.size());
        assertEquals("Producto Test", venta.get(0).getName());
        assertEquals(200.0f, venta.get(0).getPrecio()); // 100 * 2
    }


    @AfterEach
    void tearDown() throws SQLException {
        String deleteVenta = "DELETE FROM ventas WHERE codigo = ?";
        PreparedStatement stmtVenta = connection.prepareStatement(deleteVenta);
        stmtVenta.setString(1, "P001");
        stmtVenta.executeUpdate();
        stmtVenta.close();

        String deleteProducto = "DELETE FROM productos WHERE codigo = ?";
        PreparedStatement stmtProducto = connection.prepareStatement(deleteProducto);
        stmtProducto.setString(1, "P001");
        stmtProducto.executeUpdate();
        stmtProducto.close();

        String deleteUser = "DELETE FROM usuarios WHERE usuario = ?";
        PreparedStatement stmtUser = connection.prepareStatement(deleteUser);
        stmtUser.setString(1, "testuser");
        stmtUser.executeUpdate();
        stmtUser.close();

        if (connection != null) {
            connection.close();
        }
    }
}
