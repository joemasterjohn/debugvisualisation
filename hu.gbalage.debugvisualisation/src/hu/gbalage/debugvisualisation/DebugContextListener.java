/**
 * 
 */
package hu.gbalage.debugvisualisation;

import hu.gbalage.debugvisualisation.model.IStackFrameConsumer;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class DebugContextListener implements IDebugContextListener {

	IStackFrameConsumer consumer;
	
	public DebugContextListener(IStackFrameConsumer consumer){
		this.consumer = consumer;
	}
	
	/**
	 * @see org.eclipse.debug.ui.contexts.IDebugContextListener#debugContextChanged(org.eclipse.debug.ui.contexts.DebugContextEvent)
	 */
	//@Override
	public void debugContextChanged(DebugContextEvent event) {
		if ((event.getFlags() & DebugContextEvent.ACTIVATED) > 0) {
			contextActivated(event.getContext());
		}

	}

	private void contextActivated(ISelection context) {
		if (context instanceof StructuredSelection){
			Object data = ((StructuredSelection) context).getFirstElement();
			if (data instanceof IStackFrame) {
				System.out.println("Yaaaay! We got IStackFrame! :)");
				consumer.setStackFrame((IStackFrame)data);
				try {
					IVariable[] variables = ((IStackFrame) data).getVariables();
					for(IVariable var : variables){
						System.out.println(var.getName()+" : "+var.getValue().getReferenceTypeName()+" = "+var.getValue().getValueString() + " - "+var.getValue().getModelIdentifier() );
					}
				} catch (DebugException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				consumer.setStackFrame(null);
			}
		}
	}

}
