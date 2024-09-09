package ru.ifmo.se.calculators;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.clusterizers.KnnClusterizer;
import ru.ifmo.se.clusterizers.ProductQuantization;
import static ru.ifmo.se.util.VectorUtil.generateRandomMatrix;

class PqTest {

  @Test
  void knnWorks() {
    final var data = generateRandomMatrix("16", "100");

    final var knn = new KnnClusterizer(new PlainJavaCalculator());
    final var pq = new ProductQuantization(knn);
    final var res = pq.quantize(data, 4, 4, false);

    assertThat(res).hasDimensions(100, 4);
    for (final int[] re : res) {
      for (final int i : re) {
        assertThat(i).isGreaterThanOrEqualTo(1);
        assertThat(i).isLessThanOrEqualTo(16);
      }
    }
  }
}