package ru.ifmo.se.calculators;

import ru.ifmo.se.jni.NativeMathJni;
import static ru.ifmo.se.util.VectorUtil.*;

public class NativeJniCalculator implements Calculator {

  private final NativeMathJni jni;

  public NativeJniCalculator() {
    System.loadLibrary("nativemath");
    jni = new NativeMathJni();
  }

  @Override
  public float computeEuclideanDistance(final float[] a, final float[] b) {
    return jni.computeEuclideanDistance(a, b, a.length);
  }

  @Override
  public float computeAngularDistance(final float[] a, final float[] b) {
    return jni.computeAngularDistance(a, b, a.length);
  }

  @Override
  public float computeAverageValue(final float[] a) {
    return jni.computeAverageValue(a, a.length);
  }

  @Override
  public float computeDispersion(final float[] a) {
    return jni.computeDispersion(a, a.length);
  }

  @Override
  public float[] computeAverageVector(final float[][] a) {
    float[] aa = flatten(a);
    return jni.computeAverageVector(aa, a[0].length, a.length);
  }

  @Override
  public float[] computeEuclideanDistances(final float[] a, final float[][] b) {
    float[] bb = flatten(b);
    return jni.computeEuclideanDistanceMulti(a, bb, b.length, a.length);
  }

  @Override
  public float[] computeAngularDistances(final float[] a, final float[][] b) {
    float[] bb = flatten(b);
    return jni.computeAngularDistanceMulti(a, bb, b.length, a.length);
  }

  @Override
  public float[][] computeEuclideanDistanceMatrix(float[][] a) {
    int n = a.length;
    float[] aa = flatten(a);
    float[] ansFlat = jni.computeEuclideanDistanceMatrix(aa, n, a[0].length);
    return deflatten(ansFlat, n, n);
  }

  @Override
  public float[][] computeAngularDistanceMatrix(float[][] a) {
    int n = a.length;
    float[] aa = flatten(a);
    float[] ansFlat = jni.computeAngularDistanceMatrix(aa, n, a[0].length);
    return deflatten(ansFlat, n, n);
  }
}
