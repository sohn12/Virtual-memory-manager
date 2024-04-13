package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MemoryManager {
    private static final int PAGE_SIZE = Constants.PAGE_SIZE;
    private final Map<Integer, Integer> pageTable;
    private final boolean[] availableFrames;

    private int getFramesFromSize(int size) {
        return (int) Math.ceil((double) size / PAGE_SIZE);
    }

    private int getMaxSizeFromFrames(int frames) {
        return frames * PAGE_SIZE;
    }

    public boolean isMemoryAvailable(int requiredMemory) {
        try {
            getAvailableFrameIndex(requiredMemory);
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    public MemoryManager() {
        availableFrames = new boolean[Constants.TOTAL_PAGES];
        Arrays.fill(availableFrames, Boolean.TRUE);
        pageTable = new HashMap<>();
    }

    private boolean hasEnoughMemory(int frameIndex, int frames) {
        int availableFramesFromIndex = 0;
        while(frameIndex < availableFrames.length && availableFrames[frameIndex]) {
            availableFramesFromIndex++;
            frameIndex++;
        }
        return availableFramesFromIndex >= frames;
    }

    private void allocateMemoryToProcess(int processId, int frameIndex, int size) throws Exception {
        int framesNeeded = getFramesFromSize(size);
        if (hasEnoughMemory(frameIndex, framesNeeded)) {
            pageTable.put(processId, frameIndex*PAGE_SIZE);
            for(int i = 0; i < framesNeeded && frameIndex + i < availableFrames.length; i++) {
                availableFrames[frameIndex + i] = false;
            }
        } else {
            throw new Exception("Out of memory");
        }
    }

    private void deallocateMemoryOfTheProcess(Process process, int frameIndex) {
        int frames = getFramesFromSize(process.getMemorySize());
        int i = 0;
        while(i < frames && (frameIndex + i) < availableFrames.length) {
            availableFrames[frameIndex + i] = true;
            i++;
        }
        pageTable.remove(process.getProcessId());
    }
    private int getAvailableFrameIndex(int size) throws Exception {
        int frames = getFramesFromSize(size);
        for(int idx=0; idx<availableFrames.length; idx++) {
            if(availableFrames[idx]) {
                int i = idx;
                int consecutiveFrames = 0;
                while(i < availableFrames.length && availableFrames[i++]) {
                    if(++consecutiveFrames >= frames) return idx;
                }
            }
        }
        throw new Exception("Out of memory");
    }

    private void mmuDelay() {
        try {
            Thread.sleep(Constants.MMU_DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int generateProcessId() {
        int min = 10000;
        int max = 99999;
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public int getFrameNumber(int processId) {
        mmuDelay();
        if(!pageTable.containsKey(processId)) {
            System.out.println("reading failed for: " + processId);
        }
        return pageTable.get(processId);
    }

    public void terminateProcess(Process process) {
        int frameIndexInRam = pageTable.getOrDefault(process.getProcessId(), -1);
        if(frameIndexInRam == -1) {
            return;
        }

        deallocateMemoryOfTheProcess(process, frameIndexInRam/PAGE_SIZE);
    }

    public int addProcess(int requiredMemory) throws Exception {
        int processId = generateProcessId();
        allocateMemoryToProcess(processId, getAvailableFrameIndex(requiredMemory), requiredMemory);
        return processId;
    }
}
