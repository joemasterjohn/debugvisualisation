/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

/**
 * @author balage
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
	 * Indicates that the input is refreshed. This means that the viewer
	 * should rebuild the graph completely.
	 */
	public void refreshed();
	
}
