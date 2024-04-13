package org.example;

public class ProfilerStatistics {
    public static int tlbMisses = 0;
    public static int tlbHits = 0;
    public static int cacheMisses = 0;
    public static int cacheHits = 0;

    public static void clear() {
        tlbHits = 0;
        tlbMisses = 0;
        cacheHits = 0;
        cacheMisses = 0;
    }

    public static void print() {
        String s = "TLB misses: " + tlbMisses + " TLB hits: " + tlbHits + " Cache misses: " + cacheMisses + " Cache hits: " + cacheHits;
        System.out.println(s);
    }
}
