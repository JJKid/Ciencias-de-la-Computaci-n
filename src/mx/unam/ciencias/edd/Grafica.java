package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase privada para iteradores de gráficas. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Grafica<T>.Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            // Aquí va su código.
			iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
			return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
			return iterador.next().elemento;
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException("Eliminar con el iterador " +
                                                    "no está soportado");
        }
    }

    /* Vecinos para gráficas; un vecino es un vértice y el peso de la arista que
     * los une. Implementan VerticeGrafica. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vecino del vértice. */
        public Grafica<T>.Vertice vecino;
        /* El peso de vecino conectando al vértice con el vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Grafica<T>.Vertice vecino, double peso) {
            // Aquí va su código.
			this.vecino = vecino;
			this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T getElemento() {
            // Aquí va su código.
			return vecino.getElemento();
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            // Aquí va su código.
			return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            // Aquí va su código.
			return vecino.getColor();
        }

        /* Define el color del vecino. */
        @Override public void setColor(Color color) {
            // Aquí va su código.
			vecino.setColor(color);
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
			return vecino.vecinos();
        }
    }

    /* Vertices para gráficas; implementan la interfaz ComparableIndexable y
     * VerticeGrafica */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* El conjunto de vecinos del vértice. */
        public Diccionario<T, Grafica<T>.Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
			this.elemento = elemento;
			this.color = Color.NINGUNO;
			vecinos = new Diccionario<T, Grafica<T>.Vecino>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T getElemento() {
            // Aquí va su código.
			return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
			return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
			return color;
        }

        /* Define el color del vértice. */
        @Override public void setColor(Color color) {
            this.color = color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos.valores();
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.
			double diferencia = distancia - vertice.distancia;
			if(diferencia > 0)
				return 1;
			if(diferencia == 0)
				return 0;
			
			return -1;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Diccionario<T, Vertice>();
    }

    /* Método auxiliar para buscar vecinos. */
    private Vecino buscaVecino(Vertice vertice,
                               Vertice vecino) {
        // Aquí va su código.
		Iterator<Vecino> iterador = vertice.vecinos.iterator();
		Vecino vecinoChance = iterador.next();
		
		while(!vecinoChance.getElemento().equals(vecino.getElemento())) {
			vecinoChance = iterador.next();
		}
		
		return vecinoChance;
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
		return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
		return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
		if(elemento == null || this.contiene(elemento))
			throw new IllegalArgumentException();
		
		vertices.agrega(elemento, new Vertice(elemento));
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.
		conecta(a, b ,1);
    }
    
    /* Hace un cast de VerticeGrafica a Vertice*/
	private Vertice castAVertice(VerticeGrafica<T> v) {
		return (Vertice) v;
	}

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
        if(!(this.contiene(a) && this.contiene(b)))
			throw new NoSuchElementException("No estan en la grafica");
		if(sonVecinos(a, b) || a.equals(b))
			throw new IllegalArgumentException("Ya estan conectados o son iguales los elementos a conectar");
		if(peso < 1)
			throw new IllegalArgumentException("Peso no positivo");
		
		Vertice va = castAVertice(vertice(a));
		Vertice vb = castAVertice(vertice(b));
		
		va.vecinos.agrega(b, new Vecino(vb, peso));
		vb.vecinos.agrega(a, new Vecino(va, peso));
		
		aristas++;
    }
    
    /* Método que busca un vecino de un vertice */
	private Vecino vecino(T a, Vertice b) {
		Iterator<Vecino> iterador = b.vecinos.iterator();
		Vecino vecino = iterador.next();
		
		while(!vecino.getElemento().equals(a)) {
			vecino = iterador.next();
		}
		
		return vecino;
	}

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.
        if(!(this.contiene(a) && this.contiene(b)))
			throw new NoSuchElementException();
		if(!sonVecinos(a, b) || a.equals(b))
			throw new IllegalArgumentException();
		
		Vertice va = castAVertice(vertice(a));
		Vertice vb = castAVertice(vertice(b));
		
		va.vecinos.elimina(b);
		vb.vecinos.elimina(a);
		
		aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        for(Vertice v : vertices) {
			if(v.elemento.equals(elemento))
				return true;
		}
		return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(!this.contiene(elemento))
			throw new NoSuchElementException();
		
		for(VerticeGrafica<T> v : vertice(elemento).vecinos())
			desconecta(v.getElemento(), elemento);
		
		vertices.elimina(elemento);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <tt>true</tt> si a y b son vecinos, <tt>false</tt> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
        if(!(this.contiene(a) && this.contiene(b)))
			throw new NoSuchElementException();
		
		for(VerticeGrafica v : vertice(a).vecinos())
			if(v.getElemento().equals(b))
				return true;
		
		return false;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        // Aquí va su código.
        if(a.equals(b))
			throw new IllegalArgumentException();
		if(!(this.contiene(a) && this.contiene(b)))
			throw new NoSuchElementException();
		if(!sonVecinos(a, b))
			return -1;
		
		return vecino(a, castAVertice(vertice(b))).peso;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.
        if(!this.contiene(elemento))
			throw new NoSuchElementException();
		
		Iterator<Vertice> iterador = vertices.iterator();
		Vertice vertice = iterador.next();
		
		while(!vertice.getElemento().equals(elemento)) {
			vertice = iterador.next();
		}
		
		return vertice;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
		for(Vertice v : vertices) {
			accion.actua(v);
		}
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        if(!this.contiene(elemento))
			throw new NoSuchElementException();
		
		Vertice vactual;
		Cola<Vertice> bfs = new Cola<Vertice>();
		bfs.mete(castAVertice(vertice(elemento)));
		bfs.mira().color = Color.NEGRO;
		accion.actua(bfs.mira());
		
		while(!bfs.esVacia()) {
			vactual = bfs.saca();
			
			for(Vecino v : vactual.vecinos) {
				if(v.getColor() != Color.NEGRO) {
					v.setColor(Color.NEGRO);
					accion.actua(v);
					bfs.mete(castAVertice(vertice(v.getElemento())));
				}
			}
		}
		
		for(Vertice v: vertices) {
			v.color = Color.NINGUNO;
		}
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        if(!this.contiene(elemento))
			throw new NoSuchElementException();
		
		Vertice vactual;
		Pila<Vertice> dfs = new Pila<Vertice>();
		dfs.mete(castAVertice(vertice(elemento)));
		
		while(!dfs.esVacia()) {
			vactual = dfs.saca();
			if(vactual.color != Color.NEGRO) {
				vactual.color = Color.NEGRO;
				accion.actua(vactual);
				for(Vecino v : vactual.vecinos) {
					if(v.getColor() != Color.NEGRO) {
						dfs.mete(castAVertice(vertice(v.getElemento())));
					}
				}
			}
		}
		
		for(Vertice v: vertices) {
			v.color = Color.NINGUNO;
		}
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacio() {
        // Aquí va su código.
		return vertices.esVacio();
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        // Aquí va su código.
		return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <tt>a</tt> y
     *         <tt>b</tt>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
		if(!(this.contiene(origen) && this.contiene(destino)))
			throw new NoSuchElementException();
		
		for(Vertice v: vertices) {
			v.distancia = Double.MAX_VALUE;
		}
		
		Vertice original = castAVertice(vertice(origen));
		Vertice proximo = castAVertice(vertice(destino));
		
		original.distancia = 0;
		
		MonticuloMinimo<Vertice> mc = new MonticuloMinimo<Vertice>(vertices.valores());
		
		/* Calcular distancias */
		while(!mc.esVacio()) {
			Vertice vactual = mc.elimina();
			for(Vecino vecino : vactual.vecinos) {
				Vertice vec = castAVertice(vertice(vecino.getElemento()));
				double diferencia = 1 + vactual.distancia;
				if(vec.distancia > diferencia) {
					vec.distancia = diferencia;
					mc.reordena(vec);
				}
			}
		}
		
		Lista<VerticeGrafica<T>> lista = new Lista<VerticeGrafica<T>>();
		
		if(proximo.distancia == Double.MAX_VALUE)
			return lista;
		
		/* Recrear trayectoria */
		Vertice actual = proximo;
		while(actual != original) {
			lista.agrega(actual);
			for(Vecino posible : actual.vecinos) {
				Vertice posiblev = castAVertice(vertice(posible.getElemento()));
				double diferencia = 1 + posiblev.distancia;
				if(actual.distancia == diferencia)
					actual = posiblev;
			}
		}
		
		lista.agrega(original);
		
		return lista.reversa();
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <tt>origen</tt> y
     *         el vértice <tt>destino</tt>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
		if(!(this.contiene(origen) && this.contiene(destino)))
			throw new NoSuchElementException();
		
		for(Vertice v: vertices) {
			v.distancia = Double.MAX_VALUE;
		}
		
		Vertice original = castAVertice(vertice(origen));
		Vertice proximo = castAVertice(vertice(destino));
		
		original.distancia = 0;
		
		MonticuloMinimo<Vertice> mc = new MonticuloMinimo<>(vertices.valores());
		
		/* Calcular distancias */
		while(!mc.esVacio()) {
			Vertice vactual = mc.elimina();
			for(Vecino vecino : vactual.vecinos) {
				Vertice vec = castAVertice(vertice(vecino.getElemento()));
				double diferencia = vecino.peso + vactual.distancia;
				if(vec.distancia > diferencia) {
					vec.distancia = diferencia;
					mc.reordena(vec);
				}
			}
		}
		
		Lista<VerticeGrafica<T>> lista = new Lista<VerticeGrafica<T>>();
		
		if(proximo.distancia == Double.MAX_VALUE)
			return lista;
		
		/* Recrear trayectoria */
		Vertice actual = proximo;
		while(actual != original) {
			lista.agrega(actual);
			for(Vecino posible : actual.vecinos) {
				Vertice posiblev = castAVertice(vertice(posible.getElemento()));
				double diferencia = posible.peso + posiblev.distancia;
				if(actual.distancia == diferencia)
					actual = posiblev;
			}
		}
		
		lista.agrega(original);
		
		return lista.reversa();
    }
}
