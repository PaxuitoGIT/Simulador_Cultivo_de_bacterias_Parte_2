package CultivoBacterias.Lógica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    private int[][] cantidadBacterias;
    private int[][] cantidadComida;

    public SimulacionMontecarlo() {
        super("Simulación Montecarlo");

        // Crear la matriz de botones
        botones = new JButton[FILAS][COLUMNAS];
        cantidadBacterias = new int[FILAS][COLUMNAS];
        cantidadComida = new int[FILAS][COLUMNAS];
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
                cantidadBacterias[i][j] = 0;
                cantidadComida[i][j] = 0;
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
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void resaltarAreaCentro() {
        int filaInicio = FILAS_CENTRO - TAMANIO_AREA_RESALTADA / 2;
        int columnaInicio = COLUMNAS_CENTRO - TAMANIO_AREA_RESALTADA / 2;

        for (int i = filaInicio; i < filaInicio + TAMANIO_AREA_RESALTADA; i++) {
            for (int j = columnaInicio; j < columnaInicio + TAMANIO_AREA_RESALTADA; j++) {
                botones[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
        }
    }

    private void simularMontecarlo() {
        // Implementar la simulación de Montecarlo aquí
        // Usar cantidadBacterias y cantidadComida para inicializar la simulación
    }

    private void darDatosPoblacion() {
        // Mostrar un diálogo para ingresar la cantidad de bacterias y comida
        String inputBacterias = JOptionPane.showInputDialog(this, "Ingrese la cantidad de bacterias:");
        if (inputBacterias != null && !inputBacterias.isEmpty()) {
            int totalBacterias = Integer.parseInt(inputBacterias);
            distribuirBacterias(totalBacterias);
        }

        String inputComida = JOptionPane.showInputDialog(this, "Ingrese la cantidad de comida:");
        if (inputComida != null && !inputComida.isEmpty()) {
            int totalComida = Integer.parseInt(inputComida);
            distribuirComida(totalComida);
        }

        // Actualizar la interfaz gráfica para reflejar los cambios en la distribución de bacterias y comida
        actualizarInterfaz();
    }

    private void distribuirBacterias(int totalBacterias) {
        // Distribuir equitativamente las bacterias dentro del área resaltada
        int bacteriasPorCelda = totalBacterias / (TAMANIO_AREA_RESALTADA * TAMANIO_AREA_RESALTADA);
        for (int i = FILAS_CENTRO - TAMANIO_AREA_RESALTADA / 2; i < FILAS_CENTRO + TAMANIO_AREA_RESALTADA / 2; i++) {
            for (int j = COLUMNAS_CENTRO - TAMANIO_AREA_RESALTADA / 2; j < COLUMNAS_CENTRO + TAMANIO_AREA_RESALTADA / 2; j++) {
                cantidadBacterias[i][j] = bacteriasPorCelda;
            }
        }
    }

    private void distribuirComida(int totalComida) {
        // Distribuir equitativamente la comida en todas las casillas
        int comidaPorCelda = totalComida / (FILAS * COLUMNAS);
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                cantidadComida[i][j] = comidaPorCelda;
            }
        }
    }

    private void actualizarInterfaz() {
        // Actualizar el texto de los botones para mostrar la cantidad de bacterias y comida en cada celda
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                botones[i][j].setText("B: " + cantidadBacterias[i][j]);
                botones[i][j].setToolTipText("Comida: " + cantidadComida[i][j]);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimulacionMontecarlo::new);
    }
}
