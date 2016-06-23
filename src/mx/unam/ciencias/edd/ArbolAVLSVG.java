package mx.unam.ciencias.edd;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;

import java.awt.*;

/**
 * Clase para imprimir un ArbolAVL en SVG.
 */
public class ArbolAVLSVG{

    private ArbolAVL<Palabra> arbolAVL;
    private String arbolAVLSVG;
    private mapaSVG m;

    /**
     * Crea un ArbolAVLSVG a partir de un ÁrbolAVL, creado por una lista de palabras.
     * @param arbolAVL : ArbolAVL del cuál se creará el ÁrbolAVLSVG.
     */
    public ArbolAVLSVG(ArbolAVL<Palabra> arbolAVL) {
        this.arbolAVL = arbolAVL;
        imprimir();
    }

    /**
     *  Dibuja un ArbolAVL SVG a partir de un vértice.    
     */
    private void imprimir() {
	
        int profundidad = arbolAVL.profundidad();
        // Variable p que obtiene la maxima separacion sobre el eje x de los nodos, en el primer nivel (raíz del arbol).
        int p = (int) ((Math.pow(2, profundidad)) * 80);
        m = new mapaSVG(4*p, (profundidad * 300));
        arbolAVLSVG = m.getMapa();
        Point centro = new Point(m.getX() / 2, 120);
        dibujarSubArbol(arbolAVL.raiz(), centro, profundidad);
    }

    /**
     * Método que imprime un subárbol de tipo AVL a partir de un vértice recibido, el cual desplegará
     * en la parte superior izquierda un cociente que representa la altura entre el balance del nodo.
     * @param v : Vertice a partir del cual se imprimira su respectivo subarbol.
     * @param x : Coordenada x en el mapa SVG de donde se comenzará a imprimir el subárbol.
     * @param y : Coordenada y en el mapa SVG de donde se comenzará a imprimir el subárbol.
     * @param p : Profundidad del subarbol a partir del vertice recibido.
     */
    private void dibujarSubArbol(VerticeArbolBinario<Palabra> vertice, Point centro, int profundidad) {

        int altura = arbolAVL.getAltura(vertice);
        int alturaVerticeIzquierdo= -1;
        int alturaVerticeDerecho= -1;
        int profundidadVerticeIzquierdo;
        int profundidadVerticeDerecho;
        // Variable que se usará para recalcular la separacion en x, de dos nodos en un mismo nivel(su
        //profundidad es la misma).
        int a= (int)Math.pow(2,profundidad)*(60);
	
        if (vertice.hayIzquierdo()) {
            profundidadVerticeIzquierdo = profundidad - 1;
            VerticeArbolBinario<Palabra> verticeIzquierdo = vertice.getIzquierdo();
            Point centroVerticeIzquierdo = new Point(centro.x - a, centro.y + 200);
            arbolAVLSVG = arbolAVLSVG.concat(dibujarArista(centro.x, centro.y, centroVerticeIzquierdo.x, centroVerticeIzquierdo.y));
            dibujarSubArbol(verticeIzquierdo, centroVerticeIzquierdo, profundidadVerticeIzquierdo);
        }

        if (vertice.hayDerecho()) {
            profundidadVerticeDerecho = profundidad - 1;
            VerticeArbolBinario<Palabra> verticeDerecho = vertice.getDerecho();
            Point centroVerticeDerecho = new Point(centro.x + a, centro.y + 200);
            arbolAVLSVG = arbolAVLSVG.concat(dibujarArista(centro.x, centro.y, centroVerticeDerecho.x, centroVerticeDerecho.y));
            dibujarSubArbol(verticeDerecho, centroVerticeDerecho, profundidadVerticeDerecho);
        }
	
        String alturaEnCociente = Integer.toString(altura);
        String balanceenCociente = Integer.toString(alturaVerticeIzquierdo - alturaVerticeDerecho);
        String cociente = alturaEnCociente + "/" + balanceenCociente;
        arbolAVLSVG = arbolAVLSVG.concat(dibujarNodo(vertice,centro.x,centro.y,cociente));
    }
    
    /**
     * Método que dibuja un nodo de un ArbolAVL.
     * @param v     : Vertice del cual se imprimira su elemento.
     * @param x1    : Coordenada x desde la parte izquierda del mapa SVG.
     * @param y1    : Coordenada y desde la parte superior del mapa SVG.
     */
    private String dibujarNodo(VerticeArbolBinario<Palabra> v, int x1, int y1, String cociente) {
        String nodo;
        String e = v.get().getCadena();
	
        nodo = "<circle cx='"+x1+"' cy='"+y1+"' r='80' stroke='black' stroke-width='5' fill='white' />"+
	    "<text fill='black' font-family='sans-serif' font-size='25' x='"+x1+"' y='" + (y1+16)+"'" +
	    "text-anchor='middle'>"+ e +"</text>"+
	    "<text fill='black' font-family='sans-serif' font-size='20' x='"+(x1-80)+"' y='"+(y1-60)+"' text-anchor='middle'>"+cociente+"</text>";
        return nodo;
    }
    
    /**
     *  Metodo que dibuja una arista que une dos nodos del ArbolAVL.
     * @param x1 : Coordenada x inicial a partir de la cual se comenzará a trazar la arista
     * @param y1 : Coordenada y inicial a partir de la cual se comenzará a trazar la arista
     * @param x2 : Coordenada x final a partir de la cual se comenzará a trazar la arista
     * @param y2 : Coordenada y final a partir de la cual se comenzará a trazar la arista
     */
    private String dibujarArista (int x1, int y1, int x2, int y2) {
        String arista = "<line x1='"+x1+"' y1='"+(y1+15)+"' x2='"+x2+"' y2='"+(y2-15)+"' stroke='black' stroke-width='4' />";
        return arista;
    }

    /**
     * Regresa el mapa en SVG sobre el cual se traza el Árbol AVL
     * @return mapa en SVG del Árbol AVL
     */
    public mapaSVG getM() {
        return m;
    }

    /**
     * Regresa el ÁrbolAVL en SVG
     * @return Árbol AVL en SVG
     */
    public String getArbolAVLSVG() {
        return arbolAVLSVG;
    }
}
