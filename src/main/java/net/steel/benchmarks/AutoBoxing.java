package net.steel.benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AutoBoxing {

    private static final int WARMUP = 20;
    private static final int MEASURE = 30;

    private static final int LIST_SIZE = 1000;

    @State(Scope.Benchmark)
    public static class MyState {

        Random r = new Random();
        Long valueObject;
        long valuePrim;
        Long totalObject = 0L;
        long totalPrim = 0L;

        @Setup
        public void createLists() {
            valueObject = r.nextLong();
            valuePrim = valueObject.longValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = WARMUP)
    @Measurement(iterations = MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public void autoBoxArrayList(AutoBoxing.MyState myState) throws Exception {
        myState.totalObject += myState.valuePrim;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = WARMUP)
    @Measurement(iterations = MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public void autoBoxPrimList(AutoBoxing.MyState myState) throws Exception {
        myState.totalPrim += myState.valuePrim;
    }



}
