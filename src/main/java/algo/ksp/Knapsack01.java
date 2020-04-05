package algo.ksp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * A non-public class that defines a Knapsack item.
 */
class KnapsackItem extends Sack implements Comparable<KnapsackItem> {

  protected Double value;

  public KnapsackItem(Double v, Double w) {
    super(Grain.WHEAT, w);
    value = v;
  }

  /**
   * Compare by the value density (value / weight)
   */
  @Override
  public int compareTo(KnapsackItem o) {
    Double den1 = this.value / this.weight;
    Double den2 = o.value / o.weight;
    return den1.compareTo(den2);
  }

  /**
   * @return the value
   */
  public Double getValue() {
    return value;
  }

}

/**
 * A non-public class that stores an instance of Knapsack.
 */
class KnapsackInstance {

  /**
   * The number of items in the problem instance.
   */
  protected Integer numItems;
  /**
   * The capacity of the knapsack in the problem instance.
   */
  protected Double W;
  /**
   * The items to be considered.
   */
  KnapsackItem[] items;
  /**
   * current weight of the knapsack
   */
  double curWeight;

  /**
   * current value of the items in the knapsack
   */
  double curValue;

  /**
   * best value achieved so far
   */
  double bestValue;

  /**
   * Initialize a knapsack instance.
   *
   * @param numItems Number of items in the problem instance
   * @param W        Capacity of the backpack.
   */
  KnapsackInstance(Integer numItems, Double W) {
    this.numItems = numItems;
    this.W = W;
    items = new KnapsackItem[this.numItems];
    curWeight = 0;
    curValue = 0;
    bestValue = 0;
  }

  /**
   * @return The number of items in the problem instance.
   */
  public Integer numItems() {
    return this.numItems;
  }

  /**
   * Set the value and weight of the id-th item.
   */
  public void setItem(Double value, Double weight, Integer id) {
    this.items[id] = new KnapsackItem(value, weight);
  }

  /**
   * Obtain an item's value
   */
  public Double value(int i) {
    return this.items[i].getValue();
  }

  /**
   * Obtain and item's weight
   */
  public Double weight(int i) {
    return this.items[i].getWeight();
  }

  /**
   * Obtain the knapsack's capacity
   */
  public Double capacity() {
    return this.W;
  }

  /**
   * Obtain the array of items.
   */
  public KnapsackItem[] items() {
    return this.items;
  }

  /**
   * Printable representation of the problem instance.
   */
  public String toString() {
    StringBuilder result = new StringBuilder();

    result.append("W = ").append(this.W).append("\n");
    for (int i = 0; i < this.numItems; i++) {
      result.append(this.items[i].getValue())
          .append(", ")
          .append(this.items[i].getWeight())
          .append("\n");
    }
    return result.toString();
  }

}

public class Knapsack01 {

  public static KnapsackInstance readKnapsackInstance(String filename) {

    Scanner infile = null;
    try {
      infile = new Scanner(new File(filename));
    } catch (FileNotFoundException e) {
      System.out.println("Error: " + filename + " could not be opened.");
    }

    // Try to read the knapsack capacity and the number of items.
    if (!infile.hasNextDouble()) {
      throw new RuntimeException("Error: expected knapsack weight as a real number");
    }
    Double W = infile.nextDouble();

    if (!infile.hasNextInt()) {
      throw new RuntimeException("Error: expected integer number of items.");
    }
    int numItems = infile.nextInt();

    // Create a knapsack instance for the given number of items.
    KnapsackInstance K = new KnapsackInstance(numItems, W);

    // Read each value-weight pair.
    for (int i = 0; i < numItems; i++) {
      if (!infile.hasNextDouble()) {
        throw new RuntimeException("Error: expected a value while reading item " + i + ".");
      }
      Double v = infile.nextDouble();
      if (!infile.hasNextDouble()) {
        throw new RuntimeException("Error: expected a weight while reading item " + i + ".");
      }
      Double w = infile.nextDouble();

      // Store the value-weight pair in the problem instance.
      K.setItem(v, w, i);
    }

    infile.close();

    return K;
  }

  /**
   * Backtracking solution to Knapsack
   *
   * @param K an instance of knapsack
   * @return total value of the items that were packed in the knapsack
   */
  public static double backTrackingSol(KnapsackInstance K) {
//		sortByDens(K.items());
    Arrays.sort(K.items(), Collections.reverseOrder());
    backTrack(K, 0);
    return K.bestValue;
  }

