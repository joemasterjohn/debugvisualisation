/**
 * 
 */
package hu.gbalage.debugvisualisation;

/**
 * This class stores constants about the extension points
 * defined in this plugin
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class ExtensionConstants {

	/**
	 * A layout algorithm extension defines a wrapper for a
	 * LayoutAlgorithm for the Zest framework. 
	 */
	public static final String EP_LayoutAlgorithm = "hu.gbalage.debugvisualisation.layoutalgorithm";
	
	public static final String EPA_Layout_id = "id";
	
	public static final String EPA_Layout_name = "name";
	
	public static final String EPA_Layout_class = "class";
	
	
	/**
	 * Filters are used to restructure variable tree in order
	 * to reduce complexity by hiding irrelevant information. 
	 */
	public static final String EP_Filter = "hu.gbalage.debugvisualisation.filters";
	
	/**
	 * The attribute name of the filter element, which defines the
	 * class of the filter. 
	 */
	public static final String EPA_Filter_class = "filter";
	
	/**
	 * The attribute name of the filter element, which defines the
	 * type name of the IValue, on which the filter can be applied.
	 */
	public static final String EPA_Filter_type = "use_on";
	
}
