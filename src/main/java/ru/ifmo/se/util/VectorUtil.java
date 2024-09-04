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
}
