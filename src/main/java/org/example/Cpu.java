package org.example;

import java.util.*;

public class Cpu {
    private final LRUMap tlb;
    private final MemoryManager mmu;
    private boolean doCache = false;
    private final LRUMap cache;
    private final Set<Integer> activeProcesses = new HashSet<>();
    private final RAM ram;

    public Cpu(int tlbLimit, boolean showUI) {
        this.ram = new RAM(showUI);
        this.mmu = new MemoryManager();
        tlb = new LRUMap(tlbLimit);
        cache = new LRUMap(Constants.CACHE_SIZE);
    }

    public Cpu(boolean showUI) {
        this(16, showUI);
    }

    public void printRam() {
        ram.printRam();
    }

    public void doCaching() {
        doCache = true;
    }

    public boolean isMemoryAvailable(int requiredMemory) {
        return mmu.isMemoryAvailable(requiredMemory);
    }

    public void doNotCache() {
        doCache = false;
        cache.clear();
    }

    public Process addProcess(int requiredMemory) throws Exception {
        Process p = new Process(this, mmu.addProcess(requiredMemory), requiredMemory);
        activeProcesses.add(p.getProcessId());
        return p;
    }

    public void invalidateProcessCache(Process process) {
        int frameIndex = getFrameIndex(process.getProcessId());
        int size = process.getMemorySize();
        for(int i=0; i < frameIndex+size; i++) {
            cache.remove(frameIndex+i);
        }
    }

    public void terminateProcess(Process process) {
        if(activeProcesses.contains(process.getProcessId())) {
            invalidateProcessCache(process);
            activeProcesses.remove(process.getProcessId());
            ram.clearValue(mmu.getFrameNumber(process.getProcessId()), process.getMemorySize());
            mmu.terminateProcess(process);
            process.terminate();
        }
    }

    public void addValue(int processId, int mem, int value) throws Exception {
        ProfilerStatistics.incrementQueryCount();
        ram.addValueToRam(new MemoryLocation(getFrameIndex(processId), mem), value);
    }

    private int getFrameIndex(int processId) {
        int frame = tlb.getOrDefault(processId, -1);
        if(frame == -1) {
            ProfilerStatistics.incrementTlbMisses();
            frame = mmu.getFrameNumber(processId);
            tlb.put(processId, frame);
        } else {
            ProfilerStatistics.incrementTlbHits();
        }
        return frame;
    }

    public int getValue(int processId, int mem) throws Exception {
        ProfilerStatistics.incrementQueryCount();
        int frame = getFrameIndex(processId);
        if(cache.containsKey(frame+mem)) {
            ProfilerStatistics.incrementCacheHits();
            return cache.get(frame+mem);
        }

        ProfilerStatistics.incrementCacheMisses();
        int value = ram.getValueFromRam(new MemoryLocation(frame, mem));
        if (doCache) {
            cache.put(frame + mem, value);
        }
        return value;
    }
}
