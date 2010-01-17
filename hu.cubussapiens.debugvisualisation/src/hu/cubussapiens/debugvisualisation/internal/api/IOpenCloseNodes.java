/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.api;

import hu.cubussapiens.debugvisualisation.internal.step.input.OpenCloseNodeState;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;

/**
 * Adapter interface to manage open/closed nodes
 */
public interface IOpenCloseNodes {

	/**
	 * Retrieve the current state of the given node
	 * 
	 * @param node
	 * @return the state of the node
	 */
	public OpenCloseNodeState getNodeState(IDVValue node);

	/**
	 * Opens a closed node if it can be opened, and close it is opened and
	 * allowed to be closed.
	 * 
	 * @param node
	 */
	public void toggleOpenNode(IDVValue node);

}
