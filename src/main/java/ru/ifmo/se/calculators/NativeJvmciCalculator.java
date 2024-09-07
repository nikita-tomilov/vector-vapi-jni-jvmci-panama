package ru.ifmo.se.calculators;

import one.nalim.Linker;
import ru.ifmo.se.jni.NativeMathJvmci;
import static ru.ifmo.se.util.VectorUtil.*;

public class NativeJvmciCalculator implements Calculator {

  public NativeJvmciCalculator() {
    System.loadLibrary("nativemath");
    Linker.loadLibrary("nativemath");
    Linker.linkClass(NativeMathJvmci.class);
  }

  @Override
  public float computeEuclideanDistance(final float[] a, final float[] b) {
    return NativeMathJvmci.computeEuclideanDistance(a, a.length, b, b.length);
  }

  @Override
  public float computeAngularDistance(final float[] a, final float[] b) {
    return NativeMathJvmci.computeAngularDistance(a, a.length, b, b.length);
  }

  @Override
  public float computeAverageValue(final float[] a) {
    return NativeMathJvmci.computeAverageValue(a, a.length);
  }

  @Override
  public float computeDispersion(final float[] a) {
    return NativeMathJvmci.computeDispersion(a, a.length);
  }

  @Override
  public float[] computeAverageVector(final float[][] a) {
    float[] aa = flatten(a);
    float[] ans = new float[a[0].length];
    NativeMathJvmci.computeAverageVectorN(aa, aa.length, a[0].length, a.length, ans);
    return ans;
  }

  @Override
  public float[] computeEuclideanDistances(final float[] a, final float[][] b) {
    float[] bb = flatten(b);
    float[] ans = new float[b.length];
    NativeMathJvmci.computeEuclideanDistanceMultiN(a, a.length, bb, bb.length, ans);
    return ans;
  }

  @Override
  public float[] computeAngularDistances(final float[] a, final float[][] b) {
    float[] bb = flatten(b);
    float[] ans = new float[b.length];
    NativeMathJvmci.computeAngularDistanceMultiN(a, a.length, bb, bb.length, ans);
    return ans;
  }

  @Override
  public float[][] computeEuclideanDistanceMatrix(float[][] a) {
    int n = a.length;
    float[] aa = flatten(a);
    float[] ans = new float[n * n];
    NativeMathJvmci.computeEuclideanDistanceMatrixN(aa, aa.length, n, a[0].length, ans);
    return deflatten(ans, n, n);
  }

  @Override
  public float[][] computeAngularDistanceMatrix(float[][] a) {
    int n = a.length;
    float[] aa = flatten(a);
    float[] ans = new float[n * n];
    NativeMathJvmci.computeAngularDistanceMatrixN(aa, aa.length, n, a[0].length, ans);
    return deflatten(ans, n, n);
  }
}
