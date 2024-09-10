package ru.ifmo.se.calculators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Disabled("Needs special configuration to run properly")
class CalculatorTest {

  /*
    LD_LIBRARY_PATH=`pwd`/src/main/resources/22
     --enable-native-access=ALL-UNNAMED --enable-preview --add-modules jdk.incubator.vector
   */
  final Calculator plainCalculator = new PlainJavaCalculator();

  final List<Calculator> calculators = List.of(
      new PlainJavaCalculator(),
      new VectorApiCalculator(),
      new NativeJniCalculator()
      // new NativeJvmciCalculator() // Does not work on Java 22
      // new NativePanamaCalculator() // Works only on Java 22, wont compile on earlier Java versions
  );

  @Test
  public void euclideanDistanceTest() {
    float[] a = {1.0f, 2.0f, 3.0f};
    float[] b = {4.0f, 5.0f, 6.0f};

    check(
        new Input("euclidean single", a, b),
        (calculator, input) -> calculator.computeEuclideanDistance(input.a, input.b));
  }

  @Test
  public void angularDistanceTest() {
    float[] a = {1.0f, 2.0f, 3.0f};
    float[] b = {4.0f, 5.0f, 6.0f};

    check(
        new Input("angular single", a, b),
        (calculator, input) -> calculator.computeAngularDistance(input.a, input.b));
  }

  @Test
  public void averageValueTest() {
    float[] a = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f};
    check(
        new Input("avg val", a, null),
        (calculator, input) -> calculator.computeAverageValue(input.a));
  }

  @Test
  public void dispersionTest() {
    float[] a = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f};
    check(
        new Input("disp", a, null),
        (calculator, input) -> calculator.computeDispersion(input.a));
  }

  @Test
  public void averageVectorTest() {
    float[][] c = {
        {1.0f, 2.0f, 3.0f, 4.0f},
        {4.0f, 5.0f, 6.0f, 7.0f},
        {7.0f, 8.0f, 9.0f, 10.0f}
    };
    checkArr(
        new Input("avg vec", null, null, c),
        (calculator, input) -> calculator.computeAverageVector(input.c));
  }

  @Test
  public void euclideanMultiTest() {
    float[] a = {1.0f, 2.0f, 3.0f, 4.0f};
    float[][] c = {
        {1.0f, 2.0f, 3.0f, 4.0f},
        {4.0f, 5.0f, 6.0f, 7.0f},
        {7.0f, 8.0f, 9.0f, 10.0f}
    };
    checkArr(
        new Input("euclidean multi", a, null, c),
        (calculator, input) -> calculator.computeEuclideanDistances(input.a, input.c));
  }

  @Test
  public void angularMultiTest() {
    float[] a = {1.0f, 2.0f, 3.0f, 4.0f};
    float[][] c = {
        {1.0f, 2.0f, 3.0f, 4.0f},
        {4.0f, 5.0f, 6.0f, 7.0f},
        {7.0f, 8.0f, 9.0f, 10.0f}
    };
    checkArr(
        new Input("angular multi", a, null, c),
        (calculator, input) -> calculator.computeAngularDistances(input.a, input.c));
  }

  @Test
  public void euclideanMatrixTest() {
    float[][] c = {
        {1.0f, 2.0f, 3.0f, 4.0f},
        {4.0f, 5.0f, 6.0f, 7.0f},
        {7.0f, 8.0f, 9.0f, 10.0f}
    };
    checkMat(
        new Input("euclidean matrix", null, null, c),
        (calculator, input) -> calculator.computeEuclideanDistanceMatrix(input.c));
  }

  @Test
  public void angularMatrixTest() {
    float[][] c = {
        {1.0f, 2.0f, 3.0f, 4.0f},
        {4.0f, 5.0f, 6.0f, 7.0f},
        {7.0f, 8.0f, 9.0f, 10.0f}
    };
    checkMat(
        new Input("angular matrix", null, null, c),
        (calculator, input) -> calculator.computeAngularDistanceMatrix(input.c));
  }

  private void check(final Input input, final BiFunction<Calculator, Input, Float> invocation) {
    final var expected = invocation.apply(plainCalculator, input);
    System.out.println(input.test + ": " + expected);
    for (Calculator calculator : calculators) {
      final var actual = invocation.apply(calculator, input);
      System.out.println(" - for calc " + calculator.getClass().getSimpleName() + " got " + actual);
      Assertions.assertEquals(expected, actual, 1e-6);
    }
  }

  private void checkArr(
      final Input input,
      final BiFunction<Calculator, Input, float[]> invocation
  ) {
    final var expected = invocation.apply(plainCalculator, input);
    System.out.println(input.test + ": " + Arrays.toString(expected));
    for (Calculator calculator : calculators) {
      final var actual = invocation.apply(calculator, input);
      System.out.println(" - for calc " + calculator.getClass().getSimpleName() + " got "
          + Arrays.toString(actual));
      Assertions.assertEquals(expected.length, actual.length);
      for (int i = 0; i < expected.length; i++) {
        Assertions.assertEquals(expected[i], actual[i], 1e-6);
      }
    }
  }

  private void checkMat(
      final Input input,
      final BiFunction<Calculator, Input, float[][]> invocation
  ) {
    final var expected = invocation.apply(plainCalculator, input);
    System.out.println(input.test + ": " + Arrays.stream(expected) //
        .map(Arrays::toString) //
        .collect(Collectors.joining(", ")));
    for (Calculator calculator : calculators) {
      final var actual = invocation.apply(calculator, input);
      System.out.println(" - for calc " + calculator.getClass().getSimpleName() + " got "
          + Arrays.stream(actual) //
          .map(Arrays::toString) //
          .collect(Collectors.joining(", ")));
      Assertions.assertEquals(expected.length, actual.length);
      Assertions.assertEquals(expected[0].length, actual[0].length);
      for (int i = 0; i < expected.length; i++) {
        for (int j = 0; j < expected[i].length; j++) {
          Assertions.assertEquals(expected[i][j], actual[i][j], 1e-6);
        }
      }
    }
  }

  private record Input(String test, float[] a, float[] b, float[][] c) {

    public Input(String test, float[] a, float[] b) {
      this(test, a, b, null);
    }
  }
}