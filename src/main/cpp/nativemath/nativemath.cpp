#include <cmath>
#include "ru_ifmo_se_jni_NativeMathJni.h"
#define UNUSED(x) (void)(x)

float computeEuclideanDistance(const float *p, const float *q, int size) {
    float result = 0;
    for (int i = 0; i < size; i++) {
        result += (p[i] - q[i]) * (p[i] - q[i]);
    }
    return sqrt(result);
}

float computeCosineSimilarity(const float *p, const float *q, int size) {
    float a2 = 0.0f;
    float b2 = 0.0f;
    float ab = 0.0f;
    for (int i = 0; i < size; i++) {
        a2 += p[i] * p[i];
        b2 += q[i] * q[i];
        ab += p[i] * q[i];
    }

    if ((a2 == 0.0f) || (b2 == 0.0f)) {
        return 0.0f;
    }

    float cosineSimilarity = ab / (sqrt(a2) * sqrt(b2));
    return cosineSimilarity;
}

float computeAngularDistance(const float *p, const float *q, int size) {
    float cosineSimilarity = computeCosineSimilarity(p, q, size);
    float cosineDistance = 1 - cosineSimilarity;
    return cosineDistance;
}

extern "C" __attribute__((visibility("default")))
float computeEuclideanDistance(const float *p, int ps, const float *q, int qs) {
    UNUSED(qs);
    return computeEuclideanDistance(p, q, ps);
}

extern "C" __attribute__((visibility("default")))
float computeAngularDistance(const float *p, int ps, const float *q, int qs) {
    UNUSED(qs);
    return computeAngularDistance(p, q, ps);
}

extern "C" __attribute__((visibility("default")))
float computeAverageValue(const float *p, int size) {
    double avg = 0.0;
    for (int i = 0; i < size; i++) {
        avg += p[i];
    }
    return avg / size;
}

void computeAverageVector(const float *p, int size, int dim, float *ans) {
    for (int i = 0; i < dim; i++) {
        double sum = 0;
        for (int j = 0; j < size; j++) {
            sum += p[j * dim + i];
        }
        ans[i] = sum / size;
    }
}

extern "C" __attribute__((visibility("default")))
void computeAverageVectorN(const float *p, int, int size, int dim, float* ans) {
    computeAverageVector(p, size, dim, ans);
}

extern "C" __attribute__((visibility("default")))
float computeDispersion(const float *p, int size) {
    if (size <= 1) return 0.0f;
    float mean = computeAverageValue(p, size);
    double sum = 0.0f;
    for (int i = 0; i < size; i++) {
        sum += (p[i] - mean) * (p[i] - mean);
    }
    return sum / size;
}

JNIEXPORT jfloat JNICALL Java_ru_ifmo_se_jni_NativeMathJni_computeAverageValue
  (JNIEnv *env, jobject, jfloatArray arr, jint size) {
    float a[size];
    env->GetFloatArrayRegion(arr, 0, size, a);
    return computeAverageValue(a, size);
}


JNIEXPORT jfloat JNICALL Java_ru_ifmo_se_jni_NativeMathJni_computeEuclideanDistance
        (JNIEnv *env, jobject, jfloatArray p, jfloatArray q, jint s) {
    float pp[s];
    float qq[s];

    env->GetFloatArrayRegion(p, 0, s, pp);
    env->GetFloatArrayRegion(q, 0, s, qq);

    float result = computeEuclideanDistance(pp, s, qq, s);
    return result;
}

JNIEXPORT jfloat JNICALL Java_ru_ifmo_se_jni_NativeMathJni_computeAngularDistance
        (JNIEnv *env, jobject, jfloatArray p, jfloatArray q, jint s) {
    float pp[s];
    float qq[s];

    env->GetFloatArrayRegion(p, 0, s, pp);
    env->GetFloatArrayRegion(q, 0, s, qq);

    float result = computeAngularDistance(pp, s, qq, s);
    return result;
}

JNIEXPORT jfloat JNICALL Java_ru_ifmo_se_jni_NativeMathJni_computeDispersion
        (JNIEnv *env, jobject, jfloatArray arr, jint size) {
    float a[size];
    env->GetFloatArrayRegion(arr, 0, size, a);
    return computeDispersion(a, size);
}

JNIEXPORT jfloatArray JNICALL Java_ru_ifmo_se_jni_NativeMathJni_computeAverageVector
        (JNIEnv *env, jobject, jfloatArray pm, jint dim, jint size) {
    auto p = new float[dim * size];
    float a[dim];

    env->GetFloatArrayRegion(pm, 0, dim * size, p);
    computeAverageVector(p, size, dim, a);

    auto ansArr = env->NewFloatArray(dim);
    env->SetFloatArrayRegion(ansArr, 0, dim, a);

    delete[] p;
    return ansArr;
}