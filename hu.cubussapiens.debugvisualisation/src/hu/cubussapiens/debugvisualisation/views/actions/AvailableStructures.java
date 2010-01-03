/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.DebugVisualisationPlugin;
import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;

import java.util.Hashtable;
import java.util.Map;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILogicalStructureType;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

/**
 *
 */
public class AvailableStructures extends CompoundContributionItem implements
		IWorkbenchContribution {

	private IServiceLocator locator;
	private static final String commandID = "hu.cubussapiens.debugvisualisation.availablelogicalstructures";
	private static final String parameterID = "hu.cubussapiens.debugvisualisation.logicalstructure";

	// private static final String parameterID = "structureid";

	/**
	 * 
	 */
	public AvailableStructures() {
		super();
	}

	/**
	 * @param id
	 */
	public AvailableStructures(String id) {
		super(id);
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IViewPart view = page.findView(DebugVisualisationPlugin.VIEW_ID);
		ISelection selection = view.getSite().getSelectionProvider()
				.getSelection();
		Object item = ((StructuredSelection) selection).getFirstElement();
		ILogicalStructureType[] structures = DebugPlugin
				.getLogicalStructureTypes(((IDVValue) item).getRelatedValue());
		IContributionItem[] list = new IContributionItem[structures.length + 1];
		CommandContributionItemParameter parameter = new CommandContributionItemParameter(
				locator, null, commandID, CommandContributionItem.STYLE_RADIO);
		parameter.label = "Raw structure";
		Map<String, String> parameters = new Hashtable<String, String>();
		
		parameters.put(parameterID,
				DebugVisualisationPlugin.LOGICALSTRUCTURE_RAW_ID);
		parameter.parameters = parameters;
		CommandContributionItem contribution = new CommandContributionItem(parameter);
		list[0] = contribution;

		for (int i = 0; i < structures.length; i++) {
			parameters.put(
					parameterID,
					structures[i].getId());
			parameter = new CommandContributionItemParameter(locator, null,
					commandID, CommandContributionItem.STYLE_RADIO);
			parameter.label = structures[i].getDescription();
			parameter.parameters = parameters;
			contribution = new CommandContributionItem(parameter);
			list[i + 1] = contribution;
		}
		return list;
	}

	public void initialize(IServiceLocator serviceLocator) {
		this.locator = serviceLocator;
	}
}
