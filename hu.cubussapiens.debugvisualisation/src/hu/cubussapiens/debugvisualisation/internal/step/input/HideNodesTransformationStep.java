/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.api.IHiddenNodes;
import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class HideNodesTransformationStep extends
		AbstractGraphTransformationStep implements IHiddenNodes {

	private final Set<IDVValue> hidden = new HashSet<IDVValue>();

	/**
	 * @param parent
	 */
	public HideNodesTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	public void hideNodes(Collection<IDVValue> nodes) {
		hidden.addAll(nodes);
		trigger(null);
	}

	public void showHiddenChildNodes(Collection<IDVValue> nodes) {
		for (IDVValue o : nodes)
			for (IDVVariable us : getParent().getEdges(o)) {
				IDVValue u = getEdgeTarget(us);
				if (hidden.contains(u))
					hidden.remove(u);
			}
		trigger(null);
	}

	@Override
	protected Object tryAdapter(Class<?> adapter) {
		if (IHiddenNodes.class.equals(adapter))
			return this;
		return super.tryAdapter(adapter);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<IDVValue> getRoots() {
		// roots can't be hidden
		return getParent().getRoots();
	}

	public IDVValue getEdgeTarget(IDVVariable edge) {
		return getParent().getEdgeTarget(edge);
	}

	public Collection<IDVVariable> getEdges(IDVValue node) {
		List<IDVVariable> result = new ArrayList<IDVVariable>();
		for (IDVVariable o : getParent().getEdges(node))
			if (!hidden.contains(getParent().getEdgeTarget(o)))
				result.add(o);
		return result;
	}

}
