package csc3a.GraphClasses;
import csc3a.DataStructures.*;
/**
 * Class to hold the edges the graph
 * @param <E>
 */
public class Edge<E,V> {

	//Attributes
	private E element;
	private Position<Edge<E,V>> position;
	private Vertex<V,E>[ ] endpoints;
	
	
	/**
	 * Constructor for the edge
	 * @param vertex1
	 * @param vertex2
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	public Edge(Vertex<V,E> vertex1, Vertex<V,E> vertex2, E element)
	{
		//set the attributes
		this.element = element;
		endpoints = (Vertex<V,E>[ ]) new Vertex[ ]{vertex1,vertex2};
			
	}
	
	/**
	 * Function to return the element
	 * @return
	 */
	public E getElement() 
	{
	 return element; 
	 
	}
	
	/**
	 * Function to return the vertices connected the edge
	 */
	public Vertex<V,E>[] getEndpoints() { 
		
		return endpoints; 
	
	}
	
	/**
	 * Function to set the position
	 * @param position
	 */
	public void setPosition(Position<Edge<E,V>> position) { 
		
		this.position = position;
		
    }
	
	/**
	 * Function to return the edge position
	 * @return
	 */
	public Position<Edge<E,V>> getPosition() 
	{ 
		return position; 
		
	}
	
	
	
}
