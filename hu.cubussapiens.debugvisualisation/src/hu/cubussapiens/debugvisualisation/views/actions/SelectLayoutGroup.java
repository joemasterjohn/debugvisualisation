/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.zestlayouts.LayoutManager;
import hu.cubussapiens.zestlayouts.LayoutRegistry.LayoutEntry;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 *
 */
public class SelectLayoutGroup extends ActionGroup{

	private SelectLayoutDropdown dropdown;
	private List<Action> layouts;
	private SelectLayoutAction selectedLayout;
	
	public SelectLayoutGroup(LayoutManager manager, GraphViewer viewer) {
		layouts = new ArrayList<Action>();
		for (LayoutEntry entry : manager.getEntries()) {
			SelectLayoutAction layout = new SelectLayoutAction(entry, viewer, this);
			if (entry.equals(manager.getDefaultEntry())) {
				selectedLayout = layout;
				layout.setChecked(true);
				layout.run();
			}
			layouts.add(layout);
		}
	}
	
	public void selectLayout(SelectLayoutAction layout) {
		selectedLayout = layout;
	}
	
	public void applyLayout() {
		selectedLayout.run();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
	 */
	@Override
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);
		dropdown = new SelectLayoutDropdown(layouts);
		actionBars.getToolBarManager().add(dropdown);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		IMenuManager layoutMenu = new MenuManager("Select Layout");
		menu.add(layoutMenu);
		for (Action layout : layouts) {
			layoutMenu.add(layout);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#dispose()
	 */
	@Override
	public void dispose() {
		if (dropdown != null) {
			dropdown.dispose();
		}
		super.dispose();
	}
	
	
}
