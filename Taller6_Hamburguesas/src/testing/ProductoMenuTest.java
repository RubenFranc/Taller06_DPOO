package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modelo.ProductoMenu;

public class ProductoMenuTest {
	
	private static ProductoMenu productoMenu;
	
	@BeforeAll
	public static void setUp() {
		productoMenu = new ProductoMenu("ProductoPrueba",99999.9,9);
	}
	
	
	@Test
	public void testGenerarTextoFactura() {
		assertEquals("9. ProductoPrueba -> $99999.9\n", productoMenu.generarTextoFactura(), "Generar texto factura NO funciona");
	}
	
	@Test
	public void testGetNo() {
		assertEquals(9, productoMenu.getNo(), "Get list number NO funciona");
	}
	
	@Test
	public void testGetNombre() {
		assertEquals("ProductoPrueba", productoMenu.getNombre(), "Get nombre NO funciona");
	}
	
	@Test
	public void testGetPrecio() {
		assertEquals(99999.9, productoMenu.getPrecio(), "Get precio NO funciona");
	}
	
}
