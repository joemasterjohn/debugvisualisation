/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.ImagePool;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

/**
 * An Action with a dropdown for selecting from a list of layouts.
 */
public class SelectLayoutDropdown extends Action implements IMenuCreator {

	private Menu fMenu;
	private List<Action> layouts;

	/**
	 * Initializes a layout dropdown action component with a list of layout
	 * actions
	 * @param layouts the list of layout action
	 */
	public SelectLayoutDropdown(List<Action> layouts) {
		this.layouts = layouts;
		setText("Layout");
		setImageDescriptor(ImagePool.getInstance().getDescriptor(
				ImagePool.icon_select_layout));
		setMenuCreator(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.IMenuCreator#dispose()
	 */
	public void dispose() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets
	 * .Control)
	 */
	public Menu getMenu(Control parent) {
		if (fMenu != null) {
			fMenu.dispose();
		}
		fMenu = new Menu(parent);
		int accelerator = 0;
		for (Action element : layouts) {
			addActionToMenu(fMenu, element, accelerator);
		}
		return fMenu;
	}

	private void addActionToMenu(Menu parent, Action action, int accelerator) {
		ActionContributionItem item = new ActionContributionItem(action);
		item.fill(parent, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets
	 * .Menu)
	 */
	public Menu getMenu(Menu parent) {
		return null;
	}

}
