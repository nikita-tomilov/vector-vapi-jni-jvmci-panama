package ru.ifmo.se.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;
import ru.ifmo.se.clusterizers.KnnClusterizer;
import ru.ifmo.se.clusterizers.ProductQuantization;
import ru.ifmo.se.util.CalculatorFactory;
import ru.ifmo.se.util.VectorUtil;

@State(Scope.Benchmark)
public class ProductQuantizationBenchmark {

  @Param("256")
  public String dimension = "";

  @Param({"10000"})
  public String vectorsCount = "";

  @Param({"8", "12", "16"})
  public String NBits = "";

  @Param({"4", "8"})
  public String M = "";

  @Param({"PlainJava"})
  public String calc = "";

  @Param({"euc", "ang"})
  public String distance = "";

  public float[][] data;
  public int m;
  public int nBits;
  public boolean useAngularDistance;
  public ProductQuantization pq;
  public int[][] ans = null;

  @Setup(Level.Invocation)
  public void beforeInvocation() {
    data = VectorUtil.generateRandomMatrix(dimension, vectorsCount);
    m = Integer.parseInt(M);
    nBits = Integer.parseInt(NBits);
    useAngularDistance = distance.equals("ang");
    var calculator = CalculatorFactory.get(calc);
    var clusterizer = new KnnClusterizer(calculator);
    pq = new ProductQuantization(clusterizer);
  }

  @Benchmark
  public void pq(final Blackhole blackhole) {
    ans = pq.quantize(data, m, nBits, useAngularDistance);
    blackhole.consume(ans);
  }

  @TearDown(Level.Invocation)
  public void verification() {
    if (ans.length != data.length) {
      System.err.println("Expected " + data.length + " entries, but got " + ans.length);
    }
    if (ans[0].length != m) {
      System.err.println("Expected " + m + " subspaces, but got " + ans[0].length);
    }
  }
}
