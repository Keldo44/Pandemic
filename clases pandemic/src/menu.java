

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class menu extends JFrame {
	

    public menu() { 
    	
    	g_archivos lector = new g_archivos();
    	String[] reglas=lector.leer_archivo("reglas.txt");
    	this.setExtendedState(MAXIMIZED_BOTH);
        this.setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Crea una barra de menú
        JMenuBar menuBar = new JMenuBar();
        

        // Crea un menú llamado "Nueva partida"
        JMenuItem menuNuevaPartida = new JMenuItem("Nueva partida");
        menuBar.add(menuNuevaPartida);
        menuNuevaPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                diff ventana = new diff();
                ventana.frame.setVisible(true);
                dispose();
            } 
        });


        // Crea un elemento de menú llamado "Cargar partida"
        JMenuItem menuCargarPartida = new JMenuItem("Cargar Partida");
        menuBar.add(menuCargarPartida);
        menuCargarPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            } 
        });
        // Crea un elemento de menú llamado "Información"
        JMenuItem menuItemInformacion = new JMenuItem("Información");
        menuItemInformacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestra la información y reglas del juego
            	
                JOptionPane.showMessageDialog(menu.this,reglas);
            	
            }
        });
        menuBar.add(menuItemInformacion);

        // Crea un elemento de menú llamado "Resumen de puntuaciones"
        JMenuItem menuItemResumen = new JMenuItem("Ranking");
        menuBar.add(menuItemResumen);

        // Crea un elemento de menú llamado "Autores"
        JMenuItem menuItemAutores = new JMenuItem("Autores");
        menuItemAutores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestra el perfil y contacto de los autores
                JOptionPane.showMessageDialog(menu.this, "Ivan y Alejandro (zombies)");
            }
        });
        menuBar.add(menuItemAutores);

        // Crea un elemento de menú llamado "Versión"
        JMenuItem menuItemVersion = new JMenuItem("Versión");
        menuItemVersion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestra la versión actual de la entrega
                JOptionPane.showMessageDialog(menu.this, "Versión 1.0");
            }
        });
        menuBar.add(menuItemVersion);

        // Crea un elemento de menú llamado "Salir"
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Finaliza el programa
                System.exit(0);
            }
        });
        menuBar.add(menuItemSalir);

        // Establece la barra de menú en el marco
        setJMenuBar(menuBar);

        // Crea un panel para mostrar la imagen
        JPanel panelImagen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Carga la imagen desde la ruta especificada
                    Image imagen = ImageIO.read(new File("Pandemia3.jpg"));
                    // Dibuja la imagen en el panel
                    g.drawImage(imagen, 0, 0, this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        // Agregar el panel al marco
        panelImagen.setBounds(0, 0, getWidth(), getHeight()); // Ajusta el tamaño del panel para que cubra todo el marco
        add(panelImagen);

        // Configurar el tamaño y la visibilidad del marco
        setExtendedState(MAXIMIZED_BOTH); // Configura el tamaño del marco según tus necesidades
        setVisible(true); // Hace visible el marco
    }
}
