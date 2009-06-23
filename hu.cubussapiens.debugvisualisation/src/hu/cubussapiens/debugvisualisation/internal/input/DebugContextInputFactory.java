package hu.cubussapiens.debugvisualisation.internal.input;

import hu.cubussapiens.debugvisualisation.Activator;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

/**
 * When something happens in the debug context (start, step, stop, etc..), an
 * event is dispatched among its listeners which provides the currently selected
 * IStackFrame. A next event can step into another stack frame (with a method
 * call), which has to be handled by another IDebugContextInput instance. Then
 * after a method can return to a previous stack frame, for which the previously
 * used IDebugContextInput instance is restored from a cache, which ensures that
 * the same input object is used for the same stack frame. This enables us to
 * save states (open/closed, visibility, etc..) of nodes displayed for the user
 * between stack changes.
 */
public class DebugContextInputFactory {

	private final Map<IStackFrame, IDebugContextInput> inputs = new HashMap<IStackFrame, IDebugContextInput>();

	/**
	 * Call this method to get an IDebugContextInput from an IStackFrame. If the
	 * given "sf" parameter is null, this method clears the cache, because a
	 * null stack frame means that the debug context is closed.
	 * 
	 * @param sf
	 * @return a cached or newly created IDebugContextInput, which will be an
	 *         up-to-date representation of the given stack frame. This method
	 *         returns null if the parameter is null.
	 */
	public IDebugContextInput getInput(IStackFrame sf) {
		if (sf == null) {
			// System.err.println("Debug context is closed!");
			inputs.clear();
			return null;
		} else {
			if (!inputs.containsKey(sf))
				try {
					// System.err.println("Creating new IDebugContextInput");
					inputs.put(sf, new DebugContextInputWithNodeVisibility(sf));
				} catch (DebugException e) {
					Activator.getDefault().logError(e,
							"Exception on creating a DebugContextInput");
					return null;
				}
			else {
				// System.err.println("Recalling IDebugContextInupt");
				try {
					inputs.get(sf).refresh();
				} catch (DebugException e) {
					Activator.getDefault().logError(e,
							"Exception on refreshing the DebugContextInput");
				}
			}
			return inputs.get(sf);
		}
	}

}
