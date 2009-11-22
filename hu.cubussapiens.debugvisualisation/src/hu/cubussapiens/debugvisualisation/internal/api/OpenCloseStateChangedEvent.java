/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.api;

import hu.cubussapiens.debugvisualisation.internal.step.IGraphChangeEvent;

/**
 *
 */
public class OpenCloseStateChangedEvent implements IGraphChangeEvent {

	private final Object node;

	/**
	 * Indicate that the given node is changed
	 * 
	 * @param node
	 */
	public OpenCloseStateChangedEvent(Object node) {
		super();
		this.node = node;
	}

	/**
	 * Get the changed node
	 * 
	 * @return the target node of this event
	 */
	public Object getNode() {
		return node;
	}

}
