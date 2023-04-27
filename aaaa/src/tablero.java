import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class tablero extends JFrame {
	
	public Object[][] ciudades;
    public JButton[] botones = new JButton[49]; 
    public tablero() {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        g_archivos2 gestor = new g_archivos2("ciudades.txt");
        ciudades=gestor.coordenadas;
        // Crear un JPanel secundario con BorderLayout para contener el panel de imagen y los paneles de botones
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        
        // Crear el panel de imagen y agregarlo al centro del JPanel secundario
        JPanel panelImagen = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image imagen = ImageIO.read(new File("tr2.jpg"));
                    g.drawImage(imagen, 0, 0, this);
 
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        };
        
        //añadir botones de ciudad
        for (int i = 0; i < ciudades.length; i++) {
			botones[i] = new JButton("<html><bodi><h5>"+i+"</h6></bodi></html>");
			botones[i].setBounds((int)ciudades[i][1], (int)ciudades[i][2], 25, 25);
			botones[i].setVisible(true);
			panelImagen.add(botones[i]); 
        }
        Image imagen3;
		String imagenx="";
		int x=0;
		try {
			imagen3 = ImageIO.read(new File(imagenx));
			botones[x].setIcon(new ImageIcon(imagen3));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        contentPane.add(panelImagen, BorderLayout.CENTER);
        setBounds(100, 100, 1555, 1050);
        setResizable(false);
      
        
        
        
        // Crear un panel para los botones inferior izquierdo y derecho, y agregarlos al sur del JPanel secundario
        JPanel panelBotones = new JPanel(new BorderLayout());
        contentPane.add(panelBotones, BorderLayout.SOUTH);
        panelBotones.setBackground(Color.GRAY);
        // Crear el primer botón grande y agregarlo al panelBotones a la izquierda
        JButton boton1 = new JButton("Curar Ciudad");
        boton1.setPreferredSize(new Dimension(250, 175));
        panelBotones.add(boton1, BorderLayout.WEST);
        Image imagen;
		try {
			imagen = ImageIO.read(new File("botton_verde.png"));
			boton1.setIcon(new ImageIcon(imagen));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Crear el segundo botón grande y agregarlo al panelBotones a la derecha
        JButton boton2 = new JButton("Dessarrollo de Cura");
        boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pan_vacunas frame = new pan_vacunas();
				frame.setVisible(true);
			}
		});
        boton2.setPreferredSize(new Dimension(250, 175));
        panelBotones.add(boton2, BorderLayout.EAST);
        Image imagen2;
		try {
			imagen2 = ImageIO.read(new File("botton_rojo.png"));
			boton2.setIcon(new ImageIcon(imagen2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        setLocationRelativeTo(null); // Centra el JFrame en la pantalla
        setVisible(true);
    }

    public static void main(String[] args) {
        new tablero();
    }
}
