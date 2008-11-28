/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

/**
 * Values of this enumeration indicates the state of a node. 
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public enum NodeState {

	/**
	 * This is the state of the root node in the tree.
	 * The root node is always open, and cannot be closed.
	 */
	Root,
	
	/**
	 * A primitive node hasn't got any child nodes, thus it
	 * can't be opened.
	 */
	Primitive,
	
	/**
	 * This state indicates a node which is currently open, but
	 * it can be closed by the user.
	 */
	Open,
	
	/**
	 * This state indicates a node which is currently closed, but
	 * can be opened by the user.
	 */
	Closed
	
}
