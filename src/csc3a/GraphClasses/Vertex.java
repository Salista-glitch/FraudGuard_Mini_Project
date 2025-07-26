package csc3a.GraphClasses;

import csc3a.DataStructures.*;

/**
 * Class to hold the vertex of the graph
 * @param <V>
 * @param <E>
 */
public class Vertex<V, E> {

	//Attributes
	private V element;
	private Position<Vertex<V, E>> position;
	private HashedMap<Vertex<V, E>, Edge<E,V>> connection;

	
	/**
	 * Constructor for the vertex
	 * @param element
	 */
	public Vertex(V element)
	{
		//Set the attributes
		this.element = element;
		connection = new HashedMap<>();
	}
	
	
	/**
	 * Function to return the element
	 * @return
	 */
	public V getElement()
	{
		return element;
	}
	
	
	/**
	 * Function to set the position
	 */
	public void setPosition(Position<Vertex<V,E>> position) { 
		this.position= position;
		}
	
	/**
	 * Function to set the position
	 * @return
	 */
	public Position<Vertex<V,E>> getPosition( ) { 
		return position; 
		}
	
	/**
	 * Funtion to return the map of vertices this map is connected to
	 */
	public HashedMap<Vertex<V, E>, Edge<E,V>> getConnection()
	{
		return connection;
	}
	
	
}
 