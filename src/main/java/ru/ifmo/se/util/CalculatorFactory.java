package ru.ifmo.se.util;

import ru.ifmo.se.calculators.Calculator;

public class CalculatorFactory {
  public static Calculator get(String name) {
    try {
      ClassLoader cl = CalculatorFactory.class.getClassLoader();
      String cname = "ru.ifmo.se.calculators." + name + "Calculator";
      Class<?> clz = cl.loadClass(cname);
      return (Calculator) clz.getConstructors()[0].newInstance();
    } catch (final Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
