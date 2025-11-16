package monticulo;

import java.util.ArrayList;

public class Monticulo<T extends Comparable<T>> {
	private ArrayList<T> heap;

	public Monticulo() {
		heap = new ArrayList<>();
		heap.add(null); // Agrego algo en la pos 0 para empezar a utilizar desde la pos 1
	}

	public void agregar(T valor) {
		heap.add(valor);
		subir(heap.size() - 1);
	}

	public T remover() {
		if (heap.size() <= 1)
			return null;

		T min = heap.get(1);
		T ult = heap.remove(heap.size() - 1);

		if (heap.size() == 1)
			return min; // Si habia un solo elem, lo eliminé y quedó solo el que esta en la pos 0 (no se
						// usa)

		heap.set(1, ult);
		bajar(1);

		return min;
	}

	private void subir(int i) {
		while (i > 1 && heap.get(i).compareTo(heap.get(i / 2)) < 0) {
			intercambiar(i, i / 2);
			i = i / 2;
		}
	}

	private void bajar(int i) {
		int size = heap.size() - 1;

		while (size >= (2 * i)) {
			int hijoIzq = 2 * i;
			int hijoDer = (2 * i) + 1;
			int menor = hijoIzq;

			if (size >= hijoDer && heap.get(hijoDer).compareTo(heap.get(hijoIzq)) < 0) // Veo que exista el hijo derecho
				menor = hijoDer;

			if (heap.get(i).compareTo(heap.get(menor)) > 0) {
				intercambiar(i, menor);
				i = menor;
			} else {
				break;
			}
		}
	}

	private void intercambiar(int x, int y) {
		T aux = heap.get(x);
		heap.set(x, heap.get(y));
		heap.set(y, aux);
	}
	
	public boolean vacio() {
		return heap.size() == 1;
	}
	
	public Object verRaiz () {
		return heap.get(1);
	}
	
	public void vaciarMonticulo() {
		heap.clear();
	}
}
