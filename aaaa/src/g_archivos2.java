import java.util.*;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import java.io.*;

public class g_archivos2 {
	public Object[][] data;
	public int[][] colind;
	public Object[][] ciudades;
	public Object[][] coordenadas;
	public String cities_file;
	public Object[][] illness;
	public int[] param=new int[4];
	
	

	/**
	 * 
	 * find id es una funcion que recogera el string de una ciudad y buscara el identificador para devolverlo en formato int
	 * 
	 */
	
	public int find_id(String citi) {
		int id = 0;
		int cant_ciudades = data.length;
		boolean isFound = false;
		int cont = 0;
		if (citi == null) {
			id = 0;
		} else {
			if (citi.equals("no_data")) {
				id = 0;
			} else {
				while (!isFound && cont <= cant_ciudades + 1) {
					Object city = ciudades[cont][1];
					String tmp = String.valueOf(city);
					if (citi.equals(tmp)) {
						id = Integer.parseInt((ciudades[cont][0]).toString());
						isFound = true;
					}
					cont++;
				}
				if (cont == cant_ciudades + 1) {
					System.out.println("NO se encontro la ciudad");
				}
			}
		}
		return id;
	}

	/**
	 * 
	 * fill colind es una funcion que guardara en una matriz las ciudades y sus colindantes por id
	 */
	public void fill_colind() {
		String[] data_line = new String[6];
		Arrays.fill(data_line, "no_data");
		int[] data_int = new int[6];
		for (int i = 0; i < ciudades.length; i++) {
			data_line[0] = data[i][0].toString();
			for (int j = 1; j < data_line.length; j++) {
				data_line[j] = data[i][j + 3].toString();
			}

			for (int j = 0; j < 6; j++) {
				String tmp = data_line[j];
				int id_tmp = find_id(tmp);
				data_int[j] = id_tmp;
			}

			colind = escribirDatosInt(colind, data_int);
		}
	}

