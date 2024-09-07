package ru.ifmo.se.util;

import java.util.Random;

public class VectorUtil {

  private static final Random random = new Random();

  public static float[] generateRandomVector(final String dimensionString) {
    int dimension = Integer.parseInt(dimensionString);
    float[] vector = new float[dimension];
    for (int i = 0; i < dimension; i++) {
      vector[i] = random.nextFloat();
    }
    return vector;
  }

  public static float[][] generateRandomMatrix(
      final String dimensionParam,
      final String vectorsCountParam
  ) {
    int dimension = Integer.parseInt(dimensionParam);
    int vectorsCount = Integer.parseInt(vectorsCountParam);

    float[][] matrix = new float[vectorsCount][dimension];
    for (int i = 0; i < vectorsCount; i++) {
      matrix[i] = generateRandomVector(dimensionParam);
    }
    return matrix;
  }

  public static float[] flatten(final float[][] a) {
    final var aa = new float[a.length * a[0].length];

    for (int i = 0; i < a.length; i++) {
      System.arraycopy(a[i], 0, aa, i * a[i].length, a[i].length);
    }

    return aa;
  }

  public static float[][] deflatten(final float[] a, int count, int dim) {
    float[][] ans = new float[count][dim];
    for (int i = 0; i < count; i++) {
      float[] row = new float[dim];
      System.arraycopy(a, i * dim, row, 0, dim);
      ans[i] = row;
    }
    return ans;
  }
}
