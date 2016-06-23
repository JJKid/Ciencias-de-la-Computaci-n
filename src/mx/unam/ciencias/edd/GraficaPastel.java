package mx.unam.ciencias.edd;

/**
 * Clase que recibe una lista de Palabras , con las n mas repetidas y construye una grafica de pastel
 */

public class GraficaPastel {
    
    private Lista<Palabra> listaMasRepetidos;
    private int totalPalabras;
    private Referencia centro = new Referencia(500, 500, 500);
    private String tabla;
    private String graficaDePastelHTML;
    private double ultimoAngulo;
    private double palabrasRestantes;
    
    public GraficaPastel(Lista<Palabra> listaMasRepetidos, int totalPalabras) {
        this.listaMasRepetidos = listaMasRepetidos;
        this.totalPalabras = totalPalabras;
        imprimirGrafica();
    }
    
    /**
     * Método que imprime una Grafica de Pastel en SVG y su tabla de palabras y porcentajes junto con el color
     * que les corresponde
     * @return Grafica de Pastel en SVG
     */
    private void imprimirGrafica() {
        // Calcula el valor del ángulo en radianes
        mapaSVG m = new mapaSVG((int) centro.getRadio() * 2 + 100, (int) centro.getRadio() * 2 + 100);
        graficaDePastelHTML = m.getMapa();
        double[] ap = new double[999];
        // Angulos de partida
        double[] af = new double[999];
        // Angulos finales
        tabla = "<p>" +
	    "<h3>Palabras y Porcentajes</h3>\n" +
	    "<table style=\"width:20%\">\n" +
	    "  <caption></caption>\n" +
	    "  <tr>\n" +
	    "    <th>Palabras</th>\n" +
	    "    <th>Porcentajes</th>\n" +
	    "  </tr>\n" +
	    "  <tr>";
        int i = 0;
        palabrasRestantes = totalPalabras;
        for (Palabra palabra : listaMasRepetidos) {
            int r = parametroColorAleatorio(0, 255);
            int g = parametroColorAleatorio(0, 255);
            int b = parametroColorAleatorio(0, 255);
            String colorAleatorio = "rgb(" + r + "," + g + "," + b + ")";
            double porcentaje = 100 * ((double) (palabra.getRepeticiones()) / (double) (totalPalabras));
            tabla = tabla.concat(" <td>" + palabra.getCadena() +
				 "</td>\n" +
				 "  <td>" + porcentaje +
				 "% </td>\n" +
				 " <td>" +
				 "<svg width=\"50\" height=\"20\">\n" +
				 "  <rect width=\"50\" height=\"20\" style=\"fill:" + colorAleatorio +
				 ";stroke-width:3;stroke:rgb(0,0,0)\">") +
		                 "</td>\n" +
	                         "  </tr>\n";
            //Angulo en grados sexagesimales
            double anguloactual = ((porcentaje * 360) / 100);
            af[i] = anguloactual;
            if (i > 0) {
                af[i] += af[i - 1];
                ap[i] = af[i - 1];
            } else {
                ap[i] = 0;
                Gajo gajo = new Gajo(colorAleatorio, porcentaje, palabra.getCadena(), ap[i], af[i]);
            }
            Gajo gajo = new Gajo(colorAleatorio, porcentaje, palabra.getCadena(), ap[i], af[i]);
            ultimoAngulo = af[i];
            palabrasRestantes -= palabra.getRepeticiones();
            i++;
            graficaDePastelHTML = graficaDePastelHTML.concat(nuevoGajo(gajo));
        }
	if (ultimoAngulo != 360) {
	    double porcentajeFinal = 100 * (palabrasRestantes / totalPalabras);
	    tabla = tabla.concat(" <td> Otras palabras" +
				 "</td>\n" +
				 "  <td>" + porcentajeFinal +
				 "% </td>\n" +
				 " <td>" +
				 "<svg width=\"50\" height=\"20\">\n" +
				 " <rect width=\"50\" height=\"20\" style=\"fill:rgb(0,0,0);stroke-width:3;stroke:rgb(0,0,0)\">") +
                 "</td>\n" +
                 "</tr>\n";
	    Gajo gajofinal = new Gajo("rgb(0,0,0)", porcentajeFinal, "Otros", ultimoAngulo, 360);
	    graficaDePastelHTML = graficaDePastelHTML.concat(nuevoGajo(gajofinal));
	}
	tabla = tabla.concat("</table>" + "</p>");
	graficaDePastelHTML.concat("</p>");
    }
    
    /**
     * Método que traza el perímetro de un gajo
     * @param g : Gajo a dibujar
     * @return perímetro del gajo en SVG
     */
    private String trazarGajo(Gajo g) {
        String trazoGajo = "\n" + "<path d=";
        trazoGajo = trazoGajo.concat("\"M" + centro.getX() + "," + centro.getY());
        trazoGajo = trazoGajo.concat("\n" + "L" + g.inicioArco.getX() + "," + g.inicioArco.getY());
        trazoGajo = trazoGajo.concat("\n" + "A" + centro.getRadio() + "," + centro.getRadio()
				     + " 0 0, 1 " + " " + g.finArco.getX() + "," + g.finArco.getY());
        trazoGajo = trazoGajo.concat("\n" + " z \"");
        return trazoGajo;
    }
    
