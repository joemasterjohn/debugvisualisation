/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.CacheTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.DigInTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.HideNodesTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.OpenCloseTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.ParametersTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.ReferenceTrackerTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.StackFrameRootedGraphContentProvider;

import java.util.Collection;

import org.eclipse.debug.core.model.IStackFrame;

/**
 *
 */
public class StackFrameContextInput extends AbstractGraphTransformationStep {

	final StackFrameRootedGraphContentProvider root;

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
		rootcache = new CacheTransformationStep(root);
		t4 = new ReferenceTrackerTransformationStep(rootcache);
		t0 = new DigInTransformationStep(t4);
		t1 = new OpenCloseTransformationStep(t0);
		t2 = new ParametersTransformationStep(t1);
		t3 = new HideNodesTransformationStep(t2);


		last = t3;
		last.addListener(this);
	}

	@Override
	protected Object tryToGetNodeState(Object node, Object statedomain) {
		return last.getNodeState(node, statedomain);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep#tryToExecute(hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand)
	 */
	@Override
	protected boolean tryToExecute(IGraphCommand command) {
		last.execute(command);
		return true;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getChilds(java.lang.Object)
	 */
	public Collection<Object> getChilds(Object node) {
		return last.getChilds(node);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getEdge(java.lang.Object, java.lang.Object)
	 */
	public Collection<Object> getEdge(Object nodea, Object nodeb) {
		return last.getEdge(nodea, nodeb);
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider#getRoots()
	 */
	public Collection<Object> getRoots() {
		return last.getRoots();
	}

}
