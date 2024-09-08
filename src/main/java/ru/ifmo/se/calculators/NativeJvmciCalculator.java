package ru.ifmo.se.calculators;

import one.nalim.Link;
import one.nalim.Linker;
import static ru.ifmo.se.util.VectorUtil.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class NativeJvmciCalculator implements Calculator {

  private static final AtomicBoolean LOADED = new AtomicBoolean(false);

  public NativeJvmciCalculator() {
    if (LOADED.compareAndSet(false, true)) {
      System.loadLibrary("nativemath");
      Linker.loadLibrary("nativemath");
      Linker.linkClass(NativeJvmciCalculator.class);
    }
  }

  @Override
  public float computeEuclideanDistance(final float[] a, final float[] b) {
    return computeEuclideanDistance(a, a.length, b, b.length);
  }

  @Override
  public float computeAngularDistance(final float[] a, final float[] b) {
    return computeAngularDistance(a, a.length, b, b.length);
  }

  @Override
  public float computeAverageValue(final float[] a) {
    return computeAverageValue(a, a.length);
  }

  @Override
  public float computeDispersion(final float[] a) {
    return computeDispersion(a, a.length);
  }

  @Override
  public float[] computeAverageVector(final float[][] a) {
    float[] aa = flatten(a);
    float[] ans = new float[a[0].length];
    computeAverageVectorN(aa, aa.length, a.length, a[0].length, ans);
    return ans;
  }

  @Override
  public float[] computeEuclideanDistances(final float[] a, final float[][] b) {
    float[] bb = flatten(b);
    float[] ans = new float[b.length];
    computeEuclideanDistanceMultiN(a, a.length, bb, bb.length, ans);
    return ans;
  }

  @Override
  public float[] computeAngularDistances(final float[] a, final float[][] b) {
    float[] bb = flatten(b);
    float[] ans = new float[b.length];
    computeAngularDistanceMultiN(a, a.length, bb, bb.length, ans);
    return ans;
  }

  @Override
  public float[][] computeEuclideanDistanceMatrix(float[][] a) {
    int n = a.length;
    float[] aa = flatten(a);
    float[] ans = new float[n * n];
    computeEuclideanDistanceMatrixN(aa, aa.length, n, a[0].length, ans);
    return deflatten(ans, n, n);
  }

  @Override
  public float[][] computeAngularDistanceMatrix(float[][] a) {
    int n = a.length;
    float[] aa = flatten(a);
    float[] ans = new float[n * n];
    computeAngularDistanceMatrixN(aa, aa.length, n, a[0].length, ans);
    return deflatten(ans, n, n);
  }

  @Link
  static native float computeAverageValue(float[] values, int size);

  @Link
  static native float computeEuclideanDistance(float[] p, int ps, float[] q, int qs);

  @Link
  static native float computeAngularDistance(float[] p, int ps, float[] q, int qs);

  @Link
  static native float computeDispersion(float[] values, int size);

  @Link
  static native void computeAverageVectorN(
      float[] values,
      int size,
      int count,
      int dim,
      float[] ans
  );

  @Link
  static native void computeEuclideanDistanceMultiN(
      float[] a,
      int aSize,
      float[] b,
      int bSize,
      float[] ans
  );

  @Link
  static native void computeAngularDistanceMultiN(
      float[] a,
      int aSize,
      float[] b,
      int bSize,
      float[] ans
  );

  @Link
  static native void computeEuclideanDistanceMatrixN(
      float[] a,
      int aSize,
      int count,
      int dim,
      float[] ans
  );

  @Link
  static native void computeAngularDistanceMatrixN(
      float[] a,
      int aSize,
      int count,
      int dim,
      float[] ans
  );
}
