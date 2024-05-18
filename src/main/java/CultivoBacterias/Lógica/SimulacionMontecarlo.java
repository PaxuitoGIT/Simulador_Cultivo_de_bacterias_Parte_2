package CultivoBacterias.Lógica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulacionMontecarlo extends JFrame {
    private JButton[][] botones;
    private final int FILAS = 20;
    private final int COLUMNAS = 20;

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
                panel.add(botones[i][j]);
            }
        }

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

        // Configurar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void simularMontecarlo() {

    }
}
