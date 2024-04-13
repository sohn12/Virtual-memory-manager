package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Profiler {
    private static final int LIMIT = 100;
    private static final int PROCESS_MEMORY_LIMIT = 8;
    private static final int BOUND = 10000;
    private final List<Process> processes;
    private final Cpu cpu;
    public Profiler(Cpu cpu) {
        this.cpu = cpu;
        processes = new ArrayList<>();
    }

    private void executeRandomProcess() {
        if (!processes.isEmpty()) {
            int randomIndex = new Random().nextInt(processes.size());
            Process processToExecute  = processes.get(randomIndex);
            try {
                int sum = processToExecute.sum("a", "b", "c","d");
                if(processToExecute.isEqual("e", sum)) {
//                    System.out.println("Process executed successfully");
                } else {
                    System.out.println("Process returned incorrect results");
                }

            } catch (Exception e) {
//                throw new RuntimeException(e);
            }
        } else {
//            System.out.println("No active processes to terminate.");
        }
    }

    private void terminateRandomProcess() {
        if (!processes.isEmpty()) {
            int randomIndex = new Random().nextInt(processes.size());
            Process processToTerminate = processes.get(randomIndex);
            processes.remove(randomIndex);
            cpu.terminateProcess(processToTerminate);
        } else {
//            System.out.println("No active processes to terminate.");
        }
    }
    public long run(boolean doCache) throws Exception {
        ProfilerStatistics.clear();
        if(doCache) cpu.doCaching();
        else cpu.doNotCache();
        long startTime = System.nanoTime();
        for(int i=0; i<LIMIT; i++) {
            if(cpu.availableMemory() < PROCESS_MEMORY_LIMIT) {
                terminateRandomProcess();
            }
            Process p = cpu.addProcess(5);
            int a = new Random().nextInt(BOUND);
            int b = new Random().nextInt(BOUND);
            int c = new Random().nextInt(BOUND);
            int d = new Random().nextInt(BOUND);
            p.assign("a", a);
            p.assign("b", b);
            p.assign("c", c);
            p.assign("d", d);
            p.assign("e", a+b+c+d);

            processes.add(p);
            executeRandomProcess();
        }
        while(!processes.isEmpty()) {
            executeRandomProcess();
            terminateRandomProcess();
        }
        ProfilerStatistics.print();
        return (System.nanoTime() - startTime)/1000000;
    }
}
