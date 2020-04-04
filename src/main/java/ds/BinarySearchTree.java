package ds;

import java.util.ArrayList;
import algo.Sort;

public class BinarySearchTree<E> extends BinaryTree<E> {


	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected boolean isValidBST() {
		ArrayList bfs = this.traverseLevelOrder();
		return Sort.isSorted(bfs);
	}

}
