/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

/**
 * This interface will be given to the viewer as an input. An IDebugContextInput 
 * handles the currently visible stack frame got from the debug context. This object
 * is responsible to handle the variables and values as edges and nodes.
 * 
 * An IVariable in the debug context means an edge in the graph as it connects its
 * parent value (or the stack local context) with a value, which can be a simple
 * primitive-type value, or an object with a unique id. Every object with a unique id
 * has its own node (if visible), but primitive typed values are handled as parameters
 * of a node.
 *
 * Every aspect of graph-likeness of the model is handled by this object for example
 * this object can handle that a node can be closed, opened (hiding or showing 
 * referenced nodes). 
 *
 */
public interface IDebugContextInput {

	//---------------------------------------------------------
	//Listener-related methods
	//---------------------------------------------------------
	
	/**
	 * Register a listener
	 * @param listener
	 */
	public void addDCInputChangeListener(IDCInputChangeListener listener);
	
	/**
	 * Unregister a listener
	 * @param listener
	 */
	public void removeDCInputChangeListener(IDCInputChangeListener listener);
	
	//---------------------------------------------------------
	//Getters for visible objects
	//---------------------------------------------------------
	
	/**
	 * List all node, which is currently visible
	 * @return
	 */
	public Set<Integer> getVisibleNodes();
	
	/**
	 * Return all variables, whose value is the object represented by the given node
	 * @param node
	 * @return
	 */
	public Set<IVariable> getReferencesForNode(Integer node);
	
	/**
	 * Return all variables which are child variables of the node given by "from" and
	 * references of the node given by "to".
	 * @param from
	 * @param to
	 * @return
	 */
	public Set<IVariable> getRelations(Integer from, Integer to);
	
	//------------------------------------------------------------------
	//Methods related to node state
	//----------------------------------------------------------------
	
	/**
	 * Checks whether the given node is currently open (child nodes are visible)
	 * @param node
	 * @return
	 */
	public boolean isOpen(Integer node);
	
	/**
	 * Opens the node if not, and close it if it's open
	 * @param node
	 */
	public void toggleOpen(Integer node);
	
	/**
	 * Checks whether the given node can be opened. This method returns true even the node 
	 * is already opened.
	 * @param node
	 * @return
	 */
	public boolean canOpen(Integer node);
	
	/**
	 * Get constant parameters for the value represented by the given node
	 * @param node
	 * @return
	 */
	public String[] getParams(Integer node);
	
	/**
	 * Hide a node from the graph
	 * @param node
	 */
	public void hideNode(Integer node);
	
	/**
	 * Set all previously hidden child nodes of the given node visible
	 * @param node
	 */
	public void showHiddenChildNodes(Integer node);
	
	//------------------------------------------------------------------
	//Other methods
	//------------------------------------------------------------------
	
	/**
	 * Rebuild data. Need to be called when the underlying debug context is changed.
	 */
	public void refresh() throws DebugException;
	
}
