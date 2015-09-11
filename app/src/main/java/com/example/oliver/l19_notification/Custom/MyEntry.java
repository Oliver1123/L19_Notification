package com.example.oliver.l19_notification.Custom;

import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by oliver on 11.09.15.
 */
public class MyEntry<K, V> implements Map.Entry<K, V> {
    private K mKey;
    private V mValue;

    public MyEntry (@NonNull K _key, V _value) {
        mKey = _key;
        mValue = _value;
    }

    @Override
    public K getKey() {
        return mKey;
    }

    @Override
    public V getValue() {
        return mValue;
    }

    @Override
    public V setValue(V object) {
        V oldValue = mValue;
        mValue = object;
        return oldValue;
    }

    @Override
    public String toString() {
        return "key: " + mKey + ", value: " + mValue;
    }
}
