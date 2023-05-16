import java.io.*;
import java.util.*;

public class g_archivos {
	
	g_archivos(){
		
	}
   
	/*
	 * funcion para leer archivos, se le pasa el nombre del archivo como parametro y este guarda las lineas del archivo en un array de string.
	 */
	public  String[] leer_archivo(String file) {
		String [] file_lines = null;
		try (RandomAccessFile raf = new RandomAccessFile(file,"rw")){
			
		
			String line=raf.readLine();
			int cont = 0;
			while (line != null) {
				cont++;
				line=raf.readLine();
			}
			file_lines = new String[cont];
			raf.seek(0);
			line=raf.readLine();
			for (int i = 0; i < cont; i++) {
				file_lines[i]= line;
				line=raf.readLine();
			}
			
			
		} catch (IOException e) {
			System.out.println("NO ha chutado");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file_lines;
	}
	/*
	 * funcion para escrivir archivos, se le pasa el nombre del archivo y una matriz que desees escrivir en un archivo y lo guarda en un txt.
	 */
    public static void escribirArchivo(String nombreArchivo, Object[][] matriz) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo));
        for (Object[] fila : matriz) {
            for (int i = 0; i < fila.length; i++) {
                bw.write(fila[i].toString());
                if (i < fila.length - 1) {
                    bw.write(",");
                }
            }
            bw.newLine();
        }
        bw.close();
    }
   
}
