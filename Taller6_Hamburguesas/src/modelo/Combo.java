package modelo;

import java.util.ArrayList;

public class Combo implements Producto {

	private double descuento;
	private String nombreCombo;
	private ArrayList<Producto> items;
	private int n;
	
	public Combo(String nombre, double descuento, int n) {
		this.descuento = descuento;
		this.nombreCombo = nombre;
		this.items = new ArrayList<Producto>();
		this.n = n;
	}
	
	public void agregarItemACombo(Producto itemCombo) {
		items.add(itemCombo);
	}
	
	public ArrayList<Producto> getItems(){
		return items;
	}
	
	@Override
	public double getPrecio() {
		double p = 0;
		for (Producto producto: items) {
			p += producto.getPrecio()*(1 - descuento);
		}
		return p;
	}
	
	@Override
	public String generarTextoFactura() {
		String mssg = n + ". " + nombreCombo + " -> $" + getPrecio() + "\n";
		for (Producto producto: items) {
			mssg += "  -" + producto.getNombre() + "\n";
		}
		return mssg;
	}

	@Override
	public String getNombre() {
		return this.nombreCombo;
	}

	@Override
	public int getNo() {
		return n;
	}
	
}