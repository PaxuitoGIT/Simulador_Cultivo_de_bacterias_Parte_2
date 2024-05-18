/*
 * Experimento.java
 * Esta clase representa un experimento que contiene poblaciones de bacterias.
 * Se encarga de gestionar las poblaciones asociadas al experimento.
 */

package CultivoBacterias.Lógica;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Experimento implements Serializable {
    // Lista de poblaciones de bacterias asociadas al experimento
    private List<PoblacionBacterias> poblaciones;

    // Constructor de la clase Experimento
    public Experimento() {
        this.poblaciones = new ArrayList<>();
    }

    // Método para agregar una población de bacterias al experimento
    public void agregarPoblacion(PoblacionBacterias poblacion) {
        poblaciones.add(poblacion);
    }

    // Método para eliminar una población de bacterias del experimento
    public void eliminarPoblacion(PoblacionBacterias poblacion) {
        poblaciones.remove(poblacion);
    }

    // Método para obtener una población de bacterias del experimento
    public List<PoblacionBacterias> getPoblaciones() {
        return poblaciones;
    }
}
