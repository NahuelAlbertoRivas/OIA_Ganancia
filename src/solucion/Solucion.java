package solucion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import monticulo.Monticulo;

public class Solucion {
	private Monticulo<Fabricante> montFabricantes = new Monticulo<>();
	private Comprador mejorOpcionComprador = new Comprador(0, 0, 0);
	private int presupuesto = 0;
	private int ganancia = -1;
	private int limiteUnidadesSegunPresupuesto = 0;

	public Solucion() {

	}

	public void evaluadorLocal() {
		int mejorGanancia = 0, precio, cant;
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
			System.out.print("Fabricante " + (i + 1) + ": ");
			precio = sc.nextInt();
			cant = sc.nextInt();
			insertarMonticuloFabricantes(i + 1, precio, cant);
		}

		// 3. Pedir los datos de los COMPRADORES usando un bucle

		System.out.print("Comprador 1: ");
		precio = sc.nextInt();
		cant = sc.nextInt();
		mejorOpcionComprador.setId(1);
		mejorOpcionComprador.setCantidadSolicitada(cant);
		mejorOpcionComprador.setValorPorUnidad(precio);
		
		System.out.println("--- Ingrese las " + C + " condiciones de los compradores (Precio Cantidad) ---");
		for (int i = 1; i < C; i++) {
			System.out.print("Comprador " + (i + 1) + ": ");
			precio = sc.nextInt();
			cant = sc.nextInt();
			if ((cant * precio) > mejorGanancia && cant <= limiteUnidadesSegunPresupuesto && cant < mejorOpcionComprador.getCantidadSolicitada()) {
				mejorOpcionComprador.setId(i+1);
				mejorOpcionComprador.setCantidadSolicitada(cant);
				mejorOpcionComprador.setValorPorUnidad(precio);
				mejorGanancia = cant * precio;
			}
		}

		sc.close();
	}

	public int ganancia(String path) throws IOException {

		procesarArchivo(path);

		return solucion();

	}

	public int ganancia() throws IOException {

		evaluadorLocal();

		return solucion();

	}

	public int solucion() {

		Fabricante fab = (Fabricante) montFabricantes.remover(), aux;
		if (fab == null && mejorOpcionComprador.getId() == 0) {
			System.out.println("Es recomendable resignar el negocio de esta jornada");
			return -1;
		}
		int cantNecesaria = Math.max(mejorOpcionComprador.getCantidadSolicitada(), fab.getCantidadMinima()),
				gananciaAux;

		ganancia = mejorOpcionComprador.getCantidadSolicitada() * mejorOpcionComprador.getValorPorUnidad()
				- cantNecesaria * fab.getValorPorUnidad();

		while (!montFabricantes.vacio()) {
			aux = (Fabricante) montFabricantes.remover();
			cantNecesaria = Math.max(mejorOpcionComprador.getCantidadSolicitada(), aux.getCantidadMinima());
			gananciaAux = mejorOpcionComprador.getCantidadSolicitada() * mejorOpcionComprador.getValorPorUnidad()
					- cantNecesaria * aux.getValorPorUnidad();
			if (gananciaAux > ganancia) {
				fab = aux;
				ganancia = gananciaAux;
			}
		}

		if (ganancia < 0) {
			ganancia = -1;
			System.out.println("Es recomendable resignar el negocio de esta jornada");
		} else {
			System.out.println("Seleccionando el fabricante (id) " + fab.getId() + " y el comprador (id) "
					+ mejorOpcionComprador.getId() + ", entonces, la ganancia será -> " + ganancia);
		}

		montFabricantes.vaciarMonticulo();

		return ganancia;

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
			insertarMonticuloFabricantes(i + 1, precio, cant);
		}

		br.readLine();
		
		String[] parts = br.readLine().trim().split(" ");
		precio = Integer.parseInt(parts[0]);
		cant = Integer.parseInt(parts[1]);
		mejorOpcionComprador.setId(1);
		mejorOpcionComprador.setCantidadSolicitada(cant);
		mejorOpcionComprador.setValorPorUnidad(precio);

		for (int i = 1; i < C; i++) {
			String[] partes = br.readLine().trim().split(" ");
			precio = Integer.parseInt(partes[0]);
			cant = Integer.parseInt(partes[1]);
			if ((cant * precio) >= mejorGanancia && cant <= limiteUnidadesSegunPresupuesto && cant < mejorOpcionComprador.getCantidadSolicitada()) {
				mejorOpcionComprador.setId(i+1);
				mejorOpcionComprador.setCantidadSolicitada(cant);
				mejorOpcionComprador.setValorPorUnidad(precio);
				mejorGanancia = cant * precio;
			}
		}

		br.close();
	}

	private void insertarMonticuloFabricantes(int nroFabricante, int valUnidad, int cantMin) {
		if (cantMin * valUnidad <= presupuesto) {
			montFabricantes.agregar(new Fabricante(nroFabricante, valUnidad, cantMin));
			actualizarLimiteUnidadesSegunPresupuesto(valUnidad);
		}
	}

	private void actualizarLimiteUnidadesSegunPresupuesto(int valorPorUnidad) {
		int cantMaxUnidadesSegunPresupuesto = presupuesto / valorPorUnidad;
		if (cantMaxUnidadesSegunPresupuesto > limiteUnidadesSegunPresupuesto) {
			limiteUnidadesSegunPresupuesto = cantMaxUnidadesSegunPresupuesto;
		}
	}
	
}
