package csc3a.DataStructures;


/**
 * Interface for the position Lists
 * @param <T>
 */
public interface PositionList<T> {

	int size();
	boolean IsEmpty();
	Position<T> First();
	Position<T> Last();
	Position<T> Before(Position<T> p)throws IllegalArgumentException;
	Position<T> After(Position<T> p) throws IllegalArgumentException;
	Position<T> addFirst(T element);
	Position<T> addLast(T element);
	Position<T> addAfter(Position<T> p,T element)throws IllegalArgumentException;
	Position<T> addBefore(Position<T> p,T element)throws IllegalArgumentException;
	T set(Position<T> p,T element) throws IllegalArgumentException;
	T remove(Position<T> p) throws IllegalArgumentException;
	
	
}
