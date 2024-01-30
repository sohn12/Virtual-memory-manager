package org.example;

import java.util.HashMap;
import java.util.Map;

public class Process {

    private final int processId;
    private final Cpu cpu;

    private final Map<String, Integer> variables = new HashMap<>();

    public Process(Cpu cpu, int processId) {
        this.cpu = cpu;
        this.processId = processId;
    }

    private int getVariableValue(String a) throws Exception {
        int idx = variables.getOrDefault(a, -1);
        if(idx == -1) {
            throw new RuntimeException("Variable is not declared");
        }
        return cpu.getValue(processId, idx);
    }


    public void assign(String variable, int value) throws Exception {
        if (variables.getOrDefault(variable, -1) == -1) {
            if (variables.size() == 4) {
                throw new RuntimeException("Memory is full");
            }
            variables.put(variable, variables.size());
        }
        cpu.addValue(processId, variables.get(variable), value);
    }

    public int sum(String a, String b) throws Exception {
        return getVariableValue(a) + getVariableValue(b);
    }
}
