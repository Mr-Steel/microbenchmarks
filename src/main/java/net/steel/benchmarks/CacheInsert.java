package net.steel.benchmarks;

import org.apache.commons.lang3.RandomUtils;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CacheInsert {
    private static final int WARMUP = 20;
    private static final int MEASURE = 30;

    private static final int LIST_SIZE = 1000;

    @State(Scope.Benchmark)
    public static class MyState {

        List<Long> theArrayList  = new ArrayList<>(LIST_SIZE);
        MutableLongList primLongList = new LongArrayList(LIST_SIZE);
        Random r = new Random();
        Long randomLongObject;
        long randomLongPrim;

        @Setup
        public void createLists() {
            for (int i = 0; i < LIST_SIZE; i++) {
                Long aLong = RandomUtils.nextLong();
                theArrayList.add(aLong);
                primLongList.add(aLong.longValue());
            }
            randomLongObject = r.nextLong();
            randomLongPrim = randomLongObject.longValue();
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = WARMUP)
    @Measurement(iterations = MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public void addArrayList(CacheInsert.MyState myState) throws Exception {
        myState.theArrayList.add(500, myState.randomLongObject);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = WARMUP)
    @Measurement(iterations = MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public void setArrayList(CacheInsert.MyState myState) throws Exception {
        myState.theArrayList.set(100, myState.randomLongObject);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = WARMUP)
    @Measurement(iterations = MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public void addPrimLongList(CacheInsert.MyState myState) throws Exception {
        myState.primLongList.addAllAtIndex(500, myState.randomLongPrim);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = WARMUP)
    @Measurement(iterations = MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public void setPrimLongList(CacheInsert.MyState myState) throws Exception {
        myState.primLongList.set(500, myState.randomLongPrim);
    }

}
