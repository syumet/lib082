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
	
	public BinaryTree(E[] items) throws IllegalArgumentException {
		if (items.length == 0)
			throw new IllegalArgumentException("Attempted to creat an empty tree.");
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
		return (left == null && right == null);
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
	
	public static void main(String[] args) {
		Character[] words = {'A','B','C','D','E','F','G','H','I','J'};
		BinaryTree<Character> T = new BinaryTree<>(words);
		System.out.println(T.toString());
		System.out.println(T.left.toString());
		for (Character item : T) {
			System.out.print(T.contains(item) + "\t");
		}
		System.out.println();
		
		Character[] w1 = {'B','C','D','E','G','H','I'};
		BinaryTree<Character> T1 = new BinaryTree<>(w1);
		System.out.print(T.containsAll(T1) + "\t");
		System.out.print(T.left.containsAll(T1) + "\n");
		
		try {
			T.retainAll(T1);
		} catch (UnsupportedOperationException e) {
			System.out.println("Exception catched.");
		}
		
		T.clear();
	}

}
