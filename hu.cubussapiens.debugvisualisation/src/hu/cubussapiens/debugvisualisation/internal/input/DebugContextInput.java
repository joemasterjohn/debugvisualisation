/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import hu.cubussapiens.debugvisualisation.Activator;
import hu.cubussapiens.debugvisualisation.internal.MultiMap;
import hu.cubussapiens.debugvisualisation.internal.ValueUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * @author balage
 *
 */
abstract class DebugContextInput extends DebugContextInputListenerHandler{
	
	/**
	 * Contains the objects in the given context. Object is any IValue item, which has
	 * a unique id.
	 */
	protected final Map<Integer, IValue> objects = new HashMap<Integer, IValue>();
	
	/**
	 * Contains all IVariable items, which is a reference to the object with the given ID. 
	 */
	protected final MultiMap<Integer, IVariable> references = new MultiMap<Integer, IVariable>();
	
	/**
	 * Stores children of nodes
	 */
	protected final MultiMap<Integer, Integer> children = new MultiMap<Integer, Integer>();
	
	/**
	 * Stores relations
	 */
	protected final Map<Integer, MultiMap<Integer, IVariable>> relations = new HashMap<Integer, MultiMap<Integer,IVariable>>();
	
	protected void addRelation(Integer from, Integer to, IVariable rel){
		if (!relations.containsKey(from)) relations.put(from, new MultiMap<Integer, IVariable>());
		relations.get(from).add(to, rel);
	}
	
	public Set<IVariable> getRelations(Integer from, Integer to) {
		if (relations.containsKey(from)){
			MultiMap<Integer, IVariable> mm = relations.get(from);
			if (mm.containsKey(to)) return mm.get(to);
		}
		return new HashSet<IVariable>();
	}
	
	public IValue getValue(Integer node) {
		if (objects.containsKey(node)) return objects.get(node);
		return null;
	}
	
	/**
	 * ID of root node (local context)
	 */
	public final Integer root = -1;
	
	public Set<Integer> getChilds(Integer node){
		if (children.containsKey(node)) return children.get(node);
		return new HashSet<Integer>();
	}
	
	/**
	 * Registers var as a reference to the object identified by id
	 * @param id
	 * @param var
	 */
	private void addRef(Integer id, IVariable var){
		references.add(id, var);
	}
	
	private final IStackFrame stackframe;
	
	public DebugContextInput(IStackFrame sf) throws DebugException {
		stackframe = sf;
		refresh();
	}
	
	
	
	private void readVars(IVariable[] vars, Integer parent) throws DebugException{
		for(IVariable v : vars){
			IValue val = v.getValue();
			int id = ValueUtils.getID(val);
			if (id != -1){
				if (!objects.containsKey(id)) objects.put(id, val);
				addRef(id,v);
				addRelation(parent, id, v);
				children.add(parent, id);
				if (val.hasVariables())
					readVars(val.getVariables(), id);
			}
		}
	}
	
	/**
	 * Reload variables
	 * @throws DebugException 
	 */
	public void refresh() throws DebugException{
		//System.out.println("Refreshing.."+stackframe.getVariables().length);
		objects.clear();
		references.clear();
		children.clear();
		relations.clear();
		readVars(stackframe.getVariables(),root);
		onRefresh();
		trigger().refreshed();
	}
	
	protected abstract void onRefresh();
	
	public Set<IVariable> getReferencesForNode(Integer node) {
		if (references.containsKey(node)) return references.get(node);
		return new HashSet<IVariable>();
	}
	
	public boolean canOpen(Integer node) {
		if (objects.containsKey(node)){
			try {
				return objects.get(node).hasVariables();
			} catch (DebugException e) {
				ILog logger = Activator.getDefault().getLogger();
				IStatus status = new Status(IStatus.ERROR,Activator.PLUGIN_ID,-1,"Internal error in Debug Visualisation", e);
				logger.log(status);
			}
		}
		return false;
	}
	
	public String[] getParams(Integer node) {
		if (objects.containsKey(node)){
			try {
				ArrayList<String> params = new ArrayList<String>();
				for(IVariable var : objects.get(node).getVariables()){
					IValue v = var.getValue();
					if (ValueUtils.getID(v) == -1){
						params.add(var.getName()+": "+v.getValueString());
					}
				}
				return params.toArray(new String[0]);
			} catch (DebugException e) {
				ILog logger = Activator.getDefault().getLogger();
				IStatus status = new Status(IStatus.ERROR,Activator.PLUGIN_ID,-1,"Internal error in Debug Visualisation", e);
				logger.log(status);
			}
		}
		return null;
	}
	
}
