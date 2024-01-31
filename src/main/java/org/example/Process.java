package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Process {
    private final int processId;
    private final int memorySize;
    private final Cpu cpu;
    private String status = "new";

    private final Map<String, Integer> variables = new HashMap<>();

    public Process(Cpu cpu, int processId, int memorySize) {
        this.cpu = cpu;
        this.processId = processId;
        this.memorySize = memorySize;
    }

    public void terminateProcess() {
        this.status = "terminated";
    }
    public int getProcessId() {
        return processId;
    }
    public int getMemorySize() {
        return memorySize;
    }

    private int getVariableValue(String a) throws Exception {
        int idx = variables.getOrDefault(a, -1);
        if(idx == -1) {
            throw new RuntimeException("Variable is not declared");
        }
        return cpu.getValue(processId, idx);
    }

    public void assign(String variable, int value) throws Exception {
        if(Objects.equals(status, "terminated")) {
            throw new Exception("Cannot perform action on a terminated process");
        }

        if (variables.getOrDefault(variable, -1) == -1) {
            if (variables.size() == memorySize) {
                throw new RuntimeException("Memory is full");
            }
            variables.put(variable, variables.size());
        }
        cpu.addValue(processId, variables.get(variable), value);
    }

    public int sum(String a, String b) throws Exception {
        if(Objects.equals(status, "terminated")) {
            throw new Exception("Cannot perform action on a terminated process");
        }
        return getVariableValue(a) + getVariableValue(b);
    }
}
