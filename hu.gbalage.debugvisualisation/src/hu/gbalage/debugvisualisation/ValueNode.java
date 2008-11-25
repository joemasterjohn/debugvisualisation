/**
 * 
 */
package hu.gbalage.debugvisualisation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.swt.SWT;
import org.eclipse.zest.core.widgets.GraphNode;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class ValueNode {

	private int referencenum = 0;

	protected GraphNode node;
	
	private boolean open = false;
	
	protected final VariableGraph graph;
	
	private final Map<String,VariableReference> refs = new HashMap<String, VariableReference>();
	
	private IValue value;
	
	public void setValue(IValue value){
		this.value = value;
	}
	
	protected ValueNode(IValue value, VariableGraph graph){
		this.graph = graph;
		this.value = value;
		node = new GraphNode(graph,SWT.NONE);
		setCaption();
		node.setData(this);
	}
	
	private void setCaption(){
		try {
			String caption = (value==null) ? "Local scope" : value.getValueString();
			node.setText(caption);
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	protected void clearRefs(){
		for(String key : refs.keySet()){
			VariableReference ref = refs.get(key);
			ref.dispose();
		}
		refs.clear();
	}
	
	protected void refreshRefs(){
		try {
			if ((value != null)&&(value.hasVariables())){
				clearRefs();
				for (IVariable var : value.getVariables()){
					addVariable(var);
				}
			}
		} catch (DebugException e) {
			e.printStackTrace();
		}
		graph.refresh();
	}
	
	protected boolean isOpen(){
		return open;
	}
	

	protected void addVariable(IVariable var){
		try {
			ValueNode other = graph.getValueNode(var.getValue());
			String key = var.getName();
			if (refs.containsKey(key)){
				refs.get(key).dispose();
				refs.remove(key);
			}
			refs.put(var.getName(),new VariableReference(this,other,var.getName()));
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	protected void incref(){
		referencenum++;
	}
	
	protected void decref(){
		if (referencenum>0)
			referencenum--;
	}
	
	public boolean isDisposable(){
		return referencenum==0;
	}
	
	public void dispose(){
		clearRefs();
		node.dispose();
		open = false;
	}
	
	public void toogleOpen(){
		if (value == null) return; //root node can't be closed
		open = !open;
		if (open){
			//open
			refreshRefs();
		}else{
			//close
			clearRefs();
			graph.refresh();
		}
	}
	
	public void setName(String name){
		try {
			if (ValueUtils.getID(value) == -1){
				//constant
				this.node.setText(name+" = "+value.getValueString());
			}else{
				//object
				String typename = value.getReferenceTypeName();
				int cut = Math.max(typename.lastIndexOf("."),typename.lastIndexOf("$"))+1;
				if ((cut>0)&&(cut<typename.length())) typename = typename.substring(cut);
				this.node.setText(name+": "+typename);
			}
		} catch (DebugException e) {
			this.node.setText(name);
			e.printStackTrace();
		}
	}
	
}
