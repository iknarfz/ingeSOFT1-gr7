package Sistema_administrativo_de_tienda.controlador;

import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.ConnectionDb;
import database.CrudSelecteProductos;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ConsultarProductoController implements Initializable{

    @FXML Button regresar;
    
    @FXML
    private Button buscar;
    
    @FXML
    private DatePicker Fechavencimiento;

    @FXML
    private TextField Nombreproducto;

    @FXML
    private TextField Codigo;
    
    @FXML
    private TableView<CrudSelecteProductos> tablaProductos;
    
    @FXML
    private TableColumn<CrudSelecteProductos, Float> colCantidad;

    @FXML
    private TableColumn<CrudSelecteProductos, String> colCodigo;

    @FXML
    private TableColumn<CrudSelecteProductos, String> colFechaDeVencimiento;

    @FXML
    private TableColumn<CrudSelecteProductos, Integer> colLote;

    @FXML
    private TableColumn<CrudSelecteProductos, String> colNombre;

    @FXML
    private TableColumn<CrudSelecteProductos, Float> colPrecioCompra;

    @FXML
    private TableColumn<CrudSelecteProductos, Float> colPrecioVenta;
    
    Navegador o;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	this.colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
    	this.colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
    	this.colFechaDeVencimiento.setCellValueFactory(new PropertyValueFactory("vencimiento"));
    	this.colLote.setCellValueFactory(new PropertyValueFactory("lot"));
    	this.colNombre.setCellValueFactory(new PropertyValueFactory("name"));
    	this.colPrecioCompra.setCellValueFactory(new PropertyValueFactory("precioC"));
    	this.colPrecioVenta.setCellValueFactory(new PropertyValueFactory("precioV"));
    	
    	
    	Connection con = new ConnectionDb().conectar();
    	CrudSelecteProductos crudSelecteProductos =new CrudSelecteProductos(con);
    	ObservableList<CrudSelecteProductos> obs = crudSelecteProductos.allProducts();
    	this.getTablaProductos().setItems(obs);
    	
		
	}

    @FXML
    public void switchToMenu() {
    	
    	
    	boolean comprobante = InicioController.globalcomprobante;
    	
    	
    	if(comprobante) {
    		
    		o = new Navegador("Menu empleado",regresar, "Menu empleado");
    		o.cambioVentanaAnterior();
	    	
    	}else {
    		o = new Navegador("Menu jefe",regresar,"Menu jefe");
    		o.cambioVentanaAnterior();
    	}
    	
    }
    
    @FXML
    void Buscar(ActionEvent event) {
    	String nombre = getNombreproducto().getText();
    	String codigo = getCodigo().getText();
    	
    	this.colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
    	this.colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
    	this.colFechaDeVencimiento.setCellValueFactory(new PropertyValueFactory("vencimiento"));
    	this.colLote.setCellValueFactory(new PropertyValueFactory("lot"));
    	this.colNombre.setCellValueFactory(new PropertyValueFactory("name"));
    	this.colPrecioCompra.setCellValueFactory(new PropertyValueFactory("precioC"));
    	this.colPrecioVenta.setCellValueFactory(new PropertyValueFactory("precioV"));
    	
    	
    	Connection con = new ConnectionDb().conectar();
    	CrudSelecteProductos crudSelecteProductos =new CrudSelecteProductos(con);
    	ObservableList<CrudSelecteProductos> obs = crudSelecteProductos.search(nombre, codigo);
    	
    	this.getTablaProductos().setItems(obs);
    	
    }

	public TableView<CrudSelecteProductos> getTablaProductos() {
		return tablaProductos;
	}

	public void setTablaProductos(TableView<CrudSelecteProductos> tablaProductos) {
		this.tablaProductos = tablaProductos;
	}

	public TextField getNombreproducto() {
		return Nombreproducto;
	}

	public void setNombreproducto(TextField nombreproducto) {
		Nombreproducto = nombreproducto;
	}

	public TextField getCodigo() {
		return Codigo;
	}

	public void setCodigo(TextField codigo) {
		Codigo = codigo;
	}

}