/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.api.IOpenCloseNodes;
import hu.cubussapiens.debugvisualisation.internal.api.OpenCloseStateChangedEvent;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class OpenCloseTransformationStep extends
		AbstractGraphTransformationStep implements IOpenCloseNodes {

	public OpenCloseNodeState getNodeState(Object node) {
		if (StackFrameRootedGraphContentProvider.root.equals(node))
			return OpenCloseNodeState.Root;
		if (getParent().getEdges(node).isEmpty())
			return OpenCloseNodeState.ChildLess;
		if (open.contains(node))
			return OpenCloseNodeState.Open;
		return OpenCloseNodeState.Close;
	}

	public void toggleOpenNode(Object node) {
		if (open.contains(node))
			open.remove(node);
		else
			open.add(node);
		trigger(new OpenCloseStateChangedEvent(node));
	}

	@Override
	protected Object tryAdapter(Class<?> adapter) {
		if (IOpenCloseNodes.class.equals(adapter))
			return this;
		return super.tryAdapter(adapter);
	}

	/**
	 * @param parent
	 */
	public OpenCloseTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	final Set<Object> open = new HashSet<Object>();

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<Object> getRoots() {
		return getParent().getRoots();
	}

	public Object getEdgeTarget(Object edge) {
		return getParent().getEdgeTarget(edge);
	}

	public Collection<Object> getEdges(Object node) {
		boolean o = (StackFrameRootedGraphContentProvider.root.equals(node))
				|| (open.contains(node));
		return o ? getParent().getEdges(node) : new ArrayList<Object>();
	}

}
