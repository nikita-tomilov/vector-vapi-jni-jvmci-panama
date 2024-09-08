package ru.ifmo.se.panama;
import java.lang.foreign.Linker.Option;
// Generated by jextract

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

public class nativemath_h {

    nativemath_h() {
        // Should not be called directly
    }

    static final Arena LIBRARY_ARENA = Arena.ofAuto();
    static final boolean TRACE_DOWNCALLS = Boolean.getBoolean("jextract.trace.downcalls");

    static void traceDowncall(String name, Object... args) {
         String traceArgs = Arrays.stream(args)
                       .map(Object::toString)
                       .collect(Collectors.joining(", "));
         System.out.printf("%s(%s)\n", name, traceArgs);
    }

    static MemorySegment findOrThrow(String symbol) {
        return SYMBOL_LOOKUP.find(symbol)
            .orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol: " + symbol));
    }

    static MethodHandle upcallHandle(Class<?> fi, String name, FunctionDescriptor fdesc) {
        try {
            return MethodHandles.lookup().findVirtual(fi, name, fdesc.toMethodType());
        } catch (ReflectiveOperationException ex) {
            throw new AssertionError(ex);
        }
    }

    static MemoryLayout align(MemoryLayout layout, long align) {
        return switch (layout) {
            case PaddingLayout p -> p;
            case ValueLayout v -> v.withByteAlignment(align);
            case GroupLayout g -> {
                MemoryLayout[] alignedMembers = g.memberLayouts().stream()
                        .map(m -> align(m, align)).toArray(MemoryLayout[]::new);
                yield g instanceof StructLayout ?
                        MemoryLayout.structLayout(alignedMembers) : MemoryLayout.unionLayout(alignedMembers);
            }
            case SequenceLayout s -> MemoryLayout.sequenceLayout(s.elementCount(), align(s.elementLayout(), align));
        };
    }

    static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.loaderLookup()
            .or(Linker.nativeLinker().defaultLookup());

    public static final ValueLayout.OfBoolean C_BOOL = ValueLayout.JAVA_BOOLEAN;
    public static final ValueLayout.OfByte C_CHAR = ValueLayout.JAVA_BYTE;
    public static final ValueLayout.OfShort C_SHORT = ValueLayout.JAVA_SHORT;
    public static final ValueLayout.OfInt C_INT = ValueLayout.JAVA_INT;
    public static final ValueLayout.OfLong C_LONG_LONG = ValueLayout.JAVA_LONG;
    public static final ValueLayout.OfFloat C_FLOAT = ValueLayout.JAVA_FLOAT;
    public static final ValueLayout.OfDouble C_DOUBLE = ValueLayout.JAVA_DOUBLE;
    public static final AddressLayout C_POINTER = ValueLayout.ADDRESS
            .withTargetLayout(MemoryLayout.sequenceLayout(java.lang.Long.MAX_VALUE, JAVA_BYTE));
    public static final ValueLayout.OfLong C_LONG = ValueLayout.JAVA_LONG;

    private static class computeEuclideanDistance {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            nativemath_h.C_FLOAT,
            nativemath_h.C_POINTER,
            nativemath_h.C_INT,
            nativemath_h.C_POINTER,
            nativemath_h.C_INT
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("computeEuclideanDistance");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * float computeEuclideanDistance(const float *p, int ps, const float *q, int qs)
     * }
     */
    public static FunctionDescriptor computeEuclideanDistance$descriptor() {
        return computeEuclideanDistance.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * float computeEuclideanDistance(const float *p, int ps, const float *q, int qs)
     * }
     */
    public static MethodHandle computeEuclideanDistance$handle() {
        return computeEuclideanDistance.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * float computeEuclideanDistance(const float *p, int ps, const float *q, int qs)
     * }
     */
    public static MemorySegment computeEuclideanDistance$address() {
        return computeEuclideanDistance.ADDR;
    }

