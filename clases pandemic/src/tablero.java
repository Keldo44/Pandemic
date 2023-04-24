import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class tablero extends JFrame {
    
    public tablero() {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear un JPanel secundario con BorderLayout para contener el panel de imagen y los paneles de botones
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        
        // Crear el panel de imagen y agregarlo al centro del JPanel secundario
        JPanel panelImagen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image imagen = ImageIO.read(new File("mapa.png"));
                    g.drawImage(imagen, 0, 0, this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        contentPane.add(panelImagen, BorderLayout.CENTER);
        this.setExtendedState(MAXIMIZED_BOTH);
        
        // Crear un panel para los botones inferior izquierdo y derecho, y agregarlos al sur del JPanel secundario
        JPanel panelBotones = new JPanel(new BorderLayout());
        contentPane.add(panelBotones, BorderLayout.SOUTH);
        
        // Crear el primer botón grande y agregarlo al panelBotones a la izquierda
        JButton boton1 = new JButton("Curar Ciudad");
        boton1.setPreferredSize(new Dimension(300, 200));
        panelBotones.add(boton1, BorderLayout.WEST);
        
        // Crear el segundo botón grande y agregarlo al panelBotones a la derecha
        JButton boton2 = new JButton("Dessarrollo de Cura");
        boton2.setPreferredSize(new Dimension(300, 200));
        panelBotones.add(boton2, BorderLayout.EAST);
        
        // Crear un panel para los cuatro botones pequeños y agregarlo al panelBotones en el centro
        JPanel panelBotonesPequeños = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 100));
        panelBotones.add(panelBotonesPequeños, BorderLayout.CENTER);
        
        // Crear los cuatro botones pequeños y agregarlos al panelBotonesPequeños
        
        
        JButton botonPequeño1 = new JButton("B1");
        botonPequeño1.setPreferredSize(new Dimension(50, 50));
        panelBotonesPequeños.add(botonPequeño1);
        botonPequeño1.setBackground(Color.BLUE);
        JButton botonPequeño2 = new JButton("B1");
        botonPequeño2.setPreferredSize(new Dimension(50, 50));
        panelBotonesPequeños.add(botonPequeño2);
        botonPequeño2.setBackground(Color.RED);
        JButton botonPequeño3 = new JButton("B1");
        botonPequeño3.setPreferredSize(new Dimension(50, 50));
        panelBotonesPequeños.add(botonPequeño3);
        botonPequeño3.setBackground(Color.BLACK);
        JButton botonPequeño4 = new JButton("B1");
        botonPequeño4.setPreferredSize(new Dimension(50, 50));
        panelBotonesPequeños.add(botonPequeño4);
        botonPequeño4.setBackground(Color.YELLOW);

        
        setLocationRelativeTo(null); // Centra el JFrame en la pantalla
        setVisible(true);
    }

    public static void main(String[] args) {
        new tablero();
    }
}
