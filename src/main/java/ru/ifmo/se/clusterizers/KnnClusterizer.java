package ru.ifmo.se.clusterizers;

import ru.ifmo.se.calculators.Calculator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KnnClusterizer {

  private final Calculator calculator;

  private final Random random = new Random(42);

  public KnnClusterizer(final Calculator calculator) {
    this.calculator = calculator;
  }

  public int[] knn(
      final float[][] data,
      final int k,
      final int maxIterations,
      final boolean useAngularDistance
  ) {
    int maxIters = (maxIterations < 0) ? k * 2 : maxIterations;
    var dataWithIndexes = new ArrayList<VectorWithIndex>();
    for (int i = 0; i < data.length; i++) {
      dataWithIndexes.add(new VectorWithIndex(data[i], i));
    }
    var res = fit(data, dataWithIndexes, k, maxIters, calculator, useAngularDistance);

    int[] resMapped = new int[data.length];
    for (Map.Entry<Centroid, ArrayList<VectorWithIndex>> entry : res.entrySet()) {
      for (VectorWithIndex vector : entry.getValue()) {
        resMapped[vector.index()] = entry.getKey().index();
      }
    }

    return resMapped;
  }

  public HashMap<Centroid, ArrayList<VectorWithIndex>> fit(
      float [][] vectorsRaw,
      final List<VectorWithIndex> vectors,
      final int k,
      final int maxIterations,
      final Calculator calculator,
      final boolean useAngularDistance
  ) {
    var centroids = randomCentroids(vectorsRaw, k);
    var clusters = new HashMap<Centroid, ArrayList<VectorWithIndex>>();
    var lastState = new HashMap<Centroid, ArrayList<VectorWithIndex>>();

    float[][] centroidsCoordinates = centroidsCoordinates(centroids);
    for (int i = 0; i < maxIterations; i++) {
      boolean isLastIteration = (i == maxIterations - 1);

      for (VectorWithIndex vector : vectors) {
        var centroid = nearestCentroid(
            vector,
            centroids,
            centroidsCoordinates,
            calculator,
            useAngularDistance);
        assignToCluster(clusters, vector, centroid);
      }

      boolean shouldTerminate = isLastIteration || clusters.equals(lastState);
      lastState = clusters;
      if (shouldTerminate) {
        break;
      }

      centroids = relocateCentroids(clusters);
      clusters = new HashMap<>();
      centroidsCoordinates = centroidsCoordinates(centroids);
    }

    return lastState;
  }

  private List<Centroid> relocateCentroids(
      final HashMap<Centroid, ArrayList<VectorWithIndex>> clusters
  ) {
    return clusters.entrySet().stream()
        .map(entry -> average(entry.getKey(), entry.getValue()))
        .toList();
  }

  private Centroid average(
      final Centroid centroid,
      final List<VectorWithIndex> vectors
  ) {
    if (vectors.isEmpty()) {
      return centroid;
    }
    float[][] vecs = vectors.stream().map(VectorWithIndex::vector).toArray(float[][]::new);
    float[] average = calculator.computeAverageVector(vecs);
    return new Centroid(centroid.index(), average);
  }

  private void assignToCluster(
      final HashMap<Centroid, ArrayList<VectorWithIndex>> clusters,
      final VectorWithIndex vector,
      final Centroid centroid
  ) {
    clusters.computeIfAbsent(centroid, k -> new ArrayList<>()).add(vector);
  }

  private Centroid nearestCentroid(
      final VectorWithIndex vector,
      final List<Centroid> centroids,
      final float[][] centroidsCoordinates,
      final Calculator calculator,
      final boolean useAngularDistance
  ) {
    float minimumDistance = Float.MAX_VALUE;

    float[] distances = useAngularDistance //
        ? calculator.computeAngularDistances(vector.vector(), centroidsCoordinates) //
        : calculator.computeEuclideanDistances(vector.vector(), centroidsCoordinates); //

    int nearestIndex = 0;
    for (int i = 0; i < distances.length; i++) {
      if (distances[i] < minimumDistance) {
        minimumDistance = distances[i];
        nearestIndex = i;
      }
    }
    return centroids.get(nearestIndex);
  }

  private List<Centroid> randomCentroids(final float[][] vectors, final int k) {
    int dim = vectors[0].length;
    List<Centroid> centroids = new ArrayList<>();
    float[] maxs = new float[dim];
    float[] mins = new float[dim];
    Arrays.fill(maxs, Float.MIN_VALUE);
    Arrays.fill(mins, Float.MAX_VALUE);

    float[] average = calculator.computeAverageVector(vectors);
    for (final float[] vector : vectors) {
      for (int idx = 0; idx < dim; idx++) {
        float value = vector[idx];
        if (value > maxs[idx]) maxs[idx] = value;
        if (value < mins[idx]) mins[idx] = value;
      }
    }
    float[] uBound = new float[dim];
    float[] lBound = new float[dim];
    for (int idx = 0; idx < dim; idx++) {
      uBound[idx] = average[idx] + 0.5f * (maxs[idx] - average[idx]);
      lBound[idx] = average[idx] - 0.5f * (average[idx] - mins[idx]);
    }

    for (int i = 0; i < k; i++) {
      float[] coordinates = new float[dim];
      for (int index = 0; index < dim; index++) {
        float max = uBound[index];
        float min = lBound[index];
        coordinates[index] = random.nextFloat() * (max - min) + min;
      }
      centroids.add(new Centroid(i + 1, coordinates));
    }

    return centroids;
  }

  private float[][] centroidsCoordinates(final List<Centroid> centroids) {
    return centroids.stream().map(Centroid::vector).toArray(float[][]::new);
  }
}