    /**
     * {@snippet lang=c :
     * float computeEuclideanDistance(const float *p, int ps, const float *q, int qs)
     * }
     */
    public static float computeEuclideanDistance(MemorySegment p, int ps, MemorySegment q, int qs) {
        var mh$ = computeEuclideanDistance.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("computeEuclideanDistance", p, ps, q, qs);
            }
            return (float)mh$.invokeExact(p, ps, q, qs);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class computeAngularDistance {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            nativemath_h.C_FLOAT,
            nativemath_h.C_POINTER,
            nativemath_h.C_INT,
            nativemath_h.C_POINTER,
            nativemath_h.C_INT
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("computeAngularDistance");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * float computeAngularDistance(const float *p, int ps, const float *q, int qs)
     * }
     */
    public static FunctionDescriptor computeAngularDistance$descriptor() {
        return computeAngularDistance.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * float computeAngularDistance(const float *p, int ps, const float *q, int qs)
     * }
     */
    public static MethodHandle computeAngularDistance$handle() {
        return computeAngularDistance.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * float computeAngularDistance(const float *p, int ps, const float *q, int qs)
     * }
     */
    public static MemorySegment computeAngularDistance$address() {
        return computeAngularDistance.ADDR;
    }

    /**
     * {@snippet lang=c :
     * float computeAngularDistance(const float *p, int ps, const float *q, int qs)
     * }
     */
    public static float computeAngularDistance(MemorySegment p, int ps, MemorySegment q, int qs) {
        var mh$ = computeAngularDistance.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("computeAngularDistance", p, ps, q, qs);
            }
            return (float)mh$.invokeExact(p, ps, q, qs);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class computeAverageValue {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            nativemath_h.C_FLOAT,
            nativemath_h.C_POINTER,
            nativemath_h.C_INT
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("computeAverageValue");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * float computeAverageValue(const float *p, int size)
     * }
     */
    public static FunctionDescriptor computeAverageValue$descriptor() {
        return computeAverageValue.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * float computeAverageValue(const float *p, int size)
     * }
     */
    public static MethodHandle computeAverageValue$handle() {
        return computeAverageValue.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * float computeAverageValue(const float *p, int size)
     * }
     */
    public static MemorySegment computeAverageValue$address() {
        return computeAverageValue.ADDR;
    }

    /**
     * {@snippet lang=c :
     * float computeAverageValue(const float *p, int size)
     * }
     */
    public static float computeAverageValue(MemorySegment p, int size) {
        var mh$ = computeAverageValue.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("computeAverageValue", p, size);
            }
            return (float)mh$.invokeExact(p, size);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class computeDispersion {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            nativemath_h.C_FLOAT,
            nativemath_h.C_POINTER,
            nativemath_h.C_INT
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("computeDispersion");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * float computeDispersion(const float *p, int size)
     * }
     */
    public static FunctionDescriptor computeDispersion$descriptor() {
        return computeDispersion.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * float computeDispersion(const float *p, int size)
     * }
     */
    public static MethodHandle computeDispersion$handle() {
        return computeDispersion.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * float computeDispersion(const float *p, int size)
     * }
     */
    public static MemorySegment computeDispersion$address() {
        return computeDispersion.ADDR;
    }

