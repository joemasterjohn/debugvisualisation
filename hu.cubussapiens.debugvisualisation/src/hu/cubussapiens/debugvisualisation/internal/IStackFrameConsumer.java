/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal;

import org.eclipse.debug.core.model.IStackFrame;

/**
 * An IStackFrameConsumer object makes use of an IStackFrame. Implementors of this
 * interface should use DebugContextListener to provide IStackFrame instances from
 * events of a debug context.
 *
 */
public interface IStackFrameConsumer {

	/**
	 * Give this consumer a new IStackFrame
	 * @param stackframe
	 */
	public void setStackFrame(IStackFrame stackframe);
	
}
