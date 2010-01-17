/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.api.IOpenCloseNodes;
import hu.cubussapiens.debugvisualisation.internal.api.OpenCloseStateChangedEvent;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class OpenCloseTransformationStep extends
		AbstractGraphTransformationStep implements IOpenCloseNodes {

	public OpenCloseNodeState getNodeState(IDVValue node) {
		// if (StackFrameRootedGraphContentProvider.root.equals(node))
		// return OpenCloseNodeState.Root;
		if (getParent().getEdges(node).isEmpty())
			return OpenCloseNodeState.ChildLess;
		if (open.contains(node))
			return OpenCloseNodeState.Open;
		return OpenCloseNodeState.Close;
	}

	public void toggleOpenNode(IDVValue node) {
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
	 * @param factory
	 */
	public OpenCloseTransformationStep(IRootedGraphContentProvider parent,
			ViewModelFactory factory) {
		super(parent, factory);
	}

	final Set<Object> open = new HashSet<Object>();

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<IDVValue> getRoots() {
		return getParent().getRoots();
	}

	public IDVValue getEdgeTarget(IDVVariable edge) {
		return getParent().getEdgeTarget(edge);
	}

	public Collection<IDVVariable> getEdges(IDVValue node) {
		boolean o = open.contains(node);
		return o ? getParent().getEdges(node) : new ArrayList<IDVVariable>();
	}

}
