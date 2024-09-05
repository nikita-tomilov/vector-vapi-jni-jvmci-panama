package ru.ifmo.se.calculators;

import one.nalim.Link;
import one.nalim.Linker;
import ru.ifmo.se.jni.NativeMathJvmci;

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
    float[] aa = new float[a.length * a[0].length];

    for (int i = 0; i < a.length; i++) {
      System.arraycopy(a[i], 0, aa, i * a[i].length, a[i].length);
    }

    return NativeMathJvmci.computeAverageVectorN(aa, aa.length, a[0].length, a.length);
  }
}
