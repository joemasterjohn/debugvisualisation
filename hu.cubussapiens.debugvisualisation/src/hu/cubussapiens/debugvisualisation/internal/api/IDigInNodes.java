/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.api;

import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;

import java.util.Collection;

import org.eclipse.debug.core.model.IVariable;

/**
 * Interface to enable the selection of the root nodes
 */
public interface IDigInNodes {

	/**
	 * Set the given collection nodes to be the root nodes of the graph
	 * 
	 * @param nodes
	 */
	public void digIn(Collection<IDVValue> nodes);

	/**
	 * Add the given variables as root to the visualization
	 * 
	 * @param variables
	 */
	public void addVariables(Collection<IVariable> variables);

	/**
	 * Show the real root elements
	 */
	public void showRoot();

}
