package ds;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BinaryTree<E> extends AbstractTree<E> {

	BinaryTree<E> left;
	BinaryTree<E> right;
	
	public BinaryTree(E[] items) throws IllegalArgumentException {
		if (items.length == 0)
			throw new IllegalArgumentException("Attempted to creat an empty tree.");
		BinaryTree<E> root = span(items, 1);
		I = root.item();
		left = root.getLeft();
		right = root.getRight();
	}
		
	protected BinaryTree(E item) {
		I = item;
	}

	private BinaryTree<E> span(E[] items, int i) {
		if (items[i-1] == null) return null;
		BinaryTree<E> newTree = new BinaryTree<E>(items[i-1]);
		if (i * 2 <= items.length)
			newTree.setLeft(span(items, i*2));
		if (i * 2 < items.length)
			newTree.setRight(span(items, i*2+1));
		return newTree;
	}

	public BinaryTree<E> getLeft() throws NoSuchElementException {
		if (left == null)
			throw new NoSuchElementException("Empty left child.");
		return left;
	}

	protected void setLeft(BinaryTree<E> t) {
		this.left = t;
	}

	public BinaryTree<E> getRight() throws NoSuchElementException {
		if (right == null)
			throw new NoSuchElementException("Empty right child.");
		return right;
	}

	protected void setRight(BinaryTree<E> t) {
		this.right = t;
	}

	@Override
	public E item() throws NoSuchElementException {
		if (I == null)
			throw new NoSuchElementException("Attempted to access an empty tree.");
		return I;
	}
	
	protected void setItem(E item) {
		this.I = item;
	}	

	@Override
	public Iterator<E> iterator() {
		return new TreeItr();	
	}
	
	private final class TreeItr implements Iterator<E> {

		LinkedList<E> treeItems;
		Iterator<E> iter;
		
		public TreeItr() {
			super();
			treeItems = toListBFS();
			iter = treeItems.iterator();
		}
 
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public E next() {
			return iter.next();
		}
		
	}

	@Override
	public int size() {
		int lc = (left == null) ? 0 : left.size();
		int rc = (right == null) ? 0 : right.size();
		return (1 + lc + rc);
	}

	protected LinkedList<E> toListBFS() {
		
		LinkedList<E> L = new LinkedList<>();
		Queue<BinaryTree<E>> Q = new LinkedList<>();
		
		Q.add(this);
		while (!Q.isEmpty()) {
			BinaryTree<E> cur = Q.poll();
			L.add(cur.item());
			if (cur.left != null)
				Q.add(cur.left);
			if (cur.right != null)
				Q.add(cur.right);
		}
		return L;
	}

	@Override
	public Object[] toArray() {
		return toListBFS().toArray();
	}
	
	

}
