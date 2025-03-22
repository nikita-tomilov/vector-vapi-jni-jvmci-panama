package ru.ifmo.se.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;
import ru.ifmo.se.clusterizers.KmeansClusterizer;
import ru.ifmo.se.util.CalculatorFactory;
import ru.ifmo.se.util.VectorUtil;
import java.util.Arrays;

@State(Scope.Benchmark)
public class KmeansBenchmark {

  @Param("256")
  public String dimension = "";

  @Param({"1000", "5000", "10000"})
  public String vectorsCount = "";

  @Param({"20", "50"})
  public String K = "";

  @Param({"PlainJava"})
  public String calc = "";

  @Param({"euc", "ang"})
  public String distance = "";

  public float[][] data;
  public int k;
  public boolean useAngularDistance;
  public KmeansClusterizer clusterizer;
  public int[] ans;

  @Setup(Level.Invocation)
  public void beforeInvocation() {
    data = VectorUtil.generateRandomMatrix(dimension, vectorsCount);
    k = Integer.parseInt(K);
    useAngularDistance = distance.equals("ang");
    var calculator = CalculatorFactory.get(calc);
    clusterizer = new KmeansClusterizer(calculator);
  }

  @Benchmark
  public void kmeans(final Blackhole blackhole) {
    ans = clusterizer.kmeans(data, k, 10000, useAngularDistance);
    blackhole.consume(ans);
  }

  @TearDown(Level.Invocation)
  public void verification() {
    final var distinctClustersCount = Arrays.stream(ans).distinct().toArray().length;
    if (distinctClustersCount != k) {
      System.err.println("Expected " + k + " clusters, but got " + distinctClustersCount);
    }
  }
}
