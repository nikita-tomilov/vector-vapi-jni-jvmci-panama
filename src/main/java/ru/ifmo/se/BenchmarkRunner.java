package ru.ifmo.se;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class BenchmarkRunner {

  public static void main(String[] args) throws RunnerException {
    List<String> benchmarksInclude = getListProperty("benchmarksInclude");
    List<String> benchmarksExclude = getListProperty("benchmarksExclude");
    if (benchmarksInclude.isEmpty()) {
      System.out.println("No benchmarks specified");
      System.exit(1);
    }

    int numThreads = Optional.ofNullable(getNullableStringProperty("threads"))
        .map(Integer::parseInt)
        .orElse(1);
    Mode mode = Mode.valueOf(Optional.ofNullable(getNullableStringProperty("mode"))
        .orElse(Mode.SampleTime.name()));
    TimeUnit timeUnit = TimeUnit.valueOf(Optional.ofNullable(getNullableStringProperty("timeUnit"))
        .orElse(TimeUnit.MILLISECONDS.name()));
    String modeSpec = (mode == Mode.Throughput) //
        ? mode + "-" + numThreads + ".csv" //
        : mode + ".csv";
    String resultName = getNullableStringProperty("resultName") + "-" + modeSpec;
    int warmupIterations = Optional.ofNullable(getNullableIntProperty("warmupIterations"))
        .orElse(5);
    int iterations = Optional.ofNullable(getNullableIntProperty("iterations")).orElse(10);
    Map<String, List<String>> params = getParams();

    ChainedOptionsBuilder opt = new OptionsBuilder()
        .jvmArgs(args)
        .detectJvmArgs()
        .mode(mode)
        .shouldDoGC(true)
        .result(resultName)
        .resultFormat(ResultFormatType.CSV);

    for (String benchmark : benchmarksInclude) {
      opt = opt.include(benchmark);
    }

    for (String benchmark : benchmarksExclude) {
      opt = opt.exclude(benchmark);
    }

    for (Map.Entry<String, List<String>> entry : params.entrySet()) {
      opt = opt.param(entry.getKey(), entry.getValue().toArray(new String[0]));
    }

    opt = opt
        .forks(1)
        .warmupIterations(warmupIterations)
        .warmupTime(TimeValue.seconds(1))
        .measurementIterations(iterations)
        .measurementTime(TimeValue.seconds(1))
        .timeout(TimeValue.minutes(5))
        .timeUnit(timeUnit);

    opt = opt.threads(numThreads);
    new Runner(opt.build()).run();
  }

  private static String getNullableStringProperty(String key) {
    return System.getProperty(key);
  }

  private static List<String> getListProperty(final String key) {
    final var property = getNullableStringProperty(key);
    return property != null ? Arrays.asList(property.split(",")) : Collections.emptyList();
  }

  private static Map<String, List<String>> getParams() {
    final Map<String, String> params = new HashMap<>();
    System.getProperties().forEach((key, value) -> {
      if (key.toString().startsWith("param")) {
        params.put(key.toString(), value.toString());
      }
    });
    Map<String, List<String>> result = new HashMap<>();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      String k = entry.getKey().replace("param", "");
      List<String> v = Arrays.asList(entry.getValue().split(","));
      result.put(k, v);
    }
    return result;
  }

  private static Integer getNullableIntProperty(String key) {
    String value = getNullableStringProperty(key);
    return value != null ? Integer.parseInt(value) : null;
  }
}
