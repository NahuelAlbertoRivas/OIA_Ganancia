package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import solucion.Solucion;

class SolucionTest {

	@Test
	void testEjemploEnunciado() throws IOException {
		
		Solucion sol = new Solucion();
		
		int gananciaObtenida= sol.ganancia("tests/ganancia.txt");
		
		assertEquals(5, gananciaObtenida, "Debe elegir la opción con mayor utilidad (5).");
        
        
	}

}
