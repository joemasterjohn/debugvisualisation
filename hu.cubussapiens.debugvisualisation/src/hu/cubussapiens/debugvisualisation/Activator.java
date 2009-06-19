package hu.cubussapiens.debugvisualisation;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	/**
	 *  The plug-in ID
	 */
	public static final String PLUGIN_ID = "com.googlecode.debugvisualization";

	// The shared instance
	private static Activator plugin;
	
	private ILog log;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Bundle bundle = context.getBundle();
		new ImagePool(bundle);
		plugin = this;
		log = Platform.getLog(bundle);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		ImagePool.getInstance().dispose();
		super.stop(context);
		log = null;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	/**
	 * Returns the logger interface of the bundle
	 * @return the logger interface
	 */
	public ILog getLogger() {
		return log;
	}

}
