package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Cpu {
    private final Map<Integer, Integer> tlb = new HashMap<>();

    private final MemoryManager mmu;
    private boolean doCache = false;
    private final Map<Integer, Integer> cache = new HashMap<>();

    private final Set<Integer> activeProcesses = new HashSet<>();

    private final RAM ram;
    private static Cpu cpu = null;

    private Cpu(RAM ram, MemoryManager mmu) {
        this.ram = ram;
        this.mmu = mmu;
    }

    public void printRam() {
        ram.printRam();
    }

    public void doCaching() {
        doCache = true;
    }

    public Process addProcess(int requiredMemory) throws Exception {
        Process p = new Process(this, mmu.addProcess(requiredMemory), requiredMemory);
        activeProcesses.add(p.getProcessId());
        return p;
    }

    public void killProcess(Process process) {
        if(activeProcesses.contains(process.getProcessId())) {
            activeProcesses.remove(process.getProcessId());
            mmu.killProcess(process);
            process.terminateProcess();
        }
    }

    public void addValue(int processId, int mem, int value) throws Exception {
        ram.addValueToRam(new MemoryLocation(mmu.getFrameNumber(processId), mem), value);
    }

    public int getValue(int processId, int mem) throws Exception {
        int frame = tlb.getOrDefault(processId, -1);
        if(frame == -1) {
            System.out.println("tlb miss");
            frame = mmu.getFrameNumber(processId);
            tlb.put(processId, frame);
        } else {
            System.out.println("tlb hit");
        }
        if(cache.containsKey(frame+mem)) {
            System.out.println("reading from cache");
            return cache.get(frame+mem);
        }
        System.out.println("reading from memory");

        int value = ram.getValueFromRam(new MemoryLocation(frame, mem));
        if (doCache) {
            cache.put(frame + mem, value);
        }
        return value;
    }

    public static Cpu getInstance() {
        if(cpu == null) {
            cpu = new Cpu(RAM.getInstance(), MemoryManager.getInstance());
        }
        return cpu;
    }

}
