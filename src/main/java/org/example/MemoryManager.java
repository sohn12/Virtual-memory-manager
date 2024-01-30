package org.example;

import java.util.HashMap;
import java.util.Map;

public class MemoryManager {

    private static MemoryManager memoryManager = null;
    private final Map<Integer, Integer> addressLookup = new HashMap<>();
    private int availableMemory = 32;

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

    public int addProcess(int requiredMemory) throws Exception {
        if(requiredMemory > availableMemory) {
            throw new Exception("not enough memory");
        }

        int processId = generateProcessId();
        addressLookup.put(processId, 0);
        availableMemory-= requiredMemory;
        return processId;
    }

    public static MemoryManager getInstance() {
        if(memoryManager == null) {
            memoryManager = new MemoryManager();
        }
        return memoryManager;
    }
}
