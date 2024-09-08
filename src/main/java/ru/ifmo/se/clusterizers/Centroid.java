package ru.ifmo.se.clusterizers;

import java.util.Arrays;
import java.util.Objects;

public record Centroid(int index, float[] vector) {
  @Override
  public String toString() {
    return "Centroid{" +
        "index=" + index +
        ", vector=" + Arrays.toString(vector) +
        '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Centroid centroid = (Centroid) o;
    return index == centroid.index && Objects.deepEquals(vector, centroid.vector);
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, Arrays.hashCode(vector));
  }
}
