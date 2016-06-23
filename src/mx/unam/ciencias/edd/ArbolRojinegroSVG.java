package mx.unam.ciencias.edd;
import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.Color;

import java.awt.*;
/**
 * Clase para imprimir un ArbolRojinegro en SVG.
 */
public class ArbolRojinegroSVG {

    private ArbolRojinegro<Palabra> arbolRojinegro;
    private String arbolRojinegroSVG;
    private mapaSVG m;

    /**
     * Crea un ArbolRojinegroSVG a partir de un ArbolRojinegro.
     * creado por una lista de elementos recibido en la entrada estandar.
     *
     * @param arbolRojinegro : ArbolRojinegro del cuál se creará el ArbolRojinegroSVG.
     */
    public ArbolRojinegroSVG(ArbolRojinegro<Palabra> arbolRojinegro) {
        this.arbolRojinegro = arbolRojinegro;
        imprimir();
    }

    /**
     * Dibuja un ArbolRojinegroSVG a partir de un vértice.
     */
    public void imprimir() {

        int profundidad = arbolRojinegro.profundidad();
        // Variable p que obtiene la maxima separacion en x de los nodos, en el primer nivel (raíz del arbol)
        int p = (int) ((Math.pow(2, profundidad)) * 40);
        m = new mapaSVG(4 * p, (profundidad * 250));
        //String impresionArbolRojinegroSVG;
        arbolRojinegroSVG = m.getMapa();
        Point centro = new Point(m.getX() / 2, 100);
        dibujarSubArbol(arbolRojinegro.raiz(), centro, profundidad);
    }

    /**
     * Método que imprime un subárbol de tipo ArbolRojinegro a partir de un vértice recibido y lo guarda en un String.
     *
     * @param v : Vertice a partir del cual se imprimira su respectivo subarbol.
     * @param x : Coordenada x en el mapa SVG de donde se comenzará a imprimir el subárbol.
     * @param y : Coordenada y en el mapa SVG de donde se comenzará a imprimir el subárbol.
     * @param p : Profundidad del subarbol a partir del vertice recibido.
     */
    private void dibujarSubArbol(VerticeArbolBinario<Palabra> vertice, Point centro, int profundidad) {

        Color color = arbolRojinegro.getColor(vertice);
        int a = (int) (Math.pow(2, profundidad)) * (40);
        int j, k;
        int profundidadVerticeIzquierdo;
        int profundidadVerticeDerecho;

        if (vertice.hayIzquierdo()) {
            profundidadVerticeIzquierdo = profundidad - 1;
            VerticeArbolBinario<Palabra> verticeIzquierdo = vertice.getIzquierdo();
            Point centroVerticeIzquierdo = new Point(centro.x - a, centro.y + 200);
            arbolRojinegroSVG = arbolRojinegroSVG.concat(dibujarArista(centro.x, centro.y, centroVerticeIzquierdo.x, centroVerticeIzquierdo.y));
            dibujarSubArbol(verticeIzquierdo, centroVerticeIzquierdo, profundidadVerticeIzquierdo);
        }

        if (vertice.hayDerecho()) {
            profundidadVerticeDerecho = profundidad - 1;
            VerticeArbolBinario<Palabra> verticeDerecho = vertice.getDerecho();
            Point centroVerticeDerecho = new Point(centro.x + a, centro.y + 200);
            arbolRojinegroSVG = arbolRojinegroSVG.concat(dibujarArista(centro.x, centro.y, centroVerticeDerecho.x, centroVerticeDerecho.y));
            dibujarSubArbol(verticeDerecho, centroVerticeDerecho, profundidadVerticeDerecho);
        }
        arbolRojinegroSVG = arbolRojinegroSVG.concat(dibujarNodo(vertice, centro.x, centro.y, color));
    }

    /**
     * Método que dibuja un nodo de un ArbolRojinegroSVG.
     *
     * @param v     : Vertice del cual se imprimira su elemento.
     * @param x1    : Coordenada x desde la parte izquierda del mapa SVG.
     * @param y1    : Coordenada y desde la parte superior del mapa SVG.
     * @param color : Color del nodo.
     */
    private String dibujarNodo(VerticeArbolBinario<Palabra> v, int x1, int y1, Color color) {
        String nodo;
        String e = v.get().getCadena();
        String c;

        if (color == Color.ROJO) {
            c = "red";
        } else {
            c = "black";
        }

        nodo = "<circle cx='" + x1 + "' cy='" + y1 + "' r='60' stroke='" + c + "' stroke-width='3' fill='" + c + "'/>" +
                "<text fill='white' font-family='sans-serif' font-size='20' x='" + x1 + "' y='" + (y1 + 16) + "'\n" +
                "             text-anchor='middle'>" + e + "</text>";
        return nodo;
    }

    /**
     * Metodo que dibuja una arista que une dos nodos del ArbolRojinegro.
     *
     * @param x1 : Coordenada x inicial a partir de la cual se comenzará a trazar la arista.
     * @param y1 : Coordenada y inicial a partir de la cual se comenzará a trazar la arista.
     * @param x2 : Coordenada x final a partir de la cual se comenzará a trazar la arista.
     * @param y2 : Coordenada y final a partir de la cual se comenzará a trazar la arista.
     */
    public String dibujarArista(int x1, int y1, int x2, int y2) {
        String arista = "<line x1='" + x1 + "' y1='" + y1 + "' x2='" + x2 + "' y2='" + y2 +
                "' stroke='black' stroke-width='3' />";
        return arista;
    }

    /**
     * Regresa el mapa en SVG sobre el cual se traza el Árbol Rojinegro
     *
     * @return mapa en SVG del Árbol Rojinegro
     */
    public mapaSVG getM() {
        return m;
    }

    /**
     * Regresa el ÁrbolRojinegro en SVG
     *
     * @return Árbol Rojinegro en SVG
     */
    public String getArbolRojinegroSVG() {
        return arbolRojinegroSVG;
    }
}