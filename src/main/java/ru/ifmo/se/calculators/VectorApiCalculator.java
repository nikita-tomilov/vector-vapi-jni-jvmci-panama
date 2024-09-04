package ru.ifmo.se.calculators;

import static java.lang.Math.sqrt;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class VectorApiCalculator implements Calculator {

  private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

  @Override
  public float computeEuclideanDistance(final float[] a, final float[] b) {
    double sumSqrDiff = 0.0;
    int i = 0;
    int bound = SPECIES.loopBound(a.length);
    while (i < bound) {
      FloatVector aa = FloatVector.fromArray(SPECIES, a, i);
      FloatVector bb = FloatVector.fromArray(SPECIES, b, i);
      FloatVector cc = aa.sub(bb);
      sumSqrDiff += cc.mul(cc).reduceLanes(VectorOperators.ADD);
      i += SPECIES.length();
    }
    while (i < a.length) {
      sumSqrDiff += (a[i] - b[i]) * (a[i] - b[i]);
      i++;
    }
    return (float) sqrt(sumSqrDiff);
  }

  @Override
  public float computeAngularDistance(final float[] a, final float[] b) {
    float cosineSimilarity = computeCosineSimilarity(a, b);
    return 1 - cosineSimilarity;
  }

  @Override
  public float computeAverageValue(final float[] a) {
    float ans = 0.0f;
    int upperBound = SPECIES.loopBound(a.length);
    int i = 0;
    while (i < upperBound) {
      FloatVector va = FloatVector.fromArray(SPECIES, a, i);
      ans += va.reduceLanes(VectorOperators.ADD);
      i += SPECIES.length();
    }
    while (i < a.length) {
      ans += a[i];
      i++;
    }
    return ans / a.length;
  }

  @Override
  public float computeDispersion(final float[] a) {
    if (a.length <= 1) {
      return 0.0f;
    }
    final var mean = computeAverageValue(a);
    final var sum = calculateMeanSum(a, mean);
    return (sum / a.length);
  }

  private float computeCosineSimilarity(float[] p, float[] q) {
    float dp = 0.0f;
    float sumA = 0.0f;
    float sumB = 0.0f;
    int i = 0;
    int upperBound = SPECIES.loopBound(p.length);
    while (i < upperBound) {
      final FloatVector a = FloatVector.fromArray(SPECIES, p, i);
      final FloatVector b = FloatVector.fromArray(SPECIES, q, i);
      final FloatVector a2 = a.mul(a);
      final FloatVector b2 = b.mul(b);
      final FloatVector ab2 = a.mul(b);
      dp += ab2.reduceLanes(VectorOperators.ADD);
      sumA += a2.reduceLanes(VectorOperators.ADD);
      sumB += b2.reduceLanes(VectorOperators.ADD);
      i += SPECIES.length();
    }
    while (i < p.length) {
      dp += p[i] * q[i];
      sumA += p[i] * p[i];
      sumB += q[i] * q[i];
      i++;
    }

    if ((sumA == 0.0f) || (sumB == 0.0f)) {
      return 0.0f;
    }

    return dp / ((float) sqrt(sumA) * (float) sqrt(sumB));
  }

  private float calculateMeanSum(float[] a, float mean) {
    float ans = 0.0f;
    int upperBound = SPECIES.loopBound(a.length);
    var i = 0;
    while (i < upperBound) {
      var aa = FloatVector.fromArray(SPECIES, a, i);
      aa = aa.sub(mean);
      aa = aa.mul(aa);
      ans += aa.reduceLanes(VectorOperators.ADD);
      i += SPECIES.length();
    }
    while (i < a.length) {
      ans += (a[i] - mean) * (a[i] - mean);
      i++;
    }
    return ans;
  }
}
