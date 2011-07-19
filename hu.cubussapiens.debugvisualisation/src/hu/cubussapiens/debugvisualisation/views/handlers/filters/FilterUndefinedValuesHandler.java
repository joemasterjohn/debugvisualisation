package hu.cubussapiens.debugvisualisation.views.handlers.filters;

import hu.cubussapiens.debugvisualisation.viewmodel.VisualisationSettings;

import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Toggles the display of undefined values
 */
public class FilterUndefinedValuesHandler extends ToggleFilterCommand {

	@Override
	ViewerFilter getDefinedFilter() {
		return VisualisationSettings.undefinedValuesFilter;
	}

	@Override
	void setState(boolean newState) {
		VisualisationSettings.filterUndefinedValues = newState;
	}
}
