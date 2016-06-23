package mx.unam.ciencias.edd;

import java.io.File;

/**
 * Clase que genera una gráfica en base al número de repeticiones por palabra
 * contenidas en el diccionario
 */
public class GraficaArchivos {
    
    private Lista<File> listaArchivos;
    private Diccionario<String, Diccionario<String, Integer>> diccionarioDeArchivos;
    private Diccionario<String, Diccionario<String, Integer>> diccionarioDeArchivosFiltrado;
    private Grafica<String> graficaNombresArchivos;
    
    /**
     * Constructor que recibe una lista de archivos y crea una grafica
     *
     * @param listaArchivos la lista de archivos de la cual se creará la grafica
     */
    public GraficaArchivos(Lista<File> listaArchivos) {
        this.listaArchivos = listaArchivos;
        creaGraficaArchivos();
    }
    
    /**
     * Metodo que crea una grafica a partir de una lista de archivos
     */
    private void creaGraficaArchivos(){
        graficaNombresArchivos = new Grafica<>();
        for (File archivo : listaArchivos){
            graficaNombresArchivos.agrega(archivo.getName());
	}
        conectaGraficaArchivos();
    }
    
    /**
     * Conecta la grafica que contiene los elementos de la lista de archivos.
     */
    private void conectaGraficaArchivos() {
        diccionarioDeArchivos = new Diccionario<>();
        diccionarioDeArchivosFiltrado = new Diccionario<>();
        for (File archivo : listaArchivos) {
            ContadorPalabras contadorPalabras = new ContadorPalabras(archivo);
            diccionarioDeArchivos.agrega(archivo.getName(), contadorPalabras.getDiccionarioPalabrasYRepeticiones());
            diccionarioDeArchivosFiltrado.agrega(archivo.getName(),
						 filtrar(diccionarioDeArchivos.get(archivo.getName())));
        }
	
        for(String archivoFiltrado1 : diccionarioDeArchivosFiltrado.llaves()){
            for(String archivoFiltrado2 : diccionarioDeArchivosFiltrado.llaves()) {
                if (sonVecinos(diccionarioDeArchivosFiltrado.get(archivoFiltrado1),
			       diccionarioDeArchivosFiltrado.get(archivoFiltrado2))) {
                    if(!graficaNombresArchivos.sonVecinos(archivoFiltrado1,archivoFiltrado2)){
                        if (!archivoFiltrado1.equals(archivoFiltrado2)) {
                            graficaNombresArchivos.conecta(archivoFiltrado1, archivoFiltrado2);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Crea un diccionario con todas las palabras mayores o iguales a 5 caracteres
     * @param diccionario1 : el diccionario en el cual se buscaran las palabras de 5 o mas caracteres
     * @return el nuevo diccionario con las palabras de 5 o mas caracteres almacenadas
     */
    private Diccionario<String, Integer> filtrar(Diccionario<String, Integer> diccionario1) {
        Diccionario<String, Integer> palabrasFiltradas = new Diccionario<>();
        for (String palabra : diccionario1.llaves()) {
            if (palabra.length() >= 5) {
                palabrasFiltradas.agrega(palabra, diccionario1.get(palabra));
            }
        }
        return palabrasFiltradas;
    }
    
    /**
     * Devuelve si dos diccionarios tienen al menos 10 palabras iguales, por lo tanto son vecinos.
     * @param diccionario1 el primero diccionario por comparar
     * @param diccionario2 el segundo diccionario por comparar
     * @return si ambos diccionarios poseen 10 o mas palabras iguales
     */
    private boolean sonVecinos(Diccionario<String, Integer> diccionario1, Diccionario<String, Integer> diccionario2) {
        int palabrasIguales = 0;
        for (String entrada1 : diccionario1.llaves()) {
            for (String entrada2 : diccionario2.llaves()) {
                if (entrada1.equals(entrada2)) {
                    palabrasIguales++;
                }
            }
        }
        return palabrasIguales >= 10;
    }
    
    /**
     * Devuelve la grafica con los nombres de archivo
     * @return la grafica con los nombres de archivo
     */
    public Grafica<String> getGraficaNombresArchivos() {
        return graficaNombresArchivos;
    }
}
