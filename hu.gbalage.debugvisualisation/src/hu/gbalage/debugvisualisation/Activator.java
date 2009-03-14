package hu.gbalage.debugvisualisation;

import hu.gbalage.debugvisualisation.model.IStackFrameConsumer;

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin implements IStackFrameConsumer{

	// The plug-in ID
	public static final String PLUGIN_ID = "hu.gbalage.debugvisualisation";

	// The shared instance
	private static Activator plugin;
	
	private IStackFrame stackframe = null;
	
	private IDebugContextListener listener = null;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		listener = new DebugContextListener(this);
		
		DebugUITools.getDebugContextManager().
		addDebugContextListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		if (listener != null){
			DebugUITools.getDebugContextManager().
			removeDebugContextListener(listener);
		}
		
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public void setStackFrame(IStackFrame stackframe) {
		this.stackframe = stackframe;
	}
	
	public static IStackFrame getStackFrame(){
		return plugin.stackframe;
	}

}
