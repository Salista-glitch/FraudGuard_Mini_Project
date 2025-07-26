package csc3a.DataStructures;

import java.util.Iterator;

/**
 * A double Linked Positional List Class
 * @param <T>
 */
public class LinkedPositionalList<T> implements PositionList<T>,Iterable<T>  {

	//Attributes
	private int size = 0;
	private ListNode<T> header;
	private ListNode<T> trailer;
	
	
	/**
	 * Constructor for the linked list
	 */
	public LinkedPositionalList()
	{
		//Initialise the attributes
		header = new ListNode<T>(null,null,null);
		trailer = new ListNode<T>(null,null,null);
		
		header.setNext(trailer);
		trailer.setPrev(header);	
	}
	
	/**
	 * Function to return the size
	 */
	
	@Override
	public int size() {
	
		return size;
	}

	/**
	 * Checks whether the list is empty or not
	 */
	@Override
	public boolean IsEmpty() {
		
		return size == 0;
	}

	
	/**
	 * returns the first element
	 */
	@Override
	public Position<T> First() {
		
		return position(header.getNext());
	}

	/**
	 * returns the last element
	 */
	@Override
	public Position<T> Last() {
		
		return position(trailer.getPrev());
	}

	
	/**
	 * Return a node before p
	 */
	@Override
	public Position<T> Before(Position<T> p) throws IllegalArgumentException {
		
		return position(validateNode(p).getPrev());
	}

	/**
	 * Return a node after p
	 */
	@Override
	public Position<T> After(Position<T> p) throws IllegalArgumentException {
	
		return position(validateNode(p).getNext());
	}

	/**
	 * Creates a new node and adds it to the beginning of the list
	 */
	@Override
	public Position<T> addFirst(T element) {
		
		return addBetween(element, header, header.getNext());
	}

	/**
	 * Creates a new node and adds it to the end of the list
	 */
	@Override
	public Position<T> addLast(T element) {
		
		return addBetween(element, trailer.getPrev(), trailer);
	}

	/**
	 * Creates a new node after the passed node
	 */
	@Override
	public Position<T> addAfter(Position<T> p, T element) throws IllegalArgumentException {
		//validate the node
		ListNode<T> node = validateNode(p);
		
		return addBetween(element, node, node.getNext()) ;
	}

	
	/**
	 * Creates a new node before the passed node
	 */
	@Override
	public Position<T> addBefore(Position<T> p, T element) throws IllegalArgumentException {
		//validate the node
	    ListNode<T> node = validateNode(p);
	    
		return addBetween(element, node.getPrev(), node);
	}

	/**
	 * Replaces an element placed at a certain node and returns the old element
	 */
	@Override
	public T set(Position<T> p, T element) throws IllegalArgumentException {
		
		//validate the node
	    ListNode<T> node = validateNode(p);
	    
	    T oldElement = node.getElement();
		
	    //set the new element
	    node.setElement(element);
	    
		return oldElement;
	}

	/**
	 * Removes the node and returns the element it stored
	 */

	@Override
	public T remove(Position<T> p) throws IllegalArgumentException {
		
		//Validate the node 
		ListNode<T> node = validateNode(p);
		
		ListNode<T> prev = node.getPrev();
		ListNode<T> next = node.getNext();
		
		prev.setNext(next);
		next.setPrev(prev);
		
		//reduce the size
		size--;
		
		//get the element
		T element = node.getElement();
		
		node.setNext(null);
		node.setPrev(null);
		node.setElement(null);
		
		
		
		return element;
	}
	
	
	//Helper Methods
	
	/**
	 * Check if the node to be returned is a header or trailer
	 * @param listnode
	 * @return
	 */
	private Position<T> position(Position<T> listnode)
	{
		if(listnode == header || listnode == trailer)
		{
			return null; // if the node is trailer or header
		}
	
		return listnode;
	}
	
	/**
	 * Validation method before adding a new node before or after the passed 'Position
	 * @param position
	 * @return
	 * @throws IllegalArgumentException
	 */
	private ListNode<T> validateNode(Position<T> position) throws IllegalArgumentException
	{
		//Check the node is of listnode
		if(!(position instanceof ListNode))
		{
			throw new IllegalArgumentException("The argument does not belong to the list");
		}
		
		//Cast the node
		ListNode<T> node = (ListNode<T>) position;
		
		if(node.getPrev() == null)
		{
			throw new IllegalArgumentException("This element is not in the list");
		}
		
		return node;
		
	}
	
	/**
	 * Adds a new node between the given nodes
	 * @param element
	 * @param before
	 * @param after
	 * @return
	 */
	private Position<T> addBetween(T element, ListNode<T> after, ListNode<T> before)
	{
		//Create the new node
		ListNode<T> newnode = new ListNode<T>(element, before, after);
		
		//update the list
		after.setNext(newnode);
		before.setPrev(newnode);
		size++;
		
		return newnode;
		
		
	}

	public Iterator<T> iterator() {
	    return new Iterator<T>() {
	        private Position<T> cursor = First(); // Start at the first element

	        @Override
	        public boolean hasNext() {
	            return cursor != null;
	        }

	        @Override
	        public T next() {
	            T value = cursor.getElement();
	            cursor = After(cursor); // Move to the next position
	            return value;
	        }
	    };
	}
}
