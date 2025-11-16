package evaluadorlocal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import monticulo.Monticulo;

public class EvaluadorLocal {
	private Monticulo<Fabricante> MontFabricantes = new Monticulo<>(); // en primer lugar siempre la mejor opción
	private Comprador mejorOpcionComprador = new Comprador(0, 0, 0);
	private int presupuesto = 0;
	private int utilidad = -1;
	private int limiteUnidadesSegunPresupuesto = 0;

	public EvaluadorLocal() {

	}
	
	public void evaluadorLocal() {
		int mejorGanancia = 0;
		Scanner sc = new Scanner(System.in);

        // 1. Pedir los valores iniciales
        System.out.println("Ingrese Presupuesto, Nro Fabricantes y Nro Compradores (ej: 1000 4 4)");
        
        int P = sc.nextInt();
        int F = sc.nextInt();
        int C = sc.nextInt();
        
        presupuesto = P;

        // 2. Pedir los datos de los FABRICANTES usando un bucle
        System.out.println("--- Ingrese las " + F + " condiciones de los fabricantes (Precio CantidadMinima) ---");
        for (int i = 0; i < F; i++) {
            System.out.print("Fabricante " + (i + 1) + ": "); // Prompt para cada línea
            int precio = sc.nextInt();
            int cant = sc.nextInt();
            insertarColaPrioridadFabricantes(i + 1, precio, cant);
        }

        // 3. Pedir los datos de los COMPRADORES usando un bucle

        System.out.println("--- Ingrese las " + C + " condiciones de los compradores (Precio Cantidad) ---");
        for (int i = 0; i < C; i++) {
            System.out.print("Comprador " + (i + 1) + ": "); // Prompt para cada línea
            int precio = sc.nextInt();
            int cant = sc.nextInt();
            if((cant * precio) > mejorGanancia) {
            	mejorOpcionComprador.setId(i);
            	mejorOpcionComprador.setCantidadSolicitada(cant);
            	mejorOpcionComprador.setValorPorUnidad(precio);
            }
            //insertarColaPrioridadCompradores(i + 1, precio, cant);
        }
        
        sc.close();
	}

	public int ganancia(String path) throws IOException {
		procesarArchivo(path);
		//evaluadorLocal();

		Fabricante fab = (Fabricante) MontFabricantes.remover(), aux;
		int cantNecesaria = Math.max(mejorOpcionComprador.getCantidadSolicitada(), fab.getCantidadMinima()), utilidadAux;

		utilidad = mejorOpcionComprador.getCantidadSolicitada() * mejorOpcionComprador.getValorPorUnidad() - cantNecesaria * fab.getValorPorUnidad();

		while (!MontFabricantes.vacio()) {
			aux = (Fabricante) MontFabricantes.remover();
			cantNecesaria = Math.max(mejorOpcionComprador.getCantidadSolicitada(), aux.getCantidadMinima());
			utilidadAux = mejorOpcionComprador.getCantidadSolicitada() * mejorOpcionComprador.getValorPorUnidad()
					- cantNecesaria * aux.getValorPorUnidad();
			if (utilidadAux > utilidad) {
				fab = aux;
				utilidad = utilidadAux;
			}
		}

		if (utilidad < 0) {
			utilidad = -1;
			System.out.println("Es recomendable resignar el negocio de esta jornada");
		} else {
			System.out.println("Seleccionando el fabricante (id) " + fab.getId() + " y el comprador (id) " 
					+ mejorOpcionComprador.getId() + ", entonces, la ganancia será -> "
					+ utilidad);
		}

		MontFabricantes.vaciarMonticulo();

		return utilidad;
	}

	public void procesarArchivo(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		int precio, cant, mejorGanancia = 0;

		String[] primeraLinea = br.readLine().trim().split(" ");
		int P = Integer.parseInt(primeraLinea[0]);
		int F = Integer.parseInt(primeraLinea[1]);
		int C = Integer.parseInt(primeraLinea[2]);

		presupuesto = P;

		// Leer línea de comentario
		br.readLine();

		for (int i = 0; i < F; i++) {
			String[] partes = br.readLine().trim().split(" ");
			precio = Integer.parseInt(partes[0]);
			cant = Integer.parseInt(partes[1]);
			insertarColaPrioridadFabricantes(i + 1, precio, cant);
		}

		br.readLine();

		for (int i = 0; i < C; i++) {
			String[] partes = br.readLine().trim().split(" ");
			precio = Integer.parseInt(partes[0]);
			cant = Integer.parseInt(partes[1]);
			if((cant * precio) > mejorGanancia) {
            	mejorOpcionComprador.setId(i);
            	mejorOpcionComprador.setCantidadSolicitada(cant);
            	mejorOpcionComprador.setValorPorUnidad(precio);
            }
			//insertarColaPrioridadCompradores(i + 1, precio, cant);
		}

		br.close();
	}

	private void insertarColaPrioridadFabricantes(int nroFabricante, int valUnidad, int cantMin) {
		if (cantMin * valUnidad <= presupuesto) { // filtramos acorde al presupuesto que disponemos
			MontFabricantes.agregar(new Fabricante(nroFabricante, valUnidad, cantMin));
			// System.out.println("nroFab: " + nroFabricante + " valUnidad: " + valUnidad +
			// " cantMin: " + cantMin);
			actualizarLimiteUnidadesSegunPresupuesto(valUnidad);
		}
	}

	private void actualizarLimiteUnidadesSegunPresupuesto(int valorPorUnidad) {
		int cantMaxUnidadesSegunPresupuesto = presupuesto / valorPorUnidad;
		if (cantMaxUnidadesSegunPresupuesto > limiteUnidadesSegunPresupuesto) {
			limiteUnidadesSegunPresupuesto = cantMaxUnidadesSegunPresupuesto;
			// System.out.println("NUEVO LÍMITE DE UNIDADES -> " +
			// limiteUnidadesSegunPresupuesto);
		}
	}
	
	/*
	private void insertarColaPrioridadCompradores(int nroComprador, int valUnidad, int cantSolicitada) {
		if (cantSolicitada <= limiteUnidadesSegunPresupuesto) {
			// System.out.println("nroComp: " + nroComprador + " valUnidad: " + valUnidad +
			// " cantSolicitada: " + cantSolicitada);
			MontCompradores.agregar(new Comprador(nroComprador, valUnidad, cantSolicitada));
		}
	}
	*/

}
