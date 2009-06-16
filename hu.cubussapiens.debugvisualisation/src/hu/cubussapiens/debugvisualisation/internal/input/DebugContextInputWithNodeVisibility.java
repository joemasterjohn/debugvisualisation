/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

/**
 * @author balage
 *
 */
public class DebugContextInputWithNodeVisibility extends
		DebugContextInput {

	/**
	 * Visible nodes
	 */
	protected final Set<Integer> visible = new HashSet<Integer>();
	
	/**
	 * Open nodes
	 */
	protected final Set<Integer> open = new HashSet<Integer>();
	
	protected void setVisibility(Integer node, boolean visibility){
		if(visibility)
			visible.add(node);
		else visible.remove(node);
	}
	
	/**
	 * @param sf
	 * @throws DebugException
	 */
	public DebugContextInputWithNodeVisibility(IStackFrame sf)
			throws DebugException {
		super(sf);
		setVisibility(root, true);
		openNode(root);
	}
	
	@Override
	protected void onRefresh() {
		//setVisibility(root, true);
		/*try{
		openNode(root);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		//if (visible.contains(root)) openNode(root);
		if (open != null){
			System.out.println("onRefresh.. "+open.size());
			openNode(root);
		}
	}
	
	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput#getVisibleNodes()
	 */
	public Set<Integer> getVisibleNodes() {
		return visible;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput#isOpen(java.lang.Integer)
	 */
	public boolean isOpen(Integer node) {
		return open.contains(node);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput#toggleOpen(java.lang.Integer)
	 */
	public void toggleOpen(Integer node) {
		if (isOpen(node)){
			closeNode(node);
		}else{
			openNode(node);
		}
		trigger().openStateChanged(node, isOpen(node));
	}

	private void openNode(Integer node){
		//open closed node
		open.add(node);
		//show all child nodes
		for(Integer child : getChilds(node)){
			setVisibility(child, true);
		}
	}

	private void closeNode(Integer node){
		if (node.equals(root)) return; //root node can't be closed
		for(Integer child : getChilds(node)){
			if (isOpen(child)) closeNode(node);
			setVisibility(child, false);
		}
		open.remove(node);
	}
	
}
