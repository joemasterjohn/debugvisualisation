/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

/**
 * @author balage
 *
 */
public class DebugContextInputFactory{
	
	private final Map<IStackFrame, IDebugContextInput> inputs = new HashMap<IStackFrame, IDebugContextInput>();
	
	public IDebugContextInput getInput(IStackFrame sf){
		if (sf == null){
			System.err.println("Debug context is closed!");
			inputs.clear();
			return null;
		}else{
			if (!inputs.containsKey(sf))
				try {
					System.err.println("Creating new IDebugContextInput");
					inputs.put(sf, new DebugContextInputWithNodeVisibility(sf));
				} catch (DebugException e) {
					e.printStackTrace();
					return null;
				}
			return inputs.get(sf);
		}
	}
	
}
