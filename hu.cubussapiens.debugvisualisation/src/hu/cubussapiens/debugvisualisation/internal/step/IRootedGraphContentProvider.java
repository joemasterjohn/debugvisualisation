/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step;

import java.util.Collection;

/**
 * An interface, which provides elements of a graph. Every separately
 * weakly-connected graph components are identified by so-called roots, which
 * are simple nodes, except that every node in a component can be reached from
 * the root node of the component.
 */
public interface IRootedGraphContentProvider {

	/**
	 * Get the root nodes of the separated components
	 * 
	 * @return root nodes of the separated components of the graph
	 */
	public Collection<Object> getRoots();

	/**
	 * Get the nodes, which have a directed connection from the given node.
	 * 
	 * @param node
	 * @return the child nodes of the given node
	 */
	public Collection<Object> getChilds(Object node);

	/**
	 * Get all directed edge, which connects the given two nodes directed from
	 * nodea to nodeb.
	 * 
	 * @param nodea
	 * @param nodeb
	 * @return all directed edge, which connects nodea with nodeb.
	 */
	public Collection<Object> getEdge(Object nodea, Object nodeb);
}
