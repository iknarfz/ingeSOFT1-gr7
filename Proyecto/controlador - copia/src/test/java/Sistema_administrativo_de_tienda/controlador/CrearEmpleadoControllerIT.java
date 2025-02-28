package Sistema_administrativo_de_tienda.controlador;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.CrudInsert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrearEmpleadoControllerIT {

    private Connection connection;
    private CrudInsert crudInsert;

    @BeforeEach
    void setUp() throws SQLException {
        // Conexión a la base de datos de prueba
        String url = "jdbc:mysql://localhost:3306/pruebaingesoft?serverTimezone=UTC"; // BD de prueba
        String user = "root"; // Usuario de la BD
        String password = "957846Oso"; // Contraseña de la BD

        connection = DriverManager.getConnection(url, user, password);
        crudInsert = new CrudInsert(connection);
    }

    @Test
    void testInsertarUsuarioCorrectamente() throws SQLException {
        // Datos de prueba
        String usuario = "testuser";
        String contraseña = "testpass";
        boolean esJefe = false;

        // Llamamos directamente al CRUD sin pasar por la UI
        crudInsert.insertarUs(usuario, contraseña, esJefe);

        // Verificar que el usuario se insertó en la base de datos
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, usuario);
        ResultSet rs = stmt.executeQuery();

        assertTrue(rs.next(), "El usuario debería existir en la base de datos.");
        assertEquals(usuario, rs.getString("usuario"));
        assertEquals(contraseña, rs.getString("password"));
        assertFalse(rs.getBoolean("jefe")); // Debe ser `false`

        rs.close();
        stmt.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Limpiar la base de datos eliminando el usuario de prueba
        String deleteSql = "DELETE FROM usuarios WHERE usuario = ?";
        PreparedStatement stmt = connection.prepareStatement(deleteSql);
        stmt.setString(1, "testuser");
        stmt.executeUpdate();
        stmt.close();

        if (connection != null) {
            connection.close();
        }
    }
}
