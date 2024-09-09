package ru.ifmo.se.clusterizers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductQuantization {

  private final KnnClusterizer clusterizer;

  public ProductQuantization(final KnnClusterizer clusterizer) {
    this.clusterizer = clusterizer;
  }

  public int[][] quantize(
      float[][] input,
      int m, // amount of subvectors
      int nBits, // amount of bits per subvector
      final boolean useAngularDistance
  ) {
    int dim = input[0].length;
    int n = input.length;
    int clustersCount = 1 << nBits; // 2 ^ nBits
    int subvecSize = (int) Math.round((double) dim / m);

    // Generate subvectors for each partition
    List<float[][]> subvecs = new ArrayList<>();
    for (int subvecIdx = 0; subvecIdx < m; subvecIdx++) {
      float[][] subvec = new float[n][];
      for (int i = 0; i < n; i++) {
        subvec[i] = chunk(input[i], subvecSize, subvecIdx);
      }
      subvecs.add(subvec);
    }

    int dimCheck = subvecs.stream().mapToInt(subvec -> subvec[0].length).sum();
    if (dimCheck < dim) {
      throw new IllegalArgumentException(
          "Subvectors coverage failure for size " + subvecSize + " for m " + m + " and dim " + dim
      );
    }

    // Clusterize the subvectors
    List<int[]> clusters = new ArrayList<>();
    for (float[][] subvec : subvecs) {
      clusters.add(clusterizer.knn(subvec, clustersCount, -1, useAngularDistance));
    }

    // Create result array
    int[][] result = new int[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        result[i][j] = clusters.get(j)[i];
      }
    }

    return result;
  }

  private float[] chunk(float[] array, int size, int cur) {
    float[] result = new float[size];
    int offset = cur * size;
    int length = Math.min(size, array.length - offset);
    System.arraycopy(array, offset, result, 0, length);

    if (length < result.length) {
      return Arrays.copyOf(result, length);
    }
    return result;
  }
}
