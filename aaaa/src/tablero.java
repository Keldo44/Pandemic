import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static javax.swing.Action.SHORT_DESCRIPTION;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;

public class tablero extends JFrame {
	public g_archivos2 gestor = new g_archivos2("ciudades.txt");
	public partida play;
	public static JButton[] botones = new JButton[49];
	public JLabel[] ciudades2 = new JLabel[49];
	public String imagenx;
	public JProgressBar progressBar[] = new JProgressBar[4];
	public boolean is_curando = false;
	public boolean is_mking_vacuna = false;
	public int turnos_for_inf = 2;
	public int n_inf_x_ronda = gestor.param[1];

	// funcioin que genera un numero aleatorio para decidir que ciudad se infectara
	// y lo envia a la funcioninfectar ciudad de la clase play.
	public void infectar_ciuades_rand() {
		for (int i = 0; i < n_inf_x_ronda; i++) {
			int rnum = (int) (Math.random() * 49);
			play.infectar_ciudad(rnum);
		}
		play.alrdy_brt.clear();
	}

	// Crear un panel para los paneles secundarios
	public static JPanel contentPane = new JPanel(new BorderLayout());

	// Crear un panel para los botones inferior izquierdo y derecho, y agregarlos al
	// sur del JPanel secundario
	public static JPanel panelBotones = new JPanel(new BorderLayout());
	public static JPanel barras = new JPanel(new FlowLayout());
	public static JPanel abort = new JPanel();

