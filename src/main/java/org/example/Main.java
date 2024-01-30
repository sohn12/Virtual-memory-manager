package org.example;

public class Main {

    private static void printSumTiming(Process p) throws Exception {
        long startTime = System.nanoTime();
        int ans = p.sum("x", "y");
        long endTime = System.nanoTime();

        long duration = (endTime - startTime)/1000000;
        System.out.println("The sum is: " + ans + ", Time taken: " + duration);
    }
    public static void main(String[] args) {

        // 1. supports only int variables
        // 2. a process will have fixed memory
        // 3. only one page is allocated to process
        // 4. the corresponding frame is contiguous in memory

        Cpu cpu = Cpu.getInstance();
        try {
            Process p = cpu.addProcess(4);
            p.assign("x", 22);
            p.assign("y", 23);

            printSumTiming(p);

            cpu.doCaching(); // cache the variables in cpu

            printSumTiming(p);
            printSumTiming(p);
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }


    }
}