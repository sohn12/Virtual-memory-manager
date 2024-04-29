package org.example;

public class ProfilerStatistics {
    public static int tlbMisses = 0;
    public static int tlbHits = 0;
    public static int cacheMisses = 0;
    public static int cacheHits = 0;
    public static int totalQueries = 0;
    public static boolean doLog = false;

    public static void clear() {
        tlbHits = 0;
        tlbMisses = 0;
        cacheHits = 0;
        cacheMisses = 0;
        totalQueries = 0;
        doLog = false;
    }
    public static void log(boolean d) {
        doLog = d;
    }
    public static void incrementQueryCount() {
        totalQueries++;
    }
    public static void incrementTlbHits() {
        if(doLog) {
            System.out.println("TLB hit");
        }
        tlbHits++;
    }
    public static void incrementTlbMisses() {
        if(doLog) {
            System.out.println("TLB miss");
        }
        tlbMisses++;
    }
    public static void incrementCacheHits() {
        if(doLog) {
            System.out.println("Reading from cache memory");
        }
        cacheHits++;
    }
    public static void incrementCacheMisses() {
        if(doLog) {
            System.out.println("Reading from main memory");
        }
        cacheMisses++;
    }

    public static void print() {
        String s = "TLB misses: " + tlbMisses + " TLB hits: " + tlbHits + " Cache misses: " + cacheMisses + " Cache hits: " + cacheHits + " Total queries: " + totalQueries;
        System.out.println(s);
    }
}
