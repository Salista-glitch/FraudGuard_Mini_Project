package csc3a.GraphClasses;
import csc3a.DataStructures.*;

/**
 * Class to create a undirected graph
 * @param <V>
 * @param <E>
 */
public class Graph<V, E> {

	//Attributes
	private PositionList<Vertex<V,E>> vertices = new LinkedPositionalList<>( );
	private PositionList<Edge<E,V>> edges = new LinkedPositionalList<>( );
	
	
	//methods
	/**
	 * Function to the number of vertices
	 * @return
	 */
	public int numVertices() 
	{ 
		return vertices.size( );
	
	}
	
	/**
	 * Function to the number of edges
	 * @return
	 */
	public int numEdges() 
	{ 
		return edges.size( );
	
	}
	
	/**
	 * Get the edges for a specific vertex
	 * @return
	 */
	public Iterable<Edge<E,V>> vertEdges(Vertex<V, E> vertex) 
	{
		
		return vertex.getConnection().values();
		
	}
	
	/**
	 * Get the degree of a vertex
	 * @return
	 */
	public int getDegree(Vertex<V, E> vertex) 
	{
		return vertex.getConnection().size();
	}
	
	/**
	 * Function to return the edge between two vertices
	 * @param u
	 * @param v
	 * @return
	 */
	public Edge<E,V> getEdge(Vertex<V,E> vertex1, Vertex<V,E> vertex2)
	{
		return vertex1.getConnection().get(vertex2);
	
	}
	
	/**
	 * Return the vertices connected by the edge
	 * @param edge
	 * @return
	 */
	
	public Vertex<V,E>[] endVertices(Edge<E,V> edge) 
	{
		return edge.getEndpoints( );
	}
	
	/**
	 * Get the vertex opposite the given node
	 * @param v
	 * @param e
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vertex<V,E> opposite(Vertex<V,E> vertex, Edge<E,V> edge) throws IllegalArgumentException {
	
	
		   Vertex<V,E>[ ] endpoints = edge.getEndpoints( );
		   if (endpoints[0] == vertex)
		   {
			   return endpoints[1];
		   }
		   else if (endpoints[1] == vertex)
		   {
			   return endpoints[0];
		   }
		   else
		   {
			   throw new IllegalArgumentException();
		   }
	
	}
	
	/**
	 * Function to create a new vertex and add it the graph
	 * @param element
	 * @return
	 */
	public Vertex<V,E> insertVertex(V element) 
	{
		Vertex<V,E> newVertex = new Vertex<>(element);
		newVertex.setPosition(vertices.addLast(newVertex));
	    return newVertex;
		
	}
	public Edge<E,V> insertEdge(Vertex<V,E> vertex1, Vertex<V,E> vertex2, E element) throws IllegalArgumentException
	{
	
		//Check if they are in the graph
		if(getEdge(vertex1, vertex2) != null)
		{
			throw new IllegalArgumentException("The edge is already in the graph");
		}
		else
		{
			Edge<E,V> newEdge = new Edge<E,V>(vertex1, vertex2, element);
			
			newEdge.setPosition(edges.addLast(newEdge));
			
			//Add to the map for the vertices
			vertex1.getConnection().put(vertex2, newEdge);
			vertex2.getConnection().put(vertex1,newEdge);
			
			return newEdge;
		}
		
	}
	
	/**
	 * Function to remove the vertex from the graph
	 * @param vertex
	 */
	public void RemoveVertex(Vertex<V,E> vertex)
	{
		
		for(Edge<E,V> edge: vertex.getConnection().values())
		{
			//Remove the edges
			edges.remove(edge.getPosition());
		}
		
		//remove the vertex
		vertices.remove(vertex.getPosition());
	}
	
	@SuppressWarnings("unchecked")
	public Iterable<Vertex<V,E>> vertices( ) { 
		
		return (Iterable<Vertex<V, E>>) vertices; 
	
	}
	
	@SuppressWarnings("unchecked")
	public Iterable<Edge<E,V>> edges( ) { 
		
		return (Iterable<Edge<E, V>>) edges; 
		
	}
}
