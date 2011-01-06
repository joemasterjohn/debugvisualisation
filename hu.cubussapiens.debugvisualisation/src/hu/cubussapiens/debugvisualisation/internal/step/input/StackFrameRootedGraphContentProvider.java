/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;
import hu.cubussapiens.debugvisualisation.internal.api.ILogicalStructureAdapter;
import hu.cubussapiens.debugvisualisation.internal.api.IOpenCloseNodes;
import hu.cubussapiens.debugvisualisation.internal.api.IRootControl;
import hu.cubussapiens.debugvisualisation.internal.api.OpenCloseStateChangedEvent;
import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphTransformationStep;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;
import hu.cubussapiens.debugvisualisation.viewmodel.IPropertyKey;
import hu.cubussapiens.debugvisualisation.viewmodel.OpenCloseNodeState;
import hu.cubussapiens.debugvisualisation.viewmodel.util.PropertyKeys;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILogicalStructureType;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * This class provides the graph content based on a stack frame
 */
public class StackFrameRootedGraphContentProvider extends
		AbstractGraphTransformationStep implements IRootControl,
		ILogicalStructureAdapter, IOpenCloseNodes {

	final IStackFrame sf;
	private HashSet<IDVValue> roots;
	private HashSet<IDVValue> localContext;
	private Hashtable<IDVValue, HashSet<IDVVariable>> edgeFrom;// , edgeTo;
	private Hashtable<IValue, Collection<IVariable>> childrenCache;

	/**
	 * @param sf
	 * @param factory
	 * 
	 */
	public StackFrameRootedGraphContentProvider(IStackFrame sf,
			ViewModelFactory factory) {
		super(null, factory);
		this.sf = sf;
		edgeFrom = new Hashtable<IDVValue, HashSet<IDVVariable>>();
		childrenCache = new Hashtable<IValue, Collection<IVariable>>();
		// edgeTo = new Hashtable<IDVValue, HashSet<IDVVariable>>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider
	 * #getRoots()
	 */
	public Collection<IDVValue> getRoots() {
		if (roots == null) {
			roots = new HashSet<IDVValue>();
			showRoot();
		}
		return roots;
	}

	public IDVValue getEdgeTarget(IDVVariable e) {
		IVariable edge = (IVariable) e.getAdapter(IVariable.class);
		try {
			return factory.getValue(edge.getValue(), this, e);
		} catch (DebugException e1) {
			DebugVisualisationPlugin.getDefault().logError(e1,
					"Can't retrieve value of " + edge);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Collection<IDVVariable> getEdges(IDVValue n) {
		// List<IDVVariable> os = new ArrayList<IDVVariable>();
		// IValue node = (IValue) n.getAdapter(IValue.class);
		// try {
		// for (IVariable v : node.getVariables()) {
		// os.add(factory.getVariable(v, this, n));
		// }
		// } catch (DebugException e) {
		// DebugVisualisationPlugin.getDefault().logError(e,
		// "Can't list variables");
		// }
		// return os;
		if (roots.contains(n))
			return edgeFrom.get(n);
		else
			return Collections.EMPTY_SET;
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (IRootControl.class.equals(adapter))
			return this;
		if (ILogicalStructureAdapter.class.equals(adapter))
			return this;
		if (IOpenCloseNodes.class.equals(adapter))
			return this;
		return null;
	}

	public void clearCache() {
		childrenCache.clear();
	}

	public ViewModelFactory getViewModelFactory() {
		return factory;
	}

	public void setRoots(Collection<IDVValue> nodes) {
		roots = new HashSet<IDVValue>(nodes);
		trigger(null);
	}

	/**
	 * Adds a variable with the given parent to the visualisation. The parent
	 * can be null (in case of newly added variables).
	 * 
	 * @param v
	 * @param parent
	 */
	public void addVariable(IVariable v, IDVValue parent) {
		IPropertyKey<OpenCloseNodeState> key = PropertyKeys.OPENED;
		try {
			IValue value = v.getValue();
			IDVValue dvValue = factory.getValue(value, this, v,
					factory.getVariable(v, this, parent));
			// dvValue.addParent(factory.getVariable(v));
			if (roots.contains(dvValue))
				return;
			if (parent == null
					&& (localContext == null || localContext.contains(dvValue))) {
				dvValue.setLocalContext();
				localContext.add(dvValue);
			}
			roots.add(dvValue);
			HashSet<IDVVariable> edgeSet = new HashSet<IDVVariable>();
			edgeFrom.put(dvValue, edgeSet);
			// Calculating open/close state
			Collection<IVariable> childVariables = getChildVariables(value);
			if (childVariables.isEmpty())
				dvValue.setProperty(key, OpenCloseNodeState.ChildLess);
			else
				dvValue.setProperty(key, OpenCloseNodeState.Close);
			// Adding required edges from new node
			for (IVariable referredVariable : childVariables) {
				IDVValue referredDVValue = factory.getValue(referredVariable
						.getValue());
				if (referredDVValue != null) {
					IDVVariable variable = factory.getVariable(
							referredVariable, this, dvValue);
					edgeSet.add(variable);
					referredDVValue.addParent(variable);
				}
			}
			// Adding required edges to new node
			for (IDVValue existingValue : roots) {
				for (IVariable variableTarget : getChildVariables(existingValue)) {
					if (value.equals(variableTarget.getValue())) {
						HashSet<IDVVariable> otherSet = edgeFrom
								.get(existingValue);
						otherSet.add(factory.getVariable(variableTarget, this,
								existingValue));
					}
				}

			}
		} catch (DebugException e) {
			DebugVisualisationPlugin
					.getDefault()
					.getLog()
					.log(new Status(IStatus.ERROR,
							DebugVisualisationPlugin.PLUGIN_ID, e.getMessage(),
							e));
		}
	}

	public void addVariables(Collection<IVariable> variables) {
		for (IVariable v : variables) {
			addVariable(v, null);
		}
		trigger(null);

	}

	public void showRoot() {
		try {
			localContext = new HashSet<IDVValue>();
			addVariables(Arrays.asList(sf.getVariables()));
		} catch (DebugException e) {
			DebugVisualisationPlugin
					.getDefault()
					.getLog()
					.log(new Status(IStatus.ERROR,
							DebugVisualisationPlugin.PLUGIN_ID, e.getMessage(),
							e));
		}
	}

	public void clearVisualization() {
		removeRoots((Collection<IDVValue>) roots.clone(), true);
		edgeFrom.clear();
		factory.clearModel();
		childrenCache.clear();
		trigger(null);

	}

	public void removeRoots(Collection<IDVValue> nodes) {
		removeRoots(nodes, true);
	}

	public void removeRoots(Collection<IDVValue> nodes, boolean removeChildren) {
		roots.removeAll(nodes);
		for (IDVValue node : nodes) {
			if (localContext.contains(node))
				localContext.remove(node);
			edgeFrom.remove(node);
			if (removeChildren)
				removeChildren(node);
			factory.finalize(node.getRelatedValue());
		}
		for (IDVValue node : roots) {
			HashSet<IDVVariable> edges = edgeFrom.get(node);
			Iterator<IDVVariable> edgeIterator = edges.iterator();
			while (edgeIterator.hasNext()) {
				IDVVariable variable = edgeIterator.next();
				IDVValue value = variable.getValue();
				factory.finalize(variable.getRelatedVariable());
				factory.finalize(value.getRelatedValue());
				if (!roots.contains(value)) {
					edgeIterator.remove();
				}
			}

		}

		trigger(null);

	}

	public void addChildren(IDVValue node) {
		for (IVariable v : getChildVariables(node.getRelatedValue())) {
			addVariable(v, node);
		}
		trigger(null);
	}

	public void addChildren(Collection<IDVValue> nodes) {
		for (IDVValue node : nodes) {
			addChildren(node);
		}
	}

	/**
	 * Removes all children nodes from the visualisation.
	 * 
	 * @param node
	 */
	public void removeChildren(IDVValue node) {
		try {
			ArrayList<IDVValue> valuesToRemove = new ArrayList<IDVValue>();
			for (IVariable variable : getChildVariables(node)) {
				IDVValue targetValue = factory.getValue(variable.getValue());
				// Handle non-existing nodes
				if (targetValue == null)
					continue;
				// Remove nodes
				if (targetValue.getAllParents().size() <= 1) {
					valuesToRemove.add(targetValue);
				} else {
					targetValue.removeParent(factory.getVariable(variable));
				}
			}
			removeChildren(valuesToRemove);
			removeRoots(valuesToRemove, true);
			childrenCache.remove(node.getRelatedValue());
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Removes all children nodes from the visualisation.
	 * 
	 * @param nodes
	 */
	public void removeChildren(Collection<IDVValue> nodes) {
		for (IDVValue node : nodes) {
			removeChildren(node);
		}
	}

	/**
	 * Returns the child variables of a value element. Filters (such as logical
	 * structures) are also applied here.
	 * 
	 * @param node
	 * @return the collection of reachable values
	 */
	public Collection<IVariable> getChildVariables(IDVValue node) {
		return getChildVariables(node.getRelatedValue());
	}

	/**
	 * Returns the child variables of a value element. Filters (such as logical
	 * structures) are also applied here. The method uses cache as the debug
	 * framework responds slowly.
	 * 
	 * @param value
	 * @return the collection of reachable values
	 */
	public Collection<IVariable> getChildVariables(IValue value) {
		if (childrenCache.contains(value)) {
			return childrenCache.get(value);
		}
		IDVValue node = factory.getValue(value);
		Collection<IVariable> children;
		if (node == null) {
			factory.getValue(value);
			children = Collections.EMPTY_SET;
		} else {
			ILogicalStructureType logicalStructureType;
			IPropertyKey<String> key = PropertyKeys.STRUCTURE_NAME;
			if (!node.isPropertySet(key)) {
				logicalStructureType = getLogicalStructureType(value, "");
			} else if (node.getProperty(key).contentEquals(
					DebugVisualisationPlugin.LOGICALSTRUCTURE_RAW_ID)) {
				logicalStructureType = null;
			} else {
				logicalStructureType = getLogicalStructureType(value,
						node.getProperty(key));
			}
			children = getChildVariables(value, logicalStructureType);
		}
		childrenCache.put(value, children);
		return children;

	}

	/**
	 * Returns the child variables of a value element. Filters (such as the
	 * selected logical structure) are also applied here. The method uses cache
	 * as the debug framework responds slowly.
	 * 
	 * @param value
	 * @param logicalStructureType
	 * @return the collection of reachable values
	 */
	public Collection<IVariable> getChildVariables(IValue value,
			ILogicalStructureType logicalStructureType) {
		if (childrenCache.containsKey(value)) {
			return childrenCache.get(value);
		}
		HashSet<IVariable> result = new HashSet<IVariable>();
		try {

			if (logicalStructureType == null) {
				return getRawChildren(value);
			}
			IVariable[] variables = logicalStructureType.getLogicalStructure(
					value).getVariables();
			result.addAll(Arrays.asList(variables));
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		childrenCache.put(value, result);
		return result;
	}

	private Collection<IVariable> getRawChildren(IValue value)
			throws DebugException {
		return new HashSet<IVariable>(Arrays.asList(value.getVariables()));
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

	public void setLogicalStructure(Collection<IDVValue> nodes,
			String logicalStructure) {
		for (IDVValue value : nodes) {
			removeChildren(value);
			value.setProperty(PropertyKeys.STRUCTURE_NAME, logicalStructure);
			addChildren(nodes);
		}
		clearCache();
		trigger(null);

	}

	public OpenCloseNodeState getNodeState(IDVValue node) {
		IPropertyKey<OpenCloseNodeState> key = PropertyKeys.OPENED;
		OpenCloseNodeState state = node.getProperty(key);
		return state == null ? OpenCloseNodeState.Close : state;
	}

	public void toggleOpenNode(IDVValue node) {
		IPropertyKey<OpenCloseNodeState> key = PropertyKeys.OPENED;
		OpenCloseNodeState state = node.getProperty(key);
		if (state == null || state == OpenCloseNodeState.Close) {
			// Open Node
			node.setProperty(key, OpenCloseNodeState.Open);
			addChildren(node);
			trigger(new OpenCloseStateChangedEvent(node));
		} else if (state == OpenCloseNodeState.Open) {
			// Close Node
			node.setProperty(key, OpenCloseNodeState.Close);
			removeChildren(node);
			trigger(new OpenCloseStateChangedEvent(node));
		}
	}
}
