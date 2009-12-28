/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.CacheTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.DigInTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.HideNodesTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.OpenCloseTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.ParametersTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.ReferenceTrackerTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.StackFrameRootedGraphContentProvider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.model.IStackFrame;

/**
 *
 */
public class StackFrameContextInput extends AbstractGraphTransformationStep {

	final StackFrameRootedGraphContentProvider root;

	// final FilterTransformationStep filter;
	final CacheTransformationStep rootcache;
	final DigInTransformationStep t0;
	final OpenCloseTransformationStep t1;
	final ParametersTransformationStep t2;
	final HideNodesTransformationStep t3;
	final ReferenceTrackerTransformationStep t4;

	final IGraphTransformationStep last;

	/**
	 * 
	 * @param sf
	 */
	public StackFrameContextInput(IStackFrame sf) {
		super(null);
		root = new StackFrameRootedGraphContentProvider(sf);
		// filter = new FilterTransformationStep(root);
		rootcache = new CacheTransformationStep(root);
		t4 = new ReferenceTrackerTransformationStep(rootcache);
		t0 = new DigInTransformationStep(t4);
		t1 = new OpenCloseTransformationStep(t0);
		t2 = new ParametersTransformationStep(t1);
		t3 = new HideNodesTransformationStep(t2);


		last = t3;
		last.addListener(this);
	}

	private final Map<Class<?>, Object> adapters = new HashMap<Class<?>, Object>();

	@Override
	protected Object tryAdapter(Class<?> adapter) {
		if (adapters.containsKey(adapter)) {
			return adapters.get(adapter);
		}
		Object a = last.getAdapter(adapter);
		adapters.put(adapter, a);
		return a;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<IDVValue> getRoots() {
		return last.getRoots();
	}

	public IDVValue getEdgeTarget(IDVVariable edge) {
		return last.getEdgeTarget(edge);
	}

	public Collection<IDVVariable> getEdges(IDVValue node) {
		return last.getEdges(node);
	}

	@Override
	public void clearCache() {
		last.clearCache();
		super.clearCache();
	}

}
