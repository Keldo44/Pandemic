import java.io.*;
import java.util.*;

public class g_archivos {
   
    public static Object[][] leerArchivo(String nombreArchivo) throws IOException {
        ArrayList<Object[]> filas = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] campos = linea.split(",");
            Object[] fila = new Object[campos.length];
            for (int i = 0; i < campos.length; i++) {
                fila[i] = campos[i];
            }
            filas.add(fila);
        }
        br.close();
        Object[][] matriz = new Object[filas.size()][];
        for (int i = 0; i < filas.size(); i++) {
            matriz[i] = filas.get(i);
        }
        return matriz;
    }
   
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
