package algo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SortTest {
	
	private Integer[] intArray100K;
	private Integer[] intArray10K;
	private String[] words235K;
	private String[] words10K;
	private ArrayList<Integer> arrList;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\nUnit test completed.");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		arrList = new ArrayList<>();
		
		// Reading random integer data-set 1 
		int numInts = 100000;
		intArray100K = new Integer[numInts];

		Scanner input = null;
		String inputFile = "data/Sort-Template/unif_int_100000_r.txt";
		try {
			input = new Scanner(new File(inputFile));
			for (int i = 0; i < numInts; i++)
				intArray100K[i] = input.nextInt();
			input.close();
			//System.out.println("Done reading " + numInts + " integers.");
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + inputFile + " was not found.");
		}
		assertFalse(Sort.isSorted(Arrays.asList(intArray100K)));

		// Reading random integer data-set 2
		numInts = 10000;
		intArray10K = new Integer[numInts];
		inputFile = "data/Sort-Template/unif_int_10000.txt";
		try {
			input = new Scanner(new File(inputFile));
			for (int i = 0; i < numInts; i++)
				intArray10K[i] = input.nextInt();
			input.close();
			//System.out.println("Done reading " + numInts + " integers.");
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + inputFile + " was not found.");
		}
		assertFalse(Sort.isSorted(Arrays.asList(intArray10K)));
		
		// Reading words collection data-set 1
		inputFile = "data/Sort-Template/words-10000.txt";
		try {
			input = new Scanner(new File(inputFile));  
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + inputFile + " was not found.");
		}
		
		// Read the first line of the file and convert it to an integer to see how many
		// words are in the file.
		int numWords = Integer.valueOf(input.nextLine());
		words10K = new String[numWords];
		
		// Read each word from the input file and store it in the next free element of 
		// the items array.
		int j = 0;
		while(input.hasNextLine()) {
			words10K[j++] = input.nextLine().toUpperCase();
		}
		input.close();
		//System.out.println("Done reading " + numWords + " words.");
		assertFalse(Sort.isSorted(Arrays.asList(words10K)));
		
		// Reading words collection data-set 2
		inputFile = "data/Sort-Template/words-235884.txt";
		try {
			input = new Scanner(new File(inputFile));  
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + inputFile + " was not found.");
			return;
		}

		numWords = Integer.valueOf(input.nextLine());
		words235K = new String[numWords];
		
		j = 0;
		while(input.hasNextLine()) {
			words235K[j++] = input.nextLine().toUpperCase();
		}
		input.close();
		//System.out.println("Done reading " + numWords + " words.");
		assertFalse(Sort.isSorted(Arrays.asList(words235K)));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link algo.Sort#isSorted(java.lang.Iterable)}.
	 */
	@Test
	public final void testIsSorted() {
		assertTrue(Sort.isSorted(arrList));
		arrList.add(3);
		assertTrue(Sort.isSorted(arrList));
		arrList.add(5);
		assertTrue(Sort.isSorted(arrList));
		arrList.add(1);
		assertFalse(Sort.isSorted(arrList));
		arrList.add(2);
		assertFalse(Sort.isSorted(arrList));
	}

	/**
	 * Test method for {@link algo.Sort#jSmallest(T[], int)}.
	 */
	@Test
	public final void testJSmallest() {
		Integer[] intArray10KSorted = intArray10K.clone();
		Sort.sort(intArray10KSorted);
		for (int j = 0; j < 10000; j++) {
			Sort.jSmallest(intArray10K, j);
			assertEquals(intArray10KSorted[j], intArray10K[j]);
		}
	}
	
	/**
	 * Test method for {@link algo.Sort#quickSelect(T[], int)}.
	 */
	@Test
	public final void testQuickSelect() {
		Integer[] intArray10KSorted = intArray10K.clone();
		Integer[] intArray10KOrigin = intArray10K.clone();
		Sort.sort(intArray10KSorted);
		for (int j = 0; j < 10000; j++) {
			assertEquals(intArray10KSorted[j], Sort.quickSelect(intArray10K, j));
			assertEquals(intArray10KOrigin[j], intArray10K[j]);
		}
	}

	/**
	 * Test method for {@link algo.Sort#quickSort(T[])}.
	 */
	@Test
	public final void testQuickSort() {
		long startTime = System.currentTimeMillis();		
		Sort.quickSort(intArray100K);
		long endTime = System.currentTimeMillis();
		System.out.println("Quick Sort took " + (endTime - startTime) 
			+ " ms to sort " + intArray100K.length + " integers.");
		assertTrue(Sort.isSorted(Arrays.asList(intArray100K)));
		
		startTime = System.currentTimeMillis();		
		Sort.quickSort(words235K);
		endTime = System.currentTimeMillis();
		System.out.println("Quick Sort took " + (endTime - startTime) 
			+ " ms to sort " + words235K.length + " strings.");
		assertTrue(Sort.isSorted(Arrays.asList(words235K)));
	}

	/**
	 * Test method for {@link algo.Sort#sort(T[])}.
	 */
	@Test
	public final void testSort() {
		long startTime = System.currentTimeMillis();		
		Sort.sort(intArray100K);
		long endTime = System.currentTimeMillis();
		System.out.println("Hybrid Sort took " + (endTime - startTime) 
			+ " ms to sort " + intArray100K.length + " integers.");
		assertTrue(Sort.isSorted(Arrays.asList(intArray100K)));
		
		startTime = System.currentTimeMillis();		
		Sort.sort(words235K);
		endTime = System.currentTimeMillis();
		System.out.println("Hybrid Sort took " + (endTime - startTime) 
			+ " ms to sort " + words235K.length + " strings.");
		assertTrue(Sort.isSorted(Arrays.asList(words235K)));
	}

	/**
	 * Test method for {@link algo.Sort#insertionSort(T[])}.
	 */
	@Test
	public final void testInsertionSortTArray() {
		long startTime = System.currentTimeMillis();		
		Sort.insertionSort(intArray10K);
		long endTime = System.currentTimeMillis();
		System.out.println("Insertion Sort took " + (endTime - startTime) 
			+ " ms to sort " + intArray10K.length + " integers (Array).");
		assertTrue(Sort.isSorted(Arrays.asList(intArray10K)));
		
		startTime = System.currentTimeMillis();		
		Sort.insertionSort(words10K);
		endTime = System.currentTimeMillis();
		System.out.println("Insertion Sort took " + (endTime - startTime) 
			+ " ms to sort " + words10K.length + " strings (Array).");
		assertTrue(Sort.isSorted(Arrays.asList(words10K)));
	}

	/**
	 * Test method for {@link algo.Sort#insertionSort(java.util.ArrayList)}.
	 */
	@Test
	public final void testInsertionSortArrayListOfT() {
		
		Sort.insertionSort(arrList); 
		
		ArrayList<Integer> intArrayList10K = new ArrayList<>(Arrays.asList(intArray10K));
		long startTime = System.currentTimeMillis();		
		Sort.insertionSort(intArrayList10K); 
		long endTime = System.currentTimeMillis();
		System.out.println("Insertion Sort took " + (endTime - startTime) 
			+ " ms to sort " + intArray10K.length + " integers (ArrayList).");
		assertTrue(Sort.isSorted(intArrayList10K));
		
		ArrayList<String> wordsList10K = new ArrayList<>(Arrays.asList(words10K));
		startTime = System.currentTimeMillis();		
		Sort.insertionSort(wordsList10K);
		endTime = System.currentTimeMillis();
		System.out.println("Insertion Sort took " + (endTime - startTime) 
			+ " ms to sort " + words10K.length + " strings (ArrayList).");
		assertTrue(Sort.isSorted(wordsList10K));
	}

	/**
	 * Test method for {@link algo.Sort#radixSortMSD(java.lang.String[], java.util.HashMap)}.
	 */
	@Test
	public final void testRadixSortMSD() {
//		long startTime = System.currentTimeMillis();		
//		Sort.radixSortMSD(intArray);
//		long endTime = System.currentTimeMillis();
//		System.out.println("Radix Sort took " + (endTime - startTime) 
//			+ " ms to sort " + intArray.length + " integers.");
//		assertTrue(Sort.isSorted(Arrays.asList(intArray)));
		
		long startTime = System.currentTimeMillis();		
		Sort.radixSortMSD(words235K);
		long endTime = System.currentTimeMillis();
		System.out.println("Radix Sort took " + (endTime - startTime) 
			+ " ms to sort " + words235K.length + " strings.");
		assertTrue(Sort.isSorted(Arrays.asList(words235K)));
	}

}