	/**
	 * fill ciudades guardara la informacion de las ciudades en una matriz(nombre y coordenadas)
	 */
	public void fill_ciudades() {
		ciudades = new Object[data.length][2];
		int cont = 101;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				ciudades[i][0] = cont;
				ciudades[i][1] = data[i][0];
			}
			cont++;
		}
	}
	
	/**
	 * fill data es la funcion que recogera toda la informacion, ciudades, enfermedad, colindantes y la guardara en una matriz global para el uso del resto del juego.
	 */
	public void fill_data() {
		try (RandomAccessFile city = new RandomAccessFile(cities_file, "rw")) {
			String line = city.readLine();
			while (line != null) {
				Object[] data_line = new Object[9];

				String[] ciudades = new String[5];
				String[] var = line.split(";");
				data_line[0] = var[0];
				data_line[1] = Integer.parseInt(var[1]);
				data_line[2] = Integer.parseInt(var[2].split(",")[0]);
				data_line[3] = Integer.parseInt(var[2].split(",")[1]);
				ciudades = var[3].split(",");

				String[] fixedArray = new String[5]; // array fija de tamaño 5
				Arrays.fill(fixedArray, "no_data"); // llenar la array fija con "no_data"

				// copiar elementos de la variableArray a la fixedArray
				for (int i = 0; i < ciudades.length && i < fixedArray.length; i++) {
					fixedArray[i] = String.valueOf(ciudades[i]);
				}
				for (int i = 4, j = 0; i < 10 && j < fixedArray.length; i++, j++) {
					data_line[i] = fixedArray[j];
				}

				data = escribirDatosObj(data, data_line);
				line = city.readLine();
			}

		} catch (IOException e) {

			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * funcion para ver por consola cualquier matriz pasada por parametro de tipo int.
	 */
	public void imprimirMatriz(int[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * 
	 * funcion para ver por consola cualquier matriz pasada por parametro de tipo objeto.
	 */
	public void imprimirMatriz(Object[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}

	
	/**
	 * funcion que recoge una matriz y unos datos de tipo int y los registra dentro de una matriz
	 * 
	 */
	public int[][] escribirDatosInt(int[][] matriz, int[] datos) {
		int n = matriz != null ? matriz.length : 0;
		int m = datos.length;

		if (n == 0) {
			matriz = new int[1][m];
		} else {
			matriz = Arrays.copyOf(matriz, n + 1);
			matriz[n] = new int[m];
		}

		// Añadimos los datos para la nueva fila
		for (int i = 0; i < m; i++) {
			matriz[n][i] = datos[i];
		}

		return matriz;
	}
	
	/**
	 * funcion que recoge una matriz y unos datos de tipo objeto y los registra dentro de una matriz
	 * 
	 */

	public Object[][] escribirDatosObj(Object[][] matriz, Object[] datos) {
		int n = matriz != null ? matriz.length : 0;
		int m = datos.length;

		if (n == 0) {
			matriz = new Object[1][m];
		} else {
			matriz = Arrays.copyOf(matriz, n + 1);
			matriz[n] = new Object[m];
		}

		// Añadimos los datos para la nueva fila
		for (int i = 0; i < m; i++) {
			matriz[n][i] = datos[i];
		}

		return matriz;
	}

	/*
	 * recoge el id de una ciudad y devuelve el nombre de esta
	 */
	
	public String find_name(int id) {
		String name = "";
		for (int i = 0; i < ciudades.length; i++) {
			if (Integer.parseInt((ciudades[i][0]).toString()) == id) {
				name = (ciudades[i][1]).toString();
			}
		}
		return name;
	}

	/**
	 * funcion que rellena las coordenadas de cada ciudad en la matriz data.
	 */
	public void fill_coords() {
		int filas = data.length;
		int columnas = 3;
		coordenadas = new Object[filas][columnas];

		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				if (j == 0) {
					coordenadas[i][j] = data[i][0];
				} else if (j == 1) {
					coordenadas[i][j] = data[i][2];
				} else if (j == 2) {
					coordenadas[i][j] = data[i][3];
				}
			}
		}
	}
	
	/*
	 * funcion para leer archivos, se le pasa el nombre del archivo como parametro y este guarda las lineas del archivo en un array de string.
	 */
	public String[] leer_archivo(String file) {
		String[] file_lines = null;
		try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {

			String line = raf.readLine();
			int cont = 0;
			while (line != null) {
				cont++;
				line = raf.readLine();
			}
			file_lines = new String[cont];
			raf.seek(0);
			line = raf.readLine();
			for (int i = 0; i < cont; i++) {
				file_lines[i] = line;
				line = raf.readLine();
			}

		} catch (IOException e) {
			System.out.println("NO ha chutado");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file_lines;
	}
	
	/*
	 * funcion para obtener que tipo de enfermedad tiene cada ciudad y guardarlo en el array de enfermedades
	 */
	public void fill_illness() {
		try {
			RandomAccessFile file = new RandomAccessFile("enfermedades.bin", "r");
			String line;
			int cont = 0;
			while ((line = file.readLine()) != null) {
				cont++;
			}

			illness = new Object[cont][3];
			cont = 0;
			file.seek(0);
			line = "";
			while ((line = file.readLine()) != null) {
				String[] parts = line.split(";");
				illness[cont] = parts;
				cont++;
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * funcion para obtener los parametros del documento xml de parametros.
	 */
	public void fill_param() {
		try {
		      File fXmlFile = new File("parametros.xml");
		      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		      Document doc = dBuilder.parse(fXmlFile);
		      doc.getDocumentElement().normalize();
		      NodeList nList = doc.getElementsByTagName("parametros");
		      
		      for (int temp = 0; temp < nList.getLength(); temp++) {
		        Node nNode = nList.item(temp);
		        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		          Element eElement = (Element) nNode;
		          param[0] = Integer.parseInt(eElement.getElementsByTagName("numCiudadesInfectadasInicio").item(0).getTextContent());
		          param[1] = Integer.parseInt(eElement.getElementsByTagName("numCuidadesInfectadasRonda").item(0).getTextContent());
		          param[2] = Integer.parseInt(eElement.getElementsByTagName("numEnfermedadesActivasDerrota").item(0).getTextContent());
		          param[3] = Integer.parseInt(eElement.getElementsByTagName("numBrotesDerrota").item(0).getTextContent());
		        }
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }

	}

	public g_archivos2(String file) {

		cities_file = file;
		fill_data();
		fill_ciudades();
		fill_colind();
		fill_coords();
		fill_illness();
		fill_param();
	}
}
