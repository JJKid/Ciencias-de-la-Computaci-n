package mx.unam.ciencias.edd;

import java.io.File;

/**
 * Clase que genera las estructuras en HTML que seran mostradas en la pagina HTML de cada archivo a partir de su
 * diccionario
 */
public class GeneradorEstructuraHTML {
    private int totalPalabras;
    private Lista<Palabra> listaPalabras;
    private Diccionario<String, Integer> repeticiones;
    private Lista<Palabra> listaOrdenada;
    private Lista<Palabra> listaMasRepetidos;
    private String arbolAVLHTML;
    private String arbolRojinegroHTML;
    private String diccionarioHTML;
    private String graficaDePastelHTML;
    private String tablaGraficaDePastelHTML;
    private mapaSVG mapaMaximo;

    public GeneradorEstructuraHTML(Diccionario<String, Integer> repeticiones) {
        this.repeticiones = repeticiones;
        obtenerListaOrdenada();
        ArbolAVLHTML();
        ArbolRojinegroHTML();
        DiccionarioHTML();
        GraficaPastelHTML();
    }
    
    /**
     * Metodo que ordena la lista de palabras con su numero de repeticiones generada a partir del diccionario
     * de repeticiones del archivo con el algoritmo de ordenamiento mergeSort.
     */
    
    private void obtenerListaOrdenada() {
        int numerorepeticiones;
        listaPalabras = new Lista<>();
        for (String palabra : repeticiones.llaves()) {
            numerorepeticiones = repeticiones.get(palabra);
            Palabra pal = new Palabra(palabra, numerorepeticiones);
            listaPalabras.agrega(pal);
            //La usaremos para la grafica de pastel
            totalPalabras += numerorepeticiones;
        }
        listaOrdenada = Lista.mergeSort(listaPalabras).reversa();
    }
    
    /**
     * Método que crea un Arbol AVL en SVG para usarlo en una pagina HTML con las 15 palabras mas repetidas,
     * que seran las 15 primeras de la lista ordenada.
     */
    private void ArbolAVLHTML() {
        listaMasRepetidos = masRepetidos(15, listaOrdenada);
        ArbolAVL<Palabra> arbolAVL = new ArbolAVL<>();
        for (Palabra palabra : listaMasRepetidos) {
            arbolAVL.agrega(palabra);
        }
        ArbolAVLSVG arbolAVLSVG = new ArbolAVLSVG(arbolAVL);
        arbolAVLHTML =  arbolAVLSVG.getArbolAVLSVG();
        mapaMaximo = arbolAVLSVG.getM();
    }
    
    /**
     * Método que crea un Arbol Rojinegro en SVG con las 15 palabras mas repetidas,
     * que seran las 15 primeras de la lista ordenada.
     */
    private void ArbolRojinegroHTML() {
        listaMasRepetidos = masRepetidos(15, listaOrdenada);
        ArbolRojinegro<Palabra> arbolRojinegro = new ArbolRojinegro<>();
        for (Palabra palabra : listaMasRepetidos) {
            arbolRojinegro.agrega(palabra);
        }
        ArbolRojinegroSVG arbolRojinegroSVG = new ArbolRojinegroSVG(arbolRojinegro);
        arbolRojinegroHTML = arbolRojinegroSVG.getArbolRojinegroSVG();
        if (mapaMaximo.getX()<arbolRojinegroSVG.getM().getX()) {
            mapaMaximo = arbolRojinegroSVG.getM();
        }
    }
    
    /**
     * Método que crea una tabla de palabras y repeticiones del diccionario en HTML
     */
    private void DiccionarioHTML() {
        String diccionario =
	    "<head>\n" +
	    "<style>\n" +
	    "table, th, td {\n" +
	    "    border: 1px solid black;\n" +
	    "    border-collapse: collapse;\n" +
	    "}\n" +
	    "th, td {\n" +
	    "    padding: 5px;\n" +
	    "    text-align: left;\n" +
	    "}\n" +
	    "</style>\n" +
	    "</head>\n" +
	    "<body>"+
	    "<table style=\"width:100%\">\n" +
	    "  <caption></caption>\n" +
	    "  <tr>\n" +
	    "    <th>Palabras</th>\n" +
	    "    <th>Repeticiones</th>\n" +
	    "  </tr>\n";
        for (Palabra palabra : listaOrdenada) {
            diccionario = diccionario.concat(" <tr>"+
					     "<td>" + palabra.getCadena() +"</td>\n" +
					     "  <td>" + palabra.getRepeticiones() +"</td>\n" +
					     "  </tr>\n");
        }
        diccionario = diccionario.concat("</table>\n" +
					 "\n" +
					 "</body>\n" +
					 "</html>");
        diccionarioHTML = diccionario;
    }
    
    /**
     * Método que crea una grafica de Pastel del diccionario en SVG a partir de sus 10 palabras mas comunes,
     *  y de acuerdo a sus repeticiones el porcentaje respecto al total de palabras
     */
    private void GraficaPastelHTML() {
        Lista<Palabra> listaMasRepetidos = masRepetidos(9, listaOrdenada);
        GraficaPastel graficaPastel = new GraficaPastel(listaMasRepetidos, totalPalabras);
        graficaDePastelHTML = graficaPastel.getGraficaDePastelHTML();
        tablaGraficaDePastelHTML = graficaPastel.getTabla();
    }
    
    /**
     * Obtiene la n cantidad de palabras mas repetidas en la lista de palabras
     * @param n el numero de palabras mas repetidas por encontrar
     * @return la lista de las palabras mas repetidas
     */
    public Lista<Palabra> masRepetidos(int n, Lista<Palabra> listaOrdenada) {
        if (n < 1 || n > listaOrdenada.getElementos()) {
            throw new IllegalArgumentException("La lista que recibe para obtener los mas repetidos es nula");
        }
        Lista<Palabra> masRepetidos = new Lista<>();
        int i = 0;
        for (Palabra palabra : listaOrdenada) {
            if (i <= n) {
                masRepetidos.agrega(palabra);
                i++;
            } else {
                break;
            }
        }
        return masRepetidos;
    }
    
    /**
     * Regresa el mapa SVG mas grande, que es el encabezado mas grande de las dos estructuras de arbol
     * @return mapaSVG: El mapaSVG de mayor dimensión
     */
    public mapaSVG getMapaMaximo() {
        return mapaMaximo;
    }
    
    /**
     * Regresa el Árbol AVL en HTML
     * @return String : Arbol AVL en HTML
     */
    public String getArbolAVLHTML() {
        return arbolAVLHTML;
    }
    
    /**
     * Regresa el Árbol Rojinegro en HTML
     * @return String : Arbol Rojinegro en HTML
     */
    public String getArbolRojinegroHTML() {
        return arbolRojinegroHTML;
    }
    
    /**
     * Regresa la tabla del Diccionario en HTML
     * @return String : Diccionario en HTML
     */
    public String getDiccionarioHTML() {
        return diccionarioHTML;
    }
    /**
     * Regresa la Gráfica de Pastel en HTML
     * @return String : Gráfica de Pastel en HTML
     */
    public String getGraficaDePastelHTML() {
        return graficaDePastelHTML;
    }
    /**
     * Regresa la tabla de la Gráfica de Pastel en HTML
     * @return String : Gráfica de Pastel en HTML
     */
    public String getTablaGraficaDePastelHTML() {
        return tablaGraficaDePastelHTML;
    }
}
