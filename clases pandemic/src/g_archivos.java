import java.util.*;
import java.io.*;

public class g_archivos {
	public Object[][] data;
	public int[][] colind;
	public Object[][] ciudades;
	public Object [][] coordenadas;
	public String cities_file;

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
				while (!isFound && cont < cant_ciudades) {
					Object city = ciudades[cont][1];
					String tmp = String.valueOf(city);
					if (citi.equals(tmp)) {
						id = Integer.parseInt((ciudades[cont][0]).toString());
						isFound = true;
					}
					cont++;
				}
				if (cont == cant_ciudades+1) {
					System.out.println("NO se encontro la ciudad");
				}
			}
		}
		return id;
	}

	public  void fill_colind() {
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

	public  void fill_ciudades() {
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

	
	public  void fill_data() {
		try (RandomAccessFile city = new RandomAccessFile(cities_file, "rw")) {
			String line = city.readLine();
			while (line != null) {
				Object[] data_line = new Object[9];
				//Atlanta ; 0 ; 320,320 ; Chicago,Miami,Washington ;
				// var0    var1   var2     var3/ciudades
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

	public  void imprimirMatriz(int[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}

	public  void imprimirMatriz(Object[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}

	public  int cuantas_colind(int[][] colind, int[] ya_visit, int punto) {

		return 0;
	}



	public  void escribirMatrizEnArchivo(Object[][] matriz, String nombreArchivo) {
		try {
			PrintWriter escritor = new PrintWriter(new BufferedWriter(new FileWriter(nombreArchivo)));
			for (Object[] fila : matriz) {
				escritor.println(Arrays.toString(fila));
			}
			escritor.close();
			System.out.println("Archivo " + nombreArchivo + " creado exitosamente");
		} catch (IOException e) {
			System.out.println("Hubo un error al escribir en el archivo");
			e.printStackTrace();
		}
	}

	public  void escribirMatrizEnArchivo(int[][] matriz, String nombreArchivo) {
		try {
			PrintWriter escritor = new PrintWriter(new BufferedWriter(new FileWriter(nombreArchivo)));
			for (int[] fila : matriz) {
				escritor.println(Arrays.toString(fila));
			}
			escritor.close();
			System.out.println("Archivo " + nombreArchivo + " creado exitosamente");
		} catch (IOException e) {
			System.out.println("Hubo un error al escribir en el archivo");
			e.printStackTrace();
		}
	}

	public  int[][] escribirDatosInt(int[][] matriz, int[] datos) {
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

	public  Object[][] escribirDatosObj(Object[][] matriz, Object[] datos) {
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

	public  String find_name(int id) {
		String name = "";
		for (int i = 0; i < ciudades.length; i++) {
			if (Integer.parseInt((ciudades[i][0]).toString()) == id) {
				name = (ciudades[i][1]).toString();
			}
		}
		return name;
	}

	public  void imprimir_array(int[] ruta) {
		System.out.print("[");
		for (int i = 0; i < ruta.length; i++) {
			System.out.print(ruta[i]);
			if (i != ruta.length - 1) {
				System.out.print(", ");
			}
		}
		System.out.println("]"); // Output: [1, 5]
	}
	
	
	public  void imprimir_cities(Object[][] matriz) {
		System.out.println("*****************************");
		System.out.println("* ID\tCiudad              *");
		System.out.println("+---------------------------+");
		for (int i = 0; i < matriz.length; i++) {
			System.out.print("* " + matriz[i][0] + "\t" + matriz[i][1]);
			for (int j = 0; j < (20 - ((matriz[i][1]).toString().length())); j++) {
				System.out.print(" ");
			}
			System.out.println("*");
		}
		System.out.println("*****************************");
	}
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
	public g_archivos(String file) {
		cities_file = file;
		fill_data();
		fill_ciudades();
		fill_colind();
		fill_coords();
	}
}
