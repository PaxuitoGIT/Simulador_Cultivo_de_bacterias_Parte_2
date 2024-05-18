/*
 * UI.java
 * Esta clase define la interfaz de usuario (UI) para el sistema de cultivo de bacterias.
 * Proporciona métodos para crear, abrir, guardar y mostrar detalles de experimentos, así como eliminar poblaciones de bacterias.
 */

package CultivoBacterias.GUI;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import CultivoBacterias.Datos.ManejadorArchivos;
import CultivoBacterias.Lógica.DosisAlimento;
import CultivoBacterias.Lógica.Experimento;
import CultivoBacterias.Lógica.PoblacionBacterias;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTextField;


public class UI {
    // Atributos de la clase UI
    JFrame frame, crearExperimentoFrame;
    JTextArea textArea;
    JButton verDetallesButton, agregarPoblacionButton, eliminarPoblacionButton;
    JMenuBar menuBar;
    JMenu experimentoMenu;
    JMenuItem crearExperimentoItem, abrirExperimentoItem, guardarItem, guardarComoItem;
    JPanel crearExperimentoPanel, mainPanel, buttonsPanel;
    JLabel nombreLabel, fechaInicioLabel, fechaFinLabel, numBacteriasLabel, temperaturaLabel, luminosidadLabel, dosisInicialLabel, diaConsumirLabel, comidaFinalLabel, patronConsumirLabel;
    JXTextField nombreField, numBacteriasField, temperaturaField, dosisInicialField, diaConsumirField, comidaFinalField;
    JXDatePicker fechaInicioPicker, fechaFinPicker, fechaConsumirPicker;

    private Experimento experimentoActual;
    private String nombreArchivoActual;
    private String tipoPatronSeleccionado;
    private JComboBox<String> poblacionesComboBox; // Declarar JComboBox como variable de instancia


