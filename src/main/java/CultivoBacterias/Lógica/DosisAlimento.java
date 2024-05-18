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
    private int tipoPatron; // 0: Incremento-Decremento, 1: Constante, 2: Incremento lineal, 3: Alternativo
    private int comidaInicial;
    private int diaConsumir;
    private int comidaFinal;

    // Constructor para patrón Incremento-Decremento
    public DosisAlimento(int tipoPatron, int comidaInicial, int diaConsumir, int comidaFinal) {
        this.tipoPatron = tipoPatron;
        this.comidaInicial = comidaInicial;
        this.diaConsumir = diaConsumir;
        this.comidaFinal = comidaFinal;
    }

    // Método para calcular la cantidad de comida disponible para las bacterias en un día dado
    public int calcularCantidadComida(int dia, Experimento experimentoActual) {
        switch (tipoPatron) {
            case 0: // Incremento-Decremento
                return calcularIncrementoDecremento(dia, experimentoActual);
            case 1: // Constante
                return comidaInicial;
            case 2: // Incremento Lineal
                return calcularIncrementoLineal(dia, experimentoActual);
            case 3: // Alternativo
                return calcularAlternativo(dia);
            default:
                return 0;
        }
    }

    // Método para calcular la cantidad de comida disponible para las bacterias en un día dado
    public int calcularIncrementoDecremento(int dia, Experimento experimentoActual) {
        int duracion = experimentoActual.getDuracion();
        // Si el día es menor o igual al día de incremento, la cantidad de comida es la comida inicial
        if (dia <= diaConsumir) {
            return comidaInicial + dia;
        }
        // Si el día es mayor al día de incremento, pero menor o igual a la final, se calcula la cantidad de comida
        else if (dia <= duracion) {
            double decrementoDiario = (double) (comidaInicial - comidaFinal) / (duracion - diaConsumir);
            int cantidadComida = (int) Math.round(comidaInicial - decrementoDiario * (dia - diaConsumir));
            return Math.max(Math.min(cantidadComida, 300000), 0);
        }
        // Si el día es mayor que el día final, la cantidad de comida es la comida final
        else {
            return comidaFinal;
        }
    }
    public int calcularIncrementoLineal(int dia, Experimento experimentoActual) {
        // Se calcula el incremento diario
        double incrementoDiario = (double) (comidaFinal - comidaInicial) / (experimentoActual.getDuracion() - 1);
        int cantidadComida = (int) Math.round(comidaInicial + incrementoDiario * (dia - 1));
        return Math.max(Math.min(cantidadComida, 300000), 0); // Se ajusta el límite superior a 300000
    }

    public int calcularAlternativo(int dia) {
        // Si el día es impar, se proporciona la comida inicial; si es par, no se proporciona comida
        if (dia % 2 == 1) {
            return comidaInicial;
        } else {
            return 0;
        }
    }
}