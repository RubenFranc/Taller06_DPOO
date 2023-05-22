package excepciones;

import java.util.Map;

import modelo.Producto;

public class ProductoRepetidoException extends HamburguesaException{

	public void productoRepetidoExcep(Producto producto, Map<String, Producto> mapMenuBase) throws Exception{
		if (mapMenuBase.containsKey(producto.getNombre())) {
			throw new Exception("Producto repetido");
			}
	}
	
}
