
package Vista;

import Controlador.ArkanoidController;
import Modelo.ArkanoidModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.io.IOException;

public class Vista {
    private JFrame frame;
    public Opciones1 opciones;
    public Principal principal;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    public ArkanoidView gameView;
    public Niveles niveles;


    public Vista(ArkanoidModel v) throws IOException {
        frame = new JFrame("ARKANOID");
        opciones = new Opciones1(v);
        opciones.setVisible(true);
        principal = new Principal(v);
        gameView = new ArkanoidView(v);
        gameView.setVisible(true);
        niveles = new Niveles(v);
        niveles.setVisible(true);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(principal, "Principal");
        mainPanel.add(opciones, "Opciones");
        mainPanel.add(gameView, "Juego");
        mainPanel.add(niveles, "Niveles");

        frame.getContentPane().add(mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(460, 750);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void principal2opciones() {
        cardLayout.show(mainPanel, "Opciones");
    }
    
    public void niveles2principal() {
        cardLayout.show(mainPanel, "Principal");
    }
    
    public void principal2niveles() {
        cardLayout.show(mainPanel, "Niveles");
    }

    public void opciones2principal() {
        cardLayout.show(mainPanel, "Principal");
    }
    
    public void principal2juego() {
        cardLayout.show(mainPanel, "Juego");
        
    }

    public void show() {
        frame.setVisible(true);
    }
}