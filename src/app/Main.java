package app;

import java.io.IOException;

import evaluadorlocal.EvaluadorLocal;

public class Main {

	public static void main(String[] args) throws IOException {
		EvaluadorLocal el = new EvaluadorLocal();
		int Fab = 0, Comp = 0, utilidad;
		
		utilidad = el.ganancia(Fab, Comp);
		
		if(utilidad > 0) {
			System.out.println("Seleccionando el fabricante (id) " + Fab + " y el comprador (id) " 
					+ Comp + ", entonces, la ganancia será -> "
					+ utilidad);			
		}
		else {
			System.out.println("Es recomendable resignar el negocio de esta jornada");
		}

	}

}
