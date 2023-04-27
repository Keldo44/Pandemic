
import java.io.BufferedReader;
import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ciudades {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] ciudades=new String[49];
		ciudades=leer();
		procesar(ciudades);
		
	}

	
	static String[] leer() {
		  String[] linea=new String[49];
		  File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;
	      try {
	          // Apertura del fichero y creacion de BufferedReader para poder
	          // hacer una lectura comoda (disponer del metodo readLine()).
	          archivo = new File ("ciudades.txt");
	          fr = new FileReader (archivo);
	          br = new BufferedReader(fr);

	          // Lectura del fichero
	          
	          for (int i = 0; i < linea.length; i++) {
		          linea[i]=br.readLine();  
	          }
	      }catch(Exception e){
	          e.printStackTrace();
	       }finally{
	          // En el finally cerramos el fichero, para asegurarnos
	          // que se cierra tanto si todo va bien como si salta 
	          // una excepcion.
	          try{                    
	             if( null != fr ){   
	                fr.close();     
	             }                  
	          }catch (Exception e2){ 
	             e2.printStackTrace();
	          }
	       }
	      
	      return linea;
	    }
	
	static void procesar(String[] linea) {
		String temp;
		String temp2;
		int distancias[]=new int[4];
		double distancia[]=new double[5];
		for (int i = 0; i < linea.length; i++) {
			temp=linea[i];
			String ciudad="";
			int numeros[]=new int[3];
			String[] ciudades=new String[50];
			String[] var=temp.split(";");
			ciudad=var[0];
			numeros[0]=Integer.parseInt(var[1]);
			numeros[1]=Integer.parseInt(var[2].split(",")[0]);
			numeros[2]=Integer.parseInt(var[2].split(",")[1]);
			ciudades=var[3].split(",");
			distancias[0]=numeros[1];
			distancias[1]=numeros[2];
			for (int z = 0; z < ciudades.length; z++) {
	            for (int k = 0; k < linea.length; k++) {
	                temp2 = linea[k];
	                String ciudad2 = "";
	                int numeros2[] = new int[3];
	                String[] var2 = temp2.split(";");
	                ciudad2 = var2[0];
	                numeros2[0] = Integer.parseInt(var2[1]);
	                numeros2[1] = Integer.parseInt(var2[2].split(",")[0]);
	                numeros2[2] = Integer.parseInt(var2[2].split(",")[1]);
	                if (ciudades[z].equals(ciudad2)) {
	                    distancias[2] = numeros2[1];
	                    distancias[3] = numeros2[2];
	                    distancia[z] = Math.sqrt(Math.pow(distancias[0] - distancias[2], 2) + Math.pow(distancias[1] - distancias[3], 2));
	                }
	            }
	        }
			
			
			
			System.out.println("la ciudad " + ciudad + " esta en las coordenadas " + numeros[0] + ", " + numeros[1] + " y " + numeros[2] + " sus ciudades colindantes son: ");
	        for (int z = 0; z < ciudades.length; z++) {
	            System.out.println("    · " + ciudades[z] + ", que esta a una distancia de " + distancia[z]);
	        }
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
			FileWriter fichero = null;
			 PrintWriter pw = null;	
			 String[] datos=new String[3];
		
			try {
				fichero = new FileWriter("ciudadesRedactadas.txt",true);
				pw = new PrintWriter(fichero);
					pw.println("la ciudad "+ciudad+" esta en las coordenadas "+ numeros[0]+", "+numeros[1]+" y "+numeros[2]+" sus ciudades colindantes son: ");
					for (int z = 0; z<ciudades.length; z++) {
						pw.println("    · " + ciudades[z] + ", que esta a una distancia de " + distancia[z]);
					}
					pw.println("--------------------------------------------------------------------------------------------------------------------------------");
				} catch (IOException e) {
					
					System.out.println("Error al escribir");
				}finally {
			           try {
			               // Nuevamente aprovechamos el finally para 
			               // asegurarnos que se cierra el fichero.
			               if (null != fichero) {
			                  fichero.close();
			               } 
			           }catch (Exception e2) {
			                  e2.printStackTrace();
			           }
				}
			
			String matriz[][] = new String[49][];
		}
		
	}
	
}
