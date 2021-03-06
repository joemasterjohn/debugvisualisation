package hu.cubussapiens.debugvisualisation.internal;

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * This object listens to changes in a debug context and extracts the
 * IStackFrame object from the events to provide it to the given
 * IStackFrameConsumer
 */
public class DebugContextListener implements IDebugContextListener {

	IStackFrameConsumer consumer;

	/**
	 * Creates a new DebugContextListener.
	 * 
	 * @param consumer
	 *            an IStackFrameConsumer to which the extracted stack frame
	 *            objects will be given.
	 */
	public DebugContextListener(IStackFrameConsumer consumer) {
		this.consumer = consumer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void debugContextChanged(DebugContextEvent event) {
		if ((event.getFlags() & DebugContextEvent.ACTIVATED) > 0) {
			contextActivated(event.getContext());
		}

	}

	private void contextActivated(ISelection context) {
		if (context instanceof StructuredSelection) {
			Object data = ((StructuredSelection) context).getFirstElement();
			if (data instanceof IStackFrame) {
				consumer.setStackFrame((IStackFrame) data);
			} else {
				consumer.setStackFrame(null);
			}
		}
	}

}
