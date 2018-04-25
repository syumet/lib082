package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BinaryTree<E> extends AbstractTree<E> {

	BinaryTree<E> left;
	BinaryTree<E> right;
	
	public BinaryTree() {
		// TODO
		super();
	}
		
	public BinaryTree<E> getLeft() throws NoSuchElementException {
		if (left == null)
			throw new NoSuchElementException("Empty left child.");
		return left;
	}

	public void setLeft(BinaryTree<E> t) {
		this.left = t;
	}

	public BinaryTree<E> getRight() throws NoSuchElementException {
		if (right == null)
			throw new NoSuchElementException("Empty right child.");
		return right;
	}

	public void setRight(BinaryTree<E> t) {
		this.right = t;
	}

	@Override
	public E item() throws NoSuchElementException {
		if (I == null)
			throw new NoSuchElementException("Attempted to access an empty tree.");
		return I;
	}
	
	public void setItem(E item) {
		this.I = item;
	}	

	@Override
	public Iterator<E> iterator() {
		return new TreeItr();	
	}
	
	private final class TreeItr implements Iterator<E> {

		Queue<E> treeItems;
		Iterator<E> iter;
		
		public TreeItr() {
			super();
			// TODO Auto-generated constructor stub
			iter = treeItems.iterator();
			
		}

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	@Override
	public int size() {
		int lc = (left == null) ? 0 : left.size();
		int rc = (right == null) ? 0 : right.size();
		return (1 + lc + rc);
	}

	@Override
	public boolean add(E e) {
		// TODO Auto-generated method stub
		return super.add(e);
	}


	
	

}