    /**
     * Método que dibuja el estilo de un gajo
     * @param g : Gajo del cual se dibujará su estilo
     * @return estilo del gajo en SVG
     */
    private String estiloGajo(Gajo g) {
        String estilo = "\n" + "style=\"fill:" + g.color + ";stroke:" + g.color + ";stroke-width:1\"></path>";
        return estilo;
    }
    
    /**
     * Método que crea un gajo en SVG , primero traza su perimetro y despues obtiene su estilo
     * @param g : Gajo a dibujar
     * @return : nuevoGajo: Gajo en SVG creado
     */
    private String nuevoGajo(Gajo g) {
        String nuevoGajo = "";
        nuevoGajo = nuevoGajo.concat(trazarGajo(g));
        nuevoGajo = nuevoGajo.concat(estiloGajo(g));
        return nuevoGajo;
    }
    
    /**
     * Metodo que nos genera un numero aleatorio para generar los colores de los gajos
     * @param min rango minimo para rgb (0)
     * @param max rango maximo para rgb (255)
     * @return numero aleatorio entre el rango minimo y el maximo
     */
    private int parametroColorAleatorio(int min, int max) {
        int rango = (max - min) + 1;
        return (int) (Math.random() * rango) + min;
    }
    
    /**
     * Regresa la Grafica de Pastel en SVG para usarla en un archivo HTML
     * @return Grafica de Pastel en SVG
     */
    public String getGraficaDePastelHTML() {
        return graficaDePastelHTML;
    }
    
    /**
     * Regresa la Grafica de Pastel en SVG para usarla en un archivo HTML
     * @return Grafica de Pastel en SVG
     */
    
    public String getTabla() {
        return tabla;
    }
    
    /**
     * Clase que crea un gajo de una Grafica de Pastel
     */
    private class Gajo {
        private String color;
        private double porcentaje;
        private String nombre;
        private Referencia inicioArco;
        private Referencia finArco;
	
        private Gajo(String c, double p, String n, double a, double anguloActual) {
            color = c;
            porcentaje = p;
            nombre = n;
            //Calcula los extremos del arco, a partir de a y el angulo actual
            Referencia[] coordenadasExtremosArco = calculaArco(a, anguloActual);
            inicioArco = coordenadasExtremosArco[0];
            finArco = coordenadasExtremosArco[1];
        }
	
        /**
         * Calcula las coordenadas iniciales y finales del arco del gajo, y las guarda
         * en un arreglo de 2 puntos
         *
         * @param ini Coordenada inicial de arco del gajo
         * @param fin Coordenada final de arco del gajo
         * @return Coordenadas inicial y final del gajo
         */
        private Referencia[] calculaArco(double inicioArco, double finArco) {
            Referencia inicial = coordenadas(inicioArco);
            Referencia finV = coordenadas(finArco);
            Referencia[] r = {inicial, finV};
            return r;
        }
	
        /**
         * Método que calcula las coordenadas de un punto en la circunferencia de un círculo.
         * La función toma como argumento el ángulo ( a ) en grados sexagesimales, lo transforma en radianes (rad ),
         * calcula las coordenadas x e y del punto en la circunferencia,
         * @param a angulo a en grados sexagesimales
         * @return * Referencia que almacena dichas coordenadas.
         */
        private Referencia coordenadas(double a) {
            //Calcula cuanto le corresponde a cada elemento en radianes.
            double rad = (Math.PI / 180) * a;
            Referencia coordenadas = new Referencia(centro.getX() + (centro.getRadio() * Math.cos(rad)),
						    centro.getY() + (centro.radio * Math.sin(rad)));
            return coordenadas;
        }
    }
    
    /**
     * Clase privada que generá elementos a los cuales se les puede guardar una objeto Point con valores x y y,
     * color y un valor, los cuales serán utiles para dibujar vértices y sus conexiones en la gráfica.
     */
    private class Referencia {
        private double x;
        private double y;
        private String valor;
        private Color color;
        private double radio;
	
        public Referencia(double x, double y, String valor, Color color, int radio) {
            this.x = x;
            this.y = y;
            this.valor = valor;
            this.color = color;
            this.radio = radio;
        }
	
        public Referencia(double x, double y, double radio) {
            this.x = x;
            this.y = y;
            this.radio = radio;
        }
	
        public Referencia(double x, double y) {
            this.x = x;
            this.y = y;
        }
	
        /**
         * Regresa la coordenada y del objeto Referencia
         * @return Valor de la coordenada y
         */
        public double getX() {
            return x;
        }
	
        /**
         * Regresa la coordenada x del objeto Referencia
         * @return Valor de la coordenada x
         */
        public double getY() {
            return y;
        }
	
        /**
         * Regresa el radio del objeto Referencia
         * @return Valor del radio
         */
        public double getRadio(){
            return radio;
        }
    }
}

