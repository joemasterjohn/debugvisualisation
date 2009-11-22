/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.api;

import java.util.Collection;

/**
 * Adapter interface for filtering primitive parameters of nodes
 */
public interface INodeParameters {

	/**
	 * Get the parameters of the given node
	 * 
	 * @param node
	 * @return the parameters of the given node
	 */
	public Collection<Object> getParameters(Object node);

}
