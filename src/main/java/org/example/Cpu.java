package org.example;

import java.util.HashMap;
import java.util.Map;

public class Cpu {
    private final Map<Integer, Integer> tlb = new HashMap<>();

    private final MemoryManager mmu;

    private final RAM ram;
    private static Cpu cpu = null;

    private Cpu(RAM ram, MemoryManager mmu) {
        this.ram = ram;
        this.mmu = mmu;
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
        return ram.getValueFromRam(new MemoryLocation(frame, mem));
    }

    public static Cpu getInstance() {
        if(cpu == null) {
            cpu = new Cpu(RAM.getInstance(), MemoryManager.getInstance());
        }
        return cpu;
    }

}
