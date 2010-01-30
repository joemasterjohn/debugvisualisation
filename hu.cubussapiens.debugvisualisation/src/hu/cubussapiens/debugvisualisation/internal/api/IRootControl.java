/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.api;

import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;

import java.util.Collection;

import org.eclipse.debug.core.model.IVariable;

/**
 * Interface to enable the selection of the root nodes
 */
public interface IRootControl {

	/**
	 * Set the given collection nodes to be the root nodes of the graph
	 * 
	 * @param nodes
	 */
	public void setRoots(Collection<IDVValue> nodes);

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

	/**
	 * Clear the root list
	 */
	public void clearVisualization();

	/**
	 * Remove the given nodes from the root list
	 * 
	 * @param nodes
	 */
	public void removeRoots(Collection<IDVValue> nodes);

}
