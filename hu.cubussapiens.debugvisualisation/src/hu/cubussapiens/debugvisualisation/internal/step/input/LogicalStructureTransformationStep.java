/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;
import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.model.impl.DVVariablesImpl;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILogicalStructureType;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * Transformation step to execute filtering
 */
public class LogicalStructureTransformationStep extends AbstractGraphTransformationStep {

	/**
	 * @param parent
	 */
	public LogicalStructureTransformationStep(IRootedGraphContentProvider parent) {
		super(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider
	 * #getRoots()
	 */
	public Collection<IDVValue> getRoots() {
		return getParent().getRoots();
	}

	public IDVValue getEdgeTarget(IDVVariable edge) {

		return getParent().getEdgeTarget(edge);
	}

	public Collection<IDVVariable> getEdges(IDVValue node) {
		try {
			// IVariableFilter vf = (node instanceof IValue) ? provider
			// .getFilter(((IValue) node)) : null;
			if (node instanceof IValue) {
				ILogicalStructureType[] structureTypes = DebugPlugin
						.getLogicalStructureTypes((IValue) node);
				if (structureTypes.length > 0) {
					List<IVariable> os = Arrays.asList(structureTypes[0]
							.getLogicalStructure((IValue) node).getVariables());
					List<IDVVariable> result = new ArrayList<IDVVariable>(os
							.size());
					for (IVariable v : os) {
						result.add(new DVVariablesImpl(v, this, node));
					}
					return result;
				}
			}
			return getParent().getEdges(node);
			/*
			 * List<Object> os = new ArrayList<Object>(); for (IVariable v :
			 * vf.filter((IValue) node)) { os.add(v); } return os;
			 */
		} catch (DebugException e) {
			DebugVisualisationPlugin.getDefault()
					.logError(e, "Can't apply filter for " + node);
			return getParent().getEdges(node);
		} catch (CoreException e) {
			DebugVisualisationPlugin.getDefault()
					.logError(e, "Can't apply filter for " + node);
			return getParent().getEdges(node);
		}
	}

}
