/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal;

import org.eclipse.debug.core.model.IStackFrame;

/**
 * An IStackFrameConsumer object makes use of an IStackFrame
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface IStackFrameConsumer {

	/**
	 * Give this consumer a new IStackFrame
	 * @param stackframe
	 */
	public void setStackFrame(IStackFrame stackframe);
	
}
