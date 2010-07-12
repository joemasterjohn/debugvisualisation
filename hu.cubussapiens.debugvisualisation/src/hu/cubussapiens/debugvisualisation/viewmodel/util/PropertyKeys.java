package hu.cubussapiens.debugvisualisation.viewmodel.util;

import hu.cubussapiens.debugvisualisation.viewmodel.IPropertyKey;
import hu.cubussapiens.debugvisualisation.viewmodel.OpenCloseNodeState;

/**
 * Helper class for storing the used property key constants.
 */
public class PropertyKeys {

	/**
	 * The string identifier used for the logical structure property.
	 */
	public static final String STRUCTUREPROPERTY_ID = "logicalstructure";
	/**
	 * The string identifier used for the opened state property.
	 */
	public static final String NODE_OPENED_ID = "opened";
	/**
	 * The property key constant usable to retrieve the selected logical
	 * structure parameter of a node.
	 */
	public static final IPropertyKey<String> STRUCTURE_NAME = new AbstractKey<String>(
			STRUCTUREPROPERTY_ID);
	/**
	 * The property key constant usable to retrieve the opened state of the
	 * selected node.
	 */
	public static final IPropertyKey<OpenCloseNodeState> OPENED = new AbstractKey<OpenCloseNodeState>(
			NODE_OPENED_ID);
}
