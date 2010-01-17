/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel;

import hu.cubussapiens.debugvisualisation.viewmodel.util.AbstractKey;

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
