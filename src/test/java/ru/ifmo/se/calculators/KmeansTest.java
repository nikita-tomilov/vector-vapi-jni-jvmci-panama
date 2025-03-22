package ru.ifmo.se.calculators;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.clusterizers.KmeansClusterizer;
import java.util.Arrays;
import java.util.List;

class KmeansTest {

  @Test
  void kmeansWorks() {
    final var data = List.of(
        new float[]{0.0f, 0.0f, 5.0f},
        new float[]{0.0f, 0.0f, 4.0f},
        new float[]{1.0f, 1.0f, 1.0f},
        new float[]{2.0f, 2.0f, 2.0f},
        new float[]{3.0f, 3.0f, 3.0f},
        new float[]{15.0f, 15.0f, 15.0f}
    ).toArray(new float[0][]);

    final var kmeans = new KmeansClusterizer(new PlainJavaCalculator());
    final var res = kmeans.kmeans(data, 3, 1000, false);

    assertThat(Arrays.stream(res).distinct().boxed().toList()).hasSize(3);
  }
}