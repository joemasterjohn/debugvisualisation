/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.StackFrameRootedGraphContentProvider;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ViewModelFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.model.IStackFrame;

/**
 *
 */
public class StackFrameContextInput extends AbstractGraphTransformationStep {

	final StackFrameRootedGraphContentProvider root;

	// final LogicalStructureTransformationStep filter;
	// final CacheTransformationStep rootcache;
	// final RootControlTransformationStep t0;
	// final OpenCloseTransformationStep t1;
	// final ParametersTransformationStep t2;
	// final HideNodesTransformationStep t3;
	// final ReferenceTrackerTransformationStep t4;

	final IGraphTransformationStep last;
	/**
	 * 
	 * @param sf
	 */
	public StackFrameContextInput(IStackFrame sf) {
		super(null, new ViewModelFactory());
		root = new StackFrameRootedGraphContentProvider(sf, factory);
		// filter = new LogicalStructureTransformationStep(root, factory);
		// rootcache = new CacheTransformationStep(filter, factory);
		// rootcache = new CacheTransformationStep(root, factory);
		// t4 = new ReferenceTrackerTransformationStep(rootcache, factory);
		// t4 = new ReferenceTrackerTransformationStep(root, factory);
		// t0 = new RootControlTransformationStep(t4, factory);
		// t1 = new OpenCloseTransformationStep(t0, factory);
		// t1 = new OpenCloseTransformationStep(t4, factory);
		// t2 = new ParametersTransformationStep(t1, factory);
		// t2 = new ParametersTransformationStep(t4, factory);
		// t3 = new HideNodesTransformationStep(t2, factory);
		// t3 = new HideNodesTransformationStep(t4, factory);
		// t3 = new HideNodesTransformationStep(root, factory);


		// last = t3;
		last = root;
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

	/**
	 * Returns the view model factory object
	 * 
	 * @return the view model factory object
	 */
	public ViewModelFactory getFactory() {
		return factory;
	}

	@Override
	public void clearCache() {
		last.clearCache();
		super.clearCache();
	}

}
