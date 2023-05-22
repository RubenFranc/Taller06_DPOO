package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modelo.Ingrediente;
import modelo.ProductoAjustado;
import modelo.ProductoMenu;

public class ProductoAjustadoTest {
	
	private static ProductoMenu productoMenu;
	private static Ingrediente ingrediente;
	private static ProductoAjustado productoAjustado1;
	private static ProductoAjustado productoAjustado2;
	private static ProductoAjustado productoAjustado3;
	
	@BeforeAll
	public static void setUp() {
		productoMenu = new ProductoMenu("ProductoPrueba",10000.0,9);
		ingrediente = new Ingrediente("Prueba", 100, 0);
		productoAjustado1 = new ProductoAjustado(productoMenu);
		productoAjustado1.agregarIngrediente(ingrediente);
		productoAjustado2 = new ProductoAjustado(productoMenu);
		productoAjustado2.eliminarIngrediente(ingrediente);
		productoAjustado3 = new ProductoAjustado(productoMenu);
		productoAjustado3.agregarIngrediente(ingrediente);
		productoAjustado3.eliminarIngrediente(ingrediente);
	}
	
	@Test
	public void testAgregarIngrediente() {
		assertAll("Agregar Producto NO funciona",
				()->assertEquals(10100.0,productoAjustado1.getPrecio()),
				()->assertEquals("Prueba,",productoAjustado1.getAgregados())
				);
	}
	
	@Test
	public void testEliminarIngrediente() {
		assertAll("Eliminar Producto NO funciona",
				()->assertEquals(10000.0,productoAjustado2.getPrecio()),
				()->assertEquals("Prueba,",productoAjustado2.getEliminados())
				);
	}
	
	@Test
	public void testGetPrecio() {
		assertAll("Get precio NO funciona",
				()->assertEquals(10000.0,productoAjustado2.getPrecio()),
				()->assertEquals(10100.0,productoAjustado1.getPrecio())
				);
	}
	
	@Test
	public void testGenerarTextoFactura() {
		assertAll("Generar texto factura NO funciona",
				()->assertEquals("9. ProductoPrueba -> $10000.0\n  (Sin Prueba,)\n", productoAjustado2.generarTextoFactura()),
				()->assertEquals("9. ProductoPrueba -> $10100.0\n  (Con adición de Prueba,)\n", productoAjustado1.generarTextoFactura()),
				()->assertEquals("9. ProductoPrueba -> $10100.0\n  (Con adición de Prueba,)\n  (Sin Prueba,)\n", productoAjustado3.generarTextoFactura())
				);
	}
	
	@Test
	public void testGetNo() {
		assertEquals(9, productoAjustado1.getNo(), "Get list number funciona");
	}
	
	@Test
	public void testGetNombre() {
		assertEquals("ProductoPrueba", productoAjustado1.getNombre(), "Get nombre NO funciona");
	}
	
}
