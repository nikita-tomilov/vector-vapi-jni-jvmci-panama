package ru.ifmo.se.calculators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.function.BiFunction;

class CalculatorTest {

  /*
    LD_LIBRARY_PATH=`pwd`/src/main/resources
     --enable-native-access=ALL-UNNAMED --enable-preview --add-modules jdk.incubator.vector
   */
  final Calculator plainCalculator = new PlainJavaCalculator();

  final List<Calculator> calculators = List.of(
      new PlainJavaCalculator(),
      new VectorApiCalculator(),
      new NativeJniCalculator(),
      new NativePanamaCalculator()
  );

  @Test
  public void euclideanDistanceTest() {
    float[] a = {1.0f, 2.0f, 3.0f};
    float[] b = {4.0f, 5.0f, 6.0f};

    check(
        new Input(a, b),
        (calculator, input) -> calculator.computeEuclideanDistance(input.a, input.b));
  }

  @Test
  public void angularDistanceTest() {
    float[] a = {1.0f, 2.0f, 3.0f};
    float[] b = {4.0f, 5.0f, 6.0f};

    check(
        new Input(a, b),
        (calculator, input) -> calculator.computeAngularDistance(input.a, input.b));
  }

  private void check(final Input input, final BiFunction<Calculator, Input, Float> invocation) {
    final var expected = invocation.apply(plainCalculator, input);
    for (Calculator calculator : calculators) {
      float actual = invocation.apply(calculator, input);
      Assertions.assertEquals(expected, actual, 1e-6);
    }
  }

  private record Input(float[] a, float[] b) {
  }
}