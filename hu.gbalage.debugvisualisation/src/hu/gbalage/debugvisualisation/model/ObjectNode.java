/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

import hu.gbalage.debugvisualisation.ValueUtils;
import hu.gbalage.debugvisualisation.filters.IFilter;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class ObjectNode extends AbstractNode {

	protected final int ID;
	
	protected final Model model;
	
	protected IValue value;
	
	public ObjectNode(Model model,int ID,IValue value){
		this.ID = ID;
		this.model = model;
		this.value = value;
	}
	
	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#changeValue(org.eclipse.debug.core.model.IValue)
	 */
	public void changeValue(IValue value) {
		this.value = value;
		if (open){
			try {
				this.setVariables(value.getVariables());
			} catch (DebugException e) {
				e.printStackTrace();
			}
		}
		notifychange();
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#getCaption()
	 */
	public String getCaption() {
		return getVarName()+": "+ValueUtils.getValueString(value);
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#getID()
	 */
	public int getID() {
		return ID;
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
		return true;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#setVariables(org.eclipse.debug.core.model.IVariable[])
	 */
	public void setVariables(IVariable[] variables) {
		IVariable[] vars = variables;
		try{
			if (open){
				//apply filter if exists
				IFilter filter = null;
				if (value != null){
					filter = model.filtermanager.getFilterForType(getType());
				}
				if (filter!=null)
					try {
						vars = filter.apply(vars);
					} catch (CoreException e1) {
						e1.printStackTrace();
					}
				
				Set<Edge> unEdges = new HashSet<Edge>();
				unEdges.addAll(outs);
				for (IVariable var : vars){
					IValue value = var.getValue();
					//check if we already have this edge
					Node other = model.getNodeForValue(value);
					Edge e = getOutEdgeForNode(other);
					if (e == null){
						//new variable
						e = model.getEdge(this, other,var.getName());
					}else{
						//we already have this
						//we still need this, so it is necessary
						unEdges.remove(e);
						e.getTo().changeValue(value);
					}
				}
				//remove unnecessary edges
				for(Edge e : unEdges) e.dispose();
				unEdges.clear();
			}
		}catch (DebugException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Node#toggleOpen()
	 */
	public void toggleOpen() {
		if (open){
			this.disposeOuts();
			open = false;
		}else{
			open = true;
			try {
				if (value != null) this.setVariables(value.getVariables());
			} catch (DebugException e) {
				e.printStackTrace();
			}
		}
		notifychange();
	}
	
	@Override
	public void dispose() {
		closeChilds();
		super.dispose();
		model.disposeObjectNode(this);
	}
	
	protected void closeChilds(){
		for(Edge e : outs){
			Node n = e.getTo();
			if (n.isOpen()) n.toggleOpen();
		}
	}

	public NodeState getState() {
		try {
			if (value.getVariables().length == 0) return NodeState.Primitive;
		} catch (DebugException e) {
			e.printStackTrace();
		}
		return (open) ? NodeState.Open : NodeState.Closed;
	}

}
