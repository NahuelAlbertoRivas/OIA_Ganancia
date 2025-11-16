package evaluadorlocal;

class Fabricante implements Comparable<Fabricante> {
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
