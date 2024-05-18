/*
 * PoblacionBacterias.java
 * Esta clase representa una población de bacterias en un experimento.
 * Contiene información sobre el nombre, fechas de inicio y fin del experimento,
 * número de bacterias, temperatura, luminosidad y dosis de alimento.
 */

package CultivoBacterias.Lógica;

import java.util.Date;
import java.io.Serializable;

public class PoblacionBacterias implements Serializable {
    // Atributos de la clase PoblacionBacterias
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private int numBacterias;
    private int temperatura;
    private String luminosidad;
    private DosisAlimento dosisAlimento;
    private String tipoPatron;

    // Constructor de la clase PoblacionBacterias
    public PoblacionBacterias(String nombre, Date fechaInicio, Date fechaFin, int numBacterias, int temperatura, String luminosidad, DosisAlimento dosisAlimento, String tipoPatron) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numBacterias = numBacterias;
        this.temperatura = temperatura;
        this.luminosidad = luminosidad;
        this.dosisAlimento = dosisAlimento;
        this.tipoPatron = tipoPatron;
    }

    // Getters y setters de la clase PoblacionBacterias
    public DosisAlimento getDosisAlimento() {
        return dosisAlimento;
    }

    public void setDosisAlimento(DosisAlimento dosisAlimento) {
        this.dosisAlimento = dosisAlimento;
    }

    public String getLuminosidad() {
        return luminosidad;
    }

    public void setLuminosidad(String luminosidad) {
        this.luminosidad = luminosidad;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getNumBacterias() {
        return numBacterias;
    }

    public void setNumBacterias(int numBacterias) {
        this.numBacterias = numBacterias;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoPatron() {
        return tipoPatron;
    }

    public void setTipoPatron(String tipoPatron) {
        this.tipoPatron = tipoPatron;
    }
}