    // Constructor de la clase UI
    public UI() {
        //Crea la interfaz de usuario
        frame = new JFrame("Laboratorio de Biólogos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crea la barra de menú de arriba y le da el nombre de "Experimento"
        menuBar = new JMenuBar();
        experimentoMenu = new JMenu("Experimento");

        // Crea los items del menú
        crearExperimentoItem = new JMenuItem("Crear Nuevo Experimento");
        // Agrega un ActionListener al item para crear un nuevo experimento
        crearExperimentoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearNuevoExperimento();
            }
        });
        abrirExperimentoItem = new JMenuItem("Abrir Experimento");
        // Agrega un ActionListener al item para abrir un experimento
        abrirExperimentoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreArchivo = ManejadorArchivos.seleccionarArchivo(frame, "Abrir Experimento", FileDialog.LOAD);
                if (nombreArchivo != null) {
                    experimentoActual = ManejadorArchivos.cargarExperimento(nombreArchivo);
                    if (experimentoActual != null) {
                        PoblacionBacterias poblacion = experimentoActual.getPoblaciones().get(0);
                        tipoPatronSeleccionado = poblacion.getTipoPatron();
                        JOptionPane.showMessageDialog(frame, "Experimento cargado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error al cargar el experimento", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        guardarItem = new JMenuItem("Guardar");
        // Agrega un ActionListener al item para guardar un experimento
        guardarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nombreArchivoActual != null) {
                    ManejadorArchivos.guardarExperimento(experimentoActual, nombreArchivoActual);
                } else {
                    nombreArchivoActual = ManejadorArchivos.seleccionarArchivo(frame, "Guardar Experimento", FileDialog.SAVE);
                    if (nombreArchivoActual != null) {
                        nombreArchivoActual = ManejadorArchivos.agregarExtensionJSON(nombreArchivoActual);
                        ManejadorArchivos.guardarExperimento(experimentoActual, nombreArchivoActual);
                    }
                }
            }
        });
        guardarComoItem = new JMenuItem("Guardar Como");
        // Agrega un ActionListener al item para guardar un experimento con un nombre específico
        guardarComoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreArchivo = ManejadorArchivos.seleccionarArchivo(frame, "Guardar Experimento", FileDialog.SAVE);
                if (nombreArchivo != null) {
                    nombreArchivo = ManejadorArchivos.agregarExtensionJSON(nombreArchivo);
                    ManejadorArchivos.guardarExperimento(experimentoActual, nombreArchivo);
                }
            }
        });

        // Agrega los items al menú
        experimentoMenu.add(crearExperimentoItem);
        experimentoMenu.add(abrirExperimentoItem);
        experimentoMenu.add(guardarItem);
        experimentoMenu.add(guardarComoItem);
        menuBar.add(experimentoMenu);
        frame.setJMenuBar(menuBar);

        // Crea el panel principal y le da un layout de BorderLayout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Área de texto para mostrar detalles de la población
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        mainPanel.add(new JScrollPane(textArea));

        // Crea un botón para ver detalles de la población
        verDetallesButton = new JButton("Ver Detalles Población");
        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDetallesPoblacion();
            }
        });

        // Crea un botón para eliminar una población
        eliminarPoblacionButton = new JButton("Eliminar Población");
        eliminarPoblacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPoblacion();
            }
        });

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.add(verDetallesButton);
        buttonsPanel.add(eliminarPoblacionButton);

        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);

    }

    // Método para crear un nuevo experimento
    private void crearNuevoExperimento() {
        // Crea un nuevo frame para el experimento
        crearExperimentoFrame = new JFrame("Crear Nuevo Experimento");
        crearExperimentoPanel = new JPanel();
        crearExperimentoPanel.setLayout(new GridLayout(0, 2, 15, 15));

        // Crea los campos de texto y etiquetas para los datos de la población
        nombreLabel = new JLabel("Nombre:");
        nombreField = new JXTextField();

        // Crea un selector de fecha para la fecha de inicio con SwingX
        fechaInicioLabel = new JLabel("Fecha de Inicio:");
        fechaInicioPicker = new JXDatePicker();

        // Crea un selector de fecha para la fecha de fin con SwingX
        fechaFinLabel = new JLabel("Fecha de Fin:");
        fechaFinPicker = new JXDatePicker();

        // Crea un selector de fecha para la fecha de consumo con SwingX
        fechaConsumirPicker = new JXDatePicker();
        fechaConsumirPicker.setDate(fechaInicioPicker.getDate());

        // Agrega un ActionListener para validar la fecha seleccionada
        fechaConsumirPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date diaConsumir = fechaConsumirPicker.getDate();
                if (diaConsumir != null && (diaConsumir.before(fechaInicioPicker.getDate()) || diaConsumir.after(fechaFinPicker.getDate()))) {
                    JOptionPane.showMessageDialog(crearExperimentoFrame, "Por favor seleccione una fecha entre la fecha de inicio y la fecha de fin", "Error", JOptionPane.ERROR_MESSAGE);
                    fechaConsumirPicker.setDate(fechaInicioPicker.getDate()); // Restablece la fecha inicial si se selecciona una fecha fuera del rango
                }
            }
        });

        numBacteriasLabel = new JLabel("Número de Bacterias:");
        numBacteriasField = new JXTextField();

        temperaturaLabel = new JLabel("Temperatura:");
        temperaturaField = new JXTextField();

        // Crea un ComboBox para seleccionar la luminosidad
        luminosidadLabel = new JLabel("Luminosidad:");
        String[] luminosidades = {"Baja", "Media", "Alta"};
        JComboBox<String> luminosidadComboBox = new JComboBox<>(luminosidades);

        dosisInicialLabel = new JLabel("Dosis Inicial de Alimento:");
        dosisInicialField = new JXTextField();

        diaConsumirLabel = new JLabel("Día a partir del cual se consume la comida:");
        diaConsumirField = new JXTextField();

        patronConsumirLabel = new JLabel("Patrón de consumo de comida:");
        String[] patrones = {"Incremento-Decremento", "Incremento Lineal", "Alternativo", "Constante"};
        JComboBox<String> patronComboBox = new JComboBox<>(patrones);
        patronComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipoPatronSeleccionado = (String) patronComboBox.getSelectedItem();
            }
        });


        comidaFinalLabel = new JLabel("Comida Final:");
        comidaFinalField = new JXTextField();

        // Crea un botón para agregar la población al experimento
        agregarPoblacionButton = new JButton("Agregar Población");
        agregarPoblacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene los valores de los campos de texto
                String nombre = nombreField.getText();
                Date fechaInicio = fechaInicioPicker.getDate();
                Date fechaFin = fechaFinPicker.getDate();
                int numBacterias = Integer.parseInt(numBacteriasField.getText());
                int temperatura = Integer.parseInt(temperaturaField.getText());
                String luminosidad = (String) luminosidadComboBox.getSelectedItem();
                int dosisInicial = Integer.parseInt(dosisInicialField.getText());
                int diaConsumir = Integer.parseInt(diaConsumirField.getText());
                int comidaFinal = Integer.parseInt(comidaFinalField.getText());

                // Valida que los valores de la comida sean enteros menor a 300000
                if (dosisInicial < 0 || dosisInicial > 300000 || comidaFinal < 0 || comidaFinal > 300000) {
                    JOptionPane.showMessageDialog(crearExperimentoFrame, "Las cantidades de comida deben ser valores enteros entre 0 y 300", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                // Creación de la dosis de alimento y la población de bacterias
                DosisAlimento dosisAlimento = new DosisAlimento(0, dosisInicial, diaConsumir, comidaFinal);
                PoblacionBacterias poblacion = new PoblacionBacterias(nombre, fechaInicio, fechaFin, numBacterias, temperatura, luminosidad, dosisAlimento, tipoPatronSeleccionado);

                // Agrega la población al experimento actual
                if (experimentoActual == null) {
                    experimentoActual = new Experimento();
                }
                experimentoActual.agregarPoblacion(poblacion);

                JOptionPane.showMessageDialog(crearExperimentoFrame, "Población de bacterias agregada correctamente");

                limpiarCampos(crearExperimentoPanel);
            }
        });

        // Agrega los componentes al panel de creación de experimento
        crearExperimentoPanel.add(nombreLabel);
        crearExperimentoPanel.add(nombreField);
        crearExperimentoPanel.add(fechaInicioLabel);
        crearExperimentoPanel.add(fechaInicioPicker);
        crearExperimentoPanel.add(fechaFinLabel);
        crearExperimentoPanel.add(fechaFinPicker);
        crearExperimentoPanel.add(numBacteriasLabel);
        crearExperimentoPanel.add(numBacteriasField);
        crearExperimentoPanel.add(temperaturaLabel);
        crearExperimentoPanel.add(temperaturaField);
        crearExperimentoPanel.add(luminosidadLabel);
        crearExperimentoPanel.add(luminosidadComboBox);
        crearExperimentoPanel.add(dosisInicialLabel);
        crearExperimentoPanel.add(dosisInicialField);
        crearExperimentoPanel.add(diaConsumirLabel);
        crearExperimentoPanel.add(diaConsumirField);
        crearExperimentoPanel.add(patronConsumirLabel);
        crearExperimentoPanel.add(patronComboBox);
        crearExperimentoPanel.add(comidaFinalLabel);
        crearExperimentoPanel.add(comidaFinalField);
        crearExperimentoPanel.add(agregarPoblacionButton);

        // Añade un borde vacío al panel de creación de experimento
        crearExperimentoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Añade el panel de creación de experimento al frame y lo muestra
        crearExperimentoFrame.add(crearExperimentoPanel);
        crearExperimentoFrame.pack();
        crearExperimentoFrame.setLocationRelativeTo(null);
        crearExperimentoFrame.setVisible(true);
    }

    // Método para mostrar los detalles de una población
    private void mostrarDetallesPoblacion() {
        // Comprueba si hay poblaciones en el experimento actual y si las hay, muestra un diálogo para seleccionar una
        if (experimentoActual != null && experimentoActual.getPoblaciones().size() > 0) {
            // Crear modelo de lista para el JComboBox
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
            for (PoblacionBacterias poblacion : experimentoActual.getPoblaciones()) {
                comboBoxModel.addElement(poblacion.getNombre());
            }

            // Inicializar el JComboBox con el modelo de lista
            poblacionesComboBox = new JComboBox<>(comboBoxModel);

            // Crear botones de ordenamiento
            JButton ordenarPorFechaButton = new JButton("Ordenar por Fecha de Inicio");
            JButton ordenarPorNombreButton = new JButton("Ordenar por Nombre");
            JButton ordenarPorNumBacteriasButton = new JButton("Ordenar por Número de Bacterias");

            // Agregar ActionListener a los botones de ordenamiento
            ordenarPorFechaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ordenarPoblacionesPorFecha();
                }
            });

            ordenarPorNombreButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ordenarPoblacionesPorNombre();
                }
            });

            ordenarPorNumBacteriasButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ordenarPoblacionesPorNumBacterias();
                }
            });

            // Crear panel para organizar componentes
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(poblacionesComboBox);
            panel.add(ordenarPorFechaButton);
            panel.add(ordenarPorNombreButton);
            panel.add(ordenarPorNumBacteriasButton);

            // Mostrar ventana de diálogo con la lista de poblaciones y botones de ordenamiento
            int option = JOptionPane.showConfirmDialog(frame, panel, "Selección de una población", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String seleccion = (String) poblacionesComboBox.getSelectedItem();
                if (seleccion != null) {
                    for (PoblacionBacterias poblacion : experimentoActual.getPoblaciones()) {
                        if (poblacion.getNombre().equals(seleccion)) {
                            mostrarInformacionDetalladaPoblacion(poblacion);
                            break;
                        }
                    }
                }
            }
        } else {
            // Muestra un mensaje de error si no hay poblaciones en el experimento actual
            JOptionPane.showMessageDialog(frame, "No hay poblaciones de bacterias en el experimento", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar información detallada de una población
    private void mostrarInformacionDetalladaPoblacion(PoblacionBacterias poblacion) {
        // Crea un StringBuilder para construir el mensaje con los detalles de la población
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Detalles de la población de bacterias:\n\n");
        mensaje.append("Nombre: ").append(poblacion.getNombre()).append("\n");
        mensaje.append("Fecha de Inicio: ").append(poblacion.getFechaInicio()).append("\n");
        mensaje.append("Fecha de Fin: ").append(poblacion.getFechaFin()).append("\n");
        mensaje.append("Número de bacterias: ").append(poblacion.getNumBacterias()).append("\n");
        mensaje.append("Temperatura: ").append(poblacion.getTemperatura()).append("\n");
        mensaje.append("Luminosidad: ").append(poblacion.getLuminosidad()).append("\n");
        DosisAlimento dosisAlimento = poblacion.getDosisAlimento();
        mensaje.append("Patrón de consumo de comida: ").append(tipoPatronSeleccionado).append("\n");
        mensaje.append("Dosis de alimento por día:\n");
        // Bucle para calcular la cantidad de comida para cada día del experimento
        for (int dia = 1; dia <= experimentoActual.getDuracion(); dia++) {
            mensaje.append("  Día ").append(dia).append(": ").append(calcularCantidadComidaSegunPatron(dia, dosisAlimento, experimentoActual)).append("g\n");
        }
        textArea.setText(mensaje.toString());
    }

    // Método para limpiar los campos de texto en el panel de creación de experimento
    private void limpiarCampos(JPanel crearExperimentoPanel) {
        for (Component c : crearExperimentoPanel.getComponents()) {
            if (c instanceof JXTextField) {
                ((JXTextField) c).setText("");
            }
        }
    }

    // Método para eliminar una población de bacterias, muy similar al método mostrarDetallesPoblacion
    private void eliminarPoblacion() {
        if (experimentoActual != null && experimentoActual.getPoblaciones().size() > 0) {
            String[] nombresPoblaciones = new String[experimentoActual.getPoblaciones().size()];
            for (int i = 0; i < experimentoActual.getPoblaciones().size(); i++) {
                nombresPoblaciones[i] = experimentoActual.getPoblaciones().get(i).getNombre();
            }

            String seleccion = (String) JOptionPane.showInputDialog(frame, "Selección de una población", "Seleccione una población", JOptionPane.QUESTION_MESSAGE, null, nombresPoblaciones, nombresPoblaciones[0]);

            if (seleccion != null) {
                for (PoblacionBacterias poblacion : experimentoActual.getPoblaciones()) {
                    if (poblacion.getNombre().equals(seleccion)) {
                        experimentoActual.eliminarPoblacion(poblacion);
                        JOptionPane.showMessageDialog(frame, "Población de bacterias eliminada correctamente");
                        break;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No hay poblaciones de bacterias en el experimento", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para calcular la cantidad de comida según el patrón seleccionado
    private int calcularCantidadComidaSegunPatron(int dia, DosisAlimento dosisAlimento, Experimento experimentoActual) {
        switch (tipoPatronSeleccionado) {
            case "Incremento-Decremento":
                return dosisAlimento.calcularIncrementoDecremento(dia, experimentoActual);
            case "Incremento Lineal":
                return dosisAlimento.calcularIncrementoLineal(dia, experimentoActual);
            case "Alternativo":
                return dosisAlimento.calcularAlternativo(dia, experimentoActual.getDuracion());
            case "Constante":
                return dosisAlimento.calcularConstante(dia);
            default:
                return 0; // Retornar 0 en caso de que el patrón seleccionado no sea válido
        }
    }

    // Método para ordenar las poblaciones por fecha de inicio
    private void ordenarPoblacionesPorFecha() {
        // Ordenar la lista de poblaciones por fecha de inicio
        Collections.sort(experimentoActual.getPoblaciones(), new Comparator<PoblacionBacterias>() {
            @Override
            public int compare(PoblacionBacterias p1, PoblacionBacterias p2) {
                return p1.getFechaInicio().compareTo(p2.getFechaInicio());
            }
        });

        // Actualizar el modelo de lista del JComboBox
        DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) poblacionesComboBox.getModel();
        comboBoxModel.removeAllElements();
        for (PoblacionBacterias poblacion : experimentoActual.getPoblaciones()) {
            comboBoxModel.addElement(poblacion.getNombre());
        }
    }

    // Método para ordenar las poblaciones por nombre
    private void ordenarPoblacionesPorNombre() {
        // Ordenar la lista de poblaciones por nombre
        Collections.sort(experimentoActual.getPoblaciones(), new Comparator<PoblacionBacterias>() {
            @Override
            public int compare(PoblacionBacterias p1, PoblacionBacterias p2) {
                return p1.getNombre().compareTo(p2.getNombre());
            }
        });

        // Actualizar el modelo de lista del JComboBox
        DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) poblacionesComboBox.getModel();
        comboBoxModel.removeAllElements();
        for (PoblacionBacterias poblacion : experimentoActual.getPoblaciones()) {
            comboBoxModel.addElement(poblacion.getNombre());
        }
    }

    // Método para ordenar las poblaciones por número de bacterias
    private void ordenarPoblacionesPorNumBacterias() {
        // Ordenar la lista de poblaciones por número de bacterias
        Collections.sort(experimentoActual.getPoblaciones(), new Comparator<PoblacionBacterias>() {
            @Override
            public int compare(PoblacionBacterias p1, PoblacionBacterias p2) {
                return Integer.compare(p1.getNumBacterias(), p2.getNumBacterias());
            }
        });

        // Actualizar el modelo de lista del JComboBox
        DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) poblacionesComboBox.getModel();
        comboBoxModel.removeAllElements();
        for (PoblacionBacterias poblacion : experimentoActual.getPoblaciones()) {
            comboBoxModel.addElement(poblacion.getNombre());
        }
    }
}
