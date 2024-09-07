#pragma once

extern "C" __attribute__((visibility("default")))
float computeEuclideanDistance(const float *p, int ps, const float *q, int qs);

extern "C" __attribute__((visibility("default")))
float computeAngularDistance(const float *p, int ps, const float *q, int qs);

extern "C" __attribute__((visibility("default")))
float computeAverageValue(const float *p, int size);

extern "C" __attribute__((visibility("default")))
float computeDispersion(const float *p, int size);

extern "C" __attribute__((visibility("default")))
void computeAverageVectorN(const float *p, int size, int count, int dim, float* ans);

extern "C" __attribute__((visibility("default")))
long getAddrP(const float* arr);

extern "C" __attribute__((visibility("default")))
void computeAverageVectorP(const long *p, int count, int dim, float* ans);