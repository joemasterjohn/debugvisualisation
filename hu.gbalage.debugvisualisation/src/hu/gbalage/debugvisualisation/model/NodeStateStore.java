/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class can store and restore the state of the nodes in a model.
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class NodeStateStore {

	private final Map<String, NodeState> states = new HashMap<String, NodeState>(); 
	
	public void clearStates(){
		states.clear();
	}
	
	public void saveHiddenNode(Node node){
		states.put(node.getPath(),NodeState.Hidden);
	}
	
	public void dropNodeState(Node node){
		if (states.containsKey(node.getPath())){
			states.remove(node.getPath());
		}
	}
	
	/**
	 * Temporary set for storing visited nodes, for eliminating problems
	 * caused by directed circles in the graph
	 */
	private final Set<Node> visited = new HashSet<Node>();
	
	public void storeStates(Model model){
		visited.clear();
		storeNodeState(model.rootNode,"");
	}
	
	public void restoreStates(Model model){
		visited.clear();
		restoreNodeState(model.rootNode, "");
	}
	
	private void applyNodeState(Node node, NodeState stored){
		NodeState current = node.getState();
		
		if (stored == current) return;
		
		if (stored == NodeState.Hidden){
			node.toggleVisibility();
			return;
		}
		
		if ((current == NodeState.Root)||(current == NodeState.Primitive)) return;
		
		if ((current == NodeState.Closed)&&(stored == NodeState.Open)) node.toggleOpen();
		if ((current == NodeState.Open)&&(stored == NodeState.Closed)) node.toggleOpen();
		
	}
	
	private void restoreNodeState(Node node, String path){
		if (visited.contains(node)) return;
		if (!states.containsKey(path)) return;
		visited.add(node);
		
		NodeState stored = states.get(path);
		//System.out.println("restore: "+path+" | "+node.getState()+" -> "+stored);
		applyNodeState(node, stored);
				
		Map<String, Node> childs = node.listChildNodes();
		for(String v : childs.keySet()){
			restoreNodeState(childs.get(v),path+"/"+v);
		}
	}
	
	private void storeNodeState(Node node, String path){
		if (visited.contains(node)) return;
		visited.add(node);
		//store current node state
		states.put(path, node.getState());
		//System.out.println("store: "+path+" -> "+node.getState());
		//store child nodes state
		Map<String, Node> childs = node.listChildNodes();
		for(String v : childs.keySet()){
			storeNodeState(childs.get(v),path+"/"+v);
		}
	}
	
}
