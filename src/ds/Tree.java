package ds;

import java.util.NoSuchElementException;

public interface Tree<E> {
	
	/** 
	 * Contents of the item wrapped. 				
     */
	public E item() throws NoSuchElementException;
	
}
