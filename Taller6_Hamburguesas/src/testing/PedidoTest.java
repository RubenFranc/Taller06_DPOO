package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoMenu;

public class PedidoTest {
	
	private static Pedido pedido;
	private static Producto producto;
	private static Producto productoExcepcion;
	
	@BeforeAll
	public static void setUp() throws Exception {
		productoExcepcion = new ProductoMenu("ProductoExcepcion",200000.0,0);
		producto = new ProductoMenu("ProductoPrueba",10000.0,0);
		pedido = new Pedido("Rubén","Dirección",5);
		pedido.agregarProducto(producto);
		String ruta = "pedidos/6.txt";
		File file = new File(ruta);
		pedido.guardarFactura(file);
	}
	
	@Test
	public void testAgregarProducto() {
		assertAll("Agregar producto NO funciona",
				()->assertEquals(1,pedido.getProductos().size()),
				()->assertEquals(10000.0,pedido.getPrecioNetoPedido()),
				()->assertEquals(1900.0,pedido.getPrecioIVAPedido()),
				()->assertEquals(11900.0,pedido.getPrecioTotalPedido()),
				()->assertTrue(pedido.getProductos().contains(producto))
				);
	}
	
	@Test
    public void testException() {
        assertThrows(Exception.class, () -> {
            pedido.agregarProducto(productoExcepcion);
        });
    }
	
	@Test
	public void testGenerarTextoFactura() {
		String factura = "";
		factura += "RESTAURANTE DE HAMBURGUESAS DPOO\n";
		factura += "--------------------------------\n\n";
		factura += "ID pedido: 6\n";
		factura += "Nombre cliente: Rubén\n";
		factura += "Dirección cliente: Dirección\n\n";
		factura += "Artículos comprados\n--------------------------------\n";
		factura += "0. ProductoPrueba -> $10000.0\n";
		factura += "\n--------------------------------\nPrecio neto: $10000.0\n";
		factura += "IVA: $1900.0\n";
		factura += "Precio total: $11900.0\n--------------------------------\n";
		factura += "\n¡Gracias por su compra!";
		assertEquals(factura, pedido.generarTextoFactura(), "Generar texto factura NO funciona");
	}
	
	@Test
	public void testIdPedido() {
		assertEquals(6, pedido.getIdPedido(), "Get id pedido NO funciona");
	}
	
	@Test
    public void testGuardarFactura() {
		String ruta = "pedidos/6.txt";
		File file = new File(ruta);
        assertTrue(file.exists());
    }
	
}
