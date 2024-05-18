/*
 * ManejadorArchivos.java
 * Esta clase proporciona métodos para manejar archivos relacionados con los experimentos.
 * Permite guardar, cargar y seleccionar archivos de experimentos, así como agregar extensiones ".json" a los nombres de archivo si es necesario.
 */

package CultivoBacterias.Datos;

import CultivoBacterias.Lógica.Experimento;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ManejadorArchivos {
    // Hace referencia al directorio del proyecto
    public static final String DIRECTORIO_PROYECTO = System.getProperty("user.dir");

    // Guarda un experimento en un archivo JSON
    public static void guardarExperimento(Experimento experimento, String nombreArchivo) {
        // Ruta completa del archivo
        String rutaCompleta = DIRECTORIO_PROYECTO + File.separator + nombreArchivo;
        try (Writer writer = new FileWriter(rutaCompleta)) {
            // Crea un objeto Gson con formato legible
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            // Convierte el experimento a formato JSON y escribe en el archivo
            gson.toJson(experimento, writer);
            System.out.println("El experimento se ha guardado correctamente en " + rutaCompleta);
        } catch (IOException e) {
            // Maneja errores de entrada/salida
            System.err.println("Error al guardar el experimento: " + e.getMessage());
        }
    }

    // Carga un experimento desde un archivo JSON
    public static Experimento cargarExperimento(String nombreArchivo) {
        // Ruta completa del archivo
        String rutaCompleta = DIRECTORIO_PROYECTO + File.separator + nombreArchivo;
        Experimento experimento = null;
        try (Reader reader = new FileReader(rutaCompleta)) {
            // Crea un objeto Gson
            Gson gson = new Gson();
            // Lee el archivo y convierte el JSON a un objeto Experimento
            experimento = gson.fromJson(reader, Experimento.class);
            System.out.println("El experimento se ha cargado correctamente desde " + rutaCompleta);
        } catch (IOException e) {
            // Maneja errores de entrada/salida
            System.err.println("Error al cargar el experimento: " + e.getMessage());
        }
        return experimento;
    }

    // Selecciona un archivo mediante un diálogo de selección de archivo (Prefiero el FileDialog en lugar de JFileChooser por estética)
    public static String seleccionarArchivo(JFrame frame, String titulo, int modo) {
        // Crea un diálogo de selección de archivo
        FileDialog dialogoArchivo = new FileDialog(frame, titulo, modo);
        // Establece el directorio del proyecto como directorio inicial
        dialogoArchivo.setDirectory(DIRECTORIO_PROYECTO);
        // Muestra el diálogo y obtiene el nombre del archivo seleccionado
        dialogoArchivo.setVisible(true);
        // Obtiene el nombre del archivo seleccionado
        String nombreArchivo = dialogoArchivo.getFile();
        // Si se seleccionó un archivo, devuelve el nombre del archivo
        if (nombreArchivo != null) {
            return nombreArchivo;
        } else {
            return null;
        }
    }

    // Agrega la extensión ".json" a un nombre de archivo si no la tiene
    public static String agregarExtensionJSON(String nombreArchivo) {
        // Verifica si el nombre de archivo no es nulo y no termina con ".json"
        if (nombreArchivo != null && !nombreArchivo.endsWith(".json")) {
            return nombreArchivo + ".json";
        } else {
            // Devuelve el mismo nombre de archivo si ya tiene la extensión ".json"
            return nombreArchivo;
        }
    }
}
