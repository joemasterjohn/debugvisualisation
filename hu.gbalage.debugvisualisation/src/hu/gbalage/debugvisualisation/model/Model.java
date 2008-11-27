package hu.gbalage.debugvisualisation.model;

import hu.gbalage.debugvisualisation.ValueUtils;
import hu.gbalage.debugvisualisation.filters.FilterManager;
import hu.gbalage.debugvisualisation.view.IDebugTreePresentation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

public class Model implements IStackFrameConsumer{

	Node rootNode = null;
	
	String rootVariable = "";
	
	final Map<Integer, Node> uniqueNodes = new HashMap<Integer, Node>();
	
	final Set<Node> valueNodes = new HashSet<Node>();
	
	final IDebugTreePresentation presentation;
	
	protected final FilterManager filtermanager = new FilterManager();
	
	public Model(IDebugTreePresentation presentation){
		this.presentation = presentation;
	}
	
	/**
	 * Modify model according to the new stack frame
	 */
	public void setStackFrame(IStackFrame stackframe){
		if (rootNode == null) rootNode = new RootNode(this);
		presentation.addNode(rootNode);
		
		//TODO: skip to rootVariable
		IVariable[] vars = new IVariable[0];
		try {
			 vars = stackframe.getVariables();
		} catch (DebugException e) {
			e.printStackTrace();
		}
		
		rootNode.setVariables(vars);
		presentation.refresh();
	}
	
	public Edge getEdge(Node from, Node to,String name){
		Edge e = new VariableEdge(this,from,to,name);
		presentation.addEdge(e, from, to);
		return e;
	}
	
	public Node getNodeForValue(IValue value){
		int id = ValueUtils.getID(value);
		if (id == -1){
			//primitive node (leaf)
			Node a = new PrimitiveNode(this,value);
			valueNodes.add(a);
			presentation.addNode(a);
			return a;
		}else{
			//object node
			if (uniqueNodes.containsKey(id)){
				Node a = uniqueNodes.get(id);
				a.changeValue(value);
				return a;
			}else{
				Node a = new ObjectNode(this,id,value);
				uniqueNodes.put(id, a);
				presentation.addNode(a);
				return a;
			}
		}
	}
	
	protected void disposeValueNode(Node node){
		valueNodes.remove(valueNodes);
		presentation.removeNode(node);
	}
	
	protected void disposeObjectNode(Node node){
		presentation.removeNode(node);
		uniqueNodes.remove(node.getID());
	}
	
	protected void removeEdge(Edge edge){
		presentation.removeEdge(edge);
	}
	
}
