package excepciones;

import java.util.Map;

import modelo.Ingrediente;

public class IngredienteRepetidoException extends HamburguesaException{

	public void ingredienteRepetidoExcep(Ingrediente ingrediente, Map<String, Ingrediente> mapIngredientes) throws Exception{
		if (mapIngredientes.containsKey(ingrediente.getNombre())) {
			throw new Exception("Ingrediente repetido");
			}
	}
	
}
