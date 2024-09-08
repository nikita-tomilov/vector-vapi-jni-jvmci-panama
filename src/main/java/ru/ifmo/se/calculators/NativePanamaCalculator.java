package ru.ifmo.se.calculators;

import ru.ifmo.se.panama.nativemath_h;
import java.lang.foreign.MemorySegment;
import java.util.concurrent.atomic.AtomicBoolean;

public class NativePanamaCalculator implements Calculator {

  private static final AtomicBoolean LOADED = new AtomicBoolean(false);

  public NativePanamaCalculator() {
    if (LOADED.compareAndSet(false, true)) {
      System.loadLibrary("nativemath");
    }
  }

  @Override
  public float computeEuclideanDistance(final float[] a, final float[] b) {
    final var aa = MemorySegment.ofArray(a);
    final var bb = MemorySegment.ofArray(b);
    return nativemath_h.computeEuclideanDistance(aa, a.length, bb, b.length);
  }

  @Override
  public float computeAngularDistance(final float[] a, final float[] b) {
    final var aa = MemorySegment.ofArray(a);
    final var bb = MemorySegment.ofArray(b);
    return nativemath_h.computeAngularDistance(aa, a.length, bb, b.length);
  }

  @Override
  public float computeAverageValue(final float[] a) {
    final var aa = MemorySegment.ofArray(a);
    return nativemath_h.computeAverageValue(aa, a.length);
  }

  @Override
  public float computeDispersion(final float[] a) {
    final var aa = MemorySegment.ofArray(a);
    return nativemath_h.computeDispersion(aa, a.length);
  }

  @Override
  public float[] computeAverageVector(final float[][] a) {
    final var input = convertToArrayOfPointersToRows(a);

    final var outputArr = new float[a[0].length];
    final var output = MemorySegment.ofArray(outputArr);

    nativemath_h.computeAverageVectorP(input, a.length, a[0].length, output);
    return outputArr;
  }

  @Override
  public float[] computeEuclideanDistances(final float[] a, final float[][] b) {
    int dim = a.length;
    final var bb = convertToArrayOfPointersToRows(b);
    final var aa = MemorySegment.ofArray(a);
    final var outputArr = new float[b.length];
    final var output = MemorySegment.ofArray(outputArr);

    nativemath_h.computeEuclideanDistanceMultiP(bb, b.length, aa, dim, output);
    return outputArr;
  }

  @Override
  public float[] computeAngularDistances(final float[] a, final float[][] b) {
    int dim = a.length;
    final var bb = convertToArrayOfPointersToRows(b);
    final var aa = MemorySegment.ofArray(a);
    final var outputArr = new float[b.length];
    final var output = MemorySegment.ofArray(outputArr);

    nativemath_h.computeAngularDistanceMultiP(bb, b.length, aa, dim, output);
    return outputArr;
  }

  @Override
  public float[][] computeEuclideanDistanceMatrix(final float[][] a) {
    final float[][] ans = new float[a.length][a.length];
    final var input = convertToArrayOfPointersToRows(a);
    final var output = convertToArrayOfPointersToRows(ans);

    nativemath_h.computeEuclideanDistanceMatrixP(input, a.length, a[0].length, output);

    return ans;
  }

  @Override
  public float[][] computeAngularDistanceMatrix(final float[][] a) {
    final float[][] ans = new float[a.length][a.length];
    final var input = convertToArrayOfPointersToRows(a);
    final var output = convertToArrayOfPointersToRows(ans);

    nativemath_h.computeAngularDistanceMatrixP(input, a.length, a[0].length, output);

    return ans;
  }

  private static MemorySegment convertToArrayOfPointersToRows(final float[][] a) {
    final long[] addrs = new long[a.length];

    for (int i = 0; i < a.length; i++) {
      final var row = a[i];
      final var rowMs = MemorySegment.ofArray(row);
      final var addr = nativemath_h.getAddrP(rowMs);
      addrs[i] = addr;
    }

    return MemorySegment.ofArray(addrs);
  }
}
