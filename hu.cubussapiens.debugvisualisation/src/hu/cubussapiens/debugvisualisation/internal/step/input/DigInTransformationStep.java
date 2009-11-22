/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.api.IDigInNodes;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class DigInTransformationStep extends AbstractGraphTransformationStep
		implements IDigInNodes {

	private final List<Object> roots = new ArrayList<Object>();

	/**
	 * @param parent
	 */
	public DigInTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	@Override
	protected Object tryAdapter(Class<?> adapter) {
		if (IDigInNodes.class.equals(adapter))
			return this;
		return super.tryAdapter(adapter);
	}

	public void digIn(Collection<Object> nodes) {
		roots.clear();
		roots.addAll(nodes);
		trigger(null);
	}

	public void showRoot() {
		roots.clear();
		trigger(null);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<Object> getRoots() {
		if (roots.isEmpty())
			return getParent().getRoots();
		return roots;
	}

	public Object getEdgeTarget(Object edge) {
		return getParent().getEdgeTarget(edge);
	}

	public Collection<Object> getEdges(Object node) {
		return getParent().getEdges(node);
	}

}
