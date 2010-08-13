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
	 * Adds all the child nodes of the selected value. The method should not be
	 * called if the node is not shown.
	 * 
	 * @param node
	 *            a single node to show all the children of.
	 */
	public void addChildren(IDVValue node);

	/**
	 * Adds all the child nodes of the selected values. It is equivalent of
	 * calling the {@link #addChildren(IDVValue)} method with every member of
	 * the list.
	 * 
	 * @param nodes
	 *            a collection of nodes
	 */
	public void addChildren(Collection<IDVValue> nodes);

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
	 * Remove the given nodes from the root list together with its children.
	 * 
	 * @param nodes
	 */
	public void removeRoots(Collection<IDVValue> nodes);

	/**
	 * Remove the given nodes from the root list.
	 * 
	 * @param nodes
	 *            a collection of nodes to remove
	 * @param removeChildren
	 *            if set to true, all child elements of the nodes are also
	 *            removed
	 */
	public void removeRoots(Collection<IDVValue> nodes, boolean removeChildren);

}
