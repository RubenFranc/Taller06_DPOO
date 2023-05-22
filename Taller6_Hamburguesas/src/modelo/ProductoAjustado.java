package modelo;

public class ProductoAjustado implements Producto{
	
	private Producto productoBase;
	private double precio;
	private String agregados;
	private String eliminados;

	public ProductoAjustado(Producto base) {
		this.productoBase = base;
		this.precio = productoBase.getPrecio();
		this.agregados = "";
		this.eliminados = "";
	}
	
	public void agregarIngrediente(Ingrediente ingrediente) {
		precio += ingrediente.getCostoAdicional();
		agregados += ingrediente.getNombre() + ",";
	}
	
	public void eliminarIngrediente(Ingrediente ingrediente) {
		eliminados += ingrediente.getNombre() + ",";
	}
	
	public String getAgregados() {
		return agregados;
	}
	
	public String getEliminados() {
		return eliminados;
	}
	
	@Override
	public double getPrecio() {
		return precio;
	}

	@Override
	public String getNombre() {
		return productoBase.getNombre();
	}

	@Override
	public String generarTextoFactura() {
		String mssg = productoBase.getNo() + ". " + productoBase.getNombre() + " -> $" + precio;
		if (agregados != "") {
			mssg += "\n  (Con adición de " + agregados + ")";
		}
		if (eliminados != "") {
			mssg += "\n  (Sin " + eliminados + ")";
		}
		return mssg + "\n";
	}
	
	@Override
	public int getNo() {
		return productoBase.getNo();
	}

}