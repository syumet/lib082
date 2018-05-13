package algo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A simple definition for a binary tree node given by LeetCode.
 */
 class TreeNode {
	 int val;
	 TreeNode left;
	 TreeNode right;
	 TreeNode(int x) { val = x; }
 }

public class Tree<E> {

	/**
	 * LeetCode Problem #226
	 * See https://leetcode.com/problems/invert-binary-tree/description/ for details.
	 */
    public static TreeNode invert(TreeNode root) {
    	
        if (root == null) 
        	return null; 
		TreeNode left = root.left;
		TreeNode right = root.right;
		root.left = invert(right);
		root.right = invert(left);
		
		return root;
    }
    
	/**
	 * LeetCode Problem #100
	 * See https://leetcode.com/problems/same-tree/description/ for details.
	 */
    public static <E> Boolean isSame(TreeNode p, TreeNode q) {
    	
    	if (p == null && q == null)
    		return true;
    	if (p == null || q == null || p.val != q.val)
    		return false;
    	
    	return (isSame(p.left, q.left) && isSame(p.right, q.right));
    }
    
	/**
	 * LeetCode Problem #104
	 * See https://leetcode.com/problems/maximum-depth-of-binary-tree/description/ for details.
	 */
    public static <E> int maxDepth(TreeNode root) {
    	if (root == null) return 0;
    	return 1 + Integer.max(maxDepth(root.left), maxDepth(root.right) );
	}
    
	/**
	 * LeetCode Problem #111
	 * See https://leetcode.com/problems/minimum-depth-of-binary-tree/description/ for details.
	 */
    public static <E> int minDepth(TreeNode root) {
    	
    	if (root == null) return 0;
    	
    	Queue<TreeNode> T = new LinkedList<>();   	
    	Queue<Integer> D = new LinkedList<>();	
    	
    	T.add(root);
    	D.add(1);
    	TreeNode cur = T.poll();
    	int dep = D.poll();
    	
    	while (cur.left != null || cur.right != null) {
			if (cur.left != null) {
				T.add(cur.left);
				D.add(dep + 1);
			}
			if (cur.right != null) {
				T.add(cur.right);
				D.add(dep + 1);
			}	
			cur = T.poll();
			dep = D.poll();
		}
    	return dep;
	}
    
    public static boolean hasPathSum(TreeNode root, int sum) {
		
    	if (root.left == null && root.right == null) {
    		
    	}
    	
    	return false;
        
    }
      
    public static void main(String[] args) {
/*		
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
*/		
	}
    
}
