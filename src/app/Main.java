package app;

import java.io.IOException;

import evaluadorlocal.EvaluadorLocal;

public class Main {

	public static void main(String[] args) throws IOException {
		EvaluadorLocal el = new EvaluadorLocal();
		
		el.ganancia("ganancia.txt");
	}

}
