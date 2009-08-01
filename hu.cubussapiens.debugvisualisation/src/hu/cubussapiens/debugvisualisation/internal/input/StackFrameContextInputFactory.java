/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.model.IStackFrame;

/**
 * A factory to create and cache StackFrameContextInput instances
 */
public class StackFrameContextInputFactory {

	private final Map<IStackFrame, StackFrameContextInput> inputs = new HashMap<IStackFrame, StackFrameContextInput>();

	/**
	 * Call this method to get an StackFrameContextInput from an IStackFrame. If
	 * the given "sf" parameter is null, this method clears the cache, because a
	 * null stack frame means that the debug context is closed.
	 * 
	 * @param sf
	 * @return a cached or newly created StackFrameContextInput, which will be
	 *         an up-to-date representation of the given stack frame. This
	 *         method returns null if the parameter is null.
	 */
	public StackFrameContextInput getInput(IStackFrame sf) {
		if (sf == null) {
			// System.err.println("Debug context is closed!");
			inputs.clear();
			return null;
		} else {
			if (!inputs.containsKey(sf))
				inputs.put(sf, new StackFrameContextInput(sf));
			else {
				// this item does not need to be refreshed
			}
			return inputs.get(sf);
		}
	}

}
