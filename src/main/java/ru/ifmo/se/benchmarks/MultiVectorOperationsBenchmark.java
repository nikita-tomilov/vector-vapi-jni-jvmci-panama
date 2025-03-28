package ru.ifmo.se.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import ru.ifmo.se.calculators.Calculator;
import ru.ifmo.se.util.CalculatorFactory;
import ru.ifmo.se.util.VectorUtil;

@State(Scope.Benchmark)
public class MultiVectorOperationsBenchmark {

  @Param("100")
  public String dimension = "";

  @Param("100")
  public String vectorsCount = "";

  @Param({"PlainJava"})
  public String calc = "";

  public float[] a;

  public float[][] b;

  public Calculator calculator;

  @Setup(Level.Invocation)
  public void beforeInvocation() {
    a = VectorUtil.generateRandomVector(dimension);
    b = VectorUtil.generateRandomMatrix(dimension, vectorsCount);
    calculator = CalculatorFactory.get(calc);
  }

  @Benchmark
  public void averageVector(final Blackhole blackhole) {
    float[] ans = calculator.computeAverageVector(b);
    blackhole.consume(ans);
  }

  @Benchmark
  public void euclidDistancesMulti(final Blackhole blackhole) {
    float[] ans = calculator.computeEuclideanDistances(a, b);
    blackhole.consume(ans);
  }

  @Benchmark
  public void angularDistancesMulti(final Blackhole blackhole) {
    float[] ans = calculator.computeAngularDistances(a, b);
    blackhole.consume(ans);
  }

  @Benchmark
  public void euclidDistancesMatrix(final Blackhole blackhole) {
    float[][] ans = calculator.computeEuclideanDistanceMatrix(b);
    blackhole.consume(ans);
  }

  @Benchmark
  public void angularDistancesMatrix(final Blackhole blackhole) {
    float[][] ans = calculator.computeAngularDistanceMatrix(b);
    blackhole.consume(ans);
  }
}
