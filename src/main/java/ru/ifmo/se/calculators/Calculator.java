package ru.ifmo.se.calculators;

public interface Calculator {

  float computeEuclideanDistance(float[] a, float[] b);

  float computeAngularDistance(float[] a, float[] b);

  float computeAverageValue(float[] a);

  float computeDispersion(float[] a);

  float[] computeAverageVector(float[][] a);

  default float[] computeEuclideanDistances(float[] a, float[][] b) {
    final var ans = new float[b.length];
    for (int i = 0; i < b.length; i++) {
      ans[i] = computeEuclideanDistance(a, b[i]);
    }
    return ans;
  }

  default float[] computeAngularDistances(float[] a, float[][] b) {
    final var ans = new float[b.length];
    for (int i = 0; i < b.length; i++) {
      ans[i] = computeEuclideanDistance(a, b[i]);
    }
    return ans;
  }
}
