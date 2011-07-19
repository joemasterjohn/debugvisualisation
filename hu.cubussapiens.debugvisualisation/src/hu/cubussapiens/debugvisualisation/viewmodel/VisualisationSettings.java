/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel;

import hu.cubussapiens.debugvisualisation.viewmodel.filters.NullValuesFilter;
import hu.cubussapiens.debugvisualisation.viewmodel.filters.PrimitiveTypeFilter;

/**
 * Some settable parameters available throughout the plug-in
 */
public class VisualisationSettings {

	static public boolean trimLongNames = true;
	static public boolean filterPrimitiveTypes = false;
	static public boolean filterUndefinedValues = false;

	static public PrimitiveTypeFilter primitiveTypeFilter = new PrimitiveTypeFilter();
	static public NullValuesFilter undefinedValuesFilter = new NullValuesFilter();
}
