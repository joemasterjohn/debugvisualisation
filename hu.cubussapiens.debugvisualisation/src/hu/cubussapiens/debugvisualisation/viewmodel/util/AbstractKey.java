/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel.util;

import hu.cubussapiens.debugvisualisation.viewmodel.IPropertyKey;

/**
 * @param <T>
 * 
 */
public class AbstractKey<T> implements IPropertyKey<T> {

	private final String key;

	/**
	 * 
	 * @param key
	 */
	public AbstractKey(String key) {
		this.key = key;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractKey<?>) {
			return ((AbstractKey<?>) obj).key.equals(key);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

}
