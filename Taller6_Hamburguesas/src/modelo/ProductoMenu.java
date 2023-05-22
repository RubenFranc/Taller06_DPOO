package modelo;

public class ProductoMenu implements Producto{
	
	private String nombre;
	private double precio;
	private int n;

	public ProductoMenu(String nombre, double precioBase, int n) {
		this.nombre = nombre;
		this.precio = precioBase;
		this.n = n;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public double getPrecio() {
		return precio;
	}

	@Override
	public String generarTextoFactura() {
		return n + ". " + nombre + " -> $" + precio + "\n";
	}

	@Override
	public int getNo() {
		return n;
	} 
	
}