/**
 * 
 */
package hu.gbalage.debugvisualisation.view;

import hu.gbalage.debugvisualisation.model.Edge;
import hu.gbalage.debugvisualisation.model.Node;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface IDebugTreePresentation {

	public void addNode(Node node);
	
	public void removeNode(Node node);
	
	public void addEdge(Edge edge, Node from, Node to);
	
	public void removeEdge(Edge edge);
	
	public void refresh();
	
}
