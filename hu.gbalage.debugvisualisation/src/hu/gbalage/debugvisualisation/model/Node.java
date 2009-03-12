package hu.gbalage.debugvisualisation.model;

import java.util.Map;
import java.util.Set;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * Defines a node in the abstract variable graph. The variable graph
 * is a directed graph, where 
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface Node {

	/**
	 * @return a representative text of the value wrapped by
	 * this node 
	 */
	public String getCaption();
	
	/**
	 * @return the type string of the value wrapped by this node
	 */
	public String getType();
	
	/**
	 * The is disposable only if it's not the root node, and there
	 * is no edges ending at this node.
	 * @return true only if this node can be disposed
	 */
	public boolean isDisposable();
	
	/**
	 * Dispose the node if it is disposable.
	 */
	public void dispose();
	
	/**
	 * Tell this node that the given edge is ends at this node.
	 * @param edge
	 */
	public void addIn(Edge edge);
	
	/**
	 * Tell this node that the given edge is disposed.
	 * @param edge
	 * @note if by removing the given edge this node becomes
	 * disposable, the dispose() method is called automatically.
	 */
	public void removeIn(Edge edge);
	
	/**
	 * Tell this node that the given edge is begins at this node.
	 * @param edge 
	 */
	public void addOut(Edge edge);
	
	/**
	 * Tell this node that the given edge is disposed.
	 * @param edge
	 */
	public void removeOut(Edge edge);
	
	/**
	 * A node is unique only if it has a valid id, so it should be
	 * displayed only once, even there is more references to the
	 * value represented by this node.
	 * @return true if this node is unique
	 */
	public boolean isUnique();
	
	/**
	 * An integer ID uniquely identifies the represented value.
	 * There is a valid ID only if the value is unique, as told
	 * by the isUnique() function.   
	 * @return the unique ID of the value represented by this node.
	 * If the value is not unique, this function returns -1.
	 */
	public int getID();
	
	/**
	 * If a node is open, then all of its child nodes are displayed
	 * also.
	 * @return true if this node is open.
	 */
	public boolean isOpen();
	
	/**
	 * @return true if this node is visible
	 */
	public boolean isVisible();
	
	/**
	 * Hide this node if visible, and show if hidden. When a visible node
	 * is closed, all of the child nodes are getting closed and hid. 
	 */
	public void toggleVisibility();
	
	/**
	 * Open this node if it's closed, and close it if it's open.
	 * When an open node is closed, all of the child nodes are
	 * getting closed and hid. 
	 */
	public void toggleOpen();
	
	/**
	 * Apply a new value to this node.
	 * @param value
	 */
	public void changeValue(IValue value);
	
	/**
	 * Apply a set of variables as references to child values
	 * to this node.
	 * @param vars
	 * @note when changeValue(IValue) is called, this method is
	 * called automatically for value.getVariables().
	 */
	public void setVariables(IVariable[] vars);
	
	/**
	 * Call setVariables again on previously given variables
	 */
	public void refreshVariables();
	
	/**
	 * Add a listener, which gets a notification if any change
	 * happens on the node (value, state, open/close, etc..).
	 * @param listener
	 */
	public void addNodeChangeListener(NodeChangeListener listener);
	
	/**
	 * @return state of the node. 
	 * @see NodeState
	 */
	public NodeState getState();
	
	/**
	 * @return the Map of edge names pointig to child nodes
	 */
	public Map<String, Node> listChildNodes();
	
	/**
	 * @return a set of the "in"-edges
	 */
	public Set<Edge> listInEdges();
	
	/**
	 * Show all hidden child nodes
	 */
	public void showHiddenChildNodes();
}
