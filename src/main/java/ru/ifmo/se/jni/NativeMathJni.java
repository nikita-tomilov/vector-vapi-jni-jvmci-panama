package ru.ifmo.se.jni;

public class NativeMathJni {

  public native float computeAverageValue(float[] values, int size);

  public native float computeEuclideanDistance(float[] p, float[] q, int s);

  public native float computeAngularDistance(float[] p, float[] q, int s);

  public native float computeDispersion(float[] values, int size);

  public native float[] computeAverageVector(float[] values, int dim, int n);
}
