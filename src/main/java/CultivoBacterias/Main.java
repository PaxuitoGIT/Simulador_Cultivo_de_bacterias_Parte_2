package CultivoBacterias;

import CultivoBacterias.GUI.UI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Ejecuta la interfaz de usuario
                new UI();
            }
        });
    }
}
