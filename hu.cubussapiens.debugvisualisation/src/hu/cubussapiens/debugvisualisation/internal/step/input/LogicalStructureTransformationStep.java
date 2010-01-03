/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;
import hu.cubussapiens.debugvisualisation.internal.api.ILogicalStructureAdapter;
import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.model.impl.AbstractKey;
import hu.cubussapiens.debugvisualisation.internal.model.impl.DVVariablesImpl;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;

import java.util.ArrayList;
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
public class LogicalStructureTransformationStep extends
		AbstractGraphTransformationStep implements ILogicalStructureAdapter {

	private static final String STRUCTUREPROPERTY_ID = "logicalstructure";
	private final AbstractKey<String> key = new AbstractKey<String>(
			STRUCTUREPROPERTY_ID);

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

	private ILogicalStructureType getLogicalStructureType(IValue value,
			String filter) {
		ILogicalStructureType[] structureTypes = DebugPlugin
				.getLogicalStructureTypes(value);
		if (filter == "") {
			// Default logical structure
			return DebugPlugin.getDefaultStructureType(structureTypes);
		} else {
			for (ILogicalStructureType type : structureTypes) {
				if (type.getId().contentEquals(filter))
					return type;
			}
		}
		return null;
	}

	public Collection<IDVVariable> getEdges(IDVValue node) {
		try {
			String filter;
			if (!node.isPropertySet(key)) {
				filter = "";
			} else if (node.getProperty(key).contentEquals(
					DebugVisualisationPlugin.LOGICALSTRUCTURE_RAW_ID)) {
				return getParent().getEdges(node);
			} else {
				filter = node.getProperty(key);
			}

			IValue value = node.getRelatedValue();
			ILogicalStructureType logicalStructureType = getLogicalStructureType(
					value, filter);
			if (logicalStructureType == null
					|| logicalStructureType.providesLogicalStructure(value)) {
				return getParent().getEdges(node);
			}
			IVariable[] variables = logicalStructureType.getLogicalStructure(
					value).getVariables();
			List<IDVVariable> result = new ArrayList<IDVVariable>(
					variables.length);
			for (IVariable v : variables) {
				result.add(new DVVariablesImpl(v, this, node));
			}
			return result;

		} catch (DebugException e) {
			DebugVisualisationPlugin.getDefault().logError(e,
					"Can't apply filter for " + node);
			return getParent().getEdges(node);
		} catch (CoreException e) {
			DebugVisualisationPlugin.getDefault().logError(e,
					"Can't apply filter for " + node);
			return getParent().getEdges(node);
		}
	}

	public void setLogicalStructure(Collection<IDVValue> nodes,
			String logicalStructure) {
		for (IDVValue value : nodes) {
			value.setProperty(key, logicalStructure);
		}
		clearCache();
		trigger(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seehu.cubussapiens.debugvisualisation.internal.step.
	 * AbstractGraphTransformationStep#tryAdapter(java.lang.Class)
	 */
	@Override
	protected Object tryAdapter(Class<?> adapter) {
		if (ILogicalStructureAdapter.class.equals(adapter)) {
			return this;
		}
		return super.tryAdapter(adapter);
	}

}
