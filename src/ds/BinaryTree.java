package ds;

import java.util.NoSuchElementException;

public class BinaryTree<E> extends AbstractBinaryTree<E> {

	public BinaryTree(E[] items) throws IllegalArgumentException {
		if (items.length == 0)
			throw new IllegalArgumentException("Attempted to creat an empty tree.");
		BinaryTree<E> root = span(items, 1);
		I = root.item();
		left = root.getLeft();
		right = root.getRight();
	}
		
	public BinaryTree(E item) {
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
		
	@Override
	public BinaryTree<E> getLeft() throws NoSuchElementException {
		return (BinaryTree<E>) super.getLeft();
	}

	@Override
	public void setLeft(AbstractBinaryTree<E> t) {
		super.setLeft(t);
	}

	@Override
	public BinaryTree<E> getRight() throws NoSuchElementException {
		return (BinaryTree<E>) super.getRight();
	}

	@Override
	public void setRight(AbstractBinaryTree<E> t) {
		super.setRight(t);
	}

	@Override
	public void setItem(E item) { 
		super.setItem(item);
	}

	public static void main(String[] args) {
		Character[] words = {'A','B','C','D','E','F','G','H','I','J'};
		BinaryTree<Character> T = new BinaryTree<>(words);
		System.out.println(T.toString());
		System.out.println(T.getLeft().toString());
		for (Character item : T) {
			System.out.print(T.contains(item) + "\t");
		}
		System.out.println();
		
		Character[] w1 = {'B','C','D','E','G','H','I'};
		BinaryTree<Character> T1 = new BinaryTree<>(w1);
		System.out.print(T.containsAll(T1) + "\t");
		System.out.print(T.getLeft().containsAll(T1) + "\n");
		
		try {
			T.retainAll(T1);
		} catch (UnsupportedOperationException e) {
			System.out.println("Exception catched.");
		}
		
		T.clear();
	}

}
