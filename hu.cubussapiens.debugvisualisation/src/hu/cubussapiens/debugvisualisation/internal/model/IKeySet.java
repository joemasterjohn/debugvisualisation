/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.model;

import hu.cubussapiens.debugvisualisation.internal.model.impl.AbstractKey;

/**
 *
 */
public interface IKeySet {

	/**
	 * String property containing the name of the node
	 */
	public final static IPropertyKey<String> NODE_NAME = new AbstractKey<String>(
			"node name");

}
