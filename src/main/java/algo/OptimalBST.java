package algo;

import ds.BinaryTree;
import java.text.DecimalFormat;

public class OptimalBST {

  final int n;
  final char[] words;

  /**
   * The upper triangular part (including diagonal entries) of the cost matrix is used to record the
   * cost of the optimal tree, i.e. cost[a][b] = minimum cost starting from a to b. The lower
   * triangular part of the cost matrix is used to record the root of the optimal tree. i.e.
   * cost[b][a] = position of root of the tree starting from a to b.
   */
  final double[][] cost;

  /**
   * @param inpWords An array of characters to construct the optimal binary tree.
   * @param freq     The numerical probabilities associated with the input characters.
   */
  public OptimalBST(char[] inpWords, double[] freq) {
    words = inpWords;
    n = words.length;
    cost = new double[n][n];
    for (int i = 0; i < n; i++) {
      cost[i][i] = freq[i];
    }
    this.calCost();
  }

  public static void main(String[] args) {
    char[] words = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    double[] freq = {0.05, 0.15, 0.12, 0.08, 0.02, 0.18, 0.04, 0.07, 0.16, 0.13};
    OptimalBST C = new OptimalBST(words, freq);
    System.out.print(C);
    System.out.print(C.generate());
  }

  private void calCost() {

    // i is the distance from left to right
    for (int i = 1; i < n; i++) {

      // j is the position of starting letter
      for (int j = 0; j + i < n; j++) {

        // search for the minimum sub cost, k is the position of the root
        int root = j; // the best root so far
        double minSubCost = cost[j + 1][j + i];
        double deepCost = cost[j][j]; // sum of cost from left to right

        int k = j + 1;
        while (k < j + i) {
          if (cost[j][k - 1] + cost[k + 1][j + i] < minSubCost) {
            minSubCost = cost[j][k - 1] + cost[k + 1][j + i];
            root = k;
          }
          deepCost += cost[k][k];
          k++;
        }
        // when k reaches the last letter
        if (cost[j][k - 1] < minSubCost) {
          minSubCost = cost[j][k - 1];
          root = k;
        }
        deepCost += cost[k][k];
        // update the cost
        cost[j][j + i] = minSubCost + deepCost;
        // update the root
        cost[j + i][j] = root;
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    DecimalFormat df = new DecimalFormat("#.##");
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        double c = cost[i][j];
        s.append(Double.valueOf(df.format(c)))
            .append("|")
            .append(words[(int) cost[j][i]])
            .append(", ");
      }
      s.append("\n");
    }
    return s.toString();
  }

  /**
   * @return the optimal binary search tree.
   */
  public BinaryTree<Character> generate() {
    return generate(0, n - 1);
  }

  private BinaryTree<Character> generate(int lt, int rt) {
    BinaryTree<Character> t = new BinaryTree<>();
    if (lt == rt) {
      t.setRootItem(words[lt]);
      return t;
    }
    int r = (int) cost[rt][lt];
    t.setRootItem(words[r]);
    if (r - 1 >= lt) {
      BinaryTree<Character> lc = generate(lt, r - 1);
      t.setLeft(lc);
    }
    if (r + 1 <= rt) {
      BinaryTree<Character> rc = generate(r + 1, rt);
      t.setRight(rc);
    }
    return t;
  }
}
