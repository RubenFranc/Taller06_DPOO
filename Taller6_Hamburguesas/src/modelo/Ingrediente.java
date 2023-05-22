package modelo;

public class Ingrediente {	
	
	private String nombre;
	private int costoAdicional;
	private int n;
	
	public Ingrediente (String nombre, int costoAdicional, int n) {
		this.nombre = nombre;
		this.costoAdicional = costoAdicional;
		this.n = n;
	}
	
	public String getNombre () {
		return nombre;
	}
	
	public int getCostoAdicional() {
		return costoAdicional;
	}
	
	public int getNo() {
		return n;
	}
	
}