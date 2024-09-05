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
  public static native float[] computeAverageVectorN(float[] values, int size, int dim, int n);
}
