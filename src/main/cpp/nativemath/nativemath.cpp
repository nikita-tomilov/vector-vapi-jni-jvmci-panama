#include "nativemath.h"
#include <cmath>
#include "ru_ifmo_se_jni_NativeMathJni.h"
#include <iostream>
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

void computeAverageVector(const float *p, int count, int dim, float *ans) {
    for (int i = 0; i < dim; i++) {
        double sum = 0;
        for (int j = 0; j < count; j++) {
            sum += p[j * dim + i];
        }
        ans[i] = sum / count;
    }
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

void computeEuclideanDistanceMulti(float* a, float* bb, int count, int dim, float* ans) {
    for (int i = 0; i < count; i++) {
        float dist = computeEuclideanDistance(a, (bb + i * dim), dim);
        ans[i] = dist;
    }
}

void computeAngularDistanceMulti(float* a, float* bb, int count, int dim, float* ans) {
    for (int i = 0; i < count; i++) {
        float dist = computeAngularDistance(a, (bb + i * dim), dim);
        ans[i] = dist;
    }
}

void computeEuclideanDistanceMatrix(float* a, int count, int dim, float* ans) {
    for (int i = 0; i < count; i++) {
        for (int j = 0; j < i; j++) {
            float* aa = (a + i * dim);
            float* bb = (a + j * dim);
            float dist = computeEuclideanDistance(aa, bb, dim);
            ans[i * count + j] = dist;
            ans[j * count + i] = dist;
        }
        ans[i * count + i] = 0;
    }
}

void computeAngularDistanceMatrix(float* a, int count, int dim, float* ans) {
    for (int i = 0; i < count; i++) {
        for (int j = 0; j < i; j++) {
            float* aa = (a + i * dim);
            float* bb = (a + j * dim);
            float dist = computeAngularDistance(aa, bb, dim);
            ans[i * count + j] = dist;
            ans[j * count + i] = dist;
        }
        ans[i * count + i] = 0;
    }
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
        (JNIEnv *env, jobject, jfloatArray pm, jint dim, jint count) {
    auto p = new float[dim * count];
    float a[dim];

    env->GetFloatArrayRegion(pm, 0, dim * count, p);
    computeAverageVector(p, count, dim, a);

    auto ansArr = env->NewFloatArray(dim);
    env->SetFloatArrayRegion(ansArr, 0, dim, a);

    delete[] p;
    return ansArr;
}

JNIEXPORT jfloatArray JNICALL Java_ru_ifmo_se_jni_NativeMathJni_computeEuclideanDistanceMulti
  (JNIEnv * env, jobject, jfloatArray a, jfloatArray bb, jint count, jint dim) {
    auto aa = new float[dim];
    auto bbb = new float[dim * count];
    float ans[count];

    env->GetFloatArrayRegion(a, 0, dim, aa);
    env->GetFloatArrayRegion(bb, 0, dim * count, bbb);

    computeEuclideanDistanceMulti(aa, bbb, count, dim, ans);

    auto ansArr = env->NewFloatArray(count);
    env->SetFloatArrayRegion(ansArr, 0, count, ans);

    delete[] aa;
    delete[] bbb;
    return ansArr;
}

JNIEXPORT jfloatArray JNICALL Java_ru_ifmo_se_jni_NativeMathJni_computeAngularDistanceMulti
  (JNIEnv * env, jobject, jfloatArray a, jfloatArray bb, jint count, jint dim) {
    auto aa = new float[dim];
    auto bbb = new float[dim * count];
    float ans[count];

    env->GetFloatArrayRegion(a, 0, dim, aa);
    env->GetFloatArrayRegion(bb, 0, dim * count, bbb);

    computeAngularDistanceMulti(aa, bbb, count, dim, ans);

    auto ansArr = env->NewFloatArray(count);
    env->SetFloatArrayRegion(ansArr, 0, count, ans);

    delete[] aa;
    delete[] bbb;
    return ansArr;
}

JNIEXPORT jfloatArray JNICALL Java_ru_ifmo_se_jni_NativeMathJni_computeEuclideanDistanceMatrix
  (JNIEnv * env, jobject, jfloatArray a, jint count, jint dim) {
    auto* ans = new float[count * count];
    auto* aa = new float[count * dim];
    env->GetFloatArrayRegion(a, 0, count * dim, aa);

    computeEuclideanDistanceMatrix(aa, count, dim, ans);

    auto ansArr = env->NewFloatArray(count * count);
    env->SetFloatArrayRegion(ansArr, 0, count * count, ans);

    delete[] ans;
    delete[] aa;
    return ansArr;
}

JNIEXPORT jfloatArray JNICALL Java_ru_ifmo_se_jni_NativeMathJni_computeAngularDistanceMatrix
  (JNIEnv * env, jobject, jfloatArray a, jint count, jint dim) {
    auto* ans = new float[count * count];
    auto* aa = new float[count * dim];
    env->GetFloatArrayRegion(a, 0, count * dim, aa);
    
    computeAngularDistanceMatrix(aa, count, dim, ans);

    auto ansArr = env->NewFloatArray(count * count);
    env->SetFloatArrayRegion(ansArr, 0, count * count, ans);

    delete[] ans;
    delete[] aa;
    return ansArr;
}

extern "C" __attribute__((visibility("default")))
void computeAverageVectorN(const float *p, int pSize, int count, int dim, float* ans) {
    UNUSED(pSize);
    computeAverageVector(p, count, dim, ans);
}

extern "C" __attribute__((visibility("default")))
void computeEuclideanDistanceMultiN(float* a, int dim, float* bb, int bSize, float* ans) {
    int count = bSize / dim;
    return computeEuclideanDistanceMulti(a, bb, count, dim, ans);
}

extern "C" __attribute__((visibility("default")))
void computeAngularDistanceMultiN(float* a, int dim, float* bb, int bSize, float* ans) {
    int count = bSize / dim;
    return computeAngularDistanceMulti(a, bb, count, dim, ans);
}

extern "C" __attribute__((visibility("default")))
void computeEuclideanDistanceMatrixN(float* a, int aSize, int count, int dim, float* ans) {
    UNUSED(aSize);
    return computeEuclideanDistanceMatrix(a, count, dim, ans);
}

extern "C" __attribute__((visibility("default")))
void computeAngularDistanceMatrixN(float* a, int aSize, int count, int dim, float* ans) {
    UNUSED(aSize);
    return computeAngularDistanceMatrix(a, count, dim, ans);
}

extern "C" __attribute__((visibility("default")))
long getAddrP(const float* arr) {
  return reinterpret_cast<long>(arr);
}

extern "C" __attribute__((visibility("default")))
void computeAverageVectorP(const long *p, int count, int dim, float* ans) {

    for (int i = 0; i < count; i++) {
        long addr = p[i];
        float* arr = reinterpret_cast<float*>(addr);

        for (int j = 0; j < dim; j++) {
            ans[j] += arr[j];
        }
    }

    for (int j = 0; j < dim; j++) {
        ans[j] = ans[j] / count;
    }
}

extern "C" __attribute__((visibility("default")))
void computeEuclideanDistanceMultiP(const long *p, int count, float* a, int dim, float* ans) {
    for (int i = 0; i < count; i++) {
        long addr = p[i];
        float* b = reinterpret_cast<float*>(addr);
        float dist = computeEuclideanDistance(a, b, dim);
        ans[i] = dist;
    }
}

extern "C" __attribute__((visibility("default")))
void computeAngularDistanceMultiP(const long *p, int count, float* a, int dim, float* ans) {
    for (int i = 0; i < count; i++) {
        long addr = p[i];
        float* b = reinterpret_cast<float*>(addr);
        float dist = computeAngularDistance(a, b, dim);
        ans[i] = dist;
    }
}

void setByAddrP(int i, int j, const long* ans, float value) {
    long addr = ans[i];
    float* arr = reinterpret_cast<float*>(addr);
    arr[j] = value;
}

extern "C" __attribute__((visibility("default")))
void computeEuclideanDistanceMatrixP(const long *p, int count, int dim, const long* ans) {
    for (int i = 0; i < count; i++) {
        long addrA = p[i];
        float* a = reinterpret_cast<float*>(addrA);
        for (int j = 0; j < i; j++) {
            long addrB = p[j];
            float* b = reinterpret_cast<float*>(addrB);

            float dist = computeEuclideanDistance(a, b, dim);
            setByAddrP(i, j, ans, dist);
            setByAddrP(j, i, ans, dist);
        }
        setByAddrP(i, i, ans, 0.0f);
    }
}

extern "C" __attribute__((visibility("default")))
void computeAngularDistanceMatrixP(const long *p, int count, int dim, const long* ans) {
    for (int i = 0; i < count; i++) {
        long addrA = p[i];
        float* a = reinterpret_cast<float*>(addrA);
        for (int j = 0; j < i; j++) {
            long addrB = p[j];
            float* b = reinterpret_cast<float*>(addrB);

            float dist = computeAngularDistance(a, b, dim);
            setByAddrP(i, j, ans, dist);
            setByAddrP(j, i, ans, dist);
        }
        setByAddrP(i, i, ans, 0.0f);
    }
}
