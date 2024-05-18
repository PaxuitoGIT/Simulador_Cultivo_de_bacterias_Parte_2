/*
 * DosisAlimento.java
 * Esta clase representa la dosis de alimento para las bacterias en un experimento.
 * Contiene información sobre la cantidad inicial de comida, el día de inicio de consumo
 * y la cantidad final de comida.
 */

package CultivoBacterias.Lógica;

import java.io.Serializable;
public class DosisAlimento implements Serializable {
    // Atributos de la clase DosisAlimento
    private int comidaInicial;
    private int diaConsumir;
    private int comidaFinal;

    // Constructor de la clase DosisAlimento
    public DosisAlimento(int comidaInicial, int diaConsumir, int comidaFinal) {
        this.comidaInicial = comidaInicial;
        this.diaConsumir = diaConsumir;
        this.comidaFinal = comidaFinal;
    }

    // Método para calcular la cantidad de comida disponible para las bacterias en un día dado
    public int calcularCantidadComida(int dia, Experimento experimentoActual) {
        // Si el día es menor o igual al día de incremento, la cantidad de comida es la comida inicial
        if (dia <= diaConsumir) {
            return comidaInicial + dia;
        }
        // Si el día es mayor al día de incremento, pero menor o igual al día 30, se calcula la cantidad de comida
        else if (dia <= 30) {
            double decrementoDiario = (double) (comidaInicial - comidaFinal) / (30 - diaConsumir);
            int cantidadComida = (int) Math.round(comidaInicial - decrementoDiario * (dia - diaConsumir));
            return Math.max(Math.min(cantidadComida, 300), 0);
        }
        // Si el día es mayor que 30, la cantidad de comida es la comida final
        else {
            return comidaFinal;
        }
    }
}