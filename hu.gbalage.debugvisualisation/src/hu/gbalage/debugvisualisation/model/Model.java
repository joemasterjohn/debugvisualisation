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

/**
 * A model stores a variable tree model. It waits for IStackFrames
 * and changes the model according to it, applying filters on it,
 * and updates a presentation as the model changes.
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class Model implements IStackFrameConsumer{

	/**
	 * rootNode of the graph
	 */
	Node rootNode = null;
	
	/**
	 * path to the root variable, which will be displayed as the
	 * rootNode.
	 */
	String rootVariable = "";
	
	/**
	 * Map for the unique nodes, stored by IDs.
	 */
	final Map<Integer, Node> uniqueNodes = new HashMap<Integer, Node>();
	
	/**
	 * Set of value nodes. These nodes are not unique.
	 */
	final Set<Node> valueNodes = new HashSet<Node>();
	
	/**
	 * the presentation, which can display the graph.
	 */
	final IDebugTreePresentation presentation;
	
	/**
	 * the filtermanager to load filters from the Extension Point.
	 */
	protected final FilterManager filtermanager = new FilterManager();
	
	/**
	 * Store state of nodes
	 */
	final NodeStateStore stateStore = new NodeStateStore();
	
	/**
	 * Constructor for the model. 
	 * @param presentation The presentation is supposed to display
	 * the graph.
	 */
	public Model(IDebugTreePresentation presentation){
		this.presentation = presentation;
	}
	
	/**
	 * Modify model according to the new stack frame
	 */
	public void setStackFrame(IStackFrame stackframe){
		if (rootNode != null) stateStore.storeStates(this);
		//System.out.println("step");
		if (stackframe == null){
			//System.out.println("clear");
			if (rootNode != null) rootNode.dispose();
			rootNode = null;
			//stateStore.clearStates();
			return;
		}
		if (rootNode == null) {
			rootNode = new RootNode(this);
			presentation.addNode(rootNode);
		}
		
		
		IVariable[] vars = new IVariable[0];
		try {
			 vars = stackframe.getVariables();
		} catch (DebugException e) {
			e.printStackTrace();
		}
		
		//TODO: skip to rootVariable
		
		rootNode.setVariables(vars);
		stateStore.restoreStates(this);
		presentation.refresh();
	}
	
	/**
	 * Create and return a new edge in this model.
	 * @param from the node from the edge begins
	 * @param to the node where the edge ends
	 * @param name name of the variable represented by this variable
	 * @return a newly created edge.
	 * @note the created edge is given to the presentation 
	 * automatically
	 */
	public Edge getEdge(Node from, Node to,String name){
		Edge e = new VariableEdge(this,from,to,name);
		presentation.addEdge(e, from, to);
		return e;
	}
	
	/**
	 * Creates and returns a node for the given value.
	 * @param value
	 * @return a PrimitiveNode if the value is a primitive value,
	 * or an ObjectNode if the value is a structured value.
	 * @note if a node for a structured (unique) value is created
	 * before, than it is returned here instead of creating a new
	 * one. 
	 */
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
	
	/**
	 * Remove the given node from the model and the presentation
	 * @param node
	 */
	protected void disposeValueNode(Node node){
		valueNodes.remove(node);
		presentation.removeNode(node);
	}
	
	/**
	 * Remove the given node from the model and the presentation
	 * @param node
	 */
	protected void disposeObjectNode(Node node){
		presentation.removeNode(node);
		uniqueNodes.remove(node.getID());
	}
	
	/**
	 * Remove the given edge from the model and the presentation
	 * @param edge
	 */
	protected void removeEdge(Edge edge){
		presentation.removeEdge(edge);
	}
	
	/**
	 * Remove a node from the presentation, but not from the model
	 * @param node
	 */
	protected void hideNode(Node node){
		for (Edge e : node.listInEdges()){
			removeEdge(e);
		}
		presentation.removeNode(node);
	}

	protected void showNode(Node node){
		presentation.addNode(node);
		for (Edge e : node.listInEdges()){
			presentation.addEdge(e, e.getFrom(), node);
		}
	}
	
	public void showHiddenChildsOfNode(Node node){
		for(Node n : node.listChildNodes().values()){
			if (!n.isVisible()) n.toggleVisibility();
		}
		presentation.refresh();
	}
	
}
