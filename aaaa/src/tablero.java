import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import static javax.swing.Action.SHORT_DESCRIPTION;

import javax.imageio.ImageIO;
import javax.swing.*;

public class tablero extends JFrame {
	
	public Object[][] ciudades;
	public Object[][] data;
    public JButton[] botones = new JButton[49]; 
    public JLabel[] ciudades2 = new JLabel[49];
    int z = 0;
    public JProgressBar progressBar[] = new JProgressBar[4];
 // Crear un panel para los paneles secundarios
    public JPanel contentPane = new JPanel(new BorderLayout());
 // Crear un panel para los botones inferior izquierdo y derecho, y agregarlos al sur del JPanel secundario
    public JPanel panelBotones = new JPanel(new BorderLayout());
    public JPanel barras = new JPanel(new FlowLayout());
 // Crear un panel para el mapa
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
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        g_archivos2 gestor = new g_archivos2("ciudades.txt");
        data=gestor.data;
        ciudades=gestor.coordenadas;
        // Crear un JPanel secundario con BorderLayout para contener el panel de imagen y los paneles de botones
        setContentPane(contentPane);
        
        // Crear el panel de imagen y agregarlo al centro del JPanel secundario
        
        
        //añadir botones de ciudad
      //añadir botones de ciudad

       
        
        contentPane.add(botones(panelImagen), BorderLayout.CENTER);
        setBounds(100, 100, 1555, 1050);
        setResizable(false);
      
        
        
        
        

        
        
        
        panelBotones.add(barras, BorderLayout.CENTER);
        
        progres_barra(0, 0, 0, 0);
        
        
        barras.add(progres_barra(0, 0, 0, 0)[0]);
        barras.add(progres_barra(0, 0, 0, 0)[1]);
        barras.add(progres_barra(0, 0, 0, 0)[2]);
        barras.add(progres_barra(0, 0, 0, 0)[3]);
        
        barras.setSize(300, 500);
        barras.setVisible(true);
        
        
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
    
    public JProgressBar[] progres_barra (int k,int b,int n, int h) {
    	
    	progressBar[0]= new JProgressBar();
    	progressBar[1]= new JProgressBar();
    	progressBar[2]= new JProgressBar();
    	progressBar[3]= new JProgressBar();
    	
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
        
        
    	progressBar[0].setValue(k);
    	progressBar[1].setValue(b);
    	progressBar[2].setValue(n);
    	progressBar[3].setValue(h);
    	
    	panelBotones.add(barras, BorderLayout.CENTER);
    	 return progressBar;
    	 
    	
    }
    public String iconos(String imag, int x) {
    	String imagen="azul_3.png";
    	z = x; 
		return imagen;
    	
    }
    public void refresh() {
        new tablero();
        dispose();
    }
    	
    public JPanel botones(JPanel panelImagen) {
    	for (int i = 0; i < ciudades.length; i++) {
            final int index = i; // guardar el valor actual de i en una variable final
            botones[i] = new JButton("");
            botones[i].setContentAreaFilled(false);
            botones[i].setFocusPainted(false);
            botones[i].setBorder(null);
            botones[i].setBounds((int)ciudades[i][1], (int)ciudades[i][2], 40, 30);
            botones[i].setVisible(true);
            botones[i].setToolTipText(ciudades[i][0].toString());
            SwingAction action = new SwingAction(ciudades[i][0].toString());
            action.putValue(SHORT_DESCRIPTION, ciudades[i][0].toString());
            botones[i].setAction(action);
            botones[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    SwingAction action = (SwingAction) botones[index].getAction();
                    action.setCiudad(botones[index].getText()); // actualizar la ciudad correspondiente al botón actual
                    botones[index].setAction(action);
                }
            });

            int x=0;
            String imagenx="";
            panelImagen.add(botones[i]);
            if((int)data[i][1]==0) {
            	imagenx="azul_0.png";
            }else if((int)data[i][1]==1){
            	imagenx="red_0.png";
            }else if((int)data[i][1]==2){
            	imagenx="verde_0.png";
            }else if((int)data[i][1]==3){
            	imagenx="yellow_0.png";
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

        /*Image imagen3;
		String imagenx="";
		int x=0;
		try {
			imagen3 = ImageIO.read(new File(iconos("",x)));
			botones[z].setIcon(new ImageIcon(imagen3));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return panelImagen;
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



    public static void main(String[] args) {
        new tablero();
    }
}
