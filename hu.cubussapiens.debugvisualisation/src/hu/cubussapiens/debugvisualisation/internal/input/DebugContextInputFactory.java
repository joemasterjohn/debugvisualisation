/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

/**
 * When something happens in the debug context (start, step, stop, etc..), an event is 
 * dispatched among its listeners which provides the currently selected IStackFrame. A
 * next event can step into another stack frame (with a method call), which has to be 
 * handled by another IDebugContextInput instance. Then after a method can return to a
 * previous stack frame, for which the previously used IDebugContextInput instance is
 * restored from a cache, which ensures that the same input object is used for the
 * same stack frame.
 * 
 * This enables us to save states (open/closed, visibility, etc..) of nodes displayed
 * for the user between stack changes. 
 *
 */
public class DebugContextInputFactory{
	
	private final Map<IStackFrame, IDebugContextInput> inputs = new HashMap<IStackFrame, IDebugContextInput>();
	
	public IDebugContextInput getInput(IStackFrame sf){
		if (sf == null){
			//System.err.println("Debug context is closed!");
			inputs.clear();
			return null;
		}else{
			if (!inputs.containsKey(sf))
				try {
					//System.err.println("Creating new IDebugContextInput");
					inputs.put(sf, new DebugContextInputWithNodeVisibility(sf));
				} catch (DebugException e) {
					e.printStackTrace();
					return null;
				}
				else {
					//System.err.println("Recalling IDebugContextInupt");
					try {
						inputs.get(sf).refresh();
					} catch (DebugException e) {
						e.printStackTrace();
					}
				}
			return inputs.get(sf);
		}
	}
	
}
