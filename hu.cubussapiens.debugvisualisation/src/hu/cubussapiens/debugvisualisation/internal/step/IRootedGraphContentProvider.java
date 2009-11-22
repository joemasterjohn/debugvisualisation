/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step;

import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;

/**
 * An interface, which provides elements of a graph. Every separately
 * weakly-connected graph components are identified by so-called roots, which
 * are simple nodes, except that every node in a component can be reached from
 * the root node of the component.
 */
public interface IRootedGraphContentProvider extends IAdaptable {

	/**
	 * Get the root nodes of the separated components
	 * 
	 * @return root nodes of the separated components of the graph
	 */
	public Collection<Object> getRoots();

	/**
	 * Get all directed edges which has the source equals to the given node
	 * 
	 * @param node
	 * @return collection of edges
	 */
	public Collection<Object> getEdges(Object node);

	/**
	 * Get the target node of the given edge
	 * 
	 * @param edge
	 * @return the target node of the edge
	 */
	public Object getEdgeTarget(Object edge);

	/**
	 * Clear all cached data
	 */
	public void clearCache();
}
