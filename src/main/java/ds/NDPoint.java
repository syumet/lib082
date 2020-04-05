package ds;

/**
 * N-Dimensional Point
 */
public class NDPoint implements Comparable<NDPoint> {

  private Double[] coords;
  private int dim;

  /**
   * Create a new N-dimensional point at the origin (0,0,..., 0).
   *
   * @param dim Dimensionality of the point.
   */
  public NDPoint(int dim) {
    if (dim < 1) {
      throw new IllegalArgumentException("NDPoint: dimension of point must be at least 1.");
    }
    this.dim = dim;
    coords = new Double[dim];
    for (int i = 0; i < dim; i++) {
      coords[i] = 0.0;
    }
  }

  /**
   * Create a point from a coordinate in an array of Double.  The dimensionality of the point is
   * equal to the length of pt.
   *
   * @param pt Coordinates of the new point.
   */
  public NDPoint(Double[] pt) {
    this.dim = pt.length;
    if (this.dim < 1) {
      throw new IllegalArgumentException("NDPoint: dimension of point must be at least 1.");
    }
    coords = pt;
    this.copyArray(pt);
  }

  /**
   * Create a point from a coordinate in array of double.  The dimensionality of the point is equal
   * to the length of pt.
   *
   * @param pt Coordinates of the new point.
   */
  public NDPoint(double[] pt) {
    this.dim = pt.length;
    if (this.dim < 1) {
      throw new IllegalArgumentException("NDPoint: dimension of point must be at least 1.");
    }
    coords = new Double[this.dim];
    this.copyArray(pt);
  }

  public static void main(String[] args) {
  }

  /**
   * Use the coordinates in the given array for this point.
   *
   * @param pt New coordinates for this point.
   * @throws IllegalArgumentException if the length of pt is not equal to the dimensionality of this
   *                                  N-dimensional point.
   */
  private void copyArray(Double[] pt) {
    if (this.dim() != pt.length) {
      throw new IllegalArgumentException(
          "Array length must equal point dimensionality (" + this.dim + ")");
    }
    for (int i = 0; i < this.dim; i++) {
      coords[i] = pt[i];
    }
  }

  /**
   * Use the coordinates in the given array for this point.
   *
   * @param pt New coordinates for this point.
   * @throws IllegalArgumentException if the length of pt is not equal to the dimensionality of this
   *                                  N-dimensional point.
   */
  private void copyArray(double[] pt) {
    if (this.dim() != pt.length) {
      throw new IllegalArgumentException(
          "Array length must equal point dimensionality (" + this.dim + ")");
    }
    for (int i = 0; i < this.dim; i++) {
      coords[i] = pt[i];
    }
  }

  /**
   * Get the dimensionality of the point.
   *
   * @return The dimensionality of the point.
   */
  public int dim() {
    return this.dim;
  }

  /**
   * Convert the point object to array.
   *
   * @return An array of the coordinates of this N-dimensional point.
   */
  public Double[] toArray() {
    return this.coords;
  }

//  /**
//   * Set the coordinates of this point.
//   *
//   * @param pt The point to store represented as an array of double.
//   * @throws IllegalArgumentException if length of pt is less than 1.
//   */
//  void setPoint(Double[] pt) {
//    if (this.dim != pt.length) {
//      if (pt.length < 1) {
//        throw new IllegalArgumentException(
//            "NDPoint: dimension of point must be at least 1.");
//      }
//      this.dim = pt.length;
//      coords = new Double[this.dim];
//    }
//    this.coords = pt;
//    this.copyArray(pt);
//  }

  /**
   * Obtain the i-th coordinate of the point.
   *
   * @param i Desired coordinate of the point.
   * @return The value of the i-th coordinate of the point.
   * @throws IllegalArgumentException if i is not a valid dimension index for this n-dimensional
   *                                  point (i.e. if i >= n)
   */
  public double at(int i) {
    if (i >= this.dim) {
      throw new IllegalArgumentException(
          "Requested coordinate index exceeds point dimensionality.");
    }
    return coords[i];
  }

  @Override
  public String toString() {
    StringBuilder out = new StringBuilder("(" + coords[0]);
    for (int i = 1; i < this.dim; i++) {
      out.append(", ").append(coords[i]);
    }
    out.append(")");
    return out.toString();
  }

  /**
   * Compares the i-th coordinate of two NDPoint objects.
   *
   * @param di    Index of the coordinate to compare.
   * @param other The point to which to compare this point.
   * @return -1 if the i-th coordinate of this point is smaller than that of 'other'; 0 if the i-th
   * coordinate of this point and 'other' are equal; or 1 if the i-th coordinate of this point is
   * greater than that of 'other'.
   * @throws IllegalArgumentException if i is not less than this point's dimensionality OR
   *                                  this.dim() does not equal other.dim().
   */
  public int compareTo(NDPoint other, int di) {
    if (other.dim() != this.dim) {
      throw new IllegalArgumentException(
          "NDPoint: comparing two points of different dimension");
    }
    if (di >= this.dim) {
      throw new IllegalArgumentException(
          "NDPoint: comparing dimension: " + di + ", but point only has dimension " + this.dim);
    }
    return coords[di].compareTo(other.coords[di]);
  }

  /**
   * Compares this point to 'o' based on the first non-equal dimension (i.e. lexographic ordering).
   *
   * @param y The point to which this point is to be compared.
   */
  @Override
  public int compareTo(NDPoint y) {
    if (this.dim != y.dim) {
      throw new IllegalArgumentException("NDPoint: comparing two points of different dimension");
    }
    for (int i = 0; i < this.dim; i++) {
      if (this.coords[i].compareTo(y.coords[i]) != 0) {
        return this.coords[i].compareTo(y.coords[i]);
      }
    }
    return 0;
  }

  double distanceL1(NDPoint y) {
    if (this.dim != y.dim) {
      throw new IllegalArgumentException("NDPoint: comparing two points of different dimension");
    }
    double d = 0;
    for (int i = 0; i < dim; i++) {
      d += Math.abs(this.at(i) - y.at(i));
    }
    return d;
  }

  double distanceL2(NDPoint y) {
    if (this.dim != y.dim) {
      throw new IllegalArgumentException("NDPoint: comparing two points of different dimension");
    }
    double d2 = 0;
    for (int i = 0; i < dim; i++) {
      d2 += Math.pow(this.at(i) - y.at(i), 2);
    }
    return Math.sqrt(d2);
  }

  /**
   * @return p-norm of this vector (NDPoint)
   */
  public double norm(int p) {
    if (p == 1) {
      return distanceL1(new NDPoint(dim));
    } else if (p == 2) {
      return distanceL2(new NDPoint(dim));
    } else {
      throw new IllegalArgumentException(p + "-norm not yet supported");
    }
  }

}
