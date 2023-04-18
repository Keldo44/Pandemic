package aaaa;

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

public class diff extends JFrame{

	public JFrame frame;
	public JLabel label;
	public int selectedButton = 0;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	

	/**
	 * Initialize the contents of the frame.
	 */
	public static int[] newValues = {10, 5, 35, 9};
	
	public static void changeValues(int num) {
		switch (num) {
		case 1:
			newValues = new int[]{5, 5, 5, 5};
			break;
		case 2:
			newValues = new int[]{10, 10, 10, 10};
			break;
		case 3:
			newValues = new int[]{20, 20, 20, 20};
			break;
		default:
			break;
		}
	}
	public diff() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Medio");
		btnNewButton.setBounds(155, 143, 117, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeValues(2);
				ImageIcon icon = new ImageIcon(new ImageIcon("medd.jpg").getImage().getScaledInstance(117, 88, Image.SCALE_DEFAULT));
				label.setIcon(icon);
			}
		});
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Dificil");
		btnNewButton_1.setBounds(284, 143, 117, 25);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeValues(3);
				ImageIcon icon = new ImageIcon(new ImageIcon("dif.jpg").getImage().getScaledInstance(117, 88, Image.SCALE_DEFAULT));
				label.setIcon(icon);
			}
		});
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Facil");
		btnNewButton_2.setBounds(27, 143, 117, 25);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeValues(1);
				ImageIcon icon = new ImageIcon(new ImageIcon("facil.jpg").getImage().getScaledInstance(117, 88, Image.SCALE_DEFAULT));
				label.setIcon(icon);
			}
		});
		frame.getContentPane().add(btnNewButton_2);

		label = new JLabel("");
		label.setBounds(155, 28, 117, 88);
		ImageIcon icon = new ImageIcon(new ImageIcon("facil.jpg").getImage().getScaledInstance(117, 88, Image.SCALE_DEFAULT));
		label.setIcon(icon);
		frame.getContentPane().add(label);
		
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
						eElement.getElementsByTagName("numCiudadesInfectadasInicio").item(0).setTextContent(Integer.toString(newValues[0]));
						eElement.getElementsByTagName("numCuidadesInfectadasRonda").item(0).setTextContent(Integer.toString(newValues[1]));
						eElement.getElementsByTagName("numEnfermedadesActivasDerrota").item(0).setTextContent(Integer.toString(newValues[2]));
						eElement.getElementsByTagName("numBrotesDerrota").item(0).setTextContent(Integer.toString(newValues[3]));
					}

					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File("parametros.xml"));
					transformer.transform(source, result);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		frame.getContentPane().add(btnNewButton_3);
	}

	
}
