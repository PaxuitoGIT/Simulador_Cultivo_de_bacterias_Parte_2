package CultivoBacterias.Lógica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SimulacionMontecarlo extends JFrame {
    private JButton[][] botones;
    private final int FILAS = 20;
    private final int COLUMNAS = 20;
    private final int FILAS_CENTRO = FILAS / 2;
    private final int COLUMNAS_CENTRO = COLUMNAS / 2;
    private final int TAMANIO_AREA_RESALTADA = 4;

    private int[][] cantidadBacterias;
    private int[][] cantidadComida;
    private Random random = new Random();

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
        // Recorremos todas las celdas
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                // Comprobamos si hay suficiente comida para que una bacteria pueda comer 20 microgramos
                if (cantidadComida[i][j] >= 20) {
                    // La bacteria come 20 microgramos de comida
                    if (cantidadComida[i][j] >= 20) {
                        cantidadComida[i][j] -= 20;
                    } else {
                        cantidadComida[i][j] = 0;
                    }

                    // Generamos un número aleatorio entre 0 y 99
                    int aleatorio = (int) (Math.random() * 100);

                    // Si el número es menor que 3, la bacteria muere
                    if (aleatorio < 3) {
                        cantidadBacterias[i][j] = 0;
                    }
                    // Si el número está entre 3 y 59, la bacteria se queda en la misma celda
                    else if (aleatorio < 60) {
                        // No hacemos nada, la bacteria se queda en la misma celda
                    }
                    // Si el número está entre 60 y 99, la bacteria se mueve a una celda contigua (si es posible)
                    else {
                        // Buscamos una celda contigua válida
                        boolean movido = false;
                        int[] nuevaPosicion = buscarCeldaContigua(i, j);
                        if (nuevaPosicion != null) {
                            // Movemos la bacteria a la nueva celda
                            cantidadBacterias[nuevaPosicion[0]][nuevaPosicion[1]]++;
                            cantidadBacterias[i][j]--;
                            movido = true;
                        }

                        // Si no se pudo mover, la bacteria se queda en la misma celda
                        if (!movido) {
                            // No hacemos nada, la bacteria se queda en la misma celda
                        }
                    }

                    // Verificamos si la cantidad de bacterias es negativa
                    if (cantidadBacterias[i][j] < 0) {
                        cantidadBacterias[i][j] = 0;
                    }
                }
                // Si no hay suficiente comida para que la bacteria coma 20 microgramos, comprobamos si hay suficiente para comer 10 microgramos
                else if (cantidadComida[i][j] > 9) {
                    // La bacteria come 10 microgramos de comida
                    if (cantidadComida[i][j] >= 10) {
                        cantidadComida[i][j] -= 10;
                    } else {
                        cantidadComida[i][j] = 0;
                    }

                    // Generamos un número aleatorio entre 0 y 99
                    int aleatorio = (int) (Math.random() * 100);

                    // Si el número es menor que 6, la bacteria muere
                    if (aleatorio < 6) {
                        cantidadBacterias[i][j] = 0;
                    }
                    // Si el número está entre 6 y 19, la bacteria se queda en la misma celda
                    else if (aleatorio < 20) {
                        // No hacemos nada, la bacteria se queda en la misma celda
                    }
                    // Si el número está entre 20 y 99, la bacteria se mueve a una celda contigua (si es posible)
                    else {
                        // Buscamos una celda contigua válida
                        boolean movido = false;
                        int[] nuevaPosicion = buscarCeldaContigua(i, j);
                        if (nuevaPosicion != null) {
                            // Movemos la bacteria a la nueva celda
                            cantidadBacterias[nuevaPosicion[0]][nuevaPosicion[1]]++;
                            cantidadBacterias[i][j]--;
                            movido = true;
                        }

                        // Si no se pudo mover, la bacteria se queda en la misma celda
                        if (!movido) {
                            // No hacemos nada, la bacteria se queda en la misma celda
                        }
                    }

                    // Verificamos si la cantidad de bacterias es negativa
                    if (cantidadBacterias[i][j] < 0) {
                        cantidadBacterias[i][j] = 0;
                    }
                }
                // Si hay 9 microgramos o menos de comida, la bacteria muere
                else {
                    cantidadBacterias[i][j] = 0;
                }
            }
        }

        // Actualizamos la interfaz gráfica
        actualizarInterfaz();
    }

    // Método para buscar una celda contigua válida
    private int[] buscarCeldaContigua(int fila, int columna) {
        // Definimos los desplazamientos posibles en las filas y columnas
        int[] deltaFilas = {-1, 0, 1, 0};
        int[] deltaColumnas = {0, 1, 0, -1};

        // Iteramos sobre los posibles desplazamientos
        for (int k = 0; k < deltaFilas.length; k++) {
            int nuevaFila = fila + deltaFilas[k];
            int nuevaColumna = columna + deltaColumnas[k];

            // Verificamos si la nueva posición es válida
            if (nuevaFila >= 0 && nuevaFila < FILAS && nuevaColumna >= 0 && nuevaColumna < COLUMNAS) {
                // Si hay suficiente comida en la celda contigua, retornamos la nueva posición
                if (cantidadComida[nuevaFila][nuevaColumna] > 0) {
                    return new int[]{nuevaFila, nuevaColumna};
                }
            }
        }

        // Si no se encontró una celda contigua válida, retornamos null
        return null;
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

