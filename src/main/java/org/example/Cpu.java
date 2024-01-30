package org.example;

import java.util.HashMap;
import java.util.Map;

public class Cpu {
    private final Map<Integer, Integer> tlb = new HashMap<>();

    private final MemoryManager mmu;
    private boolean doCache = false;
    private final Map<Integer, Integer> cache = new HashMap<>();

    private final RAM ram;
    private static Cpu cpu = null;

    private Cpu(RAM ram, MemoryManager mmu) {
        this.ram = ram;
        this.mmu = mmu;
    }

    public void doCaching() {
        doCache = true;
    }

    public Process addProcess(int requiredMemory) throws Exception {
        return new Process(this, mmu.addProcess(requiredMemory));
    }

    public boolean addValue(int processId, int mem, int value) throws Exception {
        return ram.addValueToRam(new MemoryLocation(mmu.getFrameNumber(processId), mem), value);
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
            return cache.get(frame+mem);
        }

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
