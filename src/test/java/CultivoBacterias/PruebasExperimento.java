package CultivoBacterias;

import static org.junit.jupiter.api.Assertions.*;

import CultivoBacterias.Lógica.DosisAlimento;
import CultivoBacterias.Lógica.Experimento;
import CultivoBacterias.Lógica.PoblacionBacterias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class PruebasExperimento {

    private Experimento experimento;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada prueba
        experimento = new Experimento();
    }

    @Test
    void testCrearExperimento() {
        assertNotNull(experimento, "El experimento no debería ser nulo");
        assertTrue(experimento.getPoblaciones().isEmpty(), "El experimento debería estar vacío al inicio");
    }

    @Test
    void testAgregarPoblacion() {
        PoblacionBacterias poblacion = new PoblacionBacterias("Poblacion1", new Date(), new Date(), 100, 25, "Media", new DosisAlimento(50, 5, 20));
        experimento.agregarPoblacion(poblacion);
        assertFalse(experimento.getPoblaciones().isEmpty(), "El experimento no debería estar vacío después de agregar una población");
        assertTrue(experimento.getPoblaciones().contains(poblacion), "La población agregada debería estar en el experimento");
    }

    @Test
    void testEliminarPoblacion() {
        PoblacionBacterias poblacion = new PoblacionBacterias("Poblacion1", new Date(), new Date(), 100, 25, "Media", new DosisAlimento(50, 5, 20));
        experimento.agregarPoblacion(poblacion);
        assertTrue(experimento.getPoblaciones().contains(poblacion), "La población debería estar presente antes de eliminarla");
        experimento.eliminarPoblacion(poblacion);
        assertFalse(experimento.getPoblaciones().contains(poblacion), "La población no debería estar presente después de ser eliminada");
    }

}
