import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class partida  extends JFrame  {
	public ArrayList<String> next_inf = new ArrayList<String>();
	public ArrayList<String> alrdy_brt = new ArrayList<String>();
	public static Object[][] data;
	public Object[][] illnes_data;
	public int Turnos = 0;
	public static int Rondas = 0;
	public int Acciones = 3;
	public static int[][] illnes;
	public static int n_brotes = 0;
	public int vacunas_compl = 0;
	public int num_max_brotes;
	public int vac_2_win = 4;
	

	// Usando las tablas extraidas de los archivos ciudades y enfermedades creamos
	// las tablas que necessitamos en partida
	
	/**
	 * funcion curar ciudad, se le pasa como parametro una string correspondiente a la id de la ciudad seleccionada en el tablero
	 * y se reduce el nivel de infeccionde la ciudad
	 * 
	 */

	public void curar_ciudad(String city) {
		int city_2_sfe = Integer.parseInt(city);
		if(illnes[Integer.parseInt(data[city_2_sfe][1]+"")][1] >= 100) {
			data[city_2_sfe][(data[0].length) - 1] = 0;
		}else {
			data[city_2_sfe][(data[0].length) - 1] = (int) (data[city_2_sfe][data[0].length - 1]) - 1;
		}
		
	}

	
	/**
	 * 
	 * funcion dessarrollar vacuna, se recoje el identificador de la vacuna como parametro, entonces se comprueba que la vacuna no este dessarrollada al 
	 * 11 ya, si no lo esta se avanza en un 10%, en caso que ya este dessarrollada se retorna un false.
	 * 
	 */
	public boolean desarrollar_vacuna(String vacuna){
		int vacuna_2_mk = Integer.parseInt(vacuna);
		if (illnes[vacuna_2_mk][1] < 100) {
			illnes[vacuna_2_mk][1] = illnes[vacuna_2_mk][1] + 10;
			System.out.println("Se ha añadido 10 a la vacuna");
			if (illnes[vacuna_2_mk][1] == 100) {
				vacunas_compl++;
				System.out.println("Se ha completado una vacuna");
				if (vacunas_compl == vac_2_win) {
					System.out.println("Ganaste");
					tablero.gameOver(true);
					dispose();
					
				}
			}
			
			return true;
		} else if (illnes[vacuna_2_mk][1] == 100) {
			illnes[vacuna_2_mk][1] = 101;
			return false;
		} else {
			return false;
		}
		
	}

	/**
	 * funcion fill datos, esta funcion recoge los datos de los archivos de texto y rellena las variables que se utilizaran durante el juego
	 */
	public void fill_datos() {
		g_archivos2 files = new g_archivos2("ciudades.txt");
		num_max_brotes = files.param[3];
		illnes = new int[4][2];
		int filas_data = files.data.length;
		int columnas_data = files.data[0].length;

		data = new Object[filas_data][columnas_data + 1];

		for (int i = 0; i < filas_data; i++) {
			for (int j = 0; j < columnas_data; j++) {
				data[i][j] = files.data[i][j];
			}
			data[i][columnas_data] = 0;
		}
		illnes_data = files.illness;
		for (int i = 0; i < illnes.length; i++) {
			int tmp = Integer.valueOf((String) illnes_data[i][0]);
			illnes[i][0] = tmp;
			illnes[i][1] = 0;
		}

	}
	
	/**
	 * 
	 * funcion desatar brote, recoge el identificador de una ciudad cuyo nivel de infeccion haya llegado a 4 , y se suma uno al contador de brotes, 
	 * si el contador de brotes ha llegado al maximo devuelve la condicion para el game over, en caso que no se añadira a una lista de las que han brotado este turno para evitar que se brote dos veces
	 * en el mismo turno y se infectaran las colindantes
	 */
	public void desatar_brote(int city) {
		n_brotes++;
		if(n_brotes == num_max_brotes) {
			System.out.println("Perdiste");
			tablero.gameOver(false);
			dispose();
		}else {
			alrdy_brt.add(city + "");
			g_archivos2 files = new g_archivos2("ciudades.txt");
			int[] colind = files.colind[city];
			for (int i = 1; i < colind.length; i++) {
				if (colind[i] != 0 && !alrdy_brt.contains((colind[i] - 101) + "")) {
					next_inf.add((colind[i] - 101) + "");
				}
			}
		}
		

	}

	/**
	 * 
	 * esta funcion recoge el el nivel de infeccion de las ciudades y la enfermadad, entonces construye el nombre que tendra el icono de la ciudad
	 */
	public String nombre_foto(int num_ill, int lvl_inf) {
		String name = illnes_data[num_ill][2] + "_" + lvl_inf + ".png";
		return name;
	}
	
	/**
	 * 
	 * funcion de infectar ciudad recoge el id de una ciudad aleatoria, emntonces se comprueva el nivel de infeccion de cada ciudad, si es 4
	 * se desata un brote en esa ciudad si no se añade un nivel de infeccion y se modifica el icono para mostrar el nivel de infeccion
	 */
	public void infectar_ciudad(int city) {
		
		int pos_tmp = data[0].length - 1;
		int tmp = 0;

		tmp = Integer.parseInt(((data[city][pos_tmp]) )+"")+ 1;
		if (tmp == 4 && !alrdy_brt.contains(Integer.toString(city))) {
			desatar_brote(city);
			System.out.println("Se ha desatado un brote en la ciudad " + city);
		} else if(tmp < 4){
			data[city][pos_tmp] = tmp;
			System.out.println("La ciudad " +data[city][0]+ "("+city +")"+ ", se ha infectado | Infecciones: " + data[city][pos_tmp]);
			Image imagen3;
			try {
				imagen3 = ImageIO
						.read(new File(nombre_foto((int) (data[city][1]), (int) (data[city][data[0].length - 1]))));
				tablero.actualizarIconoBoton(city, new ImageIcon(imagen3));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		while (!next_inf.isEmpty()) {
			int tmp_inf = Integer.parseInt(next_inf.get(0));
			next_inf.remove(0);
			infectar_ciudad(tmp_inf);
			
		}
	}

	public partida() {
		fill_datos();
	}
}