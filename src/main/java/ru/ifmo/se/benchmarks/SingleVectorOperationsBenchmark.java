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
public class SingleVectorOperationsBenchmark {

  @Param("100")
  public String dimension = "";

  @Param({"PlainJava"})
  public String calc = "";

  public float[] a;

  public float[] b;

  public Calculator calculator;

  @Setup(Level.Invocation)
  public void beforeInvocation() {
    a = VectorUtil.generateRandomVector(dimension);
    b = VectorUtil.generateRandomVector(dimension);
    calculator = CalculatorFactory.get(calc);
  }

  @Benchmark
  public void averageValue(final Blackhole blackhole) {
    float ans = calculator.computeAverageValue(a);
    blackhole.consume(ans);
  }

  @Benchmark
  public void dispersion(final Blackhole blackhole) {
    float ans = calculator.computeDispersion(a);
    blackhole.consume(ans);
  }

  @Benchmark
  public void euclidDistance(final Blackhole blackhole) {
    float ans = calculator.computeEuclideanDistance(a, b);
    blackhole.consume(ans);
  }

  @Benchmark
  public void angularDistance(final Blackhole blackhole) {
    float ans = calculator.computeAngularDistance(a, b);
    blackhole.consume(ans);
  }
}
