/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class PrimitiveNode extends AbstractNode {

	protected IValue value;
	
	protected final Model model;
	
	public PrimitiveNode(Model model, IValue value){
		this.model = model;
		this.value = value;
	}
	
	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#getCaption()
	 */
	public String getCaption() {
		try {
			return getVarName()+" = "+value.getValueString();
		} catch (DebugException e) {
			e.printStackTrace();
			return "Error!";
		}
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#getID()
	 */
	public int getID() {
		return -1;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#getType()
	 */
	public String getType() {
		try {
			return value.getReferenceTypeName();
		} catch (DebugException e) {
			e.printStackTrace();
			return "Error!";
		}
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#isUnique()
	 */
	public boolean isUnique() {
		return false;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#toggleOpen()
	 */
	public void toggleOpen() {
		//nothing to do, a primitive node can't be opened
	}

	public void changeValue(IValue value) {
		this.value = value;
		notifychange();
	}

	public void setVariables(IVariable[] vars) {
		//a primitive node can't have variables.
	}

	@Override
	public void dispose() {
		model.disposeValueNode(this);
		super.dispose();
	}

	public NodeState getState() {
		return NodeState.Primitive;
	}
	
	public void toggleVisibility() {
		//Currently, primitive nodes are not used.
		//I delay the implementation of this feature
	}
	
	public boolean isVisible() {
		return true;
	}

	public void showHiddenChildNodes() {
		//this kind of node can't has child nodes, thus we do nothing here.	
	}
	
}
