package ru.ifmo.se.calculators;

import ru.ifmo.se.jni.NativeMathJvmci;
import ru.ifmo.se.panama.nativemath_h;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class NativePanamaCalculator implements Calculator {

  public NativePanamaCalculator() {
    // /opt/jextract-22/bin/jextract src/main/cpp/nativemath/nativemath.h
    // s!downcallHandle(ADDR, DESC);!downcallHandle(ADDR, DESC, Option.critical(true));!g
    System.loadLibrary("nativemath");
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


    // 20000 ns/op
    /*float[] aa = new float[a.length * a[0].length];

    for (int i = 0; i < a.length; i++) {
      System.arraycopy(a[i], 0, aa, i * a[i].length, a[i].length);
    }

    final var input = MemorySegment.ofArray(aa);
    final var outputArr = new float[a[0].length];
    final var output = MemorySegment.ofArray(outputArr);

    nativemath_h.computeAverageVectorN(input, aa.length, a.length, a[0].length, output);
    return outputArr;
    */

    // 9532 ns/op with ver direct 1
    // 1700 ns/op with ver direct 2
    final long[] addrs = new long[a.length];

    for (int i = 0; i < a.length; i++) {
      final var row = a[i];
      final var rowMs = MemorySegment.ofArray(row);
      final var addr = nativemath_h.getAddrP(rowMs);
      addrs[i] = addr;
    }

    final var input = MemorySegment.ofArray(addrs);
    final var outputArr = new float[a[0].length];
    final var output = MemorySegment.ofArray(outputArr);

    nativemath_h.computeAverageVectorP(input, a.length, a[0].length, output);
    return outputArr;

    /*
    // 21649 ns/op
    final var segment = Arena.ofAuto().allocate((long) a.length * a[0].length * Float.BYTES);
    int n = 0;
    for (float[] row : a) {
      segment.asSlice(n, (long) row.length * Float.BYTES).copyFrom(MemorySegment.ofArray(row));
      n += row.length * Float.BYTES;
    }

    final var outputArr = new float[a[0].length];
    final var output = MemorySegment.ofArray(outputArr);

    nativemath_h.computeAverageVectorN(segment, -1, a.length, a[0].length, output);
    return outputArr;
     */
  }
}
