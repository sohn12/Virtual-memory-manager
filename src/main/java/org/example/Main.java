package org.example;

public class Main {
    public static void main(String[] args) {

        // 1. supports only int variables
        // 2. the frames will be contiguous in memory

        Cpu cpu = Cpu.getInstance();
        try {
            Process p = cpu.addProcess(6);
            p.assign("a", 12);
            p.assign("b", 13);
            printSumWithTiming(p,"a", "b");
            cpu.doCaching();
            printSumWithTiming(p,"a", "b");
            printSumWithTiming(p,"a", "b");

            cpu.printRam();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private static void printSumWithTiming(Process p, String a, String b) throws Exception {
        long startTime = System.nanoTime();
        int ans = p.sum(a, b);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime)/1000000;
        System.out.println("The sum is: " + ans + ", Time taken: " + duration + "ms");
    }
}