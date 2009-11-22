/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.api;

import java.util.Collection;

/**
 * Adapter interface to manage hidden nodes
 */
public interface IHiddenNodes {

	/**
	 * Hide the given nodes
	 * 
	 * @param nodes
	 */
	public void hideNodes(Collection<Object> nodes);

	/**
	 * Show all hidden child nodes of the given nodes
	 * 
	 * @param nodes
	 */
	public void showHiddenChildNodes(Collection<Object> nodes);

}
