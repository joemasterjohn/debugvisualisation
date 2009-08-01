/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.OpenCloseTransFormationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.ParametersTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.input.StackFrameRootedGraphContentProvider;

import java.util.Collection;

import org.eclipse.debug.core.model.IStackFrame;

/**
 *
 */
public class StackFrameContextInput extends AbstractGraphTransformationStep {

	final StackFrameRootedGraphContentProvider root;

	final OpenCloseTransFormationStep t1;
	final ParametersTransformationStep t2;

	final IGraphTransformationStep last;

	/**
	 * 
	 * @param sf
	 */
	public StackFrameContextInput(IStackFrame sf) {
		super(null);
		root = new StackFrameRootedGraphContentProvider(sf);
		t1 = new OpenCloseTransFormationStep(root);
		t2 = new ParametersTransformationStep(t1);

		last = t2;
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
