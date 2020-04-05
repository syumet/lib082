package algo;

import ds.NDPoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Sort {

  /**
   * Check whether an iterable collection is sorted.
   *
   * @param input an iterable collection, usually a list.
   * @return true if input is already sorted, false otherwise.
   */
  public static <T extends Comparable<? super T>> boolean isSorted(Iterable<T> input) {
    Iterator<T> iter = input.iterator();
    if (!iter.hasNext()) {
      return true;
    }
    T t = iter.next();
    while (iter.hasNext()) {
      T t2 = iter.next();
      if (t.compareTo(t2) > 0) {
        return false;
      }
      t = t2;
    }
    return true;
  }

  /**
   * @param inVec array in which partitioning is to take place
   * @param left  starting offset in inVec of the sub-array to be partitioned
   * @param right ending offset in inVec of the sub-array to be partitioned
   * @return the offset at which the pivot element ended up
   */
  private static <T extends Comparable<? super T>> int partition(T[] inVec, int left,
      int right) {
    // take the element in the middle as pivot
    swap(inVec, right, (left + right) / 2);
    // now right is the pivot
    int swapOffset = left;

    for (int i = left; i < right; i++) {
      if (inVec[i].compareTo(inVec[right]) < 0) {
        swap(inVec, i, swapOffset);
        swapOffset++;
      }
    }
    swap(inVec, right, swapOffset);
    return swapOffset;
  }

  /**
   * @param left  lower limit on sub-array to be partitioned
   * @param right limit on sub-array to be partitioned
   * @param dim   the dimension to compare
   * @return the offset at which the pivot element ended up
   */
  private static int partition(NDPoint[] inVec, int left, int right, int dim) {
    double pivot = inVec[right].at(dim);
    int swapOffset = left;

    for (int i = left; i < right; i++) {
      if (inVec[i].at(dim) <= pivot) {
        swap(inVec, i, swapOffset);
        swapOffset++;
      }
    }
    swap(inVec, right, swapOffset);
    return swapOffset;
  }

  /**
   * Place the j-th smallest item at j-th entry of the input array after shuffling.
   *
   * @param inVec array in which shuffling is to take place
   * @param j     we want to find the element that belongs at array index j
   * @throws IllegalArgumentException if j is greater or equal to the size of input array
   */
  protected static <T extends Comparable<? super T>> void jSmallest(T[] inVec, int j) {
    if (j >= inVec.length) {
      throw new IllegalArgumentException("Requested rank exceeds size of array.");
    }
    jSmallest(inVec, 0, inVec.length - 1, j);
  }

  /**
   * Find the j-th smallest element in an unordered array.
   *
   * @param inVec input array
   * @param j     we want to find the j-th smallest element
   * @throws IllegalArgumentException if j is greater or equal to the size of input array
   */
  public static <T extends Comparable<? super T>> T quickSelect(T[] inVec, int j) {
    T[] tmp = inVec.clone();
    Sort.jSmallest(tmp, j);
    return tmp[j];
  }

  /**
   * @param inVec array of NDPoints
   * @param left  offset of start of sub-array for which we want the median element
   * @param right offset of end of sub-array for which we want the median element
   * @param j     we want to find the element that belongs at array index j
   */
  private static <T extends Comparable<? super T>> void jSmallest(T[] inVec, int left,
      int right, int j) {
    if (right > left) {
      int pivotIndex = partition(inVec, left, right);
      if (j < pivotIndex) {
        jSmallest(inVec, left, pivotIndex - 1, j);
      } else if (j > pivotIndex) {
        jSmallest(inVec, pivotIndex + 1, right, j);
      }
    }
  }

  /**
   * @param inVec array of NDPoints
   * @param left  offset of start of sub-array for which we want the median element
   * @param right offset of end of sub-array for which we want the median element
   * @param j     we want to find the element that belongs at array index j
   * @param dim   the dimension to compare
   */
  private static void jSmallest(NDPoint[] inVec, int left, int right, int j, int dim) {
    if (right > left) {
      int pivotIndex = partition(inVec, left, right, dim);
      if (j < pivotIndex) {
        jSmallest(inVec, left, pivotIndex - 1, j, dim);
      } else if (j > pivotIndex) {
        jSmallest(inVec, pivotIndex + 1, right, j, dim);
      }
    }
  }

  /**
   * Quick - sort the entire array
   *
   * @param inVec array in which sorting is to take place
   */
  public static <T extends Comparable<? super T>> void quickSort(T[] inVec) {
    sort(inVec, 0, inVec.length - 1, 0);
  }

  /**
   * @param inVec array in which sorting is to take place
   * @param left  starting offset in inVec of the sub-array to be sorted
   * @param right ending offset in inVec of the sub-array to be sorted
   * @param smLim critical value to determine a small array
   */
  private static <T extends Comparable<? super T>> void sort
  (T[] inVec, int left, int right, int smLim) {
    if (left < right) {
      int pivotOffset = partition(inVec, left, right);
      if (pivotOffset - left > smLim) {
        sort(inVec, left, pivotOffset - 1, smLim);
      } else {
        insertionSort(inVec, left, pivotOffset - 1);
      }
      if (right - pivotOffset > smLim) {
        sort(inVec, pivotOffset + 1, right, smLim);
      } else {
        insertionSort(inVec, pivotOffset + 1, right);
      }
    }
  }

  public static <T extends Comparable<? super T>> void sort(T[] inVec) {
    sort(inVec, 0, inVec.length - 1, 8);
  }

  public static <T extends Comparable<? super T>> void insertionSort(T[] inVec) {
    insertionSort(inVec, 0, inVec.length - 1);
  }

  private static <T extends Comparable<? super T>> void insertionSort(T[] inVec, int start,
      int end) {
    for (int i = start + 1; i <= end; i++) {
      T curValue = inVec[i];
      int j = i;
      while (j > start && inVec[j - 1].compareTo(curValue) > 0) {
        inVec[j] = inVec[j - 1];
        j--;
      }
      inVec[j] = curValue;
    }
  }

  public static <T extends Comparable<? super T>> void insertionSort(ArrayList<T> inVec) {
    for (int i = 1; i < inVec.size(); i++) {
      T curValue = inVec.get(i);
      int j = i;
      while (j > 0 && inVec.get(j - 1).compareTo(curValue) > 0) {
        inVec.set(j, inVec.get(j - 1));
        j--;
      }
      inVec.set(j, curValue);
    }
  }

  /**
   * The given array of strings would be in lexicographic order by MSD Radix Sort
   *
   * @param S an array of strings to be sorted
   */
  public static void radixSortMSD(String[] S) {
    ArrayList<String> SList = new ArrayList<>(Arrays.asList(S));
    sortByDigit(SList, 0);
    for (int i = 0; i < S.length; i++) {
      S[i] = SList.get(i);
    }
  }

  /**
   * Helper function for radixSortMSD
   *
   * @param listOfString strings to be sorted
   * @param i            digit on which to partition -- i = 0 is the left - most digit
   * @return a list of sorted strings in lexicographic
   */
  private static void sortByDigit(ArrayList<String> listOfString, int i) {
    if (listOfString.size() < 26) {
      Sort.insertionSort(listOfString);
      return;
    }
    // 96 slots represent ASCII code 32 (space) to 126 (~), with slot 0 reserved.
    @SuppressWarnings("unchecked")
    ArrayList<String>[] L = new ArrayList[96];
    for (int k = 0; k < 96; k++) {
      L[k] = new ArrayList<>();
    }
    for (String s : listOfString) {
      // if s has no character at i'th position
      if (i >= s.length()) {
        L[0].add(s);
      } else {
        L[s.charAt(i) - 31].add(s);
      }
    }
    // reset the list, items in L[0] are identical (already sorted)
    listOfString.clear();
    listOfString.addAll(L[0]);
    for (int k = 1; k < 96; k++) {
      // if there is another digit to consider
      if (L[k].size() > 1) {
        sortByDigit(L[k], i + 1);
      }
      // append the sorted sub-list
      listOfString.addAll(L[k]);
    }
  }

  private static <T extends Comparable<? super T>> void swap(T[] inVec, int idx1, int idx2) {
    T temp = inVec[idx1];
    inVec[idx1] = inVec[idx2];
    inVec[idx2] = temp;
  }
}
