/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.api.IHiddenNodes;
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

	private final Set<Object> hidden = new HashSet<Object>();

	/**
	 * @param parent
	 */
	public HideNodesTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	public void hideNodes(Collection<Object> nodes) {
		hidden.addAll(nodes);
		trigger(null);
	}

	public void showHiddenChildNodes(Collection<Object> nodes) {
		for (Object o : nodes)
			for (Object us : getParent().getEdges(o)) {
				Object u = getEdgeTarget(us);
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
	public Collection<Object> getRoots() {
		// roots can't be hidden
		return getParent().getRoots();
	}

	public Object getEdgeTarget(Object edge) {
		return getParent().getEdgeTarget(edge);
	}

	public Collection<Object> getEdges(Object node) {
		List<Object> result = new ArrayList<Object>();
		for (Object o : getParent().getEdges(node))
			if (!hidden.contains(getParent().getEdgeTarget(o)))
				result.add(o);
		return result;
	}

}
