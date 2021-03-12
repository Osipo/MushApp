package ru.crock.app.utils.structures;

import ru.crock.app.utils.structures.KeyValuePair;
import ru.crock.app.utils.structures.LinkedList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class LinkedDictionary<K,V> implements Iterable<Map.Entry<K,V>> {
    private int _count;
    private LinkedList<Map.Entry<K, V>> _entries;
    private boolean _overrideVals;

    public LinkedDictionary() {
        this._count = 0;
        this._entries = new LinkedList<Map.Entry<K, V>>();
        this._overrideVals = false;
    }

    public void setOverrideMode(boolean f) {
        this._overrideVals = f;
    }

    public boolean isOverrideValues() {
        return _overrideVals;
    }

    //MAKENULL
    public void Clear() {
        this._count = 0;
        this._entries.clear();
    }

    public int IndexOfKey(K key) {//HANDLE PARAM
        int idx = 1;
        if (key == null)
            return -1;
        for (Map.Entry<K, V> entry : _entries) {
            if (entry.getKey().equals(key)) {
                return idx;
            }
            idx += 1;
        }
        return -1;
    }

    //CONTAINS(M,key): BOOLEAN
    public boolean ContainsKey(K key) { //HANDLE PARAM
        return IndexOfKey(key) != -1;
    }

    public boolean Contains(Map.Entry<K, V> item) {
        if (item == null) {
            System.out.println("Error. ArgumentNullException");
            return false;
        }
        return ContainsKey(item.getKey());
    }

    public void put(K key, V value) {
        int idx = IndexOfKey(key);
        if (idx == -1) {
            _entries.append(new KeyValuePair<>(key, value));
            _count += 1;
        } else if (_overrideVals) {
            _entries.set(idx, new KeyValuePair<>(key, value));//same key but new value.
        }
    }

    public void Add(KeyValuePair<K, V> item) {
        if (item == null) {
            System.out.println("Error. ArgumentNullException");
            return;
        }
        put(item.getKey(), item.getValue());
    }

    //REMOVE pair.
    public boolean remove(K key) {
        int idx = IndexOfKey(key);
        if (idx == -1) {
            System.out.println("Error. Key not found.");
            return false;
        }
        _entries.removeAt(idx);
        _count -= 1;
        return true;
    }

    public boolean remove(KeyValuePair<K, V> item) {
        if (item == null) {
            System.out.println("Error. ArgumentNullException");
            return false;
        }
        return remove(item.getKey());
    }


    public V get(K key) {
        for (Map.Entry<K, V> entry : _entries) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean tryGet(K key, V value) {
        for (Map.Entry<K, V> entry : _entries) {
            if (entry.getKey().equals(key)) {
                value = entry.getValue();
                return true;
            }
        }
        value = null;
        return false;
    }


    public Collection<K> getKeys() {
        LinkedList<K> lks = new LinkedList<>();
        for (Map.Entry<K, V> entry : _entries) {
            lks.append(entry.getKey());
        }
        return lks;
    }

    public Collection<V> values() {
        LinkedList<V> lks = new LinkedList<>();
        for (Map.Entry<K, V> entry : _entries) {
            lks.add(entry.getValue());
        }
        return lks;
    }

    public void CopyTo(Map.Entry<K, V>[] array, int arrayIndex) {
        if (arrayIndex < 0 || arrayIndex >= array.length) {
            System.out.println("arrayIndex is out of range");
            return;
        }
        int i = arrayIndex;
        for (Map.Entry<K, V> entry : _entries) {
            array[i] = entry;
            i += 1;
            if (i == array.length)
                break;
        }
    }

    @Override
    public Iterator<Map.Entry<K,V>> iterator(){
        return _entries.iterator();
    }

    //EMPTY(M): BOOLEAN
    public boolean IsEmpty() {
        return _count == 0;
    }
}