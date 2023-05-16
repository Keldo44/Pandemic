import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class menu extends JFrame {

	public menu(Object[][] ranking) {
		g_archivos lector = new g_archivos();
		String[] reglas = lector.leer_archivo("reglas.txt");

		this.setExtendedState(MAXIMIZED_BOTH);
		this.setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Crea una barra de menú
		JMenuBar menuBar = new JMenuBar();

		// Crea un menú item llamado "Nueva partida"
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
				main.cargado1();
				main.cargado2();
				main.cargado3();
				main.is_charged = true;
				diff ventana = new diff(true);
				ventana.frame.setVisible(true);
				dispose();
			}
		});
		// Crea un elemento de menú llamado "Información"
		JMenuItem menuItemInformacion = new JMenuItem("Información");
		menuItemInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestra la información y reglas del juego
				JOptionPane.showMessageDialog(menu.this, reglas);

			}
		});
		menuBar.add(menuItemInformacion);
		
		
		String[] rk = new String[11];
		rk[0] = "nombre:    rondas:    fecha:";
		for (int i = 1; i < ranking.length + 1; i++) {
			rk[i] = ranking[i - 1][0] + " | " + ranking[i - 1][1] + " | " + ranking[i - 1][2];
		}

		// Crea un elemento de menú llamado "Resumen de puntuaciones"
		JMenuItem menuItemResumen = new JMenuItem("Ranking");
		menuItemResumen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestra el perfil y contacto de los autores

				JOptionPane.showMessageDialog(menu.this, rk);
			}
		});
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
				JOptionPane.showMessageDialog(menu.this, "Versión 2.0");
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
					Dimension dimension = this.getSize();
					// Carga la imagen desde la ruta especificada
					Image imagen = ImageIO.read(new File("Pandemia3.jpg"));
					// Dibuja la imagen en el panel
					g.drawImage(imagen, 0, 0, dimension.width, dimension.height, this);
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
