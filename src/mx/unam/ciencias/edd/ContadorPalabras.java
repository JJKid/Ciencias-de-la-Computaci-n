package mx.unam.ciencias.edd;

import java.io.*;
import java.text.Normalizer;

/**
 * Clase que cuenta el numero de palabras de un archivo y obtiene su diccionario de palabras y repeticiones
 */
public class ContadorPalabras {
    
    private File archivo;
    private Diccionario<String, Integer> DiccionarioPalabrasYRepeticiones;
    
    public ContadorPalabras(File archivo) {
        this.archivo = archivo;
        llenaDiccionario();
    }
    
    /**
     * Metodo que llena el diccionario de palabras y repeticiones de un archivo
     */
    
    private void llenaDiccionario() {
        String str;
        DiccionarioPalabrasYRepeticiones = new Diccionario<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            while ((str = br.readLine()) != null) {
                String[] palabras = str.split("\\s");
                for (int i = 0; i < palabras.length; i++) {
                    String palabra = Normalizer
			.normalize(palabras[i].replaceAll("\\P{L}+", "").toLowerCase(), Normalizer.Form.NFD)
			.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                    if (palabra.length() > 0) {
                        if (DiccionarioPalabrasYRepeticiones.contiene(palabra))
                            DiccionarioPalabrasYRepeticiones.agrega(palabra, DiccionarioPalabrasYRepeticiones.get(palabra) + 1);
                        else {
                            DiccionarioPalabrasYRepeticiones.agrega(palabra, 1);
                        }
                    }
                }
            }
	    
        } catch (IOException e) {
            System.out.println("El archivo no se pudo leer");
        }
    }
    
    /**
     * Regresa el diccionario de palabras y sus repeticiones de un archivo
     * @return Diccionario<String,Integer>: Diccionario de palabras y repeticiones
     */
    
    public Diccionario<String, Integer> getDiccionarioPalabrasYRepeticiones() {
        return DiccionarioPalabrasYRepeticiones;
    }
}
