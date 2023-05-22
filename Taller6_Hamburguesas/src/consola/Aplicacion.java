package consola;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import modelo.Restaurante;
import modelo.Ingrediente;
import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoAjustado;

public class Aplicacion {
	
	public void ejecutarAplicacion() throws Exception {
		System.out.println("\nBienvenido al restaurante de hamburguesas DPOO");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		
		BufferedReader br = new BufferedReader(new FileReader("numeroPedidos/numeroPedidos.txt"));
		String linea = br.readLine();
		int numeroPedidosInicial = Integer.parseInt(linea);
		br.close();
		Restaurante restaurante = new Restaurante(numeroPedidosInicial);
		boolean excepction = restaurante.cargarInformacionRestaurante("data/ingredientes.txt", "data/menu.txt", "data/combos.txt");
		
		
		if (excepction==false) {
			System.out.println("MENÚ:\n------------------------------");
			ArrayList<Producto> menuBase = restaurante.getMenuBase();

			for (Producto producto: menuBase) {
				System.out.println(producto.generarTextoFactura());
			}
			
			Map<Integer, String> mapPedidos = restaurante.getPedidos();
			String idPedidos = "";
			BufferedReader br_2 = new BufferedReader(new FileReader("idPedidos.txt"));
			String id = br_2.readLine();
	        if (!id.equals("0")) {
	    		idPedidos += id + "\n";
	    		while (id != null) {
	    			String id_ = id.replace("\n", "");
	    			BufferedReader br_3 = new BufferedReader(new FileReader("pedidos/"+ id_ +".txt"));
	    			String lineaFactura = br_3.readLine();
	    			String factura_1 = "";
	    			while (lineaFactura != null) {
	    				factura_1 += lineaFactura + "\n";
	    				lineaFactura = br_3.readLine();
	    			}
	    			br_3.close();
	    			mapPedidos.put(Integer.parseInt(id), factura_1);
	    			idPedidos += id + "\n";
	    			id = br_2.readLine();
	    		}
	    		br_2.close();
	        }		
			
			boolean continuar = true;
			boolean pedidoEnCurso = false;
			double nItems = 0;
			Pedido pedido = null;
			while (continuar){
				try{
					mostrarMenu(pedidoEnCurso);
					int opcion_seleccionada = Integer.parseInt(input("\nPor favor seleccione una opción"));
					if (pedidoEnCurso == false) {
						if (opcion_seleccionada == 1) {
							pedidoEnCurso = true;
							String nombreCliente = input("\nPor favor ingrese su nombre");
							String direccionCliente = input("\nPor favor ingrese su dirección");
							restaurante.iniciarPedido(nombreCliente, direccionCliente);
							pedido = restaurante.getPedidoEnCurso();
						}
						else if (opcion_seleccionada == 2){
							int ID = Integer.parseInt(input("\nPor favor ingrese el ID del pedido"));
							String factura = mapPedidos.get(ID);
							if (factura == null) {
								System.out.println("\nNo existe ningún pedido cuyo ID sea " + ID);
							}
							else {
								System.out.println("\n");
								System.out.println(factura);
							}
						}
						else {
							System.out.println("Saliendo de la aplicación ...");
							continuar = false;
							
							File file = new File("numeroPedidos/numeroPedidos.txt");
							FileWriter fw = new FileWriter(file);
					        BufferedWriter bw = new BufferedWriter(fw);
					        PrintWriter wr = new PrintWriter(bw);  
					        int numeroPedidosFinal = restaurante.getPedidos().size();
					        String numeroPedidos = Integer.toString(numeroPedidosFinal);
					        wr.write(numeroPedidos);
					        wr.close();
					        bw.close();
							
						}
					}
					else {
						if (opcion_seleccionada == 1) {
							for (Producto producto: menuBase) {
								System.out.println(producto.generarTextoFactura());
							}
							int No = Integer.parseInt(input("\nPor favor ingrese el número del producto que desea agregar al pedido"));
							Producto producto = restaurante.getProducto(No);
							if (producto != null) {
								System.out.println("\n¿Desea agregar o quitar algún ingrediente?");
								System.out.println("1. Agregar ingrediente");
								System.out.println("2. Quitar ingrediente");
								System.out.println("3. No deseo hacer ningún cambio");
								
								String ajustar = input("\nSeleccione una opción");
								if (ajustar.equals("1") || ajustar.equals("2")){
									ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();
									if (ajustar.equals("1")) {
										for (Ingrediente ingrediente: ingredientes) {
										System.out.println("\n"+ ingrediente.getNo() +"* " + ingrediente.getNombre() + " -> $"+ingrediente.getCostoAdicional());
										}
										int noIngrediente = Integer.parseInt(input("\nPor favor ingrese el número del ingrediente del que desea la adición"));
										Ingrediente ingrediente = restaurante.getIngrediente(noIngrediente);
										if (ingrediente != null) {
											ProductoAjustado productoAjustado = new ProductoAjustado(producto);
											//System.out.println(productoAjustado.getCalorias());
											productoAjustado.agregarIngrediente(ingrediente);
											//System.out.println(productoAjustado.getCalorias());
											pedido.agregarProducto(productoAjustado);
										}
										else {
											System.out.println("Seleccione un ingrediente válido.");
										}
										
									}
									else {
										for (Ingrediente ingrediente: ingredientes) {
											System.out.println("\n"+ ingrediente.getNo() +"* " + ingrediente.getNombre() + " -> $"+ingrediente.getCostoAdicional());
										}
										int noIngrediente = Integer.parseInt(input("\nPor favor ingrese el número del ingrediente del que desea la adición"));
										Ingrediente ingrediente = restaurante.getIngrediente(noIngrediente);
										if (ingrediente != null) {
											ProductoAjustado productoAjustado = new ProductoAjustado(producto);
											//System.out.println(productoAjustado.getCalorias());
											productoAjustado.eliminarIngrediente(ingrediente);
											//System.out.println(productoAjustado.getCalorias());
											pedido.agregarProducto(productoAjustado);
										}
										else {
											System.out.println("Seleccione un ingrediente válido.");
										}
									}	
								}
								else {
									pedido.agregarProducto(producto);
								}
								nItems += 1;
							}
							else {
								System.out.println("Seleccione un producto válido.");
							}
						}
						else {
							if (nItems > 0 & opcion_seleccionada == 2) {

								mapPedidos.put(pedido.getIdPedido(), pedido.generarTextoFactura());
								restaurante.cerrarYGuardarPedido();
								pedidoEnCurso = false;
								int idPedido = pedido.getIdPedido();
								System.out.println("\nID PEDIDO: " + idPedido + "\n");
								idPedidos += idPedido + "\n";
								File file = new File("idPedidos.txt");
								FileWriter fw = new FileWriter(file);
						        BufferedWriter bw = new BufferedWriter(fw);
						        PrintWriter wr = new PrintWriter(bw);  
						        wr.write(idPedidos);
						        wr.close();
						        bw.close();
							}
						}
					}
				}
				catch (NumberFormatException e)
				{
					System.out.println("Debe seleccionar uno de los números de las opciones.");
				}
			}
		}
		
	}
	
	public static void mostrarMenu(boolean pedidoEnCurso) {
		System.out.println("\nOpciones:");
		if (pedidoEnCurso == false) {
			System.out.println("1. Hacer pedido");
			System.out.println("2. Consultar pedidos por ID");
			System.out.println("3. Cerrar aplicación");
		}
		else {
			System.out.println("1. Agregar producto");
			System.out.println("2. Terminar pedido");
		}
		
		
	}
	
	public String input(String mensaje){
		try{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e){
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Aplicacion aplicacion = new Aplicacion();
		aplicacion.ejecutarAplicacion();
	}

}