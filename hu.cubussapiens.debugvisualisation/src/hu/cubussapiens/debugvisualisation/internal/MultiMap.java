/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author balage
 *
 */
public class MultiMap<K, V> extends HashMap<K, Set<V>> {

	private static final long serialVersionUID = -1017014387761848092L;

	public void add(K key, V value){
		if (!containsKey(key)) put(key, new HashSet<V>());
		get(key).add(value);
	}
	
}
