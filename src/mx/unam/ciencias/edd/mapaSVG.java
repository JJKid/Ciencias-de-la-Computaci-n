package mx.unam.ciencias.edd;

/**
 * Clase que crea un mapaSVG,  que será un área con coordenadas de ancho y alto donde se desplegará la estructura de datos
 *  en SVG.
 **/
public class mapaSVG {
    private int x;
    private int y;
    private String mapa;

    /**
     * Crea un un mapaSVG , que sera el encabezado de el codigo donde imprimiremos las estructuras en SVG.
     * @param x : Ancho del mapaSVG.
     * @param y : Largo del mapaSVG.
     */
    public mapaSVG(int x, int y) {
        this.x = x;
        this.y = y;
        imprime();
    }
    
    /**
     * Imprime un mapa SVG a partir de sus coordenadas de ancho y alto.
     */
    public void imprime() {
        mapa ="<svg width='" + x + "' height='" + y + "'><g>";
    }
    
    /**
     * Regresa la coordenada x del mapaSVG.
     * @return x : Coordenada x del mapaSVG.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Regresa la coordenada y del mapaSVG.
     * @return y : Coordenada y del mapaSVG.
     */
    public int getY() {
        return y;
    }

    public String getMapa() {
        return mapa;
    }
}