  /**
   * @param K an instance of knapsack
   * @param i index of the current item to be considered
   * @return the total value of the best plan so far
   */
  static void backTrack(KnapsackInstance K, int i) {
    if (i >= K.numItems()) {
      K.bestValue = K.curValue;
      return;
    }
    if (K.curWeight + K.weight(i) <= K.capacity()) {
      K.curWeight += K.weight(i);
      K.curValue += K.value(i);
      backTrack(K, i + 1);
      // backtracking step
      K.curWeight -= K.weight(i);
      K.curValue -= K.value(i);
    }
    if (K.curValue + boundVal(K, i + 1) > K.bestValue) // pruning
    {
      backTrack(K, i + 1);
    }
  }

  /**
   * <b>Note:</b> Assuming K.items has been sorted in descending order
   *
   * @param K an instance of knapsack
   * @param i the start index of item
   * @return the upper bound of the value can be gained
   */
  private static double boundVal(KnapsackInstance K, int i) {
    double leftCap = K.capacity() - K.curWeight;
    double leftVal = 0;
    while (i < K.numItems() && K.weight(i) <= leftCap) {
      leftCap -= K.weight(i);
      leftVal += K.value(i);
      i++;
    }
    if (i < K.numItems()) {
      leftVal += K.value(i) / K.weight(i) * leftCap;
    }
    return leftVal;
  }

  /**
   * Bubble sort the items by their value density (value / weight) in descending order
   *
   * @param items an array of items to be sorted
   */
  static void sortByDens(KnapsackItem[] items) {
    for (int i = 0; i < items.length; i++) {
      boolean madeSwap = false;
      for (int j = 1; j < items.length; j++) {
        if (items[j - 1].getValue() / items[j - 1].getWeight() <
            items[j].getValue() / items[j].getWeight()) {
          KnapsackItem temp = items[j - 1];
          items[j - 1] = items[j];
          items[j] = temp;
          madeSwap = true;
        }
      }
      if (!madeSwap) {
        return;
      }
    }
  }

  /**
   * Greedy solution to Knapsack
   *
   * @param K an instance of knapsack
   * @return total value of the items that were packed in the knapsack
   */
  public static double greedySol(KnapsackInstance K) {
//		sortByDens(K.items());
    Arrays.sort(K.items(), Collections.reverseOrder());
    double leftCap = K.capacity();
    double total = 0;
    int i = 0;
    while (i < K.numItems() && leftCap > 0) {
      if (K.weight(i) <= leftCap) {
        leftCap -= K.weight(i);
        total += K.value(i);
      }
      i++;
    }
    return total;
//		return stochasticGreedy(K, 0, 0, 0, 0);
  }

  // Greedy algorithm with stochastic feature, jump out of local optimum by luck!
  static double stochasticGreedy(KnapsackInstance K, double bv, double cv, double cw, int i) {
    if (i >= K.numItems()) {
      return cv;
    }
    if (cw + K.weight(i) <= K.capacity()) {
      bv = stochasticGreedy(K, bv, cv + K.value(i), cw + K.weight(i), i + 1);
    } else {
      bv = stochasticGreedy(K, bv, cv, cw, i + 1);
    }
    // apparently works good with items <= 50
    if (new Random().nextDouble() > 2 * (double) i / K.numItems()) {
      bv = Math.max(bv, stochasticGreedy(K, bv, cv, cw, i + 1));
    }
    return bv;
  }

  public static void main(String[] args) {
    KnapsackInstance K = readKnapsackInstance("data/Knapsack-Template/knapsack-1000.dat");
    KnapsackInstance K2 = new KnapsackInstance(3, 5.0);
    K2.setItem(10.0, 1.0, 0);
    K2.setItem(15.0, 2.0, 1);
    K2.setItem(20.0, 3.0, 2);

//		System.out.println(K);

    // Solve the knapsack instance K here.
    long startTime = System.currentTimeMillis();

    System.out.println(backTrackingSol(K));
//		System.out.println(greedySol(K));

    long endTime = System.currentTimeMillis();
    System.out.println("Took " + (endTime - startTime) + " ms");
  }
}
