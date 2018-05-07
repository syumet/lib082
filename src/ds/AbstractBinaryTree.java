package ds;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public abstract class AbstractBinaryTree<E> extends AbstractCollection<E> implements Tree<E> {

	E I;
	AbstractBinaryTree<E> left;
	AbstractBinaryTree<E> right;

//	protected AbstractBinaryTree<E> getLeft() throws NoSuchElementException {
//		if (left == null)
//			throw new NoSuchElementException("Empty left child.");
//		return left;
//	}
//
//	protected void setLeft(AbstractBinaryTree<E> t) {
//		this.left = t;
//	}
//
//	protected AbstractBinaryTree<E> getRight() throws NoSuchElementException {
//		if (right == null)
//			throw new NoSuchElementException("Empty right child.");
//		return right;
//	}
//
//	protected void setRight(AbstractBinaryTree<E> t) {
//		this.right = t;
//	}
//
//	public E getItem() throws NoSuchElementException {
//		if (I == null)
//			throw new NoSuchElementException("Attempted to access an empty tree.");
//		return I;
//	}
//	
//	protected void setItem(E item) {
//		this.I = item;
//	}	

	@Override
	public Iterator<E> iterator() {
		return new TreeItr();	
	}
	
	private final class TreeItr implements Iterator<E> {

		Queue<AbstractBinaryTree<E>> Q;
		
		public TreeItr() {
			Q = new LinkedList<>();
			Q.add(AbstractBinaryTree.this);
		}
 
		@Override
		public boolean hasNext() {
			return (!Q.isEmpty());
		}

		@Override
		public E next() {
			AbstractBinaryTree<E> cur = Q.poll();
			if (cur.left != null)
				Q.add(cur.left);
			if (cur.right != null)
				Q.add(cur.right);
			return cur.I;
		}
	}

	@Override
	public int size() {
		if (isEmpty()) return 0;
		int lc = (left == null) ? 0 : left.size();
		int rc = (right == null) ? 0 : right.size();
		return (1 + lc + rc);
	}
	
	@Override
	public boolean isEmpty() {
		return (I == null && left == null && right == null);
	}

	protected boolean isLeaf() {
		return (left == null && right == null);
	}
	
	@Override
	public void clear() {
		I = null;
		if (left != null) {
			left.clear();
			left = null;
		}
		if (right != null) {
			right.clear();
			right = null;
		}
	}

}
