package hu.cubussapiens.debugvisualisation.views.handlers.filters;

import hu.cubussapiens.debugvisualisation.viewmodel.VisualisationSettings;
import hu.cubussapiens.debugvisualisation.viewmodel.filters.PrimitiveTypeFilter;


/**
 * 
 */

/**
 * Toggles to display of primitive types
 */
public class FilterPrimitiveTypesHandler extends ToggleFilterCommand {

	@Override
	PrimitiveTypeFilter getDefinedFilter() {
		return VisualisationSettings.primitiveTypeFilter;
	}

	@Override
	void setState(boolean newState) {
		VisualisationSettings.filterPrimitiveTypes = newState;
	}

}
