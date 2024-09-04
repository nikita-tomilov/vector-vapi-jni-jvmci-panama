package ru.ifmo.se.calculators;

public interface Calculator {

  float computeEuclideanDistance(float[] a, float[] b);

  float computeAngularDistance(float[] a, float[] b);

  float computeAverageValue(float[] a);

  float computeDispersion(float[] a);
}
