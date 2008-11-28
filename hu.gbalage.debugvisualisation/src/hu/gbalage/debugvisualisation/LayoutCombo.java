/**
 * 
 */
package hu.gbalage.debugvisualisation;

import hu.gbalage.debugvisualisation.layouts.LayoutManager;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public abstract class LayoutCombo{

	protected final Combo combo;
	
	protected final LayoutManager man;
	
	protected final String[] ids;
	
	protected final String[] names;
	
	public LayoutCombo(Composite parent, int style,LayoutManager man) {
		combo = new Combo(parent, style);
		this.man = man;
		
		ids = man.getLayouts();
		names = new String[ids.length];
		for(int i=0;i<ids.length;i++) 
			names[i] = man.getLayoutName(ids[i]);
		
		combo.setItems(names);
		combo.select(0);
		
		combo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				change(ids[combo.getSelectionIndex()]);
			}
		});
		
	}
	
	public abstract void change(String layoutid);

}
