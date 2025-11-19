package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import solucion.Solucion;

class SolucionTest {
	
	@Test
	void testEjemploEnunciado() throws IOException {
		
		Solucion sol = new Solucion();
		
		int gananciaObtenida= sol.ganancia("tests/test1.txt");
		
		assertEquals(3800, gananciaObtenida, "Debe elegir la opción con mayor utilidad (3800).");
        
        
	}
	
	@Test
	void testCasoBorde() throws IOException {
		
		Solucion sol = new Solucion();
		
		int gananciaObtenida= sol.ganancia("tests/test2.txt");
		
		assertEquals(5, gananciaObtenida, "Debe elegir la opción con mayor utilidad (5).");
        
        
	}
	
	@Test
	void testResignaNegocio() throws IOException {
		
		Solucion sol = new Solucion();
		
		int gananciaObtenida= sol.ganancia("tests/test3.txt");
		
		assertEquals(-1, gananciaObtenida, "Debe resignar el negocio.");
        
        
	}
	
	@Test
	void testGananciaCero() throws IOException {
		
		Solucion sol = new Solucion();
		
		int gananciaObtenida= sol.ganancia("tests/test4.txt");
		
		assertEquals(0, gananciaObtenida, "Debe devolver ganancia 0.");
        
        
	}
	
	@Test
	void testMaximaGanancia() throws IOException {
		
		Solucion sol = new Solucion();
		
		int gananciaObtenida= sol.ganancia("tests/test5.txt");
		
		assertEquals(999900000, gananciaObtenida, "Debe devolver la maxima escala de ganancia.");
        
        
	}
	
	@Test
	void testMinimoPresupuesto() throws IOException {
		
		Solucion sol = new Solucion();
		
		int gananciaObtenida= sol.ganancia("tests/test6.txt");
		
		assertEquals(4, gananciaObtenida, "Debe devolver ganancia 4.");
        
        
	}
	
	@Test
	void testMejorCompradorDescartado() throws IOException {
		
		Solucion sol = new Solucion();
		
		int gananciaObtenida= sol.ganancia("tests/test7.txt");
		
		assertEquals(180, gananciaObtenida, "Debe devolver ganancia 180, seleccionando el segundo comprador ya que el primero excede la cantidad de unidades que podemos comprar acorde a nuestro presupuesto y las opciones de fabricantes.");
        
        
	}

}
