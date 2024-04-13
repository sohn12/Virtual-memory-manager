package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MemoryManager {
    private static final int PAGE_SIZE = Constants.PAGE_SIZE;
    private static MemoryManager memoryManager = null;
    private final Map<Integer, Integer> pageTable = new HashMap<>();
    private final boolean[] availableFrames = new boolean[Constants.TOTAL_PAGES];

    private int getFramesFromSize(int size) {
        return (int) Math.ceil((double) size / PAGE_SIZE);
    }

    private int getMaxSizeFromFrames(int frames) {
        return frames * PAGE_SIZE;
    }

    public int availableMemory() {
        int size = 0;
        try {
            int idx = getAvailableFrameIndex();
            while(availableFrames[idx++]) {
                size++;
            }
        }catch (Exception e) {
        }
        return size;
    }

    private MemoryManager() {
        Arrays.fill(availableFrames, Boolean.TRUE);
    }

    private boolean hasEnoughMemory(int frameIndex, int frames) {
        int availableFramesFromIndex = 0;
        while(frameIndex < availableFrames.length && availableFrames[frameIndex]) {
            availableFramesFromIndex++;
            frameIndex++;
        }
        return availableFramesFromIndex >= frames;
    }

    private void allocateMemoryToProcess(int processId, int frameIndex, int size) {
        int framesNeeded = getFramesFromSize(size);
        if (hasEnoughMemory(frameIndex, framesNeeded)) {
            pageTable.put(processId, frameIndex*PAGE_SIZE);
            for(int i = 0; i < framesNeeded && frameIndex + i < availableFrames.length; i++) {
                availableFrames[frameIndex + i] = false;
            }
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
    private int getAvailableFrameIndex() throws Exception {
        int idx = 0;
        for(boolean isFrameAvailable: availableFrames) {
            if(isFrameAvailable) {
                return idx;
            }
            idx++;
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

    public int getFrameNumber(int page) {
        mmuDelay();
        return pageTable.get(page);
    }

    public void killProcess(Process process) {
        int frameIndexInRam = pageTable.getOrDefault(process.getProcessId(), -1);
        if(frameIndexInRam == -1) {
            return;
        }

        deallocateMemoryOfTheProcess(process, frameIndexInRam/PAGE_SIZE);
    }

    public int addProcess(int requiredMemory) throws Exception {
        int processId = generateProcessId();
        allocateMemoryToProcess(processId, getAvailableFrameIndex(), requiredMemory);
        return processId;
    }

    public static MemoryManager getInstance() {
        if(memoryManager == null) {
            memoryManager = new MemoryManager();
        }
        return memoryManager;
    }
}
