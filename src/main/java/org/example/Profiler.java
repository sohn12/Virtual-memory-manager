package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Profiler {
    private static final int LIMIT = 5;
    private static final int BOUND = 10000;
    private final String[] variables;
    private final List<Process> processes;
    private final Cpu cpu;
    public Profiler(Cpu cpu) {
        this.cpu = cpu;
        processes = new ArrayList<>();
        variables = new String[] {"first", "second", "third", "fourth"};
    }

    private void executeRandomProcess() throws Exception {
        if (!processes.isEmpty()) {
            int randomIndex = new Random().nextInt(processes.size());
            Process processToExecute  = processes.get(randomIndex);
            int sumOfStoredValues = processToExecute.sum(variables);
            if(!processToExecute.isEqual("sum", sumOfStoredValues)) {
                throw new Exception("Process returned incorrect results");
            }
        } else {
//            System.out.println("No active processes to terminate.");
        }
    }

    private void addNewProcess(int requiredMemorySize) throws Exception {
        Process p = cpu.addProcess(requiredMemorySize);

        int sum = 0;
        for(String variable: variables) {
            int randomVariableValue = new Random().nextInt(BOUND);
            p.assign(variable, randomVariableValue);
            sum += randomVariableValue;
        }
        p.assign("sum", sum);
        processes.add(p);
    }

    private void terminateRandomProcess() {
        if (!processes.isEmpty()) {
            int randomIndex = new Random().nextInt(processes.size());
            Process processToTerminate = processes.get(randomIndex);
            processes.remove(randomIndex);
            cpu.terminateProcess(processToTerminate);
        }
    }
    public long run(boolean doCache) throws Exception {
        ProfilerStatistics.clear();

        if(doCache) cpu.doCaching();
        else cpu.doNotCache();

        long startTime = System.nanoTime();
        for(int i=0; i<LIMIT; i++) {
            int requiredMemorySize = (int) ((Math.random() * (7)) + 7);
            while(!cpu.isMemoryAvailable(requiredMemorySize)) {
                terminateRandomProcess();
            }
            addNewProcess(requiredMemorySize);
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
