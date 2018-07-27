package com.exuberant.rest.util;

import java.util.*;

public class MultiValueMap<K, T> {

    private final Map<K, Set<T>> mMap = new LinkedHashMap<>();

    public Set<Map.Entry<K, Set<T>>> getAll() {
        return mMap.entrySet();
    }

    public void put(K key, T value) {
        Set<T> values = mMap.get(key);
        if (values == null) {
            values = new HashSet<>();
            mMap.put(key, values);
        }
        values.add(value);
    }

    public Set<Map.Entry<K, Set<T>>> entries() {
        Set<Map.Entry<K, Set<T>>> entries = mMap.entrySet();
        return entries;
    }

    public Set<T> get(String key) {
        return mMap.get(key);
    }

    /*public T replace(String key, T oldValue, T newValue) {
        key = key.toLowerCase();
        Set<T> values = mMap.get(key);
        if (values == null) {
            return null;
        }

        for (int n = 0; n < values.size(); n++) {
            T value = values.contains(oldValue);
            if (value == oldValue) {
                values.set(n, newValue);
                return value;
            }
        }
        return null;
    }*/

    public T remove(String key, T value) {
        key = key.toLowerCase();
        Set<T> values = mMap.get(key);
        if (values == null) {
            return null;
        }

        T result = null;
        Iterator<T> valueIterator = values.iterator();
        while (valueIterator.hasNext()) {
            if (valueIterator.next() == value) {
                valueIterator.remove();
                result = value;
                break;
            }
        }
        if (values.isEmpty()) {
            mMap.remove(key);
        }
        return result;
    }

    public Set<K> keySet(){
        return this.mMap.keySet();
    }

    public T remove(T value) {
        T result = null;
        Iterator<Map.Entry<K, Set<T>>> iterator = mMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Set<T> values = iterator.next().getValue();
            Iterator<T> valueIterator = values.iterator();
            while (valueIterator.hasNext()) {
                if (valueIterator.next() == value) {
                    valueIterator.remove();
                    result = value;
                    break;
                }
            }
            if (result != null) {
                if (values.isEmpty()) {
                    iterator.remove();
                }
                break;
            }
        }
        return result;
    }

    public List<T> values() {
        List<T> allValues = new ArrayList<>(mMap.size());
        for (Set<T> values : mMap.values()) {
            for (T value : values) {
                allValues.add(value);
            }
        }
        return allValues;
    }

    public T findSingletonValue() {
        if (mMap.size() != 1) {
            throw new IllegalArgumentException("Map is not a single entry map");
        }
        Set<T> values = mMap.values().iterator().next();
        if (values.size() != 1) {
            throw new IllegalArgumentException("Map is not a single entry map");
        }
        return values.iterator().next();
    }

    @Override
    public String toString() {
        return this.keySet().toString();
    }
}
