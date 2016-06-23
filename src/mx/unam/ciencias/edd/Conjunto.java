package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * Clase para conjuntos de elementos. Todos sus métodos tienen complejidad en
 * tiempo <em>O</em>(1) (amortizado), excepto por {@link union} y {@link
 * interseccion}, que son <em>O</em>(<em>n</em>).
 */
public class Conjunto<T> implements Coleccion<T> {

    /* El conjunto de elementos. */
    private Diccionario<T, T> conjunto;

    /**
     * Crea un nuevo conjunto.
     */
    public Conjunto() {
        // Aquí va su código.
        conjunto = new Diccionario<T, T>();
    }

    /**
     * Crea un nuevo conjunto para un número determinado de elementos.
     *
     * @param n el número tentativo de elementos.
     */
    public Conjunto(int n) {
        // Aquí va su código.
        conjunto = new Diccionario<T, T>(n);
    }

    /**
     * Agrega un elemento al conjunto.
     *
     * @param elemento el elemento que queremos agregar al conjunto.
     * @throws IllegalArgumentException si el elemento es <code>null</code>.
     */
    @Override
    public void agrega(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();

        conjunto.agrega(elemento, elemento);
    }

    /**
     * Nos dice si el elemento está en el conjunto.
     *
     * @param elemento el elemento que queremos saber si está en el conjunto.
     * @return <code>true</code> si el elemento está en el conjunto,
     * <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        // Aquí va su código.
        return conjunto.contiene(elemento);
    }

    /**
     * Elimina el elemento del conjunto es vacío, si está.
     *
     * @param elemento el elemento que queremos eliminar del conjunto.
     */
    @Override
    public void elimina(T elemento) {
        // Aquí va su código.
        conjunto.elimina(elemento);
    }

    /**
     * Nos dice si el conjunto es igual al objeto recibido.
     *
     * @param o el objeto que queremos saber si es igual al conjunto.
     * @return <code>true</code> si el objeto recibido es instancia de Conjunto,
     * y tiene los mismos elementos.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Conjunto))
            return false;
        @SuppressWarnings("unchecked") Conjunto<T> c = (Conjunto<T>) o;
        // Aquí va su código.
        return conjunto.equals(c.conjunto);
    }

    /**
     * Nos dice si el conjunto es vacío.
     *
     * @return <code>true</code> si el conjunto es vacío, <code>false</code> en
     * otro caso.
     */
    @Override
    public boolean esVacio() {
        // Aquí va su código.
        return conjunto.getElementos() == 0;
    }

    /**
     * Regresa el número de elementos en el conjunto.
     *
     * @return el número de elementos en el conjunto.
     */
    @Override
    public int getElementos() {
        // Aquí va su código.
        return conjunto.getElementos();
    }

    /**
     * Regresa la intersección del conjunto y el conjunto recibido.
     *
     * @param conjunto el conjunto que queremos intersectar con éste.
     * @return la intersección del conjunto y el conjunto recibido.
     */
    public Conjunto<T> interseccion(Conjunto<T> conjunto) {
        // Aquí va su código.
	    /* Tamaño tentativo para evitar el peor de los casos en el diccionario.*/
        Conjunto<T> interseccion = new Conjunto<T>(this.getElementos());

        for (T elemento : this.conjunto) {
            if (conjunto.contiene(elemento)) {
                interseccion.agrega(elemento);
            }
        }
        return interseccion;
    }

    /**
     * Regresa la unión del conjunto y el conjunto recibido.
     *
     * @param conjunto el conjunto que queremos unir con éste.
     * @return la unión del conjunto y el conjunto recibido.
     */
    public Conjunto<T> union(Conjunto<T> conjunto) {
        // Aquí va su código.
        Conjunto<T> union = new Conjunto<T>(this.getElementos() * 2);

        for (T elemento : this.conjunto) {
            union.agrega(elemento);
        }

        for (T elemento : conjunto) {
            union.agrega(elemento);
        }
        return union;
    }

    /**
     * Regresa un iterador para iterar el conjunto.
     * @return un iterador para iterar el conjunto.
     */
    @Override
    public Iterator<T> iterator() {
        // Aquí va su código.
        return conjunto.iterator();
    }

    /**
     * Obtiene el conjunto de la clase
     * @return el conjunto de la clase
     */
    public Diccionario<T, T> getConjunto() {
        return conjunto;
    }
}