package mx.unam.ciencias.edd;

import java.io.*;

/**
 * Clase que a partir de los argumentos de la línea de comandos crea la lista de archivos y asigna
 * el valor del directorio.
 */

public class GeneradorArchivosYDirectorio {
    
    private String[] argumentos;
    private Lista<File> archivos;
    private File directorio;
    
    public GeneradorArchivosYDirectorio(String[] argumentos) {
        this.argumentos = argumentos;
        filtrarArgumentos();
    }
    
    /**
     * Método que filtra los argumentos de la linea de comandos , determina si son archivos o si es un directorio
     * y los guarda en la lista de archivos o la variable de directorio respectivamente
     */
    private void filtrarArgumentos() {
        boolean esDirectorio = false;
        File pruebaArchivo;
        File pruebaDirectorio;
        archivos = new Lista<>();
	
        for (String argumento : argumentos) {
            if (esDirectorio) {
                pruebaDirectorio = new File(argumento);
                if (pruebaDirectorio.isDirectory()) {
                    directorio = pruebaDirectorio;
                    esDirectorio = false;
                } else {
                    System.err.println("El argumento" + argumento + " no es un directorio");
                }
            }
	    
            else if (argumento.equals("-o")) {
                esDirectorio = true;
            }
	    
            else if (!esDirectorio) {
                pruebaArchivo = new File(argumento);
                if (pruebaArchivo.isFile()) {
                    archivos.agrega(pruebaArchivo);
                } else {
                    System.err.println("El argumento " + argumento + " no es un archivo");
                }
            }
        }
    }
    
    public Lista<File> getArchivos() {
	
        return archivos;
    }
    
    public File getDirectorio() {
	
        return directorio;
    }
    
}
