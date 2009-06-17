/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

/**
 * Listens changes on an IDebugContextInput
 *
 */
public interface IDCInputChangeListener {

	/**
	 * Indicates that a node is opened or closed
	 * @param node
	 * @param open the node is now open if true, and it's closed if false
	 */
	public void openStateChanged(Integer node, boolean open);
	
	/**
	 * Indicates that a node is hidden or shown
	 * @param node
	 * @param visible
	 */
	public void visibilityChanged(Integer node, boolean visible);
	
	/**
	 * Indicates that the input is refreshed. This means that the viewer
	 * should rebuild the graph completely.
	 */
	public void refreshed();
	
}
