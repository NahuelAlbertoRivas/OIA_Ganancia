package evaluadorlocal;

public class Comprador implements Comparable<Comprador> {
	private int id;
	private int cantidadSolicitada;
	private int valorPorUnidad;

	public Comprador(int identificador, int valUnidad, int cantSolicitada) {
		id = identificador;
		cantidadSolicitada = cantSolicitada;
		valorPorUnidad = valUnidad;
	}
	
	public void setId(int nId) {
		id = nId;
	}
	
	public void setCantidadSolicitada(int cantSolicitada) {
		cantidadSolicitada = cantSolicitada;
	}
	
	public void setValorPorUnidad(int valUnidad) {
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
