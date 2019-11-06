package ds;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<E> extends AbstractCollection<E> implements Tree<E> {

	protected E val;
	protected BinaryTree<E> left;
	protected BinaryTree<E> right;

	public BinaryTree() {}
	
	public BinaryTree(E val) {
		this.val = val;
	}
	
	public BinaryTree(E[] items) {
		if (items.length == 0) return;
		BinaryTree<E> root = span(items, 1);
		val = root.val;
		left = root.left;
		right = root.right;
	}
	
	private BinaryTree<E> span(E[] items, int i) {
		if (items[i-1] == null) return null;
		BinaryTree<E> newTree = new BinaryTree<E>();
		newTree.val = items[i-1];
		if (i * 2 <= items.length)
			newTree.left = span(items, i*2);
		if (i * 2 < items.length)
			newTree.right = span(items, i*2+1);
		return newTree;
	}	

	public E getRootItem() {
		return val;
	}

	public void setRootItem(E val) {
		this.val = val;
	}

	public BinaryTree<E> getLeft() {
		return left;
	}

	public void setLeft(BinaryTree<E> left) {
		this.left = left;
	}

	public BinaryTree<E> getRight() {
		return right;
	}

	public void setRight(BinaryTree<E> right) {
		this.right = right;
	}

	@Override
	public Iterator<E> iterator() {
		return new TreeItr();	
	}
	
	private final class TreeItr implements Iterator<E> {

		Queue<BinaryTree<E>> Q;
		
		public TreeItr() {
			Q = new LinkedList<>();
			Q.add(BinaryTree.this);
		}
 
		@Override
		public boolean hasNext() {
			return (!Q.isEmpty());
		}

		@Override
		public E next() {
			BinaryTree<E> cur = Q.poll();
			if (cur.left != null)
				Q.add(cur.left);
			if (cur.right != null)
				Q.add(cur.right);
			return cur.val;
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
		return (val == null && left == null && right == null);
	}

	protected boolean isLeaf() {
		return (val != null && left == null && right == null);
	}
	
	@Override
	public void clear() {
		val = null;
		if (left != null) {
			left.clear();
			left = null;
		}
		if (right != null) {
			right.clear();
			right = null;
		}
	}
	
	/**
	 * LeetCode Problem #226
	 */
    public void invert() {
		if (left != null)
			left.invert();
		if (right != null)
			right.invert();
		
		BinaryTree<E> tmp = this.left;
		this.left = this.right;
		this.right = tmp;
    }
	
	public static void main(String[] args) {

	}

}
