/**
 * 
 */
package hu.gbalage.debugvisualisation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class VariableGraph extends Graph {

	/**
	 * This map contains Values, which can be referenced multiple times.
	 */
	protected final Map<Integer, ValueNode> objectNodes = new HashMap<Integer, ValueNode>();
	
	protected final List<ValueNode> leafs = new ArrayList<ValueNode>();
	
	protected final ValueNode rootNode;
	
	
	public void refresh(){
		//check for disposable nodes:
		boolean okay;
		do{
			okay = true;
			ArrayList<Integer> toremove = new ArrayList<Integer>();
			for(Integer i : objectNodes.keySet()){
				ValueNode vn = objectNodes.get(i);
				if (vn.isDisposable()) {
					//vn.dispose();
					//objectNodes.remove(i);
					toremove.add(i);
					okay = false;
				}
			}
			for (Integer i : toremove){
				ValueNode vn = objectNodes.get(i);
				vn.dispose();
				objectNodes.remove(i);
			}
			toremove.clear();
			
			ArrayList<ValueNode> toremovel = new ArrayList<ValueNode>();
			for(ValueNode vn : leafs){
				if (vn.isDisposable()){
					//leafs.remove(vn);
					//vn.dispose();
					toremovel.add(vn);
					okay = false;
				}
			}
			for(ValueNode vn : toremovel){
				leafs.remove(vn);
				vn.dispose();
			}
		}while(!okay);
		//refresh layout
		//this.layout();
		this.applyLayout();
	}
	
	/**
	 * @param parent
	 * @param style
	 */
	public VariableGraph(Composite parent, int style) {
		super(parent, style);
		rootNode = new ValueNode(null,this);
		this.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);

		this.addMouseListener(new MouseAdapter(){
			@SuppressWarnings("unchecked")
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				List<GraphNode> nodes = ((Graph) e.widget).getSelection();
				for(GraphNode node : nodes){
					ValueNode vn = (ValueNode)node.getData();
					vn.toogleOpen();
				}
				//super.mouseDoubleClick(e);
			}
		});
		
		/*this.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<GraphNode> nodes = ((Graph) e.widget).getSelection();
				for(GraphNode node : nodes){
					ValueNode vn = (ValueNode)node.getData();
					vn.toogleOpen();
				}
			}
		});*/

		
	}
	
	public void setStackFrame(IStackFrame stackframe){
		rootNode.clearRefs();
		
		if (stackframe != null) try {
			IVariable[] vars = stackframe.getVariables();
			for (IVariable var : vars) rootNode.addVariable(var);
		} catch (DebugException e) {
			e.printStackTrace();
		}
		this.refresh();
	}

	/**
	 * Checks if there is already a shown node for this value, and creates
	 * one if not
	 * @param value
	 * @return the ValueNode fot the given value
	 */
	protected ValueNode getValueNode(IValue value){
		int id = ValueUtils.getID(value);
		if (id<0){
			ValueNode a = new ValueNode(value,this);
			leafs.add(a);
			return a;
		}else{
			if (objectNodes.containsKey(id)) {
				ValueNode a = objectNodes.get(id);
				a.setValue(value);
				return a;
			}
			ValueNode a = new ValueNode(value,this);
			objectNodes.put(id, a);
			return a;
		}
	}
	
}
