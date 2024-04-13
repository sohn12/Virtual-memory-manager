package org.example;
import java.util.LinkedHashMap;
import java.util.Set;

public class LRUMap
{
    LinkedHashMap<Integer, Integer> m;
    int capacity;
    LRUMap(int capacity){
        this.capacity = capacity;
        m = new LinkedHashMap<>();
    }
    public int get(int key){
        int ans = -1;
        if(m.containsKey(key)) {
            ans = m.get(key);
            m.remove(key);
            m.put(key, ans);
        }
        return ans;
    }
    public boolean containsKey(int key) {
        return m.containsKey(key);
    }
    public void clear() {
        m.clear();
    }
    public int getOrDefault(int key, int defaultValue) {
        return m.getOrDefault(key, defaultValue);
    }
    public void remove(int key) {
        m.remove(key);
    }
    public void put(int key, int value){
        m.remove(key);
        m.put(key, value);
        while(m.size() > capacity) {
            Set<Integer> s = m.keySet();
            int k = s.iterator().next();
            m.remove(k);
        }
    }
}

