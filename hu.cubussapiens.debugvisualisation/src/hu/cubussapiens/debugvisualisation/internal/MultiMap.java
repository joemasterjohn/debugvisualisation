package hu.cubussapiens.debugvisualisation.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A MultiMap is a map, which maps a Key to a set of values
 * 
 * Note that this class is a subclass of HashMap<K, Set<V>>
 * 
 * @param <K> Type of key
 * @param <V> Type of elements in the sets
 */
public class MultiMap<K, V> extends HashMap<K, Set<V>> {

	private static final long serialVersionUID = -1017014387761848092L;

	/**
	 * Add a value to the set identified by the given key.
	 * 
	 * @param key
	 * @param value
	 */
	public void add(K key, V value) {
		if (!containsKey(key))
			put(key, new HashSet<V>());
		get(key).add(value);
	}

}
