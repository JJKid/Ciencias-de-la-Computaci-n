package mx.unam.ciencias.edd;

/**
 * Clase que crea un objeto palabra, que servira para comparar su atributo de repeticiones,
 * tras obtenerla de diccionario. 
 */
public class Palabra implements Comparable<Palabra> {    
    private String cadena;
    private Integer repeticiones;
    
    /**
     * Constructor que recibe una cadena y su numero de repeticiones
     * @param cadena la cadena por contabilizar
     * @param repeticiones el numero de repeticiones de la cadena
     */
    public Palabra(String cadena, int repeticiones) {
        this.cadena = cadena;
        this.repeticiones = repeticiones;
    }
    
    /**
     * Constructor que recibe una cadena e inicializa las repeticiones a 0
     * @param cadena la cadena por contabilizar
     */
    public Palabra(String cadena) {
        this.cadena = cadena;
        this.repeticiones = 0;
    }
    
    /**
     * Obtiene la cadena de la palabra
     * @return la cadena de la palabra
     */
    public String getCadena() {
        return cadena;
    }
    
    /**
     * Modifica la cadena almacenada en clase
     * @param cadena la nueva cadena de la clase
     */
    public void setCadena(String cadena) {
        this.cadena = cadena;
    }
    
    /**
     * Obtiene el numero de repeticiones de la cadena de clase
     * @return el numero de repeticiones de la cadena
     */
    public int getRepeticiones() {
        return repeticiones;
    }
    
    /**
     * Establece el numero de repeticiones de la cadena
     * @param repeticiones el numero de repeticiones de la cadena
     */
    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }
    
    /**
     * Compara una palabra con otra
     * @param o La palabra ingresada para ser comparada con la de clase
     * @return si la palabra es idéntica o no a la palabra de clase
     */
    @Override
    public int compareTo(Palabra o) {
        return repeticiones.compareTo(o.repeticiones);
    }
}
