package ru.ifmo.se.calculators;

import ru.ifmo.se.jni.NativeMathJni;

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
    float[] aa = new float[a.length * a[0].length];

    for (int i = 0; i < a.length; i++) {
      System.arraycopy(a[i], 0, aa, i * a[i].length, a[i].length);
    }

    return jni.computeAverageVector(aa, a[0].length, a.length);
  }
}
