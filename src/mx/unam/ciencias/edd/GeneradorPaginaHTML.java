package mx.unam.ciencias.edd;

import java.io.*;

/**
 * Clase que a partir de una lista de archivos y un directorio genera una pagina HTML para cada uno, y los guarda en
 * el directorio especificado.
 * Esta pagina contiene una serie de graficas respecto a las palabras y repeticiones del archivo.
 */
public class GeneradorPaginaHTML {
    private GeneradorArchivosYDirectorio ArchivosYDirectorio;
    
    public GeneradorPaginaHTML(GeneradorArchivosYDirectorio ArchivosYDirectorio) {
        this.ArchivosYDirectorio = ArchivosYDirectorio;
        generaPaginaHTML();
    }
    
    /**
     * Crea la pagina HTML del archivo y la guarda en el directorio especificado.
     * Esta contiene una tabla de palabras y sus repeticiones en el archivo.
     * Una gráfica de pastel de las 10 palabras más repetidas en el archivo, y una tabla con
     * palabras y su porcentaje respecto al total de palabras.
     * Una grafica de un Árbol rojinegro con las 15 palabras más repetidas en el archivo, donde el valor de
     * cada palabra es el número de veces que aparece.
     * Una grafica de ÁrbolAVL también con las 15 palabras más repetidas en el archivo, donde el valor de
     * cada palabra es el número de veces que aparece.
     */
    private void generaPaginaHTML() {
        File archivoHTML;
        BufferedReader br;
	
        for (File archivo : ArchivosYDirectorio.getArchivos()) {
            int posicionPunto = archivo.getPath().lastIndexOf(".");
            String nombreSinExtension = posicionPunto > 0 ? archivo.getPath().substring(0, posicionPunto) : archivo.getPath();
            try {
                ContadorPalabras contadorPalabras = new ContadorPalabras(archivo);
                GeneradorEstructuraHTML generadorEstructuraHTML =
		    new GeneradorEstructuraHTML(contadorPalabras.getDiccionarioPalabrasYRepeticiones());
                int anchoMaximo = generadorEstructuraHTML.getMapaMaximo().getX();
                BufferedWriter bw = new BufferedWriter(
						       new FileWriter("/" + ArchivosYDirectorio.getDirectorio().getPath() + "/" +
								      nombreSinExtension + ".html"));
                bw.write("<!DOCTYPE html>\n" +
			 "<html>\n" +
			 "<head>\n" +
			 "<style>\n" +
			 "header {\n" +
			 "   width:"+anchoMaximo+"px;\n" +
			 "   height:20%;\n" +
			 "   background-color:black;\n" +
			 "   color:white;\n" +
			 "   text-align:center;\n" +
			 "   padding:5px;\n" +
			 "}\n" +
			 "section {\n" +
			 "    width:100%;\n" +
			 "    float:left;\n" +
			 "    padding:10px;\n" +
			 "}\n" +
			 "footer {\n" +
			 "   width:"+anchoMaximo+"px;\n" +
			 "   height:20%;\n" +
			 "   background-color:black;\n" +
			 "   color:white;\n" +
			 "   clear:both;\n" +
			 "   text-align:center;\n" +
			 "   padding:5px;\n" +
			 "}\n" +
			 "</style>\n" +
			 "</head>\n" +
			 "<body>\n" +
			 "\n" +
			 "<header>\n" +
			 "<h1>"+archivo.getName()+"</h1>\n" +
			 "</header>\n" +
			 "\n" +
			 "<section>\n" +
			 "<h1>Palabras y Repeticiones</h1>\n" +
			 //Aqui va la tabla de la grafica de pastel
			 "<p>"+generadorEstructuraHTML.getDiccionarioHTML()+"</p>\n" +
			 "\n" +
			 "</section>\n" +
			 "<footer>\n" +
			 "</footer>\n" +
			 "\n" +
			 "<section>\n" +
			 "<h1>Grafica de pastel</h1>\n" +
			 //Aqui va la grafica de pastel
			 "<p>"+generadorEstructuraHTML.getGraficaDePastelHTML()+"</p>\n" +
			 "<p>"+generadorEstructuraHTML.getTablaGraficaDePastelHTML()+"</p>\n" +
			 "</section>\n" +
			 "<footer>\n" +
			 "</footer>\n" +
			 "\n" +
			 "\n" +
			 "<section>\n" +
			 "<h1>Grafica de Rojinegro</h1>\n" +
			 //Aqui va la grafica de Rojinegro
			 "<p>"+generadorEstructuraHTML.getArbolRojinegroHTML()+"</p>\n" +
			 "</section>\n" +
			 "<footer>\n" +
			 "</footer>\n" +
			 "\n" +
			 "\n" +
			 "<section>\n" +
			 "<h1>Grafica de AVL</h1>\n" +
			 //Aqui va la grafica de AVL
			 "<p>"+generadorEstructuraHTML.getArbolAVLHTML()+"</p>\n" +
			 "</section>\n" +
			 "<footer> \n" +
			 "</footer>\n" +
			 "\n" +
			 "\n" +
			 "</body>\n" +
			 "</html>");
                bw.flush();
                bw.close();
		
            } catch (IOException e) {
                System.out.println("Fallo al  leer" + archivo.getPath());
            }
        }
    }
}