    /**
     * {@snippet lang=c :
     * float computeDispersion(const float *p, int size)
     * }
     */
    public static float computeDispersion(MemorySegment p, int size) {
        var mh$ = computeDispersion.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("computeDispersion", p, size);
            }
            return (float)mh$.invokeExact(p, size);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class computeAverageVectorN {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            nativemath_h.C_POINTER,
            nativemath_h.C_INT,
            nativemath_h.C_INT,
            nativemath_h.C_INT,
            nativemath_h.C_POINTER
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("computeAverageVectorN");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void computeAverageVectorN(const float *p, int size, int count, int dim, float *ans)
     * }
     */
    public static FunctionDescriptor computeAverageVectorN$descriptor() {
        return computeAverageVectorN.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void computeAverageVectorN(const float *p, int size, int count, int dim, float *ans)
     * }
     */
    public static MethodHandle computeAverageVectorN$handle() {
        return computeAverageVectorN.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void computeAverageVectorN(const float *p, int size, int count, int dim, float *ans)
     * }
     */
    public static MemorySegment computeAverageVectorN$address() {
        return computeAverageVectorN.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void computeAverageVectorN(const float *p, int size, int count, int dim, float *ans)
     * }
     */
    public static void computeAverageVectorN(MemorySegment p, int size, int count, int dim, MemorySegment ans) {
        var mh$ = computeAverageVectorN.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("computeAverageVectorN", p, size, count, dim, ans);
            }
            mh$.invokeExact(p, size, count, dim, ans);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class getAddrP {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            nativemath_h.C_LONG,
            nativemath_h.C_POINTER
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("getAddrP");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * long getAddrP(const float *arr)
     * }
     */
    public static FunctionDescriptor getAddrP$descriptor() {
        return getAddrP.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * long getAddrP(const float *arr)
     * }
     */
    public static MethodHandle getAddrP$handle() {
        return getAddrP.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * long getAddrP(const float *arr)
     * }
     */
    public static MemorySegment getAddrP$address() {
        return getAddrP.ADDR;
    }

