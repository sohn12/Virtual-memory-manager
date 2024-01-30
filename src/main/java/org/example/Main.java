package org.example;


public class Main {
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
            long startTime1 = System.nanoTime();
            int ans = p.sum("x", "y");
            long endTime1 = System.nanoTime();

            long duration1 = (endTime1 - startTime1)/1000000;
            System.out.println("The sum is: " + ans + ", Time taken: " + duration1);

            long startTime2 = System.nanoTime();
            ans = p.sum("x", "y");
            long endTime2 = System.nanoTime();

            long duration2 = (endTime2 - startTime2)/1000000;
            System.out.println("The sum is: " + ans + ", Time taken: " + duration2);
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}