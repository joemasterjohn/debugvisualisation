/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel;

import hu.cubussapiens.debugvisualisation.viewmodel.filters.PrimitiveTypeFilter;

/**
 * Some settable parameters available throughout the plug-in
 */
public class VisualisationSettings {

	static public boolean trimLongNames = true;
	static public boolean filterPrimitiveTypes = true;

	static public PrimitiveTypeFilter filter = new PrimitiveTypeFilter();
}
