package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modelo.Combo;
import modelo.Producto;
import modelo.ProductoMenu;

public class ComboTest {
	
	private static Combo combo;
	private static Producto item;
	
	@BeforeAll
	public static void setUp() {
		item = new ProductoMenu("ProductoPrueba",10000.0,0);
		combo = new Combo("Prueba",0.2,9);
		combo.agregarItemACombo(item);
	}
	
	@Test
	public void testAgregarItemACombo() {
		assertAll("Agregar Item NO funciona",
				()->assertEquals(1,combo.getItems().size()),
				()->assertEquals(8000.0,combo.getPrecio()),
				()->assertTrue(combo.getItems().contains(item))
				);
	}
	
	@Test
	public void testGenerarTextoFactura() {
		assertEquals("9. Prueba -> $8000.0\n  -ProductoPrueba\n", combo.generarTextoFactura(), "Generar texto factura NO funciona");
	}
	
	@Test
	public void testGetNo() {
		assertEquals(9, combo.getNo(), "Get list number NO funciona");
	}
	
	@Test
	public void testGetNombre() {
		assertEquals("Prueba", combo.getNombre(), "Get nombre NO funciona");
	}
	
	@Test
	public void testGetPrecio() {
		assertEquals(8000.0, combo.getPrecio(), "Get precio NO funciona");
	}
	
}
