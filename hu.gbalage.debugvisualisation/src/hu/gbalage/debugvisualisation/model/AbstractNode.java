/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

import java.util.HashSet;
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
	
	protected Edge getOutEdgeForNode(Node node){
		for (Edge e : outs){
			if (e.getTo() == node) return e;
		}
		return null;
	}
	
	protected String getVarName(){
		if (ins.isEmpty()) return "noname";
		return ins.iterator().next().getName();
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
	
	protected void notifychange(){
		for(NodeChangeListener listener : listeners)
			listener.changed();
	}
	
}