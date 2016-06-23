package mx.unam.ciencias.edd;

/**
 * Proyecto 3
 * Programa que genera reportes de varios archivos de texto.
 * El programa recibe varios nombres de archivo por la línea de comandos, y un directorio existente precedido de la
 * bandera "-o".
 * Para cada archivo de texto el programa genera un archivo HTML dentro del directorio especificado en la línea de
 * comandos con la siguiente información:
 * Un conteo de cuántas veces aparece cada palabra en el archivo, sin considerar mayúsculas/minúsculas ni acentos.
 * Una gráfica de pastel de las palabras más comunes en el archivo y qué porcentaje del total de palabras ocupan.
 * Un árbol rojinegro con las 15 palabras más utilizadas en el archivo, donde el valor de cada palabra es el número
 * de veces que aparece en el archivo.
 * Un árbol AVL con los mismos datos del árbol de arriba.
 * @author : Juan José Arroyo Rivera
 */
public class proyecto3 {
    public static void main (String [] args){
        GeneradorArchivosYDirectorio ArchivosYDirectorio = new GeneradorArchivosYDirectorio(args);
        GeneradorPaginaHTML PaginasHTML = new GeneradorPaginaHTML(ArchivosYDirectorio);
        GeneradorIndexHTML indexHTML = new GeneradorIndexHTML(ArchivosYDirectorio);
        System.out.println("Reportes de archivos realizados con exito");
    }
}

