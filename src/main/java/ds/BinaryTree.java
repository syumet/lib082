package ds;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<E> extends AbstractCollection<E> implements Tree<E> {

  protected E val;
  protected BinaryTree<E> left;
  protected BinaryTree<E> right;

  public BinaryTree() {
  }

  public BinaryTree(E val) {
    this.val = val;
  }

  public BinaryTree(E[] items) {
    if (items.length == 0) {
      return;
    }
    BinaryTree<E> root = span(items, 1);
    if (root != null) {
      val = root.val;
      left = root.left;
      right = root.right;
    }
  }

  public static void main(String[] args) {

  }

  private BinaryTree<E> span(E[] items, int i) {
    if (items[i - 1] == null) {
      return null;
    }
    BinaryTree<E> newTree = new BinaryTree<>();
    newTree.val = items[i - 1];
    if (i * 2 <= items.length) {
      newTree.left = span(items, i * 2);
    }
    if (i * 2 < items.length) {
      newTree.right = span(items, i * 2 + 1);
    }
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

  @Override
  public int size() {
    if (isEmpty()) {
      return 0;
    }
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
   * @see <a href="https://leetcode.com/problems/invert-binary-tree/">LeetCode #226</a>
   */
  public void invert() {
    if (this.left != null) {
      this.left.invert();
    }
    if (this.right != null) {
      this.right.invert();
    }

    BinaryTree<E> tmp = this.left;
    this.left = this.right;
    this.right = tmp;
  }

  /**
   * @see <a href="https://leetcode.com/problems/same-tree/">LeetCode #100</a>
   */
  public final boolean equals(BinaryTree<E> t) {

    if (t == null || t.val != this.val) {
      return false;
    }

    boolean leq = true, req = true;

    if (this.left != null) {
      leq = this.left.equals(t.left);
    } else if (t.left != null) {
      return false;
    }

    if (this.right != null) {
      req = this.right.equals(t.right);
    } else if (t.right != null) {
      return false;
    }

    return leq && req;
  }

  /**
   * @see <a href="https://leetcode.com/problems/maximum-depth-of-binary-tree/">LeetCode #104</a>
   */
  public int maxDepth() {
    if (this.isEmpty()) {
      return 0;
    }

    int leftDepth = 0, rightDepth = 0;
    if (this.left != null) {
      leftDepth = this.left.maxDepth();
    }
    if (this.right != null) {
      rightDepth = this.right.maxDepth();
    }
    return 1 + Integer.max(leftDepth, rightDepth);
  }

  /**
   * @see <a href="https://leetcode.com/problems/minimum-depth-of-binary-tree/">LeetCode #111</a>
   */
  public int minDepth() {

    if (this.isEmpty()) {
      return 0;
    }

    Queue<BinaryTree<E>> bfs = new LinkedList<>();
    Queue<Integer> depth = new LinkedList<>();

    bfs.add(this);
    depth.add(1);
    BinaryTree<E> cur = bfs.poll();
    int dep = depth.poll();

    while (!cur.isLeaf()) {
      if (cur.left != null) {
        bfs.add(cur.left);
        depth.add(dep + 1);
      }
      if (cur.right != null) {
        bfs.add(cur.right);
        depth.add(dep + 1);
      }
      cur = bfs.poll();
      dep = depth.poll();
    }
    return dep;
  }

  public ArrayList<E> traverseLevelOrder() {
    return new ArrayList<>(this);
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
      if (cur.left != null) {
        Q.add(cur.left);
      }
      if (cur.right != null) {
        Q.add(cur.right);
      }
      return cur.val;
    }
  }

}
