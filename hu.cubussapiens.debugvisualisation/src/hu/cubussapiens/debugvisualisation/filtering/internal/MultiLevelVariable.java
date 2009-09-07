package hu.cubussapiens.debugvisualisation.filtering.internal;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * A virtual variable, which aggregates a path of variables
 */
public class MultiLevelVariable implements IVariable {

	final IVariable[] path;

	final IVariable last;

	final String s;

	/**
	 * @param path
	 * @param nameMask
	 * @throws DebugException
	 */
	public MultiLevelVariable(IVariable[] path, boolean[] nameMask)
			throws DebugException {
		this.path = path;
		last = path[path.length - 1];
		String t = "";
		for (int i = 0; i < path.length; i++)
			if (nameMask[i]) {
				IVariable v = path[i];
				t += v.getName();
			}
		s = t;
	}

	public String getName() throws DebugException {
		return s;
	}

	public String getReferenceTypeName() throws DebugException {
		return last.getReferenceTypeName();
	}

	public IValue getValue() throws DebugException {
		return last.getValue();
	}

	public boolean hasValueChanged() throws DebugException {
		return last.hasValueChanged();
	}

	public IDebugTarget getDebugTarget() {
		return last.getDebugTarget();
	}

	public ILaunch getLaunch() {
		return last.getLaunch();
	}

	public String getModelIdentifier() {
		return last.getModelIdentifier();
	}

	@SuppressWarnings("unchecked")
	// IVariable does not allow adding generic parameter to Class
	public Object getAdapter(Class arg0) {
		return last.getAdapter(arg0);
	}

	public void setValue(String arg0) throws DebugException {
		last.setValue(arg0);
	}

	public void setValue(IValue arg0) throws DebugException {
		last.setValue(arg0);
	}

	public boolean supportsValueModification() {
		return last.supportsValueModification();
	}

	public boolean verifyValue(String arg0) throws DebugException {
		return last.verifyValue(arg0);
	}

	public boolean verifyValue(IValue arg0) throws DebugException {
		return last.verifyValue(arg0);
	}

}
