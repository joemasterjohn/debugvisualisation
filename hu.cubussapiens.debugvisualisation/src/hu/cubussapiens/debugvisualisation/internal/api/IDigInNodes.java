/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.api;

import java.util.Collection;

/**
 * Interface to enable the selection of the root nodes
 */
public interface IDigInNodes {

	/**
	 * Set the given collection nodes to be the root nodes of the graph
	 * 
	 * @param nodes
	 */
	public void digIn(Collection<Object> nodes);

	/**
	 * Show the real root elements
	 */
	public void showRoot();

}
