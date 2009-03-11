/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public abstract class AbstractNode implements Node {

	/**
	 * Listeners on node changes
	 */
	protected final Set<NodeChangeListener> listeners = new HashSet<NodeChangeListener>();
	
	/**
	 * Set of in-edges to this node
	 */
	protected final Set<Edge> ins = new HashSet<Edge>();
	
	/**
	 * Set of out-edges from this node
	 */
	protected final Set<Edge> outs = new HashSet<Edge>();
	
	/**
	 * The node is open, if the child nodes are visible
	 */
	protected boolean open = false;
	
	/**
	 * Lookup the out edges for the given node.
	 * @param node
	 * @return the out edge, which ends at the given node, or null
	 * if an appropriate edge cannot be found.
	 */
	protected Edge getOutEdgeForNode(Node node,String varname){
		for (Edge e : outs){
			if ((e.getTo() == node)&&(e.getName().equals(varname))) 
				return e;
		}
		return null;
	}
	
	/**
	 * Ask the in edges for a variable name. If multiple in edges
	 * exists, than the first name is returned
	 * @return an applicable variable name.
	 */
	protected String getVarName(){
		if (ins.isEmpty()) return "noname";
		String name = "";
		for (Edge e : ins){
			name += ((name.equals("")) ? "" : ",") + e.getName();
			if (ins.size() > 1) e.showCaption();
			else e.hideCaption();
		}
		return name;
	}
	
	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#addIn(hu.gbalage.debugvisualisation.model.Edge)
	 */
	public void addIn(Edge edge) {
		ins.add(edge);
		notifychange();
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#addOut(hu.gbalage.debugvisualisation.model.Edge)
	 */
	public void addOut(Edge edge) {
		outs.add(edge);
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#dispose()
	 */
	public void dispose() {
		disposeOuts();
	}

	/**
	 * Dispose all outgoing edges
	 */
	protected void disposeOuts(){
		Set<Edge> outs2 = new HashSet<Edge>();
		outs2.addAll(outs);
		for(Edge e : outs2){
			e.dispose();
		}
		outs.clear();
	}
	
	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#isDisposable()
	 */
	public boolean isDisposable() {
		return ins.isEmpty();
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#isOpen()
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#removeIn(hu.gbalage.debugvisualisation.model.Edge)
	 */
	public void removeIn(Edge edge) {
		ins.remove(edge);
		if (isDisposable()) dispose();
		else notifychange();
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#removeOut(hu.gbalage.debugvisualisation.model.Edge)
	 */
	public void removeOut(Edge edge) {
		outs.remove(edge);
	}
	
	public void addNodeChangeListener(NodeChangeListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Notify all listeners about the change in this node.
	 */
	protected void notifychange(){
		for(NodeChangeListener listener : listeners)
			listener.changed();
	}
	
	public Map<String, Node> listChildNodes() {
		HashMap<String, Node> childs = new HashMap<String, Node>();
		for(Edge e : outs){
			childs.put(e.getName(), e.getTo());
		}
		return childs;
	}
	
	public Set<Edge> listInEdges() {
		return ins;
	}
	
}
