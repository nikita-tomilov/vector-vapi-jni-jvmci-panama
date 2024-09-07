float computeEuclideanDistance(const float *p, int ps, const float *q, int qs);

float computeAngularDistance(const float *p, int ps, const float *q, int qs);

float computeAverageValue(const float *p, int size);

float computeDispersion(const float *p, int size);

void computeAverageVectorN(const float *p, int size, int count, int dim, float* ans);

long getAddrP(const float* arr);

void computeAverageVectorP(const long *p, int count, int dim, float* ans);