	// Crear un panel para el mapa y cargar el mapa respetando el formato y
	// dimensiones del mapa.
	public JPanel panelImagen = new JPanel(null) {
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
	
	public tablero() {
		// Inicializamos la clase partida para controlar la logica del juego
		play = new partida();
		// Si estamos jugando una partida cargada debemos cargar los datos.
		if (main.is_charged) {
			// Los datos de data[][]
			for (int i = 0; i < play.data.length; i++) {
				play.data[i][play.data[0].length-1] = main.cargado3[i][2];
				
				Image imagen3;
				try {
					imagen3 = ImageIO.read(new File(
						play.nombre_foto(Integer.parseInt(play.data[i][1] + ""), Integer.parseInt(play.data[i][play.data[0].length - 1]+""))));
					actualizarIconoBoton(i, new ImageIcon(imagen3));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (int i = 0; i < play.illnes.length; i++) {
				play.illnes[i][1] = Integer.parseInt(main.cargado2[i+1]+"");
			}
			
			play.Rondas = Integer.parseInt(main.cargado[0]+"");
			play.n_brotes =Integer.parseInt(main.cargado[2]+"");
			actualizarProgreso();
		}

		// funcion para el guardado de la partida, cuando cierres la ventana se activara
		// el guardado de partida en la base de datos.
		addWindowListener(new WindowAdapter() {
			public void window_closing(WindowEvent e) {
				// Cragar partida

			}
		});

		// se establecen todos los elementos y parametros del tablero(jPanel) y el resto
		// de sub paneles.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		contentPane.add(botones(panelImagen), BorderLayout.CENTER);
		setBounds(100, 100, 1555, 1050);
		setResizable(false);
		progres_barra();
		panelBotones.add(barras, BorderLayout.CENTER);
		barras.setSize(300, 500);
		barras.setVisible(true);
		contentPane.add(panelBotones, BorderLayout.SOUTH);
		panelBotones.setBackground(Color.GRAY);

		// boton curar ciudades, comprueba que no hayas apretado el boton dos veces o
		// que estes con el panel de dessarrollo de vacuna ,
		// despues avilita los botones de las ciudades para que puedas elegir cual
		// curar.
		JButton boton1 = new JButton("Curar Ciudad");
		boton1.setPreferredSize(new Dimension(250, 175));
		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!is_curando && !is_mking_vacuna) {
					C_ciudades(botones);
					is_curando = true;
					is_mking_vacuna = false;
				}

			}
		});
		panelBotones.add(boton1, BorderLayout.WEST);
		Image imagen;
		try {
			imagen = ImageIO.read(new File("botton_verde.png"));
			boton1.setIcon(new ImageIcon(imagen));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// boton de curar ciudades, llama a la funcion de dessarrollar vacuna de la
		// clase tablero,
		// comprueba que la vacuna elegida no este ya al 100% y actualiza el progreso en
		// el tablero.

		JButton boton2 = new JButton("Dessarrollo de Cura");
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!is_mking_vacuna && !is_curando) {
					is_mking_vacuna = true;
					JFrame frame = new JFrame();

					/**/

					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setAlwaysOnTop(true);
					frame.setUndecorated(true);
					frame.setBounds(720, 675, 450, 179);

					// se crea un panel en el que hay 4 botones, cada uno tiene un color diferente,
					// estos corresponden a las 4 vacunas.
					// cada uno tiene su propio action listener que llamara a la funcion actualizar
					// progreso, esta cambiara el porcentage de las vacunas
					// tambien comprueban si la vacuna seleccionada ya esta completa de forma que si
					// selleccionamos una completa no nos dejara volver a apretar en la misma.
					JPanel contentPanel = new JPanel();
					contentPanel.setBorder(new LineBorder(new Color(0, 0, 0), 7, true));
					frame.setContentPane(contentPanel);

					JButton btnNewButton = new JButton("");
					btnNewButton.setBounds(25, 45, 50, 50);
					btnNewButton.setBackground(Color.BLUE);
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int vac_elegida = 1;
							if (play.desarrollar_vacuna(Integer.toString(vac_elegida - 1))) {
								is_mking_vacuna = false;
								actualizarProgreso();
								frame.dispose();
							}

						}
					});
					contentPanel.setLayout(null);
					contentPanel.add(btnNewButton);

					JButton btnNewButton_1 = new JButton("");
					btnNewButton_1.setBounds(132, 45, 50, 50);
					btnNewButton_1.setBackground(Color.red);
					btnNewButton_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int vac_elegida = 2;
							if (play.desarrollar_vacuna(Integer.toString(vac_elegida - 1))) {
								is_mking_vacuna = false;
								actualizarProgreso();
								frame.dispose();
							}
						}
					});

					contentPanel.add(btnNewButton_1);

					JButton btnNewButton_1_1 = new JButton("");
					btnNewButton_1_1.setBounds(236, 45, 50, 50);
					btnNewButton_1_1.setBackground(Color.green);
					btnNewButton_1_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int vac_elegida = 3;
							if (play.desarrollar_vacuna(Integer.toString(vac_elegida - 1))) {
								is_mking_vacuna = false;
								actualizarProgreso();
								frame.dispose();
							}
						}
					});

					contentPanel.add(btnNewButton_1_1);

					JButton btnNewButton_1_1_1 = new JButton("");
					btnNewButton_1_1_1.setBounds(339, 45, 50, 50);
					btnNewButton_1_1_1.setBackground(Color.yellow);
					btnNewButton_1_1_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int vac_elegida = 4;
							if (play.desarrollar_vacuna(Integer.toString(vac_elegida - 1))) {
								is_mking_vacuna = false;
								actualizarProgreso();
								frame.dispose();
							}
						}
					});

					contentPanel.add(btnNewButton_1_1_1);

					JLabel lblNewLabel = new JLabel("Elije que vacuna Dessarrollar");
					lblNewLabel.setBounds(125, 126, 246, 24);
					contentPanel.add(lblNewLabel);

					/**/
					frame.setVisible(true);
				}

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
		if(!main.is_charged) {
			infectar_ciuades_rand();
		}
		
		
	}

	/**
	 * 
	 * funcion de game over, se crea otra ventana que aparecera cuando se acabe el juego, se comprobara si la condicion es de victoria o derrota y cambiara el panel en cosnsecuencia
	 * en panel de victoria recogera el nikname del jugador y lo mandara para el ranking.
	 */
	
	public static void gameOver(boolean win) {
        
		
		JFrame go = new JFrame();
        go.setUndecorated(true);
        go.setBounds(new Rectangle(700, 300, 600, 316));
        go.getContentPane().setLayout(null);
		go.setAlwaysOnTop(true);
		go.dispose();
		contentPane.setVisible(false);
		
		if (win) {
		
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0, 0, 0));
			panel.setBounds(0, 0, 606, 348);
			go.getContentPane().add(panel);
			panel.setLayout(new GridLayout(3, 1, 0, 0));

			JPanel panel_1 = new JPanel();
			panel.add(panel_1);
			panel_1.setLayout(null);

			JLabel lblNewLabel = new JLabel("New label");
			lblNewLabel.setIcon(new ImageIcon("YOUWIN.jpg"));
			lblNewLabel.setBounds(0, 0, 606, 131);
			panel_1.add(lblNewLabel);

			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(0, 0, 0));
			panel.add(panel_2);
			panel_2.setLayout(null);

			JTextField textField = new JTextField();
			textField.setBounds(116, 49, 361, 36);
			panel_2.add(textField);
			textField.setColumns(10);

			JLabel lblNewLabel_1 = new JLabel("Nickname");
			lblNewLabel_1.setMaximumSize(new Dimension(300, 14));
			lblNewLabel_1.setForeground(new Color(204, 204, 204));
			lblNewLabel_1.setBounds(116, 21, 281, 30);
			panel_2.add(lblNewLabel_1);

			JPanel panel_3 = new JPanel();
			panel_3.setBackground(new Color(0, 0, 0));
			panel.add(panel_3);
			panel_3.setLayout(null);

			JButton btnNewButton = new JButton("Salir");
			btnNewButton.setBounds(177, 11, 207, 23);
			btnNewButton.setFocusable(false);
			btnNewButton.setFocusPainted(false);
			panel_3.add(btnNewButton);
			
			btnNewButton.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        String name = textField.getText();
			        int Rondas = partida.Rondas;
			    	main.guardarRanking(name,Rondas);
			        System.exit(0); 
			    }
			});
			
			
		} else {
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0, 0, 0));
			panel.setBounds(0, 0, 606, 395);
			go.getContentPane().add(panel);
			panel.setLayout(null);

			JLabel lblNewLabel = new JLabel("New label");
			lblNewLabel.setIcon(new ImageIcon("game-over-retro.jpg"));
			lblNewLabel.setBounds(0, 0, 606, 175);
			panel.add(lblNewLabel);

			JButton btnNewButton = new JButton("Salir");
			btnNewButton.setFocusable(false);
			btnNewButton.setFocusPainted(false);
			btnNewButton.setBounds(172, 223, 245, 57);
			panel.add(btnNewButton);
			
			btnNewButton.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        System.exit(0); 
			    }
			});
			
		}
		go.setVisible(true);
		

	
    }
	
	// funcion progres_barra: no retorna nada ni necesita parametros, contiene la
	// generacion de las barras de progreso de las 4 vacunas.
	public void progres_barra() {

		progressBar[0] = new JProgressBar();
		progressBar[1] = new JProgressBar();
		progressBar[2] = new JProgressBar();
		progressBar[3] = new JProgressBar();

		progressBar[0].setMinimum(0);
		progressBar[0].setMaximum(100);
		progressBar[0].setStringPainted(true);
		progressBar[0].setBackground(Color.blue);
		progressBar[0].setForeground(Color.orange);

		progressBar[1].setMinimum(0);
		progressBar[1].setMaximum(100);
		progressBar[1].setStringPainted(true);
		progressBar[1].setBackground(Color.red);
		progressBar[1].setForeground(Color.ORANGE);

		progressBar[2].setMinimum(0);
		progressBar[2].setMaximum(100);
		progressBar[2].setStringPainted(true);
		progressBar[2].setBackground(Color.green);
		progressBar[2].setForeground(Color.ORANGE);

		progressBar[3].setMinimum(0);
		progressBar[3].setMaximum(100);
		progressBar[3].setStringPainted(true);
		progressBar[3].setBackground(Color.yellow);
		progressBar[3].setForeground(Color.ORANGE);

		JButton abandonar_p = new JButton(" Abandonar Partida ");
		abandonar_p.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardar_partida();
			}
		});
		
		abandonar_p.setBackground(Color.red);
		abandonar_p.setForeground(Color.white);

		barras.add(progressBar[0]);
		barras.add(progressBar[1]);
		barras.add(progressBar[2]);
		barras.add(progressBar[3]);

		abort.add(abandonar_p);

		panelBotones.add(barras, BorderLayout.CENTER);
		panelBotones.add(abort, BorderLayout.SOUTH);

	}
	//
	// Guardar partida & salir
	public void guardar_partida() {
		main.insert1();
		main.insert2();
		main.insert3();
		System.exit(0);
	}

	// funcion de actualizar progreso de las progresbar, cambia el valor de la barra
	// segun el porcentage de dessarrollo de cada vacuna.
	public void actualizarProgreso() {
		SwingUtilities.invokeLater(() -> {
			progressBar[0].setValue(partida.illnes[0][1]);
			progressBar[1].setValue(partida.illnes[1][1]);
			progressBar[2].setValue(partida.illnes[2][1]);
			progressBar[3].setValue(partida.illnes[3][1]);
			System.out.println(play.Acciones);
			

			if (play.Acciones == 0) {
				
				infectar_ciuades_rand();
				play.Acciones = 3;
				partida.Rondas++;
			} else {
				play.Acciones--;
			}
		});
	}

	// funcion de actualizar los iconos de las ciudades, aqui se recive el numero de
	// ciudad(int) y la nueva imagene que se pondra.
	public static void actualizarIconoBoton(int indiceBoton, Icon nuevoIcono) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				botones[indiceBoton].setIcon(nuevoIcono);
			}
		});

	}

	// funcion botones, se encarga de la creacion y la colocacion de los botones de
	// cada ciudad en las coordenadas assignadas.
	// recibe el panleImagen que es el mapa para poner los botones encima.
	// cada boton tambien tiene los action listener necessarios para que si es
	// presionado despues de decidir curar ciudad se pueda curar la ciudad
	// seleccionada.

	public JPanel botones(JPanel panelImagen) {

		for (int i = 0; i < gestor.coordenadas.length; i++) {
			final int index = i; // guardar el valor actual de i en una variable final
			botones[i] = new JButton(i + "");

			botones[i].setContentAreaFilled(false);
			botones[i].setFocusPainted(false);
			botones[i].setBorder(null);
			botones[i].setBounds((int) gestor.coordenadas[i][1], (int) gestor.coordenadas[i][2], 40, 30);
			botones[i].setVisible(true);
			botones[i].setToolTipText(gestor.coordenadas[i][0].toString());
			SwingAction action = new SwingAction(gestor.coordenadas[i][0].toString());
			action.putValue(SHORT_DESCRIPTION, gestor.coordenadas[i][0].toString());
			botones[i].setAction(action);
			botones[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					SwingAction action = (SwingAction) botones[index].getAction();
					action.setCiudad(botones[index].getText()); // actualizar la ciudad correspondiente al botón actual
					botones[index].setAction(action);
				}
			});
			botones[i].setText(i + "");
			// botones[i].setEnabled(false);
			botones[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (is_curando & !is_mking_vacuna) {
						int ciudadInf = Integer.parseInt(botones[index].getText());
						// Boton ciudades accion
						// ciudadInf = ciudad pulsada ej. San Francisco = 1
						// if ((int)play.data[ciudadInf][play.data[0].length-1] > 0) {
						// play.curar_ciudad(Integer.toString(ciudadInf));

						/*
						 * play.data[ciudadInf][play.data[0].length - 1] = (int)
						 * (play.data[ciudadInf][play.data[0].length - 1]) ; if ((int)
						 * (play.data[ciudadInf][play.data[0].length - 1]) == 4) {
						 * play.data[ciudadInf][play.data[0].length - 1] = 0; }
						 */
						if ((Integer.parseInt(play.data[ciudadInf][play.data[0].length - 1]+"") > 0)) {
							play.curar_ciudad(Integer.toString(ciudadInf));

						}

						// aqui se construye el nombre del icono para poinerle a la ciudad segun el
						// nivel de infeccion y el color respectivo de la ciudad,
						// en la carpeta del proyecyo estan las 12 variaciones de icoonos possibles.
						imagenx = (play.illnes_data[(int) play.data[ciudadInf][1]][2] + "_"
								+ play.data[ciudadInf][play.data[0].length - 1] + ".png");

						Image imagen3;

						try {
							imagen3 = ImageIO.read(new File(imagenx));
							botones[ciudadInf].setIcon(new ImageIcon(imagen3));
							/*
							 * for (int j = 0; j < botones.length; j++) { botones[j].setEnabled(false); }
							 */

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println(play.Acciones);
						int i = 0;

						// en cuanto se acaban las 4 acciones permitidas por turno se ejecuta esto, asi
						// se añaden 4 acciones mas disponibles y se actualiza el estado de la parida.
						if (play.Acciones == 0) {
							
							infectar_ciuades_rand();
							play.Acciones = 3;
							partida.Rondas++;
						} else {
							play.Acciones--;
						}

						is_curando = false;
					}
				}
			});

			// la colocacion de los iconos iniciales del tablero.
			int x = 0;
			imagenx = "";
			panelImagen.add(botones[i]);
			if ((int) gestor.data[i][1] == 0) {
				imagenx = "azul_0.png";
			} else if ((int) gestor.data[i][1] == 1) {
				imagenx = "rojo_0.png";
			} else if ((int) gestor.data[i][1] == 2) {
				imagenx = "verde_0.png";
			} else if ((int) gestor.data[i][1] == 3) {
				imagenx = "amarillo_0.png";
			}
			Image imagen3;

			try {
				imagen3 = ImageIO.read(new File(imagenx));
				botones[i].setIcon(new ImageIcon(imagen3));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return panelImagen;
	}

	// esta funcion carga otro action listener a los botones, de forma que cuando
	// apretes uno de los botones este obtendra el numero de la ciudad que has
	// seleccionado.
	public String C_ciudades(JButton[] botones) {
		String[] ciudadInf = new String[1];
		for (int j = 0; j < botones.length; j++) {
			// botones[j].setEnabled(true);
			final int index = j;
			botones[j].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ciudadInf[0] = botones[index].getText();
					// JOptionPane.showMessageDialog(tablero.this, "has pulsado el boton " +
					// ciudadInf[0]);

				}
			});
		}
		return ciudadInf[0];
	}

	private class SwingAction extends AbstractAction {
		public String ciudad;

		public SwingAction(String ciudad) {
			this.ciudad = ciudad;
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, ciudad);
		}

		public void setCiudad(String ciudad) {
			this.ciudad = ciudad;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}


}
