package algo;

import ds.BinaryTree;

public class Tree<E> {

	/**
	 * LeetCode Problem #226
	 * See https://leetcode.com/problems/invert-binary-tree/description/ for details.
	 */
    public static <E> BinaryTree<E> invert(BinaryTree<E> root) {
    	
        if(root == null) return null; 
        
		BinaryTree<E> left = root.getLeft();
		BinaryTree<E> right = root.getRight();
		root.setLeft(invert(right));
		root.setRight(invert(left));
		
		return root;
    }
    
    public static void main(String[] args) {
		
    	// test for invert()
    	Character[] words = {'A','B','C','D','E','F','G','H','I','J'};
		BinaryTree<Character> T = new BinaryTree<>(words);
		System.out.println(T.toString());
		T = invert(T);
		System.out.println(T.toString());
	}
    
}
