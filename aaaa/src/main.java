import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main {

	public static Object[][] ranking = new Object[10][10]; // Ranking tabla
	public static Object[] cargado = new Object[4]; // Partida tbl
	public static Object[] cargado2 = new Object[5]; // Vacunas (id,vac1,vac2,vac3,vac4)
	public static Object[][] cargado3 = new Object[50][3];// Ciudades  (id,num_c,lvl_inf)
	
	public static boolean is_charged = false;
	// Credenciales de la BBDD
	private static final String USER = "DAW_PNDC22_23_ALIV";
	private static final String PWD = "AI123";
	private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe";// "jdbc:oracle:thin:@192.168.3.26:1521:xe";
																					// //"jdbc:oracle:thin:@oracle.ilerna.com:1521:xe"
																					// //fuera de casa

	public static void main(String[] args) {


		Connection con = conectarBaseDatos();
		try {

			if (con != null) {
				ranking = select(con);

			}
			con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		menu frame = new menu(ranking);

	}
	
	/*
	 * se establece conexion con la base de datos
	 */
	public static Connection conectarBaseDatos() {
		Connection con = null;

		System.out.println("Intentando conectarse a la base de datos");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PWD);
			System.out.println("Conectados a la base de datos");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha encontrado el driver " + e);
		} catch (SQLException e) {
			System.out.println("Error en las credenciales o en la URL " + e);
		}

		// System.out.println("Conectados a la base de datos");

		return con;
	}
	
	/*
	 * funcion para recibir los datos del ranking de la base de datos
	 */
	static Object[][] select(Connection con) {
		Object[][] datosRk = new Object[10][10];
		String sql = "SELECT p.* FROM pandemic p order by r_superv";
		int i = 0;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					String n_jug = rs.getString("n_jug");
					String r_superv = rs.getString("r_superv");
					String f_jueg = rs.getString("f_jueg");

					datosRk[i][0] = n_jug;
					datosRk[i][1] = r_superv;
					datosRk[i][2] = f_jueg;
					i++;

				}
			} else {
				System.out.println("No he encontrado nada");
			}

			// return ranking;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return datosRk;

	}
	
	/*
	 * funcion para guardar los datos de una partida ganada en el ranking en la base de datos
	 */
	public static void guardarRanking(String name, int rondas) {
		if(name==null) {
			name="Not assigned";
		}
		String n_jug = name;
		String r_superv = Integer.toString(rondas);
		String f_jueg = "SYSDATE";
		
		String sql = "INSERT INTO pandemic (i_partida,n_jug,r_superv,f_jueg) VALUES(contador_partidas.nextval,'" + n_jug + "'," + r_superv + "," + f_jueg+ ")";
		Connection con = conectarBaseDatos();
		try {

			Statement st = con.createStatement();
			st.execute(sql);

			System.out.println("insert hecho");
			con.close();
			System.out.println("CONEXION CERRADA");
		} catch (SQLException e) {
			System.out.println("Ha habido un error en el Insert " + e);
		}

		
	}
	
	
	//Instanciamos la clase partida para sacar los datos del estado de esta
	
	
	
	/*
	 * funcionde de insert 1 inser 2 y insert 3 son para guardar los datos de la partida en curso en la base de datos, estos se sacan de la clase de dificultad y partida.
	 */
	public static void insert1() {
		int ronda = partida.Rondas;
		int id_partida = 1;
		int dif = diff.N_dif;
		int brotes = partida.n_brotes;
		String sql = "INSERT INTO PARTIDA (RONDA,ID_PARTIDA,DIF,BROTES) VALUES(" + ronda + "," + id_partida + "," + dif
				+ "," + brotes + ")";
		Connection con = conectarBaseDatos();
		try {

			Statement st = con.createStatement();
			st.execute(sql);

			System.out.println("insert hecho");
			con.close();
			System.out.println("CONEXION CERRADA");
		} catch (SQLException e) {
			System.out.println("Ha habido un error en el Insert " + e);
		}

	}

	public static void insert2() {
		int id_partida = 1;
		int VACUNA1 = partida.illnes[0][1];
		int VACUNA2 = partida.illnes[1][1];
		int VACUNA3 = partida.illnes[2][1];
		int VACUNA4 = partida.illnes[3][1];
		String sql = "INSERT INTO VACUNAS (ID_PARTIDA,VACUNA1,VACUNA2,VACUNA3,VACUNA4) VALUES(" + id_partida + ","
				+ VACUNA1 + "," + VACUNA2 + "," + VACUNA3 + "," + VACUNA4 + ")";
		Connection con = conectarBaseDatos();
		try {
			Statement st = con.createStatement();
			st.execute(sql);

			System.out.println("insert hecho");
			con.close();
		} catch (SQLException e) {
			System.out.println("Ha habido un error en el Insert " + e);
		}
	}

	public static void insert3() {
		int id_partida = 1;
		int CIUDAD = 0;
		int N_INFECCION = 2;
		Connection con = conectarBaseDatos();
		
		for (int i = 0; i < 49; i++) {

			CIUDAD = i;
			N_INFECCION = Integer.parseInt(partida.data[i][partida.data[0].length-1]+"");
			String sql = "INSERT INTO CIUDADES (ID_PARTIDA,CIUDAD,N_INFECCION) VALUES(" + id_partida + "," + CIUDAD
					+ "," + N_INFECCION + ")";
			try {

				Statement st = con.createStatement();
				st.execute(sql);

				if(i==48) {
					con.close();
				}
				
			} catch (SQLException e) {
				// System.out.println("Ha habido un error en el Insert " + e);
			}
		}
	}
	
	
	
	
	/*
	 * cargado 1 cargado 2 y cargado 3 son las funciones para recoger los datos de la base de datos de la partida guardada y cargarla para reanudar el juego.
	 */
	public static void cargado1() {
		String sql="Select * from PARTIDA";	
		Connection con = conectarBaseDatos();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					cargado[0] = rs.getString("ronda");
					cargado[1] = rs.getString("dif");
					cargado[2] = rs.getString("brotes");
					cargado[3] = rs.getString("id_partida");
				}
			} else {
				System.out.println("No he encontrado nada");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void cargado2() {
		String sql="Select * from VACUNAS ";	
		Connection con = conectarBaseDatos();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					cargado2[0] = rs.getString("id_partida");
					cargado2[1] = rs.getString("vacuna1");
					cargado2[2] = rs.getString("vacuna2");
					cargado2[3] = rs.getString("vacuna3");
					cargado2[4] = rs.getString("vacuna4");
				}
			} else {
				System.out.println("No he encontrado nada");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void cargado3() {
		String sql="Select * from CIUDADES order by ciudad";	
		Connection con = conectarBaseDatos();
		int j=0;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					
						
						cargado3[j][0] = rs.getString("id_partida");
						cargado3[j][1] = rs.getString("ciudad");
						cargado3[j][2] = rs.getString("n_infeccion");
						System.out.println(cargado3[j][1]+"("+cargado3[j][2]+")");
						j++;
				}
			} else {
				System.out.println("No he encontrado nada");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
