package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import excepciones.IngredienteRepetidoException;
import excepciones.ProductoRepetidoException;

public class Restaurante {
	
	private int numeroPedidos;
	private Pedido pedidoEnCurso;
	private ArrayList<Producto> menuBase;
	private ArrayList<Ingrediente> ingredientes;
	private Map<String, Ingrediente> mapIngredientes;
	private Map<String, Producto> mapMenuBase;
	private Map<Integer, String> mapPedidos;
	private Map<Integer, Producto> mapNoProducto;
	private Map<Integer, Ingrediente> mapNoIngrediente;
	
	public Restaurante(int numeroPedidos) {
		this.numeroPedidos = numeroPedidos;
		this.menuBase = new ArrayList<>();
		this.ingredientes = new ArrayList<>();
		this.mapIngredientes = new HashMap<>();
		this.mapMenuBase = new HashMap<>();
		this.mapPedidos = new HashMap<>();
		this.mapNoProducto = new HashMap<>();
		this.mapNoIngrediente = new HashMap<>();
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente) {
		Pedido pedido = new Pedido(nombreCliente, direccionCliente, numeroPedidos);
		this.pedidoEnCurso = pedido;
		this.numeroPedidos +=1;
	}
	
	public Pedido getPedidoEnCurso() {
		return pedidoEnCurso;
	}
	
	public void cerrarYGuardarPedido() throws IOException {
		mapPedidos.put(pedidoEnCurso.getIdPedido(), pedidoEnCurso.generarTextoFactura());
		String ruta = "pedidos/"+pedidoEnCurso.getIdPedido()+".txt";
		File file = new File(ruta);
		pedidoEnCurso.guardarFactura(file);
	}
	
	public ArrayList<Producto> getMenuBase(){
		return this.menuBase;
	}
	
	public ArrayList<Ingrediente> getIngredientes() {
		return this.ingredientes;
	}
	
	public Map<Integer, String> getPedidos(){
		return this.mapPedidos;
	}
	
	public Producto getProducto(String nombre) {
		return mapMenuBase.get(nombre);
	}
	
	public Ingrediente getIngrediente(int No) {
		return mapNoIngrediente.get(No);
	}
	
	public Producto getProducto(int No) {
		return mapNoProducto.get(No);
	}
	
	public Map<Integer, Producto> getProductosNo() {
		return mapNoProducto;
	}
	
	private boolean cargarIngredientes(String archivoIngredientes, int n) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
		String linea = br.readLine();
		IngredienteRepetidoException controladorExcepcion = new IngredienteRepetidoException();
		while (linea != null) {
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String cA = partes[1].replace("\n", "");
			int costoAdicional = Integer.parseInt(cA);
			Ingrediente ingrediente = new Ingrediente(nombre, costoAdicional, n);
			try {
				controladorExcepcion.ingredienteRepetidoExcep(ingrediente, mapIngredientes);
				mapIngredientes.put(nombre, ingrediente);
				mapNoIngrediente.put(n, ingrediente);
				ingredientes.add(ingrediente);	
			}
			catch (Exception e) {
				System.out.println("¡Hubo un error!");
				System.out.println(e.getMessage() + " (" + ingrediente.getNombre() + ")");
				e.printStackTrace();
				return true;
			}
			linea = br.readLine();
			n++;
		}
		br.close();
		return false;
	}
	
	private boolean cargarMenu(String archivoMenu, int n) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
		String linea = br.readLine();
		ProductoRepetidoException controladorExcepcion = new ProductoRepetidoException();
		while (linea != null) {
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String p = partes[1].replace("\n", "");
			int precio = Integer.parseInt(p);
			ProductoMenu producto = new ProductoMenu(nombre, precio, n);
			try {
				controladorExcepcion.productoRepetidoExcep(producto, mapMenuBase);
				mapMenuBase.put(nombre, producto);
				mapNoProducto.put(n, producto);
				menuBase.add(producto);
			}
			catch (Exception e) {
				System.out.println("¡Hubo un error!");
				System.out.println(e.getMessage() + " (" + producto.getNombre() + ")");
				e.printStackTrace();
				return true;
			}
			linea = br.readLine();
			n++;
		}
		br.close();
		return false;
	}
	
	private void cargarCombos(String archivoCombos, int n) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(archivoCombos));
		String linea = br.readLine();
		while (linea != null) {
			String[] partes = linea.split(";");
			String nombre = partes[0];
			String dcto = partes[1].replace("%", "");
			double descuento = Integer.parseInt(dcto)*0.01;
			Producto p1 = mapMenuBase.get(partes[2]);
			Producto p2 = mapMenuBase.get(partes[3]);
			Producto p3 = mapMenuBase.get(partes[4]);
			Combo combo = new Combo(nombre, descuento, n);
			combo.agregarItemACombo(p1);
			combo.agregarItemACombo(p2);
			combo.agregarItemACombo(p3);
			mapMenuBase.put(nombre, combo);
			mapNoProducto.put(n, combo);
			menuBase.add(combo);
			linea = br.readLine();
			n++;
		}
		br.close();
	}
	
	public boolean cargarInformacionRestaurante(String archivoIngredientes, String archivoMenu, String archivoCombos) throws IOException {
		int n = 1;
		boolean exception = cargarMenu(archivoMenu,n);
		int n2 = menuBase.size() + 1;
		if (! exception) {
			cargarCombos(archivoCombos, n2);
			int n3 = 1;
			exception = cargarIngredientes(archivoIngredientes, n3);
			
		}
		return exception;
		}
	
}