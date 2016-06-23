package mx.unam.ciencias.edd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que imprime una gráfica de los archivos.
 */
public class GeneradorIndexHTML {
    private GeneradorArchivosYDirectorio ArchivosYDirectorio;
    
    public GeneradorIndexHTML(GeneradorArchivosYDirectorio archivosYDirectorio) {
        ArchivosYDirectorio = archivosYDirectorio;
        GraficaHTML();
    }
    
    /**
     * Método que crea la grafica con los nombres de archivo, cada uno con un enlace a su ubicación en el disco
     * duro, y los conecta si tienen mas de 10 palabras de maá de 5 carácteres en común.
     */
    private void GraficaHTML() {
        Lista<File> archivos = ArchivosYDirectorio.getArchivos();
        for (File archivo : archivos) {
            try {
                BufferedWriter bw = new BufferedWriter(
						       new FileWriter("/" + ArchivosYDirectorio.getDirectorio().getPath() + "/"+
								      "index.html"));
                GraficaArchivos graficaArchivos = new GraficaArchivos(archivos);
                Lista<String> listaArchivosString = new Lista<>();
                Lista<File> listaEnlaceArchivosString = new Lista<>();
                for (File ar : ArchivosYDirectorio.getArchivos()) {
                    listaArchivosString.agrega(ar.getName());
                    listaEnlaceArchivosString.agrega(ar);
                }
                GraficaSVG graficaSVG = new GraficaSVG(listaArchivosString, listaEnlaceArchivosString,
						       graficaArchivos.getGraficaNombresArchivos());
                bw.write(graficaSVG.getGraficaSVG());
                bw.flush();
                bw.close();
		
            } catch (IOException e) {
                System.out.println("Fallo al  leer" + archivo.getPath());
            }
        }
    }
}
