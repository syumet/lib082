package algo;

import java.util.LinkedList;
import java.util.Queue;

import ds.BinaryTree;

public class Tree<E> {

	/**
	 * LeetCode Problem #226
	 * See https://leetcode.com/problems/invert-binary-tree/description/ for details.
	 */
    public static <E> BinaryTree<E> invert(BinaryTree<E> root) {
    	
        if (root == null) return null; 
        
		BinaryTree<E> left = root.getLeft();
		BinaryTree<E> right = root.getRight();
		root.setLeft(invert(right));
		root.setRight(invert(left));
		
		return root;
    }
    
	/**
	 * LeetCode Problem #100
	 * See https://leetcode.com/problems/same-tree/description/ for details.
	 */
    public static <E> Boolean isSame(BinaryTree<E> p, BinaryTree<E> q) {
    	
    	if (p == null && q == null)
    		return true;
    	if (p == null || q == null || p.getRootItem() != q.getRootItem())
    		return false;
    	
    	return (isSame(p.getLeft(), q.getLeft()) && isSame(p.getRight(), q.getRight()));
    }
    
	/**
	 * LeetCode Problem #104
	 * See https://leetcode.com/problems/maximum-depth-of-binary-tree/description/ for details.
	 */
    public static <E> int maxDepth(BinaryTree<E> root) {
    	if (root == null) return 0;
    	return 1 + Integer.max(maxDepth(root.getLeft()), maxDepth(root.getRight()) );
	}
    
	/**
	 * LeetCode Problem #111
	 * See https://leetcode.com/problems/minimum-depth-of-binary-tree/description/ for details.
	 */
    public static <E> int minDepth(BinaryTree<E> root) {
    	
    	if (root == null) return 0;
    	
    	Queue<BinaryTree<E>> T = new LinkedList<>();   	
    	Queue<Integer> D = new LinkedList<>();	
    	
    	T.add(root);
    	D.add(1);
    	BinaryTree<E> cur = T.poll();
    	int dep = D.poll();
    	
    	while (cur.getLeft() != null || cur.getRight() != null) {
			if (cur.getLeft() != null) {
				T.add(cur.getLeft());
				D.add(dep + 1);
			}
			if (cur.getRight() != null) {
				T.add(cur.getRight());
				D.add(dep + 1);
			}	
			cur = T.poll();
			dep = D.poll();
		}
    	return dep;
	}
    
    public static void main(String[] args) {
		
    	// test for invert()
    	Character[] words = {'A','B','C','D','E','F','G','H','I','J'};
		BinaryTree<Character> T = new BinaryTree<>(words);
		System.out.println(T.toString());
		T = invert(T);
		System.out.println(T.toString());
		
		// test for isSame()
		BinaryTree<Character> T1 = new BinaryTree<>(words);
		System.out.print(isSame(T, T1) + "\t");
		
		T1 = invert(T1);
		// System.out.println(T1.toString());
		System.out.print(isSame(T, T1) + "\t");
		
    	Character[] w1 = {'A','B','C','D','E','G','H','I','J'};
		T1 = new BinaryTree<>(w1);
		System.out.print(isSame(T, T1) + "\t");
		
		// test for maxDepth(), minDepth()
		System.out.print(maxDepth(T) + "\t");
		System.out.println(minDepth(T));
		
	}
    
}
