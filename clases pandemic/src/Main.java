import java.util.*;

public class Main {
	public static Object[][] data;
	public static int[][] illnes = new int[3][4];
	//Usando las tablas extraidas de los archivos ciudades y enfermedades creamos las tablas que necessitamos en partida
	public static void fill_datos(){
		g_archivos files = new g_archivos("ciudades.txt");
		int filas_data = files.data.length;
		int columnas_data = files.data[0].length;
		data = new Object[filas_data][columnas_data + 1];

		for (int i = 0; i < filas_data; i++) {
		    for (int j = 0; j < columnas_data; j++) {
		       data[i][j] = files.data[i][j];
		    }
		    data[i][columnas_data] = 0;
		}
		Object[][] illnes = {{1, "alpha", "azul", 0}, {2, "beta", "verde", 0}, {3, "gamma", "rojo", 0}, {4, "delta", "amarillo", 0}};
		
	}
	public static void desatar_brote(int city) {
		
	}
	public static void infectar_ciudad(int city) {
		data[city][data.length-2] = (int)data[city][data.length-2]+1;
		System.out.println("La ciudad "+city + ", se ha infectado | Infecciones: "+ data[data.length-2][city]);
		if ((int)data[city][data.length-2]==3) {
			desatar_brote(city);
			data[city][data.length-2] = (int)data[city][data.length-2]-1;
		}
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		fill_datos();
		int cont_inf = 3;
		int n_ciudades_sanas = 0;
		int vacunas_compl = 0;
		boolean end = false;
		boolean win = false;
		while (!end) {

			// Infecciones
			cont_inf++;
			if (cont_inf == 4) {
				cont_inf = 0;
				for (int i = 0; i < 4; i++) {
					int ct = (int) (Math.random() * 20);
					infectar_ciudad(ct);
				}
			}

			// Acciones
			System.out.println("Elige que quieres hacer \n1 - Curar ciudad \n2 - Desarrollar cura");
			int chs = sc.nextInt();
			switch (chs) {
			case 1:
				System.out.println("¿Que ciudad?");
				int city_2_sfe = sc.nextInt();
				data[city_2_sfe][data.length-2]= (int)(data[city_2_sfe][data.length-2]);
				System.out.println("La ciudad " + data[city_2_sfe][0] + " ahora tiene un nivel de infeccion de "
						+ data[city_2_sfe][data.length-2]);
				break;
			case 2:
			    System.out.println("¿Qué vacuna?");
			    int vacuna_2_mk = sc.nextInt();
			    if (illnes[3][vacuna_2_mk] < 100) {
			        illnes[3][vacuna_2_mk] += 50;
			        if (illnes[3][vacuna_2_mk] == 100) {
			            System.out.println("Completaste la vacuna " + illnes[0][vacuna_2_mk]);
			            illnes[3][vacuna_2_mk] = 101;
			        } else {
			            System.out.println("La vacuna " + illnes[0][vacuna_2_mk] + " ha progresado hasta el "
			                    + illnes[3][vacuna_2_mk] + "%");
			        }
			    } else {
			        System.out.println("La vacuna " + illnes[0][vacuna_2_mk] + " ya está completa.");
			        vacunas_compl++;
			    }
			    break;

			}
			
			//Vacunas check
			if(vacunas_compl == 4) {
				end = true;
				win =true;
				
			}
			// Sanas check
			

		}
	}

}
