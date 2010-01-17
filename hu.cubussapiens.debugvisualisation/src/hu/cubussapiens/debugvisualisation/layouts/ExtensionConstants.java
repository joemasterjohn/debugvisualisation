package hu.cubussapiens.debugvisualisation.layouts;

/**
 * This class stores constants about the extension points defined in this plugin
 */
public class ExtensionConstants {

	/**
	 * A layout algorithm extension defines a wrapper for a LayoutAlgorithm for
	 * the Zest framework.
	 */
	public static final String EP_LayoutAlgorithm = "hu.cubussapiens.zestlayouts";

	/**
	 * Display name of a layout
	 */
	public static final String EPA_Layout_name = "name";

	/**
	 * Unique ID for a layout
	 */
	public static final String EPA_Layout_id = "id";

	/**
	 * Creator class of the layout algorithm
	 */
	public static final String EPA_Layout_class = "class";

}
