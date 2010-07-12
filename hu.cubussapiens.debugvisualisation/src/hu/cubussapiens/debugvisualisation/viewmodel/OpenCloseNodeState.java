/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel;

/**
 * This enum describes the possible node states in the domain of opened-closed
 * meaning.
 */
public enum OpenCloseNodeState {

	/**
	 * This state marks a not-root node, which does not have any child nodes,
	 * therefore there is no meaning of open operation on this node.
	 */
	ChildLess,

	/**
	 * This node is opened, its child nodes are shown. This state also indicates
	 * that this node can be closed.
	 */
	Open,

	/**
	 * Though this node has child nodes, they're hidden, because this node is
	 * closed, but can be opened.
	 */
	Close

}
