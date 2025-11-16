package evaluadorlocal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import monticulo.Monticulo;

public class EvaluadorLocal {
	private Monticulo<Fabricante> CPfabricantes = new Monticulo<>(); // en primer lugar siempre la mejor opción
	private Monticulo<Comprador> CPcompradores = new Monticulo<>(); // en primer lugar siempre la mejor opción
	private int presupuesto = 0;
	private int utilidad = -1;
	private int limiteUnidadesSegunPresupuesto = 0;

	public EvaluadorLocal() {

	}

	public int ganancia(String path) throws IOException {
		procesarArchivo(path);

		Fabricante fab = (Fabricante) CPfabricantes.remover(), aux;
		Comprador comp = (Comprador) CPcompradores.verRaiz();
		int cantNecesaria = Math.max(comp.getCantidadSolicitada(), fab.getCantidadMinima()), utilidadAux;

		utilidad = comp.getCantidadSolicitada() * comp.getValorPorUnidad() - cantNecesaria * fab.getValorPorUnidad();

		while (!CPfabricantes.vacio()) {
			aux = (Fabricante) CPfabricantes.remover();
			cantNecesaria = Math.max(comp.getCantidadSolicitada(), aux.getCantidadMinima());
			utilidadAux = comp.getCantidadSolicitada() * comp.getValorPorUnidad()
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
					+ comp.getId() + ", entonces, la ganancia será -> "
					+ utilidad);
		}

		CPfabricantes.vaciarMonticulo();
		CPcompradores.vaciarMonticulo();

		return utilidad;
	}

	public void procesarArchivo(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		int precio, cant;

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
			insertarColaPrioridadCompradores(i + 1, precio, cant);
		}

		br.close();
	}

	private void insertarColaPrioridadFabricantes(int nroFabricante, int valUnidad, int cantMin) {
		if (cantMin * valUnidad <= presupuesto) { // filtramos acorde al presupuesto que disponemos
			CPfabricantes.agregar(new Fabricante(nroFabricante, valUnidad, cantMin));
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

	private void insertarColaPrioridadCompradores(int nroComprador, int valUnidad, int cantSolicitada) {
		if (cantSolicitada <= limiteUnidadesSegunPresupuesto) {
			// System.out.println("nroComp: " + nroComprador + " valUnidad: " + valUnidad +
			// " cantSolicitada: " + cantSolicitada);
			CPcompradores.agregar(new Comprador(nroComprador, valUnidad, cantSolicitada));
		}
	}

	static class Fabricante implements Comparable<Fabricante> {
		private int id;
		private int cantidadMinima;
		private int valorPorUnidad;

		public Fabricante(int identificador, int valUnidad, int cantMin) {
			id = identificador;
			cantidadMinima = cantMin;
			valorPorUnidad = valUnidad;
		}

		public int getId() {
			return id;
		}

		public int getCantidadMinima() {
			return cantidadMinima;
		}

		public int getValorPorUnidad() {
			return valorPorUnidad;
		}

		@Override
		public int compareTo(Fabricante otro) { // ordenado por menor precio, menor cantidad mínima
			int difPrecio = valorPorUnidad - otro.valorPorUnidad;

			if (difPrecio != 0) {
				return difPrecio;
			}

			return cantidadMinima - otro.cantidadMinima;
		}

		@Override
		public String toString() {
			return "idFabricante: " + id + ", valorPorUnidad: " + valorPorUnidad + ", cantidadMinima: "
					+ cantidadMinima;
		}
	}

	static class Comprador implements Comparable<Comprador> {
		private int id;
		private int cantidadSolicitada;
		private int valorPorUnidad;

		public Comprador(int identificador, int valUnidad, int cantSolicitada) {
			id = identificador;
			cantidadSolicitada = cantSolicitada;
			valorPorUnidad = valUnidad;
		}

		public int getId() {
			return id;
		}

		public int getCantidadSolicitada() {
			return cantidadSolicitada;
		}

		public int getValorPorUnidad() {
			return valorPorUnidad;
		}

		@Override
		public int compareTo(Comprador otro) { // ordenado por mayor rendimiento
			return otro.cantidadSolicitada * otro.valorPorUnidad - cantidadSolicitada * valorPorUnidad;
		}

		@Override
		public String toString() {
			return "idComprador: " + id + ", valorPorUnidad: " + valorPorUnidad + ", cantSolicitada: "
					+ cantidadSolicitada;
		}
	}

}
