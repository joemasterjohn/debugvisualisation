package hu.cubussapiens.debugvisualisation.filtering;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 *
 */
public class CustomVariable implements IVariable {

	final IVariable[] path;

	CustomVariable(IVariable[] path) {
		this.path = path;
	}

	public String getName() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getReferenceTypeName() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	public IValue getValue() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasValueChanged() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	public IDebugTarget getDebugTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	public ILaunch getLaunch() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getModelIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	// IVariable does not allow adding generic parameter to Class
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValue(String arg0) throws DebugException {
		// TODO Auto-generated method stub

	}

	public void setValue(IValue arg0) throws DebugException {
		// TODO Auto-generated method stub

	}

	public boolean supportsValueModification() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean verifyValue(String arg0) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean verifyValue(IValue arg0) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

}
