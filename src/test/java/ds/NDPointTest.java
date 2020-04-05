package ds;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NDPointTest {

  final Double[] arr123 = {1.0, 2.0, 3.0};
  final Double[] arr321 = {3.0, 2.0, 1.0};
  final NDPoint point5D0 = new NDPoint(5);

  NDPoint point123;
  NDPoint point321;

  @BeforeClass
  public static void setUpBeforeClass() {
  }

  @AfterClass
  public static void tearDownAfterClass() {
    System.out.println("\nUnit test completed.");
  }

  @Before
  public void setUp() {
    point123 = new NDPoint(arr123);
    point321 = new NDPoint(arr321);
  }

  @After
  public void tearDown() {
  }

  /**
   * Test method for {@link NDPoint#dim()}.
   */
  @Test
  public final void testDim() {
    assertEquals(5, point5D0.dim());

    try {
      point123 = new NDPoint(0);
    } catch (IllegalArgumentException e) {
      System.out.println("testDim: Exception catched");
    } finally {
      assertEquals(3, point123.dim());
    }
  }

//  /**
//   * Test method for {@link NDPoint#setPoint(Double[])}.
//   */
//  @Test
//  public final void testSetPoint() {
//    assertEquals(3, (int) point321.at(0));
//    assertEquals(2, (int) point321.at(1));
//    assertEquals(1, (int) point321.at(2));
//
//    Double[] empty = {};
//    try {
//      point321.setPoint(empty);
//    } catch (IllegalArgumentException e) {
//      System.out.println("testSetPoint: Exception1 catched");
//    } finally {
//      assertEquals(3, point321.dim());
//    }
//
//    Double[] arr2d = {30.0, 20.0};
//    point123.setPoint(arr2d);
//    assertEquals(30, (int) point123.at(0));
//    assertEquals(20, (int) point123.at(1));
//    try {
//      point123.at(2);
//    } catch (IllegalArgumentException e) {
//      System.out.println("testSetPoint: Exception2 catched");
//    } finally {
//      assertEquals(2, point123.dim());
//    }
//  }

  /**
   * Test method for {@link NDPoint#toString()}.
   */
  @Test
  public final void testToString() {
    assertEquals("(0.0, 0.0, 0.0, 0.0, 0.0)", point5D0.toString());
    assertEquals("(3.0, 2.0, 1.0)", point321.toString());
  }

  /**
   * Test method for {@link NDPoint#compareTo(NDPoint, int)}.
   */
  @Test
  public final void testCompareByDim() {
    for (int i = 0; i < 3; i++) {
      assertEquals(0, point321.compareTo(point321, i));
    }
    assertEquals(1, point321.compareTo(point123, 0));
    assertEquals(0, point321.compareTo(point123, 1));
    assertEquals(-1, point321.compareTo(point123, 2));

    try {
      point321.compareTo(point5D0, 0);
    } catch (Exception e) {
      System.out.println("testCompareByDim: Exception catched");
    }
  }

  /**
   * Test method for {@link NDPoint#compareTo(NDPoint)}.
   */
  @Test
  public final void testCompareTo() {
    // test compareTo() when the first coordinate is different
    assertEquals(1, point321.compareTo(point123));
    assertEquals(-1, point123.compareTo(point321));

    // test compareTo() when the third coordinate is different
    Double[] arr329 = {3.0, 2.0, 9.0};
    NDPoint point329 = new NDPoint(arr329);
    assertEquals(-1, point321.compareTo(point329));
    assertEquals(1, point329.compareTo(point321));
  }

  /**
   * Test method for {@link NDPoint#distanceL1(NDPoint)}.
   */
  @Test
  public final void testDistanceL1() {
    assertEquals(4, point123.distanceL1(point321), 1e-6);
    assertEquals(4, point321.distanceL1(point123), 1e-6);
    assertEquals(6, point321.norm(1), 1e-6);
    assertEquals(6, point123.norm(1), 1e-6);
    assertEquals(0, point5D0.norm(1), 1e-6);

    try {
      point321.distanceL1(point5D0);
    } catch (Exception e) {
      System.out.println("testDistanceL1: Exception catched");
    }
  }

  /**
   * Test method for {@link NDPoint#distanceL2(NDPoint)}.
   */
  @Test
  public final void testDistanceL2() {
    assertEquals(2.828427, point123.distanceL2(point321), 1e-6);
    assertEquals(2.828427, point321.distanceL2(point123), 1e-6);
    assertEquals(3.741657, point321.norm(2), 1e-6);
    assertEquals(3.741657, point123.norm(2), 1e-6);
    assertEquals(0, point5D0.norm(2), 1e-6);

    try {
      point321.distanceL2(point5D0);
    } catch (Exception e) {
      System.out.println("testDistanceL2: Exception catched");
    }
  }

}