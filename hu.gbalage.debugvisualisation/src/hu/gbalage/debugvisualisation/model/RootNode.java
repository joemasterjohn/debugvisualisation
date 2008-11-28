/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

import org.eclipse.debug.core.model.IValue;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class RootNode extends ObjectNode {

	public RootNode(Model model) {
		super(model, 0, null);
		open = true;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#changeValue(org.eclipse.debug.core.model.IValue)
	 */
	@Override
	public void changeValue(IValue value) {
		//nothing to do
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#getCaption()
	 */
	@Override
	public String getCaption() {
		return "Local Scope";
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#getID()
	 */
	@Override
	public int getID() {
		return 0;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#getType()
	 */
	@Override
	public String getType() {
		return "null";
	}


	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#toggleOpen()
	 */
	@Override
	public void toggleOpen() {
		//root node must always be open
	}
	
	@Override
	public NodeState getState() {
		return NodeState.Root;
	}

}