    /**
     * {@snippet lang=c :
     * long getAddrP(const float *arr)
     * }
     */
    public static long getAddrP(MemorySegment arr) {
        var mh$ = getAddrP.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("getAddrP", arr);
            }
            return (long)mh$.invokeExact(arr);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class computeAverageVectorP {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            nativemath_h.C_POINTER,
            nativemath_h.C_INT,
            nativemath_h.C_INT,
            nativemath_h.C_POINTER
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("computeAverageVectorP");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void computeAverageVectorP(const long *p, int count, int dim, float *ans)
     * }
     */
    public static FunctionDescriptor computeAverageVectorP$descriptor() {
        return computeAverageVectorP.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void computeAverageVectorP(const long *p, int count, int dim, float *ans)
     * }
     */
    public static MethodHandle computeAverageVectorP$handle() {
        return computeAverageVectorP.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void computeAverageVectorP(const long *p, int count, int dim, float *ans)
     * }
     */
    public static MemorySegment computeAverageVectorP$address() {
        return computeAverageVectorP.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void computeAverageVectorP(const long *p, int count, int dim, float *ans)
     * }
     */
    public static void computeAverageVectorP(MemorySegment p, int count, int dim, MemorySegment ans) {
        var mh$ = computeAverageVectorP.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("computeAverageVectorP", p, count, dim, ans);
            }
            mh$.invokeExact(p, count, dim, ans);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class computeEuclideanDistanceMultiP {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            nativemath_h.C_POINTER,
            nativemath_h.C_INT,
            nativemath_h.C_POINTER,
            nativemath_h.C_INT,
            nativemath_h.C_POINTER
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("computeEuclideanDistanceMultiP");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void computeEuclideanDistanceMultiP(const long *p, int count, float *a, int dim, float *ans)
     * }
     */
    public static FunctionDescriptor computeEuclideanDistanceMultiP$descriptor() {
        return computeEuclideanDistanceMultiP.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void computeEuclideanDistanceMultiP(const long *p, int count, float *a, int dim, float *ans)
     * }
     */
    public static MethodHandle computeEuclideanDistanceMultiP$handle() {
        return computeEuclideanDistanceMultiP.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void computeEuclideanDistanceMultiP(const long *p, int count, float *a, int dim, float *ans)
     * }
     */
    public static MemorySegment computeEuclideanDistanceMultiP$address() {
        return computeEuclideanDistanceMultiP.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void computeEuclideanDistanceMultiP(const long *p, int count, float *a, int dim, float *ans)
     * }
     */
    public static void computeEuclideanDistanceMultiP(MemorySegment p, int count, MemorySegment a, int dim, MemorySegment ans) {
        var mh$ = computeEuclideanDistanceMultiP.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("computeEuclideanDistanceMultiP", p, count, a, dim, ans);
            }
            mh$.invokeExact(p, count, a, dim, ans);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class computeAngularDistanceMultiP {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            nativemath_h.C_POINTER,
            nativemath_h.C_INT,
            nativemath_h.C_POINTER,
            nativemath_h.C_INT,
            nativemath_h.C_POINTER
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("computeAngularDistanceMultiP");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void computeAngularDistanceMultiP(const long *p, int count, float *a, int dim, float *ans)
     * }
     */
    public static FunctionDescriptor computeAngularDistanceMultiP$descriptor() {
        return computeAngularDistanceMultiP.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void computeAngularDistanceMultiP(const long *p, int count, float *a, int dim, float *ans)
     * }
     */
    public static MethodHandle computeAngularDistanceMultiP$handle() {
        return computeAngularDistanceMultiP.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void computeAngularDistanceMultiP(const long *p, int count, float *a, int dim, float *ans)
     * }
     */
    public static MemorySegment computeAngularDistanceMultiP$address() {
        return computeAngularDistanceMultiP.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void computeAngularDistanceMultiP(const long *p, int count, float *a, int dim, float *ans)
     * }
     */
    public static void computeAngularDistanceMultiP(MemorySegment p, int count, MemorySegment a, int dim, MemorySegment ans) {
        var mh$ = computeAngularDistanceMultiP.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("computeAngularDistanceMultiP", p, count, a, dim, ans);
            }
            mh$.invokeExact(p, count, a, dim, ans);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class computeEuclideanDistanceMatrixP {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            nativemath_h.C_POINTER,
            nativemath_h.C_INT,
            nativemath_h.C_INT,
            nativemath_h.C_POINTER
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("computeEuclideanDistanceMatrixP");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void computeEuclideanDistanceMatrixP(const long *p, int count, int dim, const long *ans)
     * }
     */
    public static FunctionDescriptor computeEuclideanDistanceMatrixP$descriptor() {
        return computeEuclideanDistanceMatrixP.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void computeEuclideanDistanceMatrixP(const long *p, int count, int dim, const long *ans)
     * }
     */
    public static MethodHandle computeEuclideanDistanceMatrixP$handle() {
        return computeEuclideanDistanceMatrixP.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void computeEuclideanDistanceMatrixP(const long *p, int count, int dim, const long *ans)
     * }
     */
    public static MemorySegment computeEuclideanDistanceMatrixP$address() {
        return computeEuclideanDistanceMatrixP.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void computeEuclideanDistanceMatrixP(const long *p, int count, int dim, const long *ans)
     * }
     */
    public static void computeEuclideanDistanceMatrixP(MemorySegment p, int count, int dim, MemorySegment ans) {
        var mh$ = computeEuclideanDistanceMatrixP.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("computeEuclideanDistanceMatrixP", p, count, dim, ans);
            }
            mh$.invokeExact(p, count, dim, ans);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class computeAngularDistanceMatrixP {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            nativemath_h.C_POINTER,
            nativemath_h.C_INT,
            nativemath_h.C_INT,
            nativemath_h.C_POINTER
        );

        public static final MemorySegment ADDR = nativemath_h.findOrThrow("computeAngularDistanceMatrixP");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC, Option.critical(true));
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void computeAngularDistanceMatrixP(const long *p, int count, int dim, const long *ans)
     * }
     */
    public static FunctionDescriptor computeAngularDistanceMatrixP$descriptor() {
        return computeAngularDistanceMatrixP.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void computeAngularDistanceMatrixP(const long *p, int count, int dim, const long *ans)
     * }
     */
    public static MethodHandle computeAngularDistanceMatrixP$handle() {
        return computeAngularDistanceMatrixP.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void computeAngularDistanceMatrixP(const long *p, int count, int dim, const long *ans)
     * }
     */
    public static MemorySegment computeAngularDistanceMatrixP$address() {
        return computeAngularDistanceMatrixP.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void computeAngularDistanceMatrixP(const long *p, int count, int dim, const long *ans)
     * }
     */
    public static void computeAngularDistanceMatrixP(MemorySegment p, int count, int dim, MemorySegment ans) {
        var mh$ = computeAngularDistanceMatrixP.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("computeAngularDistanceMatrixP", p, count, dim, ans);
            }
            mh$.invokeExact(p, count, dim, ans);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }
}

