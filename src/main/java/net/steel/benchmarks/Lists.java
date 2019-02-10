package net.steel.benchmarks;

import org.apache.commons.lang3.RandomUtils;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lists {

    private static final int LIST_SIZE = 1000;

    @State(Scope.Benchmark)
    public static class MyState {

        List<Integer> theArrayList  = new ArrayList<>(LIST_SIZE);
        List<Integer> theLinkedList = new LinkedList<>();

        @Setup
        public void createLists() {
            for (int i = 0; i < LIST_SIZE; i++) {
                int anInt = RandomUtils.nextInt();
                theArrayList.add(anInt);
                theLinkedList.add(anInt);
            }
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = 10)
    @Measurement(iterations = 30)
    @BenchmarkMode(Mode.AverageTime)
    public void testArrayList(MyState myState) throws Exception {
        myState.theArrayList.sort(Comparator.comparingInt(o -> o));
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = 10)
    @Measurement(iterations = 30)
    @BenchmarkMode(Mode.AverageTime)
    public void testLinkedList(MyState myState) throws Exception {
        myState.theLinkedList.sort(Comparator.comparingInt(o -> o));
    }

}

