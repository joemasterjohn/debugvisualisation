/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

import org.eclipse.debug.core.model.IStackFrame;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface IStackFrameConsumer {

	public void setStackFrame(IStackFrame stackframe);
	
}
