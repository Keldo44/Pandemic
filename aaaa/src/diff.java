
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
/*--------------------------------------------*/
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class diff extends JFrame {

	//Creamos el marco, la etiqueta para el nombre de doificultad y un par de variables para el control de esta
	public JFrame frame;
	public JLabel label;
	public int selectedButton = 0;
	public static int N_dif = 0;

	
	//main en este caso se ejecutara para crear el objeto y hacerlo visible
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					diff window = new diff();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Creamos la array que contendra los nuevos valores del archivo parametros 
	public static int[] newValues = { 10, 5, 35, 9 };
	public static tablero ventana;
	
	//esta funcion dara una serie de valores en funcion de la dificultad que escojamos 
	public static void changeValues(int num) {
		switch (num) {
		case 1:
			newValues = new int[] { 4, 4, 10, 10 };
			break;
		case 2:
			newValues = new int[] { 7, 7, 10, 7 };
			break;
		case 3:
			newValues = new int[] { 10, 10, 10, 4 };
			break;
		default:
			break;
		}
	}
	//lo que haremos es crear dos construcotores unop por si venimos de crear una partida nueva y 
	//otro por si venimos de cargar una anterior
	
	//Esta es la de cargar partida, sacara la dificultad desde la informacion de la BBDD
	public diff(boolean a) {
		frame = new JFrame();
		changeValues(Integer.parseInt(main.cargado[1] + ""));
		try {
			//Y la almacena en el archivo param
			File xmlFile = new File("parametros.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("parametros");
			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				eElement.getElementsByTagName("numCiudadesInfectadasInicio").item(0)
						.setTextContent(Integer.toString(newValues[0]));
				eElement.getElementsByTagName("numCuidadesInfectadasRonda").item(0)
						.setTextContent(Integer.toString(newValues[1]));
				eElement.getElementsByTagName("numEnfermedadesActivasDerrota").item(0)
						.setTextContent(Integer.toString(newValues[2]));
				eElement.getElementsByTagName("numBrotesDerrota").item(0)
						.setTextContent(Integer.toString(newValues[3]));
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("parametros.xml"));
			transformer.transform(source, result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		tablero ventana = new tablero();
		frame.dispose();

	}
	
	//Aqui empezariamos una partida nueva por tanto hara lo mismo que la anterior pero para obtener la dificultad 
	//se la pedira al usuario en forma de panel con botones
	public diff() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Hay 3 dificultades  :facil, Medio y Dificil, todas asociadas con un boton y un set de parametros.
		
		//Cada boton actualiza el icono de el panel y ejecuta la funcion change values con su id para cambiar los valores de la array 
		//en funcion de la dificultad elegida
		JButton btnNewButton = new JButton("Medio");
		btnNewButton.setBounds(155, 143, 117, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeValues(2);
				ImageIcon icon = new ImageIcon(
						new ImageIcon("medd.jpg").getImage().getScaledInstance(117, 88, Image.SCALE_DEFAULT));
				label.setIcon(icon);
				N_dif = 2;
			}
		});
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Dificil");
		btnNewButton_1.setBounds(284, 143, 117, 25);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeValues(3);
				ImageIcon icon = new ImageIcon(
						new ImageIcon("dif.jpg").getImage().getScaledInstance(117, 88, Image.SCALE_DEFAULT));
				label.setIcon(icon);
				N_dif = 3;
			}
		});
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Facil");
		btnNewButton_2.setBounds(27, 143, 117, 25);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeValues(1);
				ImageIcon icon = new ImageIcon(
						new ImageIcon("facil.jpg").getImage().getScaledInstance(117, 88, Image.SCALE_DEFAULT));
				label.setIcon(icon);
				N_dif = 1;
			}
		});
		frame.getContentPane().add(btnNewButton_2);

		label = new JLabel("");
		label.setBounds(155, 28, 117, 88);
		ImageIcon icon = new ImageIcon(
				new ImageIcon("facil.jpg").getImage().getScaledInstance(117, 88, Image.SCALE_DEFAULT));
		label.setIcon(icon);
		frame.getContentPane().add(label);
		
		//El boton crear confirmara los cambios escribiendo en el archivo el contenido de la array.
		JButton btnNewButton_3 = new JButton("Crear");
		btnNewButton_3.setBounds(155, 205, 117, 25);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File xmlFile = new File("parametros.xml");
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(xmlFile);
					doc.getDocumentElement().normalize();

					NodeList nList = doc.getElementsByTagName("parametros");
					Node nNode = nList.item(0);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						eElement.getElementsByTagName("numCiudadesInfectadasInicio").item(0)
								.setTextContent(Integer.toString(newValues[0]));
						eElement.getElementsByTagName("numCuidadesInfectadasRonda").item(0)
								.setTextContent(Integer.toString(newValues[1]));
						eElement.getElementsByTagName("numEnfermedadesActivasDerrota").item(0)
								.setTextContent(Integer.toString(newValues[2]));
						eElement.getElementsByTagName("numBrotesDerrota").item(0)
								.setTextContent(Integer.toString(newValues[3]));
					}

					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File("parametros.xml"));
					transformer.transform(source, result);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				//Al final llamamos a la siguiente clase y cerramos este marco.
				tablero ventana = new tablero();
				frame.dispose();
			}
		});

		frame.getContentPane().add(btnNewButton_3);
	}

}
