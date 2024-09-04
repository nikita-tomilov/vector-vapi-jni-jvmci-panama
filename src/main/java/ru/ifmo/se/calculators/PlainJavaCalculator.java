package ru.ifmo.se.calculators;

import static java.lang.Math.sqrt;

public class PlainJavaCalculator implements Calculator {

  @Override
  public float computeEuclideanDistance(final float[] a, final float[] b) {
    float ans = 0.0f;
    for (int i = 0; i < a.length; i++) {
      float s = (a[i] - b[i]);
      ans += s * s;
      i++;
    }
    return (float) sqrt(ans);
  }

  @Override
  public float computeAngularDistance(final float[] a, final float[] b) {
    float cosineSimilarity = computeCosineSimilarity(a, b);
    return 1 - cosineSimilarity;
  }

  @Override
  public float computeAverageValue(final float[] a) {
    float sum = 0.0f;
    for (final float v : a) {
      sum += v;
    }
    return sum / a.length;
  }

  @Override
  public float computeDispersion(final float[] a) {
    if (a.length <= 1) {
      return 0.0f;
    }

    float avg = computeAverageValue(a);
    float sum = 0.0f;
    for (final float v : a) {
      sum += (v - avg) * (v - avg);
    }
    return sum / a.length;
  }

  @Override
  public float[] computeAverageVector(final float[][] a) {
    int n = a[0].length;
    float[] ans = new float[n];

    // Cache locality is better this way
    for (final float[] floats : a) {
      for (int i = 0; i < n; i++) {
        ans[i] += floats[i];
      }
    }

    for (int i = 0; i < n; i++) {
      ans[i] /= a.length;
    }

    return ans;
  }

  private float computeCosineSimilarity(final float[] a, final float[] b) {
    float a2 = 0.0f;
    float b2 = 0.0f;
    float ab = 0.0f;
    for (int i = 0; i < a.length; i++) {
      a2 += a[i] * a[i];
      b2 += b[i] * b[i];
      ab += a[i] * b[i];
    }

    if (a2 == 0.0f || b2 == 0.0f) {
      return 0.0f;
    }

    return ab / (float) (sqrt(a2) * sqrt(b2));
  }
}
