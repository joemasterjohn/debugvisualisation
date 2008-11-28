/**
 * 
 */
package hu.gbalage.debugvisualisation.view;

import hu.gbalage.debugvisualisation.model.Edge;
import hu.gbalage.debugvisualisation.model.Node;

/**
 * A presentation is supposed to display the graph described
 * in the model. The model is notified about each changes on
 * the model.
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface IDebugTreePresentation {

	/**
	 * A node is added to the model
	 * @param node
	 */
	public void addNode(Node node);
	
	/**
	 * A node is removed from the model
	 * @param node
	 */
	public void removeNode(Node node);
	
	/**
	 * An edge is added to the model
	 * @param edge
	 * @param from
	 * @param to
	 */
	public void addEdge(Edge edge, Node from, Node to);
	
	/**
	 * An edge is removed from the model
	 * @param edge
	 */
	public void removeEdge(Edge edge);
	
	/**
	 * The presentation should be refreshed
	 */
	public void refresh();
	
}
