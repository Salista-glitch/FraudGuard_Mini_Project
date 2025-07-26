package csc3a.DataStructures;


/**
 * Class for the nodes of the list 
 * @param <T>
 */
public class ListNode<T> implements Position<T> {

	//Attributes
	private T element;
	private ListNode<T> prev;
	private ListNode<T> next;
	
	
	/**
	 * Constructor for the ListNode
	 * @param element Element for the node
	 * @param next Next Node
	 * @param prev Previous Node
	 */
	public ListNode(T element,ListNode<T> next,ListNode<T> prev)
	{
		//Set the elements
		this.element = element;
		this.prev = prev;
		this.next = next;
		
	}
	
	/**
	 * Returns the element
	 */
	@Override
	public T getElement() {
		
		return element;
	}
	
	
	/**
	 * Gets the next node
	 * @return the next node
	 */
	public ListNode<T> getNext()
	{
		return next;
		
	}
	
	/**
	 * Gets the prev node
	 * @return the prev node
	 */
	public ListNode<T> getPrev()
	{
		return prev;
	}
	
	/**
	 * Sets the next node
	 * @param next
	 */
	public void setNext(ListNode<T> next)
	{
		this.next = next;
	}
	
	/**
	 * Sets the previous node
	 * @param prev
	 */
	public void setPrev(ListNode<T> prev)
	{
		this.prev = prev;
	}
	
	/**
	 * Sets the element
	 * @param element
	 */
	public void setElement(T element)
	{
		this.element = element;
	}

}
