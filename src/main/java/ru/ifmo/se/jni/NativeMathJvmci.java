package ru.ifmo.se.jni;

import one.nalim.Link;

public class NativeMathJvmci {

  @Link
  public static native float computeAverageValue(float[] values, int size);

  @Link
  public static native float computeEuclideanDistance(float[] p, int ps, float[] q, int qs);

  @Link
  public static native float computeAngularDistance(float[] p, int ps, float[] q, int qs);

  @Link
  public static native float computeDispersion(float[] values, int size);

  @Link
  public static native void computeAverageVectorN(float[] values, int size, int dim, int n, float[] ans);

  @Link
  public static native void computeEuclideanDistanceMultiN(float[] a, int aSize, float[] b, int bSize, float[] ans);

  @Link
  public static native void computeAngularDistanceMultiN(float[] a, int aSize, float[] b, int bSize, float[] ans);

  @Link
  public static native void computeEuclideanDistanceMatrixN(float[] a, int aSize, int count, int dim, float[] ans);

  @Link
  public static native void computeAngularDistanceMatrixN(float[] a, int aSize, int count, int dim, float[] ans);
}
