package ds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BinaryTreeTest {

  /**
   * Test method for {@link BinaryTree#size()}.
   */
  @Test
  public void testSize() {
    BinaryTree<Integer> t1 = new BinaryTree<>();
    assertEquals(0, t1.size());

    t1.setRootItem(9);
    assertEquals(1, t1.size());

    t1.setLeft(new BinaryTree<>(8));
    assertEquals(2, t1.size());

    t1.setRight(new BinaryTree<>(10));
    assertEquals(3, t1.size());

    Integer[] nums = {10, 20, 30, 40};
    BinaryTree<Integer> t2 = new BinaryTree<>(nums);
    assertEquals(4, t2.size());

    t1.getRight().setLeft(t2);
    assertEquals(7, t1.size());
  }

  /**
   * Test method for {@link BinaryTree#isEmpty()}.
   */
  @Test
  public void testIsEmpty() {
    BinaryTree<Integer> t1 = new BinaryTree<>();
    assertTrue(t1.isEmpty());

    t1.setRootItem(9);
    assertFalse(t1.isEmpty());

    t1.setRootItem(null);
    assertTrue(t1.isEmpty());

    Integer[] nums = {10, 20, 30, 40};
    BinaryTree<Integer> t2 = new BinaryTree<>(nums);
    assertFalse(t2.isEmpty());

    t2.clear();
    assertTrue(t2.isEmpty());
  }

  /**
   * Test method for {@link BinaryTree#clear()}.
   */
  @Test
  public void testClear() {
    BinaryTree<Integer> t1 = new BinaryTree<>();
    t1.clear();
    assertTrue(t1.isEmpty());

    Integer[] nums = {10, 20, 30, 40};
    BinaryTree<Integer> t2 = new BinaryTree<>(nums);
    t2.clear();
    assertEquals(0, t2.size());
    assertNull(t2.getRootItem());
    assertNull(t2.getLeft());
    assertNull(t2.getRight());
  }

  /**
   * Test method for {@link BinaryTree#BinaryTree(Object[])}.
   */
  @Test
  public void testBinaryTreeEArray() {
    Integer[] nums = {1, 2, 3};
    BinaryTree<Integer> t1 = new BinaryTree<>(nums);

    assertEquals(1, (int) t1.getRootItem());
    assertEquals(2, (int) t1.getLeft().getRootItem());
    assertEquals(3, (int) t1.getRight().getRootItem());
  }

  /**
   * Test method for {@link BinaryTree#iterator()}.
   */
  @Test
  public void testIterator() {
    Integer[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    BinaryTree<Integer> t1 = new BinaryTree<>(nums);

    int i = nums[0];
    for (int x : t1) {
      assertTrue(t1.contains(x));
      assertEquals(i, x);
      i++;
    }

    Integer[] odd = {1, 3, 5, 7};
    assertTrue(t1.containsAll(new BinaryTree<>(odd)));

    Integer[] even = {4, 6, 8, 10};
    assertFalse(t1.containsAll(new BinaryTree<>(even)));
  }

  /**
   * Test method for {@link BinaryTree#isLeaf()}.
   */
  @Test
  public void testIsLeaf() {
    BinaryTree<Integer> t1 = new BinaryTree<>();
    assertFalse(t1.isLeaf());

    t1.setRootItem(1);
    assertTrue(t1.isLeaf());

    Integer[] nums = {10, 20, 30, 40};
    BinaryTree<Integer> t2 = new BinaryTree<>(nums);

    assertFalse(t2.isLeaf());
    assertFalse(t2.getLeft().isLeaf());
    assertTrue(t2.getRight().isLeaf());
  }

  /**
   * Test method for {@link BinaryTree#invert()}.
   */
  @Test
  public void testInvert() {
    Character[] words = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    BinaryTree<Character> T = new BinaryTree<>(words);
    System.out.println(T.toString());
    T.invert();
    System.out.println(T.toString());
  }

  /**
   * Test method for {@link BinaryTree#equals(BinaryTree)} ()}.
   */
  @Test
  public void testEquals() {
    Integer[] nums = {10, 20, 30, 40};
    BinaryTree<Integer> t1 = new BinaryTree<>(nums);
    BinaryTree<Integer> t2 = new BinaryTree<>(nums);
    BinaryTree<Integer> t3 = new BinaryTree<>();
    assertTrue(t1.equals(t2));
    assertFalse(t1.equals(t3));
    nums[0] = 0;
    t3 = new BinaryTree<>(nums);
    assertFalse(t1.equals(t3));
  }

}