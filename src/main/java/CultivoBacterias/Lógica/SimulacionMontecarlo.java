package CultivoBacterias.Lógica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulacionMontecarlo extends JFrame {
    private JButton[][] botones;
    private final int FILAS = 20;
    private final int COLUMNAS = 20;
    private final int FILAS_CENTRO = FILAS / 2;
    private final int COLUMNAS_CENTRO = COLUMNAS / 2;
    private final int TAMANIO_AREA_RESALTADA = 4;

    private int cantidadBacteriasInicial = 0;
    private int cantidadComidaInicial = 0;
    private JFrame detallesFrame;

    public SimulacionMontecarlo() {
        super("Simulación Montecarlo");

        // Crear la matriz de botones
        botones = new JButton[FILAS][COLUMNAS];
        JPanel panel = new JPanel(new GridLayout(FILAS + 1, COLUMNAS + 1));

        // Crear y agregar los botones de las etiquetas en el borde superior
        panel.add(new JLabel());
        for (int i = 0; i < COLUMNAS; i++) {
            panel.add(new JLabel(String.valueOf(i + 1), SwingConstants.CENTER));
        }

        // Inicializar y agregar los botones al panel
        for (int i = 0; i < FILAS; i++) {
            // Agregar la etiqueta en el borde izquierdo
            panel.add(new JLabel(String.valueOf(i + 1), SwingConstants.CENTER));

            for (int j = 0; j < COLUMNAS; j++) {
                botones[i][j] = new JButton();
                // Asociar ActionListener a cada botón
                int fila = i;
                int columna = j;
                botones[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mostrarDetallesPoblacion(fila, columna);
                    }
                });
                panel.add(botones[i][j]);
            }
        }

        // Resaltar el área 4x4 comenzando desde el centro
        resaltarAreaCentro();

        // Agregar el panel a la ventana
        add(panel, BorderLayout.CENTER);

        // Crear el botón de simulación de Montecarlo
        JButton simMonteCarloButton = new JButton("Simular Montecarlo");
        simMonteCarloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simularMontecarlo();
            }
        });

        // Agregar el botón a la ventana
        add(simMonteCarloButton, BorderLayout.SOUTH);

        // Crear el botón de ver detalles de población
        JButton verDetallesButton = new JButton("Dar detalles de población");
        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darDatosPoblacion();
            }
        });

        // Agregar el botón a la ventana
        add(verDetallesButton, BorderLayout.NORTH);

        // Configurar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void resaltarAreaCentro() {
        int filaInicio = FILAS_CENTRO - TAMANIO_AREA_RESALTADA / 2;
        int columnaInicio = COLUMNAS_CENTRO - TAMANIO_AREA_RESALTADA / 2;

        for (int i = filaInicio; i < filaInicio + TAMANIO_AREA_RESALTADA; i++) {
            for (int j = columnaInicio; j < columnaInicio + TAMANIO_AREA_RESALTADA; j++) {
                botones[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                // Distribuir equitativamente las bacterias y la comida dentro del área resaltada
                botones[i][j].setText("B: " + (cantidadBacteriasInicial / (TAMANIO_AREA_RESALTADA * TAMANIO_AREA_RESALTADA)));
                botones[i][j].setToolTipText("Comida: " + (cantidadComidaInicial / (TAMANIO_AREA_RESALTADA * TAMANIO_AREA_RESALTADA)));
            }
        }
    }

    private void simularMontecarlo() {
        // Implementar la simulación de Montecarlo aquí
        // Usar cantidadBacteriasInicial y cantidadComidaInicial para inicializar la simulación
    }

    private void darDatosPoblacion() {
        // Mostrar un diálogo para ingresar la cantidad de bacterias y comida
        String inputBacterias = JOptionPane.showInputDialog("Ingrese la cantidad de bacterias:");
        if (inputBacterias != null && !inputBacterias.isEmpty()) {
            cantidadBacteriasInicial = Integer.parseInt(inputBacterias);
        }

        String inputComida = JOptionPane.showInputDialog("Ingrese la cantidad de comida:");
        if (inputComida != null && !inputComida.isEmpty()) {
            cantidadComidaInicial = Integer.parseInt(inputComida);
        }
    }

    private void mostrarDetallesPoblacion(int fila, int columna) {
        // Cerrar la ventana anterior si existe
        if (detallesFrame != null) {
            detallesFrame.dispose();
        }

        // Crear un diálogo para mostrar los detalles de la población en la celda seleccionada
        detallesFrame = new JFrame("Detalles de la población en la celda (" + (fila + 1) + ", " + (columna + 1) + ")");
        detallesFrame.setLayout(new BorderLayout());
        detallesFrame.setSize(200, 150);
        detallesFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        panel.add(new JLabel("Bacterias: " + botones[fila][columna].getText()));
        panel.add(new JLabel("Comida: " + botones[fila][columna].getToolTipText()));

        detallesFrame.add(panel, BorderLayout.CENTER);
        detallesFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimulacionMontecarlo::new);
    }
}
