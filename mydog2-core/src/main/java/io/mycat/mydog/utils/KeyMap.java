package io.mycat.mydog.utils;

import java.util.LinkedHashMap;

public class KeyMap<V> extends LinkedHashMap<String, V> {

	private static final long serialVersionUID = 5208191200124059538L;

	@Override
	public V put(String key, V value) {

		key = null == key ? "" : key.toLowerCase();

		return super.put(key, value);
	}
	
	@Override
	public V get(Object key) {
		key = null == key ? "" : key.toString().toLowerCase();
		V v = super.get(key);
		return v ; 
	}
}
