/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;
import hu.cubussapiens.debugvisualisation.internal.api.IDigInNodes;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ViewModelFactory;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

/**
 *
 */
public class DigInTransformationStep extends AbstractGraphTransformationStep
		implements IDigInNodes {

	private final Collection<IDVValue> roots;

	/**
	 * @param parent
	 * @param factory
	 */
	public DigInTransformationStep(IRootedGraphContentProvider parent,
			ViewModelFactory factory) {
		super(parent, factory);
		roots = parent.getRoots();
	}

	@Override
	protected Object tryAdapter(Class<?> adapter) {
		if (IDigInNodes.class.equals(adapter))
			return this;
		return super.tryAdapter(adapter);
	}

	public void digIn(Collection<IDVValue> nodes) {
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
	public Collection<IDVValue> getRoots() {
		if (roots.isEmpty())
			return getParent().getRoots();
		return roots;
	}

	public IDVValue getEdgeTarget(IDVVariable edge) {
		return getParent().getEdgeTarget(edge);
	}

	public Collection<IDVVariable> getEdges(IDVValue node) {
		return getParent().getEdges(node);
	}

	public void addVariables(Collection<IVariable> variables) {
		for (IVariable v : variables) {
			try {
				roots.add(this.factory.getValue(v.getValue(), getParent(), v));
			} catch (DebugException e) {
				DebugVisualisationPlugin.getDefault().getLog().log(
						new Status(IStatus.ERROR,
								DebugVisualisationPlugin.PLUGIN_ID, e
										.getMessage(), e));
			}
		}
		trigger(null);
	}

}
