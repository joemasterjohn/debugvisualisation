package hu.cubussapiens.debugvisualisation;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class DebugVisualisationPlugin extends AbstractUIPlugin {

	/**
	 * The plug-in ID
	 */
	public static final String PLUGIN_ID = "hu.cubussapiens.debugvisualisation";
	/**
	 * The view ID
	 */
	public static final String VIEW_ID = "hu.cubussapiens.debugvisualisation.views.DebugVisualisationView";
	public static final String LOGICALSTRUCTURE_RAW_ID = "hu.cubussapiens.debugvisualisation.rawstructure";

	// The shared instance
	private static DebugVisualisationPlugin plugin;

	private ILog log;

	/**
	 * The constructor
	 */
	public DebugVisualisationPlugin() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Bundle bundle = context.getBundle();
		plugin = this;
		log = Platform.getLog(bundle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		log = null;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static DebugVisualisationPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the logger interface of the bundle
	 * 
	 * @return the logger interface
	 */
	public ILog getLogger() {
		return log;
	}

	/**
	 * Logs an error message together with the an exception
	 * 
	 * @param e
	 *            an Exception to log
	 * @param message
	 *            the log message
	 */
	public void logError(Throwable e, String message) {
		logError(e, message, -1);
	}

	/**
	 * Logs an error message with an error code and an exception
	 * 
	 * @param e
	 *            an Exception to log
	 * @param message
	 *            the log message
	 * @param errorCode
	 *            an error code
	 */
	public void logError(Throwable e, String message, int errorCode) {
		Status status = new Status(IStatus.ERROR, PLUGIN_ID, errorCode,
				message, e);
		log.log(status);
	}

	// *************** Loading of images ************************* //

	/**
	 * Refresh icon
	 */
	public static final String icon_refresh = "/icons/view-refresh.gif";

	/**
	 * Icon for an open node
	 */
	public static final String icon_node_open = "/icons/node-open.gif";

	/**
	 * Icon for a closed node
	 */
	public static final String icon_node_closed = "/icons/node-closed.gif";

	/**
	 * Icon for layout selection
	 */
	public static final String icon_select_layout = "/icons/layout.gif";

	/**
	 * Icon for root node
	 */
	public static final String icon_root = "/icons/home_nav.gif";

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);
		reg.put(icon_refresh, ImageDescriptor.createFromURL(getBundle()
				.getEntry(icon_refresh)));
		reg.put(icon_root, ImageDescriptor.createFromURL(getBundle().getEntry(
				icon_root)));
		reg.put(icon_node_closed, ImageDescriptor.createFromURL(getBundle()
				.getEntry(icon_node_closed)));
		reg.put(icon_node_open, ImageDescriptor.createFromURL(getBundle()
				.getEntry(icon_node_open)));
		reg.put(icon_select_layout, ImageDescriptor.createFromURL(getBundle()
				.getEntry(icon_select_layout)));
	}

}
