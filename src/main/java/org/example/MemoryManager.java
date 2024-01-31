package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MemoryManager {

    private static MemoryManager memoryManager = null;
    private final Map<Integer, Integer> addressLookup = new HashMap<>();
    private final boolean[] availableFrames = new boolean[8]; // 32 integers space in RAM

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
        int framesNeeded = size / 4;
        if (hasEnoughMemory(frameIndex, framesNeeded)) {
            addressLookup.put(processId, frameIndex*4);
            for(int i = 0; i < framesNeeded && frameIndex + i < availableFrames.length; i++) {
                availableFrames[frameIndex + i] = false;
            }
        }

    }

    private void deallocateMemoryOfTheProcess(Process process, int frameIndex) {
        int size = process.getMemorySize() / 4;
        int i = 0;
        while(i < size && (frameIndex + i) < availableFrames.length) {
            availableFrames[frameIndex + i] = true;
            i++;
        }
        addressLookup.remove(process.getProcessId());
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
            Thread.sleep(500);
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
        return addressLookup.get(page);
    }


    public void killProcess(Process process) {
        int frameIndex = addressLookup.getOrDefault(process.getProcessId(), -1);
        if(frameIndex == -1) {
            return;
        }

        deallocateMemoryOfTheProcess(process, frameIndex/4);
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